package com.springframework.csscapstone.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.AccountSpecifications;
import com.springframework.csscapstone.data.dao.specifications.RoleSpecification;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.repositories.AccountImageRepository;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
import com.springframework.csscapstone.data.status.AccountImageType;
import com.springframework.csscapstone.payload.basic.AccountImageDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.response_dto.PageEnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.config.constant.RegexConstant.REGEX_ROLE;
import static com.springframework.csscapstone.data.status.AccountImageType.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-storage.properties")
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountImageRepository accountImageRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);


    @Value("${endpoint}")
    private String endpoint;

    @Value("${account_image_container}")
    private static String accountContainer;

    /**
     * TODO Admin get All Account
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public PageAccountDto getAccountDto(String name, String phone, String email, String address, Integer pageSize, Integer pageNumber) {
        Specification<Account> specifications = Specification
                .where(Objects.nonNull(name) ? AccountSpecifications.nameContains(name) : null)
                .and(StringUtils.isNotBlank(phone) ? AccountSpecifications.phoneEquals(phone) : null)
                .and(StringUtils.isNotBlank(email) ? AccountSpecifications.emailEquals(email) : null);
        pageNumber = Objects.nonNull(pageNumber) && (pageNumber >= 1) ? pageNumber : 1;

        List<AccountImageDto> avatar = new ArrayList<>();
        List<AccountImageDto> licenses = new ArrayList<>();
        List<AccountImageDto> idCard = new ArrayList<>();

        Page<Account> page = this.accountRepository.findAll(specifications, PageRequest.of(pageNumber - 1, pageSize));
        List<AccountResponseDto> data = page.stream()
                .peek(avatarConsumer(avatar, AccountImageType.AVATAR))
                .peek(avatarConsumer(licenses, AccountImageType.LICENSE))
                .peek(avatarConsumer(idCard, AccountImageType.ID_CARD))
                .map(MapperDTO.INSTANCE::toAccountResponseDto)
                .peek(dto -> dto.setAvatar(avatar))
                .peek(dto -> dto.setLicenses(licenses))
                .peek(dto -> dto.setIdCard(idCard))
                .collect(toList());

        return new PageAccountDto(data, page.getNumber() + 1, data.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    /**
     * TODO Admin and user get Profiles
     *
     * @param id
     * @return
     * @throws AccountInvalidException
     * @throws AccountNotFoundException
     */
    @Override
    public AccountResponseDto getById(UUID id) throws AccountInvalidException, AccountNotFoundException {
        Account result = accountRepository.findById(id).orElseThrow(handlerAccountNotFound());

        List<AccountImageDto> avatar = result.getImages().stream()
                .filter(x -> x.getType().equals(AccountImageType.AVATAR))
                .map(MapperDTO.INSTANCE::toAccountImageDto)
                .collect(toList());

        List<AccountImageDto> licenses = result.getImages().stream()
                .filter(x -> x.getType().equals(AccountImageType.LICENSE))
                .map(MapperDTO.INSTANCE::toAccountImageDto)
                .collect(toList());

        List<AccountImageDto> idCard = result.getImages().stream()
                .filter(x -> x.getType().equals(AccountImageType.ID_CARD))
                .map(MapperDTO.INSTANCE::toAccountImageDto)
                .collect(toList());
        AccountResponseDto dto = new AccountResponseDto(
                result.getId(), result.getName(), result.getDob(), result.getPhone(), result.getEmail(), result.getAddress(),
                result.getDescription(), result.getGender(), result.getPoint(),
                MapperDTO.INSTANCE.toRoleDto(result.getRole()));
        dto.setAvatar(avatar);
        dto.setLicenses(licenses);
        dto.setIdCard(idCard);
        return dto;

    }

    /**
     * TODO Using By Admin creates Account
     *
     * @param dto
     * @return
     * @throws AccountExistException
     */
    @Transactional
    @Override
    public UUID createAccount(AccountCreatorDto dto, MultipartFile avatar, MultipartFile licenses, MultipartFile idCards)
            throws AccountExistException {

        //TODO check ROlE null <BUG>
        Specification
                .where(RoleSpecification.equalNames(StringUtils.isEmpty(dto.getRole()) || !dto.getRole().matches(REGEX_ROLE) ? "Collaborator" : dto.getRole()));
        Role role = roleRepository
                .findAllByName(dto.getRole()).get();

        Account account = new Account()
                .setName(dto.getName()).setAddress(dto.getAddress())
                .setDob(dto.getDayOfBirth())
                .setPhone(dto.getPhone())
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setDescription(dto.getDescription())
                .setGender(dto.getGender()).setRole(role);
        Account saved = accountRepository.save(account);

        Account _account = imageHandler(avatar, licenses, idCards, saved);


        return this.accountRepository.save(_account).getId();
    }

    //todo mapping to account
    private Account imageHandler(MultipartFile avatar, MultipartFile licenses, MultipartFile idCards, Account account) {
        if (Objects.nonNull(avatar)) {
            saveAccountImageEntity(avatar, account.getId(), AccountImageType.AVATAR)
                    .ifPresent(account::addImage);
        }
        if (Objects.nonNull(licenses)) {
            saveAccountImageEntity(idCards, account.getId(), AccountImageType.ID_CARD)
                    .ifPresent(account::addImage);
        }
        if (Objects.nonNull(idCards)) {
            saveAccountImageEntity(licenses, account.getId(), AccountImageType.LICENSE)
                    .ifPresent(account::addImage);
        }

        return account;
    }

    /**
     * //todo Create save Account-Image
     *
     * @param images
     * @return
     */
    private Optional<AccountImage> saveAccountImageEntity(MultipartFile images, UUID id, AccountImageType type) {

        String nameImageOnAzure = id + "/";

        //Map<name, multiple-file>
        Map<String, MultipartFile> imageMap = Stream.of(images)
                .collect(Collectors.toMap(x -> nameImageOnAzure + x.getOriginalFilename(), x -> x));

        //upload to Azure:
        imageMap.forEach(BlobUploadImages::azureAccountStorageHandler);

        //Create save Account-Image
        return imageMap.keySet().stream()
                .map(imageName -> new AccountImage(type,
                        endpoint + this.accountContainer + "/" + imageName))
                .peek(this.accountImageRepository::save)
                .findFirst();
    }

    /**
     * TODO Update Account No Images
     *
     * @param dto
     * @return
     * @throws AccountInvalidException
     */
    @Transactional
    @Override
    public UUID updateAccount(AccountUpdaterDto dto,
                              MultipartFile avatars,
                              MultipartFile licenses,
                              MultipartFile idCards) throws AccountInvalidException {
        //check exist entity
        Account entity = accountRepository.findById(dto.getId())
                .orElseThrow(handlerAccountInvalid());

        //update entity
        entity.setName(dto.getName())
                .setEmail(dto.getEmail())
                .setPhone(dto.getPhone())
                .setDob(dto.getDob())
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.getGender());

        //override image in database
        Account account = imageUpdateHandler(avatars, licenses, idCards, entity);

        this.accountRepository.save(account);
        return entity.getId();
    }

    /**
     * todo create AccountImage
     *
     * @param avatars
     * @param licenses
     * @param idCards
     * @param entity
     * @return
     */
    private Account imageUpdateHandler(
            MultipartFile avatars,
            MultipartFile licenses,
            MultipartFile idCards,
            Account entity) {

        //load image from database
        updateImage(avatars, entity, AVATAR).ifPresent(image -> {
            this.accountImageRepository.save(image);
            entity.addImage(image);
        });
        updateImage(licenses, entity, LICENSE).ifPresent(image -> {
            this.accountImageRepository.save(image);
            entity.addImage(image);
        });
        updateImage(idCards, entity, ID_CARD).ifPresent(image -> {
            this.accountImageRepository.save(image);
            entity.addImage(image);
        });;

        return entity;
    }

    private Optional<AccountImage> updateImage(MultipartFile image, Account entity, AccountImageType type) {
        String imageOriginalPath = entity.getId() + "/";
        if (Objects.nonNull(image)) {
            AccountImage accountImage = this
                    .accountImageRepository
                    .findByAccountAndType(entity.getId(), type)
                    .orElse(new AccountImage());

            //Map<name, multiple-file>
            Map<String, MultipartFile> imageMap = Stream.of(image)
                    .collect(Collectors.toMap(x -> imageOriginalPath + x.getOriginalFilename(), x -> x));

            //upload to Azure:
            imageMap.forEach(BlobUploadImages::azureAccountStorageHandler);

            //update database:
            String path = endpoint + accountContainer + "/" + imageOriginalPath + image.getOriginalFilename();
            accountImage.setPath(path).setAccount(entity).setType(type);

            LOGGER.info("path image for update {}", path);
            return Optional.of(accountImage);
        }
        return Optional.empty();
    }

    /**
     * todo for admin disable account
     * @param id
     */
    @Transactional
    @Override
    public void disableAccount(UUID id) {
        accountRepository.findById(id).ifPresent(x -> {
            x.setIsActive(false);
            accountRepository.save(x);
        });
    }

    //todo for collaborator
    @Override
    public PageEnterpriseDto getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber <= 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= 1 ? 1 : pageSize;
        Page<Account> page = this.accountRepository.findAccountByRole("Enterprise", PageRequest.of(pageNumber - 1, pageSize));
        List<EnterpriseResponseDto> data = page.getContent().stream().map(MapperDTO.INSTANCE::toEnterpriseResponseDto).collect(toList());
        return new PageEnterpriseDto(data, page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    /**
     * Exception handler
     * @return
     */
    private Supplier<AccountInvalidException> handlerAccountInvalid() {
        return () -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.INVALID));
    }

    private Supplier<EntityNotFoundException> handlerAccountNotFound() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }

    /**
     * todo Consume image of Account
     *
     * @param collection
     * @param type
     * @return
     */
    private Consumer<Account> avatarConsumer(List<AccountImageDto> collection, AccountImageType type) {
        return x -> collection.addAll(x.getImages().stream()
                .filter(image -> image.getType().equals(type))
                .map(MapperDTO.INSTANCE::toAccountImageDto)
                .collect(toList()));
    }

}
