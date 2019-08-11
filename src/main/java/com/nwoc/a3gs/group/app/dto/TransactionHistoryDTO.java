package com.nwoc.a3gs.group.app.dto;


import javax.validation.constraints.NotNull;

import com.nwoc.a3gs.group.app.model.PaymentStatus;
import com.nwoc.a3gs.group.app.model.ServiceRequests;

public class TransactionHistoryDTO {
	
	private Long id;
	
	@NotNull
	private ServiceRequests serviceRequests;

	private String transactionId;

	private String type;

	private String amount;
	
	private PaymentStatus paymentStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceRequests getServiceRequests() {
		return serviceRequests;
	}

	public void setServiceRequests(ServiceRequests serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	

}
