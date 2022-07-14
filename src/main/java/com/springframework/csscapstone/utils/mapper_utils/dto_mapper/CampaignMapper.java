package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.payload.response_dto.collaborator.CampaignResDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CampaignMapper {
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    Campaign campaignResDtoToCampaign(CampaignResDto campaignResDto);

    CampaignResDto campaignToCampaignResDto(Campaign campaign);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Campaign updateCampaignFromCampaignResDto(CampaignResDto campaignResDto, @MappingTarget Campaign campaign);

    @AfterMapping
    default void linkImage(@MappingTarget Campaign campaign) {
        campaign.getImage().forEach(image -> image.setCampaign(campaign));
    }
}
