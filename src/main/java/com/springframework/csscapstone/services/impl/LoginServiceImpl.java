package com.springframework.csscapstone.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.security.services.model.AppUserDetail;
import com.springframework.csscapstone.config.security.services.model.WebUserDetail;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final FirebaseAuth firebaseAuth;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
    public UserDetails enterpriseLoginByFirebaseService(String firebaseToken)
            throws FirebaseAuthException, AccountLoginWithEmailException {

        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(firebaseToken);
        UserRecord _user = FirebaseAuth.getInstance().getUser(verifiedToken.getUid());
        String email = _user.getEmail();

        Optional<Account> accountByEmail = accountRepository.findAccountByEmail(email);
        if (accountByEmail.isPresent()) {
            return accountByEmail.map(account -> new WebUserDetail(account,
                    this.jwtTokenProvider
                            .generateJwtTokenForCollaborator(account.getRole().getName(),
                            account.getEmail())))
                    .get();
        }
        Account account = new Account().setEmail(email);
        account.addRole(this.roleRepository.getById("ROLE_2"));
        Account savedAccount = accountRepository.save(account);

        return new WebUserDetail(savedAccount, this.jwtTokenProvider.generateJwtTokenForCollaborator(
                account.getRole().getName(),
                account.getEmail()));
    }

    /**
     * Login By Collaborator:
     *
     * @param firebaseToken
     * @return
     */
    @Transactional
    @Override
    public UserDetails collaboratorLoginByFirebaseService(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(firebaseToken);
        UserRecord _user = FirebaseAuth.getInstance().getUser(verifiedToken.getUid());
        String phone = _user.getPhoneNumber();

        Optional<Account> accountByPhoneNumber = accountRepository.findAccountsByPhone(phone);
        if (accountByPhoneNumber.isPresent()) {
            return accountByPhoneNumber.map(account -> new AppUserDetail(account, this.jwtTokenProvider
                            .generateJwtTokenForCollaborator(account.getRole().getName(), account.getPhone()))).get();
        }
        Account account = new Account().setPhone(phone);
        account.addRole(this.roleRepository.getById("ROLE_3"));
        Account savedAccount = accountRepository.save(account);

        return new AppUserDetail(savedAccount, jwtTokenProvider.generateJwtTokenForCollaborator(
                        savedAccount.getRole().getName(),
                        savedAccount.getPhone()));
    }

    private AccountLoginWithEmailException getAccountLoginWithEmailException() {
        return new AccountLoginWithEmailException(MessagesUtils.getMessage(MessageConstant.Account.FAIL_LOGIN_EMAIL));
    }
}
