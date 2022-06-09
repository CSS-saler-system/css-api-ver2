package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.data.repositories.CampaignRepository;
import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class WebScheduler {
    private final CampaignService campaignService;

    @Scheduled(cron = "0 0 0 * * *")
    public void finishCampaignConfiguration() {
        this.campaignService.scheduleCloseCampaign();
    }

}
