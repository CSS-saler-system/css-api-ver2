package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Notification;
import com.springframework.csscapstone.payload.basic.NotificationBasicDto;
import com.springframework.csscapstone.payload.basic.NotificationWithTopicBasicDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NotificationMapper {
    Notification notificationBasicDtoToNotification(NotificationBasicDto notificationBasicDto);

    NotificationBasicDto notificationToNotificationBasicDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification updateNotificationFromNotificationBasicDto(NotificationBasicDto notificationBasicDto, @MappingTarget Notification notification);

    Notification notificationWithTopicBasicDtoToNotification(NotificationWithTopicBasicDto notificationWithTopicBasicDto);

    NotificationWithTopicBasicDto notificationToNotificationWithTopicBasicDto(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Notification updateNotificationFromNotificationWithTopicBasicDto(NotificationWithTopicBasicDto notificationWithTopicBasicDto, @MappingTarget Notification notification);
}
