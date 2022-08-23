package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebScheduler {
    private final CampaignService campaignService;

   @Scheduled(cron = "*/120 * * * * *") // 0 0 0 * * *
//     @Scheduled(cron = "${cron_time}") // 0 0 0 * * *
    public void finishCampaignConfiguration() {
        campaignService.scheduleCloseCampaign();
    }
   @Scheduled(cron = "*/120 * * * * *") // 0 0 0 * * *
//     @Scheduled(cron = "${cron_time}")
    public void rejectCampaignConfiguration() {
        campaignService.rejectCampaignInDate();
    }

}
