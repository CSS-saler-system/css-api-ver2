package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.payload.queries.ProductQueriesResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperProductQueriesDto {
    MapperProductQueriesDto INSTANCE = Mappers.getMapper(MapperProductQueriesDto.class);

    @Mapping(target = "quantitySale", source = "quantity")
    ProductQueriesResDto toProductQueriesDto(Product entity, Long quantity);


}
