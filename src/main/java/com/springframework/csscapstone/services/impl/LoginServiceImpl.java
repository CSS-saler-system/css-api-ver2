package com.springframework.csscapstone.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.springframework.csscapstone.config.security.services.model.AppUserDetail;
import com.springframework.csscapstone.config.security.services.model.WebUserDetail;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountToken;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.AccountTokenRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private static final double DEFAULT_POINT = 0.0;

    private static final String COLLABORATOR_ROLE = "ROLE_3";

    private static final String TEST_STRING = "string";


    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final FirebaseAuth firebaseAuth;
    private final AccountRepository accountRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final Function<Account, AppUserDetail> getAccessTokenForCollaborator =
            acc -> new AppUserDetail(acc, getToken(acc, acc.getPhone()));

    private final Function<Account, WebUserDetail> getTokenForEnterprise =
            acc -> new WebUserDetail(acc, getToken(acc, acc.getEmail()));

    private final Supplier<RuntimeException> wierdError =
            () -> new RuntimeException("Something went wrong inside collaboratorLoginByFirebaseService");


    /**
     * case 1: user not in DB,
     * case 2: user in DB
     * Login By Enterprises
     *
     * @param firebaseToken
     * @return
     * @throws FirebaseAuthException
     * @throws AccountLoginWithEmailException
     */
    @Override
    public UserDetails enterpriseLoginByFirebaseService(String firebaseToken, String registrationToken)
            throws FirebaseAuthException, AccountLoginWithEmailException {

        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(firebaseToken);
        UserRecord _user = FirebaseAuth.getInstance().getUser(verifiedToken.getUid());
        String email = _user.getEmail();

        Optional<Account> accountByEmail = accountRepository.findAccountByEmail(email);

        if (accountByEmail.isPresent()) {

            Account account = accountByEmail.get();
            if (isNull(account.getRole())) {
                accountRepository.save(account.addRole(this.roleRepository.getById("ROLE_2")));
            }
            //todo save registration token
            if (StringUtils.isNotEmpty(registrationToken) || !registrationToken.equals("string")) {
                AccountToken token = new AccountToken(registrationToken);
                AccountToken savedToken = this.accountTokenRepository.save(token);
                account.addRegistration(savedToken);
            }
            System.out.println("The last one code of if block");
            System.out.println("This is account by emial: " + accountByEmail.get());
            return accountByEmail.map(getTokenForEnterprise).get();
        }

        Account account = new Account()
                .setEmail(email).setPoint(0.0)
                .setPhone("0000-0000-0000")
                .setIsActive(false);

        Account savedAccount = accountRepository.save(
                account
                .addRole(this.roleRepository.getById("ROLE_2")));
        System.out.println("The last one code");
        return new WebUserDetail(savedAccount, this.jwtTokenProvider.generateJwtTokenForCollaborator(
                account.getRole().getName(),
                account.getEmail()));
    }

    /**
     * Login By Collaborator:
     * @param firebaseToken
     * @return
     */
    @Transactional
    @Override
    public UserDetails collaboratorLoginByFirebaseService(String firebaseToken, String registrationToken) throws FirebaseAuthException {

        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(firebaseToken);
        UserRecord _user = FirebaseAuth.getInstance().getUser(verifiedToken.getUid());
        String phone = _user.getPhoneNumber();

        Optional<Account> accountByPhoneNumber = accountRepository.findAccountsByPhone(phone);

        //todo check present account
        if (accountByPhoneNumber.isPresent()) {
            String tmpToken = "";

            Account account = accountByPhoneNumber.get();

            List<AccountToken> accountTokenByAccount = this.accountTokenRepository.getAccountTokenByAccount(account.getId());

            if (nonNull(accountTokenByAccount) && !accountTokenByAccount.isEmpty()) {
                tmpToken = accountTokenByAccount.get(0).getRegistrationToken();
            }
            //todo save registration token
            if (StringUtils.isNotEmpty(registrationToken) &&
                            !registrationToken.equals(TEST_STRING) &&
                            !registrationToken.equals(tmpToken)) {
                AccountToken token = new AccountToken(registrationToken);
                account.addRegistration(token);
                this.accountTokenRepository.save(token);
            }

            return accountByPhoneNumber
                    .map(getAccessTokenForCollaborator)
                    .orElseThrow(wierdError);
        }
        Account account = new Account().setPhone(phone).setPoint(DEFAULT_POINT);

        account.addRole(this.roleRepository.getById(COLLABORATOR_ROLE));

        Account savedAccount = accountRepository.save(account);

        //todo save registration token
        if (StringUtils.isNotEmpty(registrationToken) &&
                !registrationToken.equals(TEST_STRING)) {
            AccountToken token = new AccountToken(registrationToken);
            account.addRegistration(token);
            this.accountTokenRepository.save(token);
        }

        //write account onto firebase database
        return new AppUserDetail(savedAccount, getToken(savedAccount, savedAccount.getPhone()));
    }

    private String getToken(Account acc, String signature) {
        return jwtTokenProvider.generateJwtTokenForCollaborator(acc.getRole().getName(), signature);
    }


}
