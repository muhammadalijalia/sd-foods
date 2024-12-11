package org.caansoft.sdfood.prestashop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.caansoft.core.model.BaseEntity;

@Transactional
@Entity
@Table(name = "prestashop_order_status")
public class PrestashopOrderStatus extends BaseEntity{

	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "status_id")
	private Integer statusId;
	
	@Column(name = "status_value")
	private String statusValue;
	
	@Column(name = "comments")
	private String comments;
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	
}
