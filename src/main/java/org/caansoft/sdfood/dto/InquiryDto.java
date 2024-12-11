package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;

public class InquiryDto extends BaseDto {

    private String contactWithCompetitor;
    private String competitorName;
    private String specificBudget;
    private Float budget;
    private String projectMatureness;
    private Long orderId;

    public String getContactWithCompetitor() {
        return contactWithCompetitor;
    }

    public void setContactWithCompetitor(String contactWithCompetitor) {
        this.contactWithCompetitor = contactWithCompetitor;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public String getSpecificBudget() {
        return specificBudget;
    }

    public void setSpecificBudget(String specificBudget) {
        this.specificBudget = specificBudget;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public String getProjectMatureness() {
        return projectMatureness;
    }

    public void setProjectMatureness(String projectMatureness) {
        this.projectMatureness = projectMatureness;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
