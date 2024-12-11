package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;

public class StatusHistoryDto extends BaseDto {

    private Long time;
    private String status;
    private String user;
    private String comments;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
