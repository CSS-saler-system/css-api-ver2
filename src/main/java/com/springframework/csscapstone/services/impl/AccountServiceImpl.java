package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.config.firebase_config.FirebaseAuthService;
import com.springframework.csscapstone.data.dao.specifications.AccountSpecifications;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.AccountImageType;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.AccountCollaboratorUpdaterDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.EnterpriseSignUpDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorWithQuantitySoldByCategoryDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.InvalidCampaignAndProductException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.data_exception.DataTempException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.AccountMapper;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.CollaboratorResMapperDTO;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.data.status.AccountImageType.*;
import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionCatchHandler.peek;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Service
@PropertySource(value = "classpath:application-storage.properties")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final String prefixPhoneNumber = "+84";
    private static final String ENTERPRISE_ROLE = "Enterprise";
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int SHIFT_TO_ACTUAL_PAGE = 1;
    private final AccountRepository accountRepository;
    private final AccountImageRepository accountImageRepository;
    private final BlobUploadImages blobUploadImages;
    private final OrderRepository orderRepository;
    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FirebaseAuthService firebaseAuthService;

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final RequestSellingProductRepository requestSellingProductRepository;

    @Value("${endpoint}")
    private String endpoint;

    @Value("${account_image_container}")
    private String accountContainer;

    private final Predicate<Account> isCollaborator = acc -> acc.getRole().getName().equals("Collaborator");

    private final Function<UUID, Supplier<EntityNotFoundException>> entityNotFoundExceptionFunction =
            (id) -> () -> new EntityNotFoundException("The collaborator with id: " + id + " was not found");

    private final Consumer<Account> duplicateEmailExceptionConsumer = acc -> {
        throw new RuntimeException("Duplication Email!!!");
    };

    private final Supplier<InvalidCampaignAndProductException> handlerInvalidCampaignAndProduct =
            () -> new InvalidCampaignAndProductException(MessagesUtils.getMessage(MessageConstant.DATA.PRODUCT_NOT_BELONG_ACCOUNT));

    private final Supplier<CampaignNotFoundException> handlerCampaignNotFoundException =
            () -> new CampaignNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));


    private final Supplier<DataTempException> handlerDataTempException =
            () -> new DataTempException(MessagesUtils.getMessage(MessageConstant.DATA.INVALID));


    private final Supplier<ProductNotFoundException> handlerProductNotFound =
            () -> new ProductNotFoundException(MessagesUtils.getMessage(MessageConstant.Product.NOT_FOUND));
    /**
     * Exception handler
     */
    private final Supplier<AccountInvalidException> handlerAccountInvalid =
            () -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.INVALID));

    private final Supplier<EntityNotFoundException> handlerAccountNotFound =
            () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    //    private final Supplier<Role> getDefaultRoleSupplier = () -> new Role("ROLE_3", "Collaborator");
    private final Function<String, Consumer<Account>> duplicationEmailException = email -> {
        throw new RuntimeException("The email: " + email + " was existed!!!");
    };
    private final Function<String, Consumer<Account>> duplicationPhoneException = email -> {
        throw new RuntimeException("The email: " + email + " was existed!!!");
    };

    /**
     * todo Get Collaborator With performance skill selling
     *
     * @param uuid
     * @return
     */
    @Override
    public Optional<CollaboratorWithQuantitySoldByCategoryDto> getCollaboratorWithPerformance(UUID uuid) {

        Account collaborator = this.accountRepository
                .findById(uuid)
                .filter(isCollaborator)
                .orElseThrow(entityNotFoundExceptionFunction.apply(uuid));

        Map<String, Long> performance = this.orderRepository
                .getCollaboratorWithPerformanceById(uuid)
                .stream()
                .collect(Collectors.toMap(
                        tuple -> this.categoryRepository
                                .findById(tuple.get(OrderRepository.CATEGORY, UUID.class))
                                .map(Category::getCategoryName)
                                .orElse("Other"),
                        tuple -> tuple.get(OrderRepository.QUANTITY_SOLD, Long.class)
                ));
        Account account = collaborator.setPercentSoldByCategory(performance);
        return Optional.of(MapperDTO.INSTANCE.toCollaboratorWithQuantitySoldByCategoryDto(account));
    }

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
    public PageImplResDto<AccountResDto> getAccountDto(
            String name, String phone, String email, String address,
            Integer pageSize, Integer pageNumber) {

        Specification<Account> specifications = Specification
                .where(nonNull(name) ? AccountSpecifications.nameContains(name) : null)
                .and(StringUtils.isNotBlank(phone) ? AccountSpecifications.phoneEquals(phone) : null)
                .and(StringUtils.isNotBlank(email) ? AccountSpecifications.emailEquals(email) : null);


        pageNumber = nonNull(pageNumber) && (pageNumber >= DEFAULT_PAGE_NUMBER) ? pageNumber : DEFAULT_PAGE_NUMBER;
        pageSize = nonNull(pageSize) && (pageSize >= DEFAULT_PAGE_SIZE) ? pageSize : DEFAULT_PAGE_SIZE;


        Page<Account> page = this.accountRepository.findAll(specifications,
                PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize));

        List<AccountResDto> data = page.stream()
                .map(AccountMapper.INSTANCE::toAccountResDto)
                .collect(toList());

        return new PageImplResDto<>(
                data, page.getNumber() + SHIFT_TO_ACTUAL_PAGE, data.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }


    /**
     * TODO Admin and user get Profiles
     *
     * @param id
     * @return
     * @throws AccountInvalidException
     */
    @Override
    public AccountResDto getById(UUID id) throws AccountInvalidException {
        return accountRepository
                .findById(id)
                .map(AccountMapper.INSTANCE::toAccountResDto)
                .orElseThrow(handlerAccountNotFound);
    }

    /**
     * TODO <BR>
     *     Using By Admin creates Account
     *     Upload information into firebase
     *     Upload Image into Azure Storage
     *
     * @param reqDto
     * @return
     * @throws AccountExistException
     */
    @Transactional
    @Override
    public UUID createEnterpriseAccount(
            AccountCreatorReqDto reqDto, MultipartFile avatar,
            MultipartFile licenses, MultipartFile idCards)
            throws AccountExistException {

        if (Objects.isNull(reqDto.getEmail()) || Objects.isNull(reqDto.getPhone())) {
            throw new RuntimeException("The email or phone was null!!!");
        }

        this.accountRepository
                .findAccountByEmail(reqDto.getEmail())
                .ifPresent(duplicateEmailExceptionConsumer);

        Account account = AccountMapper.INSTANCE.accountReqDtoToAccount(reqDto);

        String phone = formatPhone(reqDto);

        firebaseAuthService.saveAccountOnFirebase(account.getEmail(), phone);
//
        Account saved = accountRepository.save(account);
//
        Account completedAccount = accountSaveImages(avatar, licenses, idCards, saved);

        return this.accountRepository.save(completedAccount).getId();
    }

    /**
     * TODO Update Account for Collaborator
     *
     * @param reqUpdateDto
     * @return
     * @throws AccountInvalidException
     */
    @Transactional
    @Override
    public UUID updateAccount(UUID accountId, AccountUpdaterJsonDto reqUpdateDto,
                              MultipartFile avatars,
                              MultipartFile licenses,
                              MultipartFile idCards) throws AccountInvalidException {

        //check exist entity
        Account entity = accountRepository
                .findById(accountId)
                .orElseThrow(handlerAccountInvalid);

        AccountMapper.INSTANCE.updateAccountFromAccountUpdaterJsonDto(reqUpdateDto, entity);

        //override image in database
        Account account = imageUpdateHandler(avatars, licenses, idCards, entity);

        this.accountRepository.save(account);
        return entity.getId();
    }

    /**
     * todo for admin disable account
     *
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
    public PageImplResDto<AccountResDto> getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber <= DEFAULT_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<Account> page = this.accountRepository
                .findAllEnterprise(PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize));
//
        List<AccountResDto> data = page
                .getContent()
                .stream()
                .map(AccountMapper.INSTANCE::toAccountResDto)
                .collect(toList());

        return new PageImplResDto<>(
                data, page.getNumber() + SHIFT_TO_ACTUAL_PAGE, data.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    /**
     * TODO Changing BUG
     * TODO Get Collaborator by join Request and Account
     *
     * @param idEnterprise
     * @return
     */
    @Override
    public PageImplResDto<AccountResDto> getAllCollaboratorsOfEnterprise(
            UUID idEnterprise, Integer pageNumber, Integer pageSize) {

        if (Objects.isNull(idEnterprise)) handlerAccountNotFound.get();

        pageNumber = Objects.isNull(pageNumber) || pageNumber < DEFAULT_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;

        //todo convert to Account response Dto
        Page<Account> page = requestSellingProductRepository
                .findAllCollaboratorByRequestSellingProduct(
                        idEnterprise, RequestStatus.REGISTERED,
                        PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize));

        List<AccountResDto> responseDto = page.getContent()
                .stream()
                .map(AccountMapper.INSTANCE::toAccountResDto)
                .collect(toList());

        return new PageImplResDto<>(
                responseDto, page.getNumber() + SHIFT_TO_ACTUAL_PAGE,
                responseDto.size(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }

    /**
     * todo get collaborators have order selling product
     *
     * @param idEnterprise
     * @return
     */
    @Override
    public PageImplResDto<CollaboratorResDto> collaboratorsByEnterpriseIncludeNumberOfQuantitySold(
            UUID idEnterprise, Integer pageNumber, Integer pageSize) {

        if (Objects.isNull(idEnterprise)) handlerAccountNotFound.get();

        pageNumber = Objects.isNull(pageNumber) || pageNumber < DEFAULT_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<Tuple> collaboratorSortBySellingPage = this.orderRepository
                .sortedPageCollaboratorByQuantitySelling(
                        idEnterprise, PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize));

        List<CollaboratorResDto> collaboratorsRes = collaboratorSortBySellingPage.getContent()
                .stream()
                .map(tuple -> this.accountRepository
                        .findById(tuple.get(OrderRepository.COLLABORATOR_IDS, UUID.class))
                        .map(peek(acc -> acc.setTotalQuantity(tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class))))
                        .map(CollaboratorResMapperDTO.INSTANCE::toCollaboratorResDto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(CollaboratorResDto::getTotalSold).reversed())
                .collect(toList());

        return new PageImplResDto<>(
                collaboratorsRes, collaboratorSortBySellingPage.getNumber(), collaboratorsRes.size(),
                collaboratorSortBySellingPage.getTotalElements(), collaboratorSortBySellingPage.getTotalPages(),
                collaboratorSortBySellingPage.isFirst(), collaboratorSortBySellingPage.isLast());
    }


    /**
     * todo list collaborator sort by quantity he sold
     *
     * @param campaign
     * @return
     */
    @Override
    public List<CollaboratorResDto> awardCollaboratorByCampaign(UUID campaign) {
        Map<UUID, Long> collaboratorProduct = new HashMap<>();

        Campaign campaignEntity = this.campaignRepository
                .findById(campaign)
                .orElseThrow(handlerCampaignNotFoundException);

        List<UUID> productIds = Stream.of(campaignEntity)
                .flatMap(camp -> camp.getProducts().stream())
                .map(Product::getId)
                .collect(toList());

        //todo throw product not found if list<UUID> is empty
        if (productIds.isEmpty()) handlerProductNotFound.get();

        //todo check enterprise is own product
        boolean checkConstraintProdWithEnterprise = productIds.stream()
                .map(uuid -> this.productRepository.findById(uuid).orElseThrow(handlerDataTempException))
                .noneMatch(product -> product.getAccount().getId().equals(campaignEntity.getAccount().getId()));

        if (checkConstraintProdWithEnterprise) handlerInvalidCampaignAndProduct.get();

        //todo main handle: case list uuid not empty
        for (UUID id : productIds) {
            Map<UUID, Long> tmp = this.orderRepository.getCollaboratorAndTotalQuantitySold(id)
                    .stream().collect(Collectors.toMap(
                            tuple -> tuple.get(OrderRepository.COLLABORATOR_IDS, UUID.class),
                            tuple -> tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class)));
            /**
             * todo check hashmap contains add or increase collaboratorProduct
             * todo need test
             */
            tmp.forEach((key, value) -> collaboratorProduct.compute(key, (k, v) -> v == null ? value : v + value));
        }
        return collaboratorProduct
                .entrySet().stream()
                .map(entry -> CollaboratorResMapperDTO.INSTANCE
                        .toCollaboratorResDto(this.accountRepository.findById(entry.getKey())
                                .map(peek(acc -> acc.setTotalQuantity(entry.getValue())))
                                .orElse(null)))
                .sorted(Comparator.comparing(CollaboratorResDto::getTotalSold).reversed())
                .collect(toList());
    }

    /**
     * create account<BR>
     * </>
     *
     * @param enterprise
     * @return
     */
    @Override
    public Optional<UUID> singUpEnterprise(EnterpriseSignUpDto enterprise) {
        Account account = AccountMapper.INSTANCE.enterpriseSignUpDtoToAccount(enterprise);

        this.accountRepository
                .findAccountByEmail(account.getEmail())
                .ifPresent(duplicationEmailException.apply(account.getEmail()));

        this.accountRepository.findAccountByPhone(account.getPhone())
                .ifPresent(duplicationPhoneException.apply(account.getPhone()));

        Account savedAccount = this.accountRepository.save(account);

        return Optional.of(savedAccount.getId());
    }

    @Override
    public Optional<AccountResDto> getProfile(UUID accountId) {
        Optional<AccountResDto> result = this.accountRepository
                .findById(accountId)
                .map(AccountMapper.INSTANCE::toAccountResDto);
        return result;
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
    private Account imageUpdateHandler(MultipartFile avatars, MultipartFile licenses,
                                       MultipartFile idCards, Account entity) {

        /**
         * updateImage(avatars, entity, AVATAR)
         *                 .map(peek(entity::addImage))
         *                 .ifPresent(this.accountImageRepository::save);
         */

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
        });

        return entity;
    }

    private Optional<AccountImage> updateImage(MultipartFile image, Account entity, AccountImageType type) {
        String imageOriginalPath = entity.getId() + "/";

        if (nonNull(image)) {
            AccountImage accountImage = this.accountImageRepository
                    .findByAccountAndType(entity.getId(), type)
                    .orElse(new AccountImage());

            //Map<name, multiple-file>
            Map<String, MultipartFile> imageMap = Stream.of(image)
                    .collect(Collectors.toMap(x -> imageOriginalPath + x.getOriginalFilename(), x -> x));

            //upload to Azure:
            imageMap.forEach(blobUploadImages::azureAccountStorageHandler);

            //update database:
            String path = endpoint + accountContainer + "/" + imageOriginalPath + image.getOriginalFilename();
            accountImage.setPath(path).setAccount(entity).setType(type);

            return Optional.of(accountImage);
        }
        return Optional.empty();
    }


    private String formatPhone(AccountCreatorReqDto dto) {
        String phone = "";
        //set phone number follow pattern +23453
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            phone = prefixPhoneNumber + StringUtils.substring(dto.getPhone(), 1);
        }
        return phone;
    }

    //todo mapping to account
    private Account accountSaveImages(
            MultipartFile avatar, MultipartFile licenses,
            MultipartFile idCards, Account account) {

        if (nonNull(avatar)) {
            saveAccountImageEntity(avatar, account.getId(), AccountImageType.AVATAR)
                    .ifPresent(account::addImage);
        }
        if (nonNull(licenses)) {
            saveAccountImageEntity(idCards, account.getId(), AccountImageType.ID_CARD)
                    .ifPresent(account::addImage);
        }
        if (nonNull(idCards)) {
            saveAccountImageEntity(licenses, account.getId(), AccountImageType.LICENSE)
                    .ifPresent(account::addImage);
        }

        return account;
    }

    /**
     * todo Create save Account-Image
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
        imageMap.forEach(blobUploadImages::azureAccountStorageHandler);

        //Create save Account-Image
        return imageMap.keySet().stream()
                .map(imageName -> new AccountImage(type, endpoint + accountContainer + "/" + imageName))
                .peek(this.accountImageRepository::save)
                .findFirst();
    }

    @Override
    public UUID updateCollaboratorProfiles(UUID collaboratorId, AccountCollaboratorUpdaterDto accountUpdaterJsonDto, MultipartFile avatar) {
        Account collaborator = this.accountRepository.findById(collaboratorId)
                .filter(acc -> acc.getRole().getName().equals("Collaborator"))
                .orElseThrow(() -> new RuntimeException("The collaborator with id: " + collaboratorId + " was not found"));

        Account account = AccountMapper.INSTANCE.updateAccountFromAccountCollaboratorUpdaterDto(accountUpdaterJsonDto, collaborator);
        if (nonNull(avatar)) {
            Optional<AccountImage> accountImage = this.saveAccountImageEntity(avatar, collaboratorId, AVATAR);
            accountImage.ifPresent(account::addImage);
        }
        return this.accountRepository.save(account).getId();

    }
}
