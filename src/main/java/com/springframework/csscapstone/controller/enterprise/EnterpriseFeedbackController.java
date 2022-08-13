package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageEnterpriseResDto;
import com.springframework.csscapstone.services.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.FeedBack.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Feedback (Enterprise)")
@RequiredArgsConstructor
@RestController
public class EnterpriseFeedbackController {

    private final FeedBackService feedBackService;

    @PostMapping(V2_FEEDBACK_CREATE)
    public ResponseEntity<?> createfeedBack(@RequestBody FeedBackCreatorReqDto dto) {
        return ok(this.feedBackService.createFeedBack(dto));
    }

    @GetMapping(V2_FEEDBACK_DETAIL)
    public ResponseEntity<?> getDetailFeedBack(@PathVariable("feedbackId") UUID feedbackId) {
        FeedBackEnterpriseDetailResDto res = this.feedBackService.getFeedbackDetailForEnterprise(feedbackId);
        return ok(res);
    }

    @GetMapping(V2_FEEDBACK_PAGE)
    public ResponseEntity<?> getPageFeedBack(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
            ) {
        PageImplResDto<FeedBackPageEnterpriseResDto> res = this.feedBackService
                .getPageFeedBackForEnterprise(pageSize, pageNumber);
        return ok(res);
    }


}
