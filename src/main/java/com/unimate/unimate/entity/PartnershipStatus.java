package com.unimate.unimate.entity;

public enum PartnershipStatus {
    NEW("New partnership request"),
    RUNNING("Partnership is ongoing"),
    CANCELED("Partnership is canceled"),
    CONFIRMED("Partnership is confirmed"),
    COMPLETED("Partnership is completed");

    private final String description;

    PartnershipStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
