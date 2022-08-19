package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.FeedBackCollaboratorListDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.services.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.FeedBack.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Feedback (Collaborator)")
@RestController
@RequiredArgsConstructor
public class CollaboratorFeedBackController {

    private final FeedBackService feedBackService;

    @PostMapping(V3_FEEDBACK_CREATE)
    public ResponseEntity<?> createFeedBack(@RequestBody FeedBackCreatorReqDto dto) {
        UUID feedBack = this.feedBackService.createFeedBack(dto);
        return ok(feedBack);
    }

    @GetMapping(V3_FEEDBACK_DETAIL + "/{feedbackId}")
    public ResponseEntity<?> getDetailFeedBack(@PathVariable("feedbackId") UUID feedbackId) {
        FeedBackEnterpriseDetailResDto res = this.feedBackService.getFeedbackDetailForEnterprise(feedbackId);
        return ok(res);
    }

    @GetMapping(V3_FEEDBACK_LIST + "/{collaboratorId}")
    public ResponseEntity<?> getListFeedback(@PathVariable("collaboratorId") UUID collaboratorId) {
        List<FeedBackCollaboratorListDto> results = this.feedBackService.getAllListFeedBackCollaborator(collaboratorId);
        return ok(results);
    }

}
