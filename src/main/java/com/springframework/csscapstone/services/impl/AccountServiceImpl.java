package com.springframework.csscapstone.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.AccountSpecifications;
import com.springframework.csscapstone.data.dao.specifications.RoleSpecification;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.AccountImageType;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageEnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.config.constant.RegexConstant.ROLE_REGEX;
import static com.springframework.csscapstone.data.status.AccountImageType.*;
import static java.util.stream.Collectors.toList;

@Service
@PropertySource(value = "classpath:application-storage.properties")
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountImageRepository accountImageRepository;
    private final BlobUploadImages blobUploadImages;
    private final OrderRepository orderRepository;
    private final CampaignRepository campaignRepository;
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FirebaseAuth firebaseAuth;

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final RequestSellingProductRepository requestSellingProductRepository;

    @Value("${endpoint}")
    private String endpoint;

    @Value("${account_image_container}")
    private String accountContainer;

    @Override
    public Optional<CollaboratorWithQuantitySoldByCategoryDto> getCollaboratorWithPerformance(UUID uuid) {
        //check existed account:
        Account collaborator = this.accountRepository
                .findById(uuid)
                .filter(acc -> acc.getRole().getName().equals("Collaborator"))
                .orElseThrow(() -> new EntityNotFoundException("The collaborator with id: " + uuid + " was not found"));

        Map<String, Long> performance = this.orderRepository
                .getCollaboratorWithPerformanceWithId(uuid)
                .stream()
                .collect(Collectors.toMap(
                        tuple -> this.categoryRepository
                                .findById(tuple.get(OrderRepository.CATEGORY, UUID.class))
                                .map(Category::getCategoryName)
                                .orElse(""),
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
                .where(Objects.nonNull(name) ? AccountSpecifications.nameContains(name) : null)
                .and(StringUtils.isNotBlank(phone) ? AccountSpecifications.phoneEquals(phone) : null)
                .and(StringUtils.isNotBlank(email) ? AccountSpecifications.emailEquals(email) : null);

        pageNumber = Objects.nonNull(pageNumber) && (pageNumber >= 1) ? pageNumber : 1;
        pageSize = Objects.nonNull(pageSize) && (pageSize >= 1) ? pageSize : 10;

        Page<Account> page = this.accountRepository.findAll(specifications,
                PageRequest.of(pageNumber - 1, pageSize));

        List<AccountResDto> data = page.stream()
                .map(MapperDTO.INSTANCE::toAccountResDto)
                .collect(toList());

        return new PageImplResDto<>(data, page.getNumber() + 1, data.size(),
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
        Account result = accountRepository.findById(id).orElseThrow(handlerAccountNotFound());
        return MapperDTO.INSTANCE.toAccountResDto(result);
    }

    //    @Async
    private void saveAccountOnFirebase(String email, String phone) throws FirebaseAuthException {
        //check email or phone is null:
        if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(phone)) {
            this.accountRepository.findAccountByPhone(phone)
                    .ifPresent(acc -> {
                        throw new RuntimeException("Phone was duplicate");
                    });
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPhoneNumber(phone)
                    .setEmailVerified(true);
            FirebaseAuth.getInstance().createUser(createRequest);
            return;
        }
        throw new RuntimeException("Phone or Email must not be null");
    }

    /**
     * TODO <BR>
     *     Using By Admin creates Account
     *     Upload information into firebase
     *     Upload Image into Azure Storage
     *
     * @param dto
     * @return
     * @throws AccountExistException
     */
    @Transactional
    @Override
    public UUID createAccount(
            AccountCreatorReqDto dto, MultipartFile avatar,
            MultipartFile licenses, MultipartFile idCards)
            throws AccountExistException, FirebaseAuthException {

        String phone = "";

        //check email existed
        this.accountRepository.findAccountByEmail(dto.getEmail())
                .ifPresent(acc -> {
                    throw new RuntimeException("Duplication Email!!!");
                });

        //TODO check ROlE null <BUG>
        Specification<Role> where = Specification.where(RoleSpecification.equalNames(
                StringUtils.isEmpty(dto.getRole()) || !dto.getRole().matches(ROLE_REGEX) ? "Enterprise" : dto.getRole()));

        Role role = roleRepository.findOne(where).get();

        //set phone number follow pattern +23453
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            phone = "+84" + StringUtils.substring(dto.getPhone(), 1);
        }

        Account account = new Account()
                .setName(dto.getName()).setAddress(dto.getAddress())
                .setDob(dto.getDayOfBirth())
                .setPhone(phone)
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setDescription(dto.getDescription())
                .setPoint(0.0)
                .setGender(dto.getGender()).setRole(role);

        //save on firebase
        //create Thread handling this

        LOGGER.info("The email {}", account.getEmail());
        //separate to services
        saveAccountOnFirebase(account.getEmail(), phone);

        //save to get
        Account saved = accountRepository.save(account);
        LOGGER.info("This is phone {}", phone);

        //create Thread handling
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
                .collect(Collectors.toMap(
                        x -> nameImageOnAzure + x.getOriginalFilename(),
                        x -> x));

        //upload to Azure:
        imageMap.forEach(blobUploadImages::azureAccountStorageHandler);

        //Create save Account-Image
        return imageMap.keySet().stream()
                .map(imageName -> new AccountImage(type, endpoint + accountContainer + "/" + imageName))
                .peek(this.accountImageRepository::save)
                .findFirst();
    }

    /**
     * TODO Update Account for Collaborator
     *
     * @param dto
     * @return
     * @throws AccountInvalidException
     */
    @Transactional
    @Override
    public UUID updateAccount(AccountUpdaterJsonDto dto,
                              MultipartFile avatars,
                              MultipartFile licenses,
                              MultipartFile idCards)
            throws AccountInvalidException {
        //check exist entity
        Account entity = accountRepository.findById(dto.getId())
                .orElseThrow(handlerAccountInvalid());

        //update entity
        entity.setName(dto.getName())
                .setEmail(dto.getEmail())
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
    private Account imageUpdateHandler(MultipartFile avatars, MultipartFile licenses,
                                       MultipartFile idCards, Account entity) {

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
        if (Objects.nonNull(image)) {
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

            LOGGER.info("path image for update {}", path);
            return Optional.of(accountImage);
        }
        return Optional.empty();
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
    public PageEnterpriseResDto getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber <= 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= 1 ? 1 : pageSize;
        Page<Account> page = this.accountRepository
                .findAccountByRole("Enterprise", PageRequest.of(pageNumber - 1, pageSize));
        List<EnterpriseResDto> data = page.getContent().stream()
                .map(MapperDTO.INSTANCE::toEnterpriseResDto).collect(toList());
        return new PageEnterpriseResDto(data, page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
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

        if (Objects.isNull(idEnterprise)) throw handlerAccountNotFound().get();

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 1 : pageSize;
        //todo get all request selling

        //todo convert to Account response Dto
        Page<Account> page = requestSellingProductRepository
                .findAllCollaboratorByRequestSellingProduct(
                        idEnterprise, RequestStatus.REGISTERED,
                        PageRequest.of(pageNumber - 1, pageSize));

        List<AccountResDto> responseDto = page.getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toAccountResDto)
                .collect(toList());

        return new PageImplResDto<>(
                responseDto, page.getNumber() + 1,
                responseDto.size(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
//        return null;
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

        if (Objects.isNull(idEnterprise)) throw handlerAccountNotFound().get();

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 1 : pageSize;

        Page<Tuple> page = this.orderRepository
                .sortedPageCollaboratorByQuantitySelling(idEnterprise, PageRequest.of(pageNumber - 1, pageSize));

        List<CollaboratorResDto> result = page.getContent()
                .stream()
                .map(tuple -> this.accountRepository
                        .findById(tuple.get(OrderRepository.COLL_ID, UUID.class))
                        .map(peek(acc -> acc.setTotalQuantity(tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class))))
                        .map(CollaboratorResMapperDTO.INSTANCE::toCollaboratorResDto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(CollaboratorResDto::getTotalSold).reversed())
                .collect(toList());

        return new PageImplResDto<>(
                result, page.getNumber(), result.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }


    /**
     * todo list collaborator sort by quantity he sold
     *
     * @param campaign
     * @return
     */
    @Override
    public List<CollaboratorResDto> collaboratorMappingCampaign(UUID campaign) {
        Map<UUID, Long> collaboratorProduct = new HashMap<>();

        Campaign campaignEntity = this.campaignRepository
                .findById(campaign)
                .orElseThrow(handlerCampaignNotFoundException());

        List<UUID> productId = Stream.of(campaignEntity)
                .flatMap(_campaign -> _campaign.getProducts().stream())
                .map(Product::getId)
                .collect(toList());

        //todo throw product not found if list<UUID> is empty
        if (productId.isEmpty()) throw handlerProductNotFound().get();

        //todo check enterprise id is own product
        boolean checkConstraintAccount = productId.stream()
                .map(uuid -> this.productRepository.findById(uuid).orElseThrow(handlerDataTempException()))
                .noneMatch(product -> product.getAccount().getId().equals(campaignEntity.getAccount().getId()));

        if (checkConstraintAccount) throw handlerInvalidCampaignAndProduct().get();

        //todo main handle: case list uuid not empty
        for (UUID id : productId) {
            Map<UUID, Long> _tmp = this.orderRepository.getCollaboratorAndTotalQuantitySold(id)
                    .stream().collect(Collectors.toMap(
                            tuple -> tuple.get(OrderRepository.COLL_ID, UUID.class),
                            tuple -> tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class)));
            /**
             * todo check hashmap contains add or increase collaboratorProduct
             * todo need test
             */
            _tmp.forEach((key, value) -> collaboratorProduct
                    .compute(key, (k, v) -> v == null ? value : v + value));
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

    private Supplier<InvalidCampaignAndProductException> handlerInvalidCampaignAndProduct() {
        return () -> new InvalidCampaignAndProductException(MessagesUtils.getMessage(MessageConstant.DATA.PRODUCT_NOT_BELONG_ACCOUNT));
    }

    private Supplier<CampaignNotFoundException> handlerCampaignNotFoundException() {
        return () -> new CampaignNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }

    private Supplier<DataTempException> handlerDataTempException() {
        return () -> new DataTempException(MessagesUtils.getMessage(MessageConstant.DATA.INVALID));
    }

    private Supplier<ProductNotFoundException> handlerProductNotFound() {
        return () -> new ProductNotFoundException(MessagesUtils.getMessage(MessageConstant.Product.NOT_FOUND));
    }

    /**
     * Exception handler
     *
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
    private Consumer<Account> avatarConsumer(List<AccountImageBasicDto> collection, AccountImageType type) {
        return x -> collection.addAll(x.getImages().stream()
                .filter(image -> image.getType().equals(type))
                .map(MapperDTO.INSTANCE::toAccountImageResDto)
                .collect(toList()));
    }

    private <T> UnaryOperator<T> peek(Consumer<T> consumer) {
        return x -> {
            consumer.accept(x);
            return x;
        };
    }
}
