package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.payload.queries.NumberProductOrderedQueryDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperQueriesDTO {
    MapperQueriesDTO INSTANCE = Mappers.getMapper(MapperQueriesDTO.class);

    @Mapping(target = "quantity", source = "sumQuantity")
    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "shortDescription", source = "product.shortDescription")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "pointSale", source = "product.pointSale")
    @Mapping(target = "productStatus", source = "product.productStatus")
    @Mapping(target = "image", source = "product.image")
    ProductCountOrderResDto toQueriesProductDto(NumberProductOrderedQueryDto dto);

}
