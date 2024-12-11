package org.caansoft.sdfood.model;

import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.core.model.BaseEntity;
import org.caansoft.core.model.Employee;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order_status")
public class OrderStatusHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    @Column(length = 1000)
    private String comments;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;

    @ManyToOne
    @JoinColumn(name = "verified_by")
    private Employee verifiedBy;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Employee getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Employee verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}


