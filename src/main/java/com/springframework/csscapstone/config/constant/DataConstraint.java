package com.springframework.csscapstone.config.constant;

import java.time.LocalDateTime;

public class DataConstraint {
    public static final LocalDateTime MIN_DATE = LocalDateTime.parse("0001-01-01T12:00:00");
    public static final LocalDateTime MAX_DATE = LocalDateTime.parse("9999-12-31T23:59:59");

    public static class CampaignConstraintData {
        public static final int KPI_CAMPAIGN = 1;
    }

}
