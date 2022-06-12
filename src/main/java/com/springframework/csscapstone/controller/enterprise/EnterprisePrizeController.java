package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.mapper_utils.converter_mapper.PrizeCreatorConvertor;
import com.springframework.csscapstone.utils.mapper_utils.converter_mapper.PrizeUpdaterConvertor;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Prize.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Prize (Enterprise)")
public class EnterprisePrizeController {
   private final PrizeService prizeService;
   private final PrizeCreatorConvertor prizeCreatorConvertor;
   private final PrizeUpdaterConvertor prizeUpdaterConvertor;
//
   @GetMapping(V2_PRIZE_LIST)
   public ResponseEntity<?> getAllPrize(
           @RequestParam(value = "name_prize", required = false) String namePrize,
           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
           @RequestParam(value = "pageSize", required = false) Integer pageSize

   ) {
        return ok(this.prizeService.getAll(namePrize, pageNumber, pageSize));
   }

   /**
    * PrizeService
    */

   @PostMapping(value = V2_PRIZE_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> createPrize(
           @RequestPart("prize") String prizeJson,
           @RequestPart("images") List<MultipartFile> images
   ) {
      PrizeCreatorReqDto dto = prizeCreatorConvertor.convert(prizeJson);
      if (Objects.isNull(dto.getPrice())  || dto.getPrice() < 1.0)
         throw new RuntimeException("The price of prize is not null");
      if (Objects.isNull(dto.getQuantity()) || dto.getQuantity() < 0)
         throw new RuntimeException("The quantity of prize is not null and greater than 0");
      UUID prize = this.prizeService.createPrize(dto, images);
      return ok(prize);
   }

   @PutMapping(value = V2_PRIZE_UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> updatePrize(
           @RequestPart("prize") String prizeJson,
           @RequestPart("images") List<MultipartFile> images
   ) {
      PrizeUpdaterReqDto dto = prizeUpdaterConvertor.convert(prizeJson);
      if (Objects.isNull(dto.getPrice())  || dto.getPrice() < 1.0)
         throw new RuntimeException("The price of prize is not null");
      if (Objects.isNull(dto.getQuantity()) || dto.getQuantity() < 0)
         throw new RuntimeException("The quantity of prize is not null and greater than 0");
      UUID prize = this.prizeService.updatePrize(dto, images);
      return ok(prize);
   }
}
