package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.domain.FeedBack;
import com.springframework.csscapstone.data.repositories.FeedBackRepository;
import com.springframework.csscapstone.data.status.FeedbackStatus;
import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackModeratorFeedbackDetailResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackPageModeraterResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageEnterpriseResDto;
import com.springframework.csscapstone.services.FeedBackService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.FeedBackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedBackRepository;

    @Override
    public UUID createFeedBack(FeedBackCreatorReqDto dto) {
        FeedBack feedBack = FeedBackMapper.INSTANCE.feedBackCreatorReqDtoToFeedBack(dto);
        feedBack.setFeedbackStatus(FeedbackStatus.CREATED);
        FeedBack saved = this.feedBackRepository.save(feedBack);
        return saved.getId();
    }

    @Override
    public void replyFeedBack(UUID feedbackId, String content) {
        this.feedBackRepository.findById(feedbackId)
                .ifPresent(feedBack -> {
                    feedBack.setFeedReply(content);
                    feedBack.setReplyDate(LocalDateTime.now());
                    feedBack.setFeedbackStatus(FeedbackStatus.REPLIED);
                });
    }

    @Override
    public PageImplResDto<FeedBackPageModeraterResDto> getPageFeedBackForModerator(Integer pageSize, Integer pageNumber) {
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;
        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 10 : pageNumber;
        Page<FeedBack> page = this.feedBackRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        List<FeedBackPageModeraterResDto> list = page.getContent()
                .stream()
                .map(FeedBackMapper.INSTANCE::feedBackToFeedBackPageModeraterResDto)
                .sorted(Comparator.comparing(FeedBackPageModeraterResDto::getCreateDate))
                .collect(Collectors.toList());
        return new PageImplResDto<>(list, page.getNumber() + 1, list.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    public PageImplResDto<FeedBackPageEnterpriseResDto> getPageFeedBackForEnterprise(Integer pageSize, Integer pageNumber) {
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;
        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 10 : pageNumber;
        Page<FeedBack> page = this.feedBackRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        List<FeedBackPageEnterpriseResDto> list = page.getContent()
                .stream()
                .map(FeedBackMapper.INSTANCE::feedBackToFeedBackPageEnterpriseResDto)
                .sorted(Comparator.comparing(FeedBackPageEnterpriseResDto::getCreateDate))
                .collect(Collectors.toList());
        return new PageImplResDto<>(list, page.getNumber() + 1, list.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    public FeedBackEnterpriseDetailResDto getFeedbackDetailForEnterprise(UUID id) {
        return this.feedBackRepository.findById(id).map(FeedBackMapper.INSTANCE::feedBackToFeedBackEnterpriseDetailResDto)
                .orElseThrow(() -> new EntityNotFoundException("The Feedback with id: " + id + " was not found!!!"));
    }

    @Override
    public FeedBackModeratorFeedbackDetailResDto getFeedbackDetailForModerator(UUID id) {
        return this.feedBackRepository.findById(id).map(FeedBackMapper.INSTANCE::feedBackToFeedBackModeratorFeedbackDetailResDto)
                .orElseThrow(() -> new EntityNotFoundException("The Feedback with id: " + id + " was not found!!!"));
    }
}
