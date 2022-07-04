package com.springframework.csscapstone.data.status;

/**
 * todo status PENDING, WAITING, cancelled: Enterprise Role
 * todo status ACCEPTED, DISABLE: Admin Role
 * todo status FINISHED: System Role
 */
public enum CampaignStatus {
    CREATED, DISABLED, SENT, //enterprise
    REJECTED, APPROVAL, FINISHED //moderator
}
