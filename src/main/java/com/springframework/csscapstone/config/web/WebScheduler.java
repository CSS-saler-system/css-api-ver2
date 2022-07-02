package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(WebScheduler.class);
    @Scheduled(cron = "${cron_time}") // 0 0 0 * * *
    public void finishCampaignConfiguration() {
        LOGGER.debug("The completing campaign is running!!!");
        System.out.println("I'm calling");
        this.campaignService.scheduleCloseCampaign();
        LOGGER.debug("The completing campaign is done!!!");
    }

}
