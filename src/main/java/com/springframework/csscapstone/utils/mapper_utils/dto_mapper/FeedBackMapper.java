package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.FeedBack;
import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackModeratorFeedbackDetailResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackPageModeraterResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.FeedBackCollaboratorListDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageEnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageForEnterpriseResDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FeedBackMapper {

    FeedBackMapper INSTANCE = Mappers.getMapper(FeedBackMapper.class);

    FeedBack feedBackCreatorReqDtoToFeedBack(FeedBackCreatorReqDto feedBackCreatorReqDto);

    FeedBackCreatorReqDto feedBackToFeedBackCreatorReqDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackCreatorReqDto(FeedBackCreatorReqDto feedBackCreatorReqDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackPageModeraterResDtoToFeedBack(FeedBackPageModeraterResDto feedBackPageModeraterResDto);

    FeedBackPageModeraterResDto feedBackToFeedBackPageModeraterResDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackPageModeraterResDto(FeedBackPageModeraterResDto feedBackPageModeraterResDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackEnterpriseDetailResDtoToFeedBack(FeedBackEnterpriseDetailResDto feedBackEnterpriseDetailResDto);

    FeedBackEnterpriseDetailResDto feedBackToFeedBackEnterpriseDetailResDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackEnterpriseDetailResDto(FeedBackEnterpriseDetailResDto feedBackEnterpriseDetailResDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackModeratorFeedbackDetailResDtoToFeedBack(FeedBackModeratorFeedbackDetailResDto feedBackModeratorFeedbackDetailResDto);

    FeedBackModeratorFeedbackDetailResDto feedBackToFeedBackModeratorFeedbackDetailResDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackModeratorFeedbackDetailResDto(FeedBackModeratorFeedbackDetailResDto feedBackModeratorFeedbackDetailResDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackPageForEnterpriseResDtoToFeedBack(FeedBackPageForEnterpriseResDto feedBackPageForEnterpriseResDto);

    FeedBackPageForEnterpriseResDto feedBackToFeedBackPageForEnterpriseResDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackPageForEnterpriseResDto(FeedBackPageForEnterpriseResDto feedBackPageForEnterpriseResDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackPageEnterpriseResDtoToFeedBack(FeedBackPageEnterpriseResDto feedBackPageEnterpriseResDto);

    FeedBackPageEnterpriseResDto feedBackToFeedBackPageEnterpriseResDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackPageEnterpriseResDto(FeedBackPageEnterpriseResDto feedBackPageEnterpriseResDto, @MappingTarget FeedBack feedBack);

    FeedBack feedBackCollaboratorListDtoToFeedBack(FeedBackCollaboratorListDto feedBackCollaboratorListDto);

    FeedBackCollaboratorListDto feedBackToFeedBackCollaboratorListDto(FeedBack feedBack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedBack updateFeedBackFromFeedBackCollaboratorListDto(FeedBackCollaboratorListDto feedBackCollaboratorListDto, @MappingTarget FeedBack feedBack);
}
