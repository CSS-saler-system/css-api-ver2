package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebScheduler {
    private final CampaignService campaignService;

//    @Scheduled(cron = "*/10 * * * * *") // 0 0 0 * * *
    @Scheduled(cron = "${cron_time}") // 0 0 0 * * *
    public void finishCampaignConfiguration() {
        campaignService.scheduleCloseCampaign();
    }

    @Scheduled(cron = "${cron_time}")
    public void rejectCampaignConfiguration() {
        campaignService.rejectCampaignInDate();
    }

}
