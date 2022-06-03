package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.payload.queries.QueriesProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperQueriesDto {
    MapperQueriesDto INSTANCE = Mappers.getMapper(MapperQueriesDto.class);

//    QueriesProductDto toQueriesProductDto()

}
