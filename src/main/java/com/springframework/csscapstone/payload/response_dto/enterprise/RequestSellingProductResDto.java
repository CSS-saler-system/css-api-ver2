package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class RequestSellingProductResDto implements Serializable {

    private final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime dateTimeRequest;

//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private final LocalDateTime modifiedDate;
    private final RequestStatus requestStatus;
    private final ProductDto product;

    @JsonCreator(mode = PROPERTIES)
    public RequestSellingProductResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("dateTimeRequest") LocalDateTime dateTimeRequest,
//            LocalDateTime modifiedDate,
            @JsonProperty("requestStatus") RequestStatus requestStatus,
            @JsonProperty("product") ProductDto product) {
        this.id = id;
        this.dateTimeRequest = dateTimeRequest;
//        this.modifiedDate = modifiedDate;
        this.requestStatus = requestStatus;
        this.product = product;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String description;
        private final Double price;
        private final Double pointSale;

        @JsonCreator(mode = PROPERTIES)
        public ProductDto(@JsonProperty("id") UUID id,
                          @JsonProperty("name") String name,
                          @JsonProperty("description") String description,
                          @JsonProperty("price") Double price,
                          @JsonProperty("pointSale") Double pointSale) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.pointSale = pointSale;
        }
    }
}
