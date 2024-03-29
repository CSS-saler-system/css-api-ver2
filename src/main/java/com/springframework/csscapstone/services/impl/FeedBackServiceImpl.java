package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.dao.specifications.FeedBackSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.FeedBack;
import com.springframework.csscapstone.data.domain.FeedBack_;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.FeedBackRepository;
import com.springframework.csscapstone.data.status.FeedbackStatus;
import com.springframework.csscapstone.payload.request_dto.FeedBackCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackModeratorFeedbackDetailResDto;
import com.springframework.csscapstone.payload.response_dto.admin.FeedBackPageModeraterResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.FeedBackCollaboratorListDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackEnterpriseDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.FeedBackPageEnterpriseResDto;
import com.springframework.csscapstone.services.FeedBackService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.FeedBackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public UUID createFeedBack(FeedBackCreatorReqDto dto) {
        if(isNull(dto.getCreator().getId())) {
            throw new RuntimeException("The creator id must be not null");
        }
        Account account = accountRepository.findById(dto.getCreator().getId())
                .orElseThrow(() -> new EntityNotFoundException("The creator with id: " + dto.getCreator().getId() + " was not found!!!"));
        FeedBack feedBack = FeedBackMapper.INSTANCE.feedBackCreatorReqDtoToFeedBack(dto);
        feedBack.setFeedbackStatus(FeedbackStatus.CREATED);

        account.addFeedBackCreate(feedBack);

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
                    this.feedBackRepository.save(feedBack);
                });
    }

    @Override
    public PageImplResDto<FeedBackPageModeraterResDto> getPageFeedBackForModerator(Integer pageSize, Integer pageNumber) {
        pageSize = isNull(pageSize) || pageSize < 1 ? 10 : pageSize;
        pageNumber = isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        Page<FeedBack> page = this.feedBackRepository
                .findAll(PageRequest.of(pageNumber - 1, pageSize, Sort.by(FeedBack_.CREATE_DATE).descending()));
        List<FeedBackPageModeraterResDto> list = page.getContent()
                .stream()
                .map(FeedBackMapper.INSTANCE::feedBackToFeedBackPageModeraterResDto)
//                 .sorted(Comparator.comparing(FeedBackPageModeraterResDto::getCreateDate).reversed())
                .collect(Collectors.toList());
        return new PageImplResDto<>(list, page.getNumber() + 1, list.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    public PageImplResDto<FeedBackPageEnterpriseResDto> getPageFeedBackForEnterprise(UUID enterpriseId, FeedbackStatus status, Integer pageSize, Integer pageNumber) {
        pageSize = isNull(pageSize) || pageSize < 1 ? 10 : pageSize;
        pageNumber = isNull(pageNumber) || pageNumber < 1 ? 10 : pageNumber;

        Specification<FeedBack> condition = Specification.where(FeedBackSpecifications.equalsByEnterpriseId(enterpriseId))
                .and(nonNull(status) ? FeedBackSpecifications.equalsStatus(status) : null);

        Page<FeedBack> page = this.feedBackRepository.findAll(condition, PageRequest.of(pageNumber - 1, pageSize,
                        Sort.by(FeedBack_.CREATE_DATE).descending().and(Sort.by(FeedBack_.REPLY_DATE).descending())));

        List<FeedBackPageEnterpriseResDto> list = page.getContent()
                .stream()
                .map(FeedBackMapper.INSTANCE::feedBackToFeedBackPageEnterpriseResDto)
                .sorted(Comparator.comparing(FeedBackPageEnterpriseResDto::getCreateDate))
                .collect(Collectors.toList());
        return new PageImplResDto<>(list, page.getNumber() + 1, list.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    public List<FeedBackCollaboratorListDto> getAllListFeedBackCollaborator(UUID collaboratorId) {
        List<FeedBack> result = this.feedBackRepository.findAllByCreator_Id(collaboratorId);
        return result.stream()
                .map(FeedBackMapper.INSTANCE::feedBackToFeedBackCollaboratorListDto)
                .collect(Collectors.toList());
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
