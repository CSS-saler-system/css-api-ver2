package com.springframework.csscapstone.data.status;

/**
 * todo status PENDING, WAITING, cancelled: Enterprise Role
 * todo status ACCEPTED, DISABLE: Admin Role
 * todo status FINISHED: System Role
 */
public enum CampaignStatus {
    // enterprise, load reject approve finish,
    // <exclude> sent
    CREATED, DISABLED,
    SENT, //not load

    // moderator load sent
    REJECTED, APPROVAL,
    FINISHED //moderator
}
