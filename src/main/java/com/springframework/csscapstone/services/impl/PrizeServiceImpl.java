package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.dao.specifications.PrizeSpecifications;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.repositories.PrizeRepository;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {

    private final PrizeRepository prizeRepository;

    @Override
    public PageImplResDto<PrizeResDto> getAll(String name, Integer pageNumber, Integer pageSize) {

        Specification<Prize> prizeSpeicfications = Specification
                .where(Objects.isNull(name) ? null : PrizeSpecifications.containsName(name));

        pageNumber = Objects.nonNull(pageNumber) && pageNumber > 1 ? pageSize : 10;
        pageSize = Objects.nonNull(pageSize) && pageSize > 1 ? pageSize : 10;

        Page<Prize> result = this.prizeRepository.findAll(prizeSpeicfications, PageRequest.of(pageNumber - 1, pageSize));
        List<PrizeResDto> prizes = result.getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toPrizeResDto)
                .collect(Collectors.toList());
        return new PageImplResDto<>(prizes, result.getNumber(), prizes.size(),
                result.getTotalElements(), result.getTotalPages(),
                result.isFirst(), result.isLast());
    }

    @Override
    public PageImplResDto<PrizeResDto> getAll(Integer pageNumber, Integer pageSize) {
        return null;
    }
}
