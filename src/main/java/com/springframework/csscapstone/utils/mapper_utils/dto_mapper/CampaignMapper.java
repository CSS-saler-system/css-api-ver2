package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.payload.response_dto.collaborator.CampaignForCollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignDetailDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CampaignMapper {
    CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

    Campaign CampaignForCollaboratorResDtoToCampaign(CampaignForCollaboratorResDto campaignForCollaboratorResDto);

    CampaignForCollaboratorResDto campaignToCampaignForCollaboratorResDto(Campaign campaign);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Campaign updateCampaignFromCampaignForCollaboratorResDto(CampaignForCollaboratorResDto campaignForCollaboratorResDto, @MappingTarget Campaign campaign);

    @AfterMapping
    default void linkImage(@MappingTarget Campaign campaign) {
        campaign.getImage().forEach(image -> image.setCampaign(campaign));
    }

    @Mapping(target = "campaignId", source = "id")
    CampaignResDto toCampaignResDto(Campaign entity);


    @Mapping(target = "prizes", source = "prizes")
    @Mapping(target = "products", source = "products")
    CampaignDetailDto toCampaignDetailDto(Campaign entity);

//    Campaign campaignForCollaboratorResDtoToCampaign(com.springframework.csscapstone.data.domain.CampaignForCollaboratorResDto campaignForCollaboratorResDto);
//
//    CampaignForCollaboratorResDto campaignToCampaignForCollaboratorResDto(Campaign campaign);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Campaign updateCampaignFromCampaignForCollaboratorResDto(CampaignForCollaboratorResDto campaignForCollaboratorResDto, @MappingTarget Campaign campaign);
//
//    @AfterMapping
//    default void linkImage(@MappingTarget Campaign campaign) {
//        campaign.getImage().forEach(image -> image.setCampaign(campaign));
//    }

}
