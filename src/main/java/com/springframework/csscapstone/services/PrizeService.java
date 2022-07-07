package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrizeService {

    PageImplResDto<PrizeResDto> getAll(UUID enterpriseId, String name, Integer pageNumber, Integer pageSize);

    UUID updatePrize(PrizeUpdaterReqDto prizeUpdater);

    UUID createPrize(PrizeCreatorReqDto prizeCreatorReqDto);

    Optional<PrizeResDto> getPrizeByPrize(UUID uuid);

}
