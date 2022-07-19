package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.payload.response_dto.collaborator.RequestSellingProductForCollResDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestSellingProductMapper {

    RequestSellingProductMapper INSTANCE = Mappers.getMapper(RequestSellingProductMapper.class);

    RequestSellingProduct requestSellingProductForCollResDtoToRequestSellingProduct(RequestSellingProductForCollResDto requestSellingProductForCollResDto);

    RequestSellingProductForCollResDto requestSellingProductToRequestSellingProductForCollResDto(RequestSellingProduct requestSellingProduct);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RequestSellingProduct updateRequestSellingProductFromRequestSellingProductForCollResDto(RequestSellingProductForCollResDto requestSellingProductForCollResDto, @MappingTarget RequestSellingProduct requestSellingProduct);
}
