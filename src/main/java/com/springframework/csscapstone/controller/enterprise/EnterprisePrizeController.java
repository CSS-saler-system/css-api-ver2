package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.services.PrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Prize.V2_PRIZE_LIST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class EnterprisePrizeController {
   private final PrizeService prizeService;
//
   @GetMapping(V2_PRIZE_LIST)
   public ResponseEntity<?> getAllPrize(
           @RequestParam(value = "name_prize", required = false) String namePrize,
           @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
           @RequestParam(value = "pageSize", required = false) Integer pageSize

   ) {
        return ok(this.prizeService.getAll(namePrize, pageNumber, pageSize));
   }
}
