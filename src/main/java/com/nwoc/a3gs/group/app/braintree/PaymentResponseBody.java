package com.nwoc.a3gs.group.app.braintree;

import java.math.BigDecimal;

import com.braintreegateway.Transaction;
import com.nwoc.a3gs.group.app.model.ServiceRequests;

public class PaymentResponseBody {

	String transactionId;
	String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	BigDecimal amount;
	ServiceRequests serviceRequests;
	Transaction.Status status;
	
	public Transaction.Status getStatus() {
		return status;
	}
	public void setStatus(Transaction.Status status) {
		this.status = status;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public ServiceRequests getServiceRequests() {
		return serviceRequests;
	}
	public void setServiceRequests(ServiceRequests serviceRequests) {
		this.serviceRequests = serviceRequests;
	}
	
	
}
