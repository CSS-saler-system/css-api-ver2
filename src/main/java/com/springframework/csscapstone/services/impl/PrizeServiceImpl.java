package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.PrizeSpecifications;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.repositories.PrizeRepository;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.exception_utils.prize_exception.PrizeJsonBadException;
import com.springframework.csscapstone.utils.exception_utils.prize_exception.PrizeNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {

    private final PrizeRepository prizeRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public PageImplResDto<PrizeResDto> getAll(String name, Integer pageNumber, Integer pageSize) {

        Specification<Prize> prizeSpecifications = Specification
                .where(StringUtils.isEmpty(name) ? null : PrizeSpecifications.containsName(name));

        pageNumber = Objects.nonNull(pageNumber) && pageNumber > 1 ? pageSize : 1; //default pagenumber 1
        pageSize = Objects.nonNull(pageSize) && pageSize > 1 ? pageSize : 10;

        Page<Prize> result = this.prizeRepository.findAll(prizeSpecifications, PageRequest.of(pageNumber - 1, pageSize));
        LOGGER.info("This is logger {}", result.getContent().size()); //0
        List<PrizeResDto> prizes = result.getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toPrizeResDto)
                .collect(Collectors.toList());


        LOGGER.info("This is logger of prize size {}", prizes.size());
        prizes.forEach(System.out::println);

        return new PageImplResDto<>(prizes, result.getNumber(), prizes.size(),
                result.getTotalElements(), result.getTotalPages(),
                result.isFirst(), result.isLast());
    }

    @Override
    public UUID updatePrize(PrizeUpdaterReqDto prizeUpdater) {
        if (Objects.isNull(prizeUpdater.getId())) throw handlerBadRequestException().get();
        Prize prize = this.prizeRepository.findById(prizeUpdater.getId()).orElseThrow(handlerPrizeNotFound());

        prize.setName(prizeUpdater.getName())
                .setDescription(prizeUpdater.getDescription())
                .setPrizeStatus(prizeUpdater.getStatus())
                .setPrice(prizeUpdater.getPrice())
                .setQuantity(prizeUpdater.getQuantity());

        //update image override images

        return this.prizeRepository.save(prize).getId();
    }

    private Supplier<PrizeNotFoundException> handlerPrizeNotFound() {
        return () -> new PrizeNotFoundException(MessagesUtils.getMessage(MessageConstant.Prize.NOT_FOUND));
    }

    private Supplier<PrizeJsonBadException> handlerBadRequestException() {
        return () -> new PrizeJsonBadException(MessagesUtils.getMessage(MessageConstant.Prize.BAD_JSON));
    }

    @Override
    public UUID createPrize(PrizeCreatorReqDto prizeCreatorReqDto) {
        return null;
    }
}
