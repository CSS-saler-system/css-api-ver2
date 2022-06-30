package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
//@EnableAsync
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebScheduler {
    private final CampaignService campaignService;

    @Async
    @Scheduled(cron = "${cron_time}") // 0 0 0 * * *
    public void finishCampaignConfiguration() {
        this.campaignService.scheduleCloseCampaign();
    }

}
