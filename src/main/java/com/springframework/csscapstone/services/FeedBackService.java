package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.FeedbackStatus;
import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackModeratorFeedbackDetailResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackPageModeraterResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.FeedBackCollaboratorListDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageEnterpriseResDto;

import java.util.List;
import java.util.UUID;

public interface FeedBackService {

    UUID createFeedBack(FeedBackCreatorReqDto dto);

    /**
     * todo auto set status
     * reply modified
     */
    void replyFeedBack(UUID feedbackId, String content);

    /**
     * todo sort create date
     * @return
     */
    PageImplResDto<FeedBackPageModeraterResDto> getPageFeedBackForModerator(Integer pageSize, Integer pageNumber);

    /**
     * datail for enterprise
     */
    FeedBackEnterpriseDetailResDto getFeedbackDetailForEnterprise(UUID id);

    /**
     * datail for moderator
     */
    FeedBackModeratorFeedbackDetailResDto getFeedbackDetailForModerator(UUID id);

    PageImplResDto<FeedBackPageEnterpriseResDto> getPageFeedBackForEnterprise(
            UUID enterpriseId, FeedbackStatus status, Integer pageSize, Integer pageNumber);

    List<FeedBackCollaboratorListDto> getAllListFeedBackCollaborator(UUID collaboratorId);

}
