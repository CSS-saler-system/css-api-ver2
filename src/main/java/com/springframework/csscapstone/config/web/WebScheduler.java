package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.services.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//@EnableAsync
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class WebScheduler {

    private final ThreadPoolTaskExecutor executor;
    private final CampaignService campaignService;

    @Scheduled(cron = "${cron_time}") // 0 0 0 * * *
    public void finishCampaignConfiguration() {
//        this.campaignService.scheduleCloseCampaign();
        executor.execute(this.campaignService::scheduleCloseCampaign);
    }

    @Scheduled(cron = "${cron_time}")
    public void rejectCampaignConfiguration() {
        executor.execute(this.campaignService::rejectCampaignInDate);
    }

}
