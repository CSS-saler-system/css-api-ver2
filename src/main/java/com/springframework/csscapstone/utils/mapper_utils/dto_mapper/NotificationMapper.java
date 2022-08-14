package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Notification;
import com.springframework.csscapstone.payload.system_model.NotificationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NotificationMapper {
    Notification notificationDtoToNotification(NotificationDto notificationDto);

    NotificationDto notificationToNotificationDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification updateNotificationFromNotificationDto(NotificationDto notificationDto, @MappingTarget Notification notification);
}