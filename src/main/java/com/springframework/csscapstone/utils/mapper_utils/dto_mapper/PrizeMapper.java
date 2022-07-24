package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorVer2ReqDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PrizeMapper {
    Prize prizeCreatorVer2ReqDtoToPrize(PrizeCreatorVer2ReqDto prizeCreatorVer2ReqDto);

    PrizeCreatorVer2ReqDto prizeToPrizeCreatorVer2ReqDto(Prize prize);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Prize updatePrizeFromPrizeCreatorVer2ReqDto(PrizeCreatorVer2ReqDto prizeCreatorVer2ReqDto, @MappingTarget Prize prize);
}
