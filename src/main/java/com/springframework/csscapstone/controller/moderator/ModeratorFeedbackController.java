package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackModeratorFeedbackDetailResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackPageModeraterResDto;
import com.springframework.csscapstone.services.FeedBackService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.FeedBack.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Feedback (Moderator)")
@RestController
@RequiredArgsConstructor
public class ModeratorFeedbackController {
    private final FeedBackService feedBackService;

    @GetMapping(V4_FEEDBACK_PAGE)
    public ResponseEntity<?> pageFeedback(
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        PageImplResDto<FeedBackPageModeraterResDto> res =
                this.feedBackService.getPageFeedBackForModerator(pageSize, pageNumber);
        return ok(res);
    }

    @PutMapping(V4_FEEDBACK_REPLY + "/{feedbackId}")
    public ResponseEntity<?> replyFeedback(@PathVariable("feedbackId") UUID feedbackId, @RequestParam("replyContent") String replyContent) {
        this.feedBackService.replyFeedBack(feedbackId, replyContent);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    @GetMapping(V4_FEEDBACK_DETAIL + "/{feedbackId}")
    public ResponseEntity<?> getDetailFeedback(@PathVariable("feedbackId") UUID feedbackId) {
        FeedBackModeratorFeedbackDetailResDto res = this.feedBackService.getFeedbackDetailForModerator(feedbackId);
        return ok(res);
    }

}
