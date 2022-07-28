package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.PrizeSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.PrizeRepository;
import com.springframework.csscapstone.data.status.PrizeStatus;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorVer2ReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.prize_exception.PrizeJsonBadException;
import com.springframework.csscapstone.utils.exception_utils.prize_exception.PrizeNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.PrizeMapper;
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

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@PropertySource(value = "classpath:application-storage.properties")
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {
    private final PrizeRepository prizeRepository;
    private final AccountRepository accountRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final Supplier<PrizeNotFoundException> handlerPrizeNotFound =
            () -> new PrizeNotFoundException(MessagesUtils.getMessage(MessageConstant.Prize.NOT_FOUND));

    private final Supplier<PrizeJsonBadException> handlerBadRequestException = () -> new PrizeJsonBadException(MessagesUtils.getMessage(MessageConstant.Prize.BAD_JSON));


    private final Function<UUID, Supplier<RuntimeException>> runtimeExceptionSupplier =
            (id) -> () -> new RuntimeException("The Prize with id: " + id + " not found");

    @Override
    public PageImplResDto<PrizeResDto> getAll(UUID enterpriseId, String name, Integer pageNumber, Integer pageSize) {

        Account enterprise = this.accountRepository.findById(enterpriseId)
                .orElseThrow(() -> new EntityNotFoundException("The enterprise with id: " + enterpriseId + " not found"));

        Specification<Prize> prizeSpecifications = Specification
                .where(StringUtils.isEmpty(name) ? null : PrizeSpecifications.containsName(name))
                .and(PrizeSpecifications.belongEnterpriseId(enterprise))
                .and(PrizeSpecifications.excludeDisableStatus());

        pageNumber = nonNull(pageNumber) && pageNumber > 1 ? pageNumber : 1;
        pageSize = nonNull(pageSize) && pageSize > 1 ? pageSize : 10;

        Page<Prize> result = this.prizeRepository
                .findAll(prizeSpecifications, PageRequest.of(pageNumber - 1, pageSize));
        LOGGER.info("This is logger {}", result.getContent().size());
        List<PrizeResDto> prizes = result.getContent()
                .stream()
                .map(PrizeMapper.INSTANCE::toPrizeResDto)
                .collect(Collectors.toList());


        LOGGER.info("This is logger of prize size {}", prizes.size());
        prizes.forEach(System.out::println);

        return new PageImplResDto<>(prizes, result.getNumber() + 1, prizes.size(),
                result.getTotalElements(), result.getTotalPages(),
                result.isFirst(), result.isLast());
    }

    //todo get prize by id
    @Override
    public Optional<PrizeResDto> getPrizeByPrize(UUID uuid) {
        return this.prizeRepository.findById(uuid)
                .map(PrizeMapper.INSTANCE::toPrizeResDto);
    }


    @Transactional
    @Override
    public UUID updatePrize(PrizeUpdaterReqDto prizeUpdater) {

        if (Objects.isNull(prizeUpdater.getId())) throw handlerBadRequestException.get();

        Prize prize = this.prizeRepository.findById(prizeUpdater.getId()).orElseThrow(handlerPrizeNotFound);

        prize
                .setName(prizeUpdater.getName())
                .setPrice(prizeUpdater.getPrice());
        return this.prizeRepository.save(prize).getId();
    }

    @Transactional
    @Override
    public UUID createPrize(PrizeCreatorVer2ReqDto prizeCreatorReqDto) {
        Account account = this.accountRepository.findById(prizeCreatorReqDto.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("The enterprise creates prize not found"));

        Prize prize = new Prize()
                .setName(prizeCreatorReqDto.getName())
                .setPrizeStatus(PrizeStatus.ACTIVE)
                .setPrice(prizeCreatorReqDto.getPrice());

        account.addCreatorPrize(prize);

        return this.prizeRepository.save(prize).getId();
    }

    @Transactional
    @Override
    public void deletePrizeById(UUID prizeId) {
        prizeRepository.findById(prizeId)
                .map(prize -> prize.setPrizeStatus(PrizeStatus.DISABLE))
                .orElseThrow(runtimeExceptionSupplier.apply(prizeId));
    }
}
