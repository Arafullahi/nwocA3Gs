package com.nwoc.a3gs.group.app.braintree.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.nwoc.a3gs.group.app.braintree.PaymentResponseBody;
import com.nwoc.a3gs.group.app.model.ServiceRequests;
import com.nwoc.a3gs.group.app.model.ServiceStatus;
import com.nwoc.a3gs.group.app.services.ServiceRequestService;
import com.braintreegateway.Transaction.Status;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private BraintreeGateway gateway;
	
	@Autowired
	private ServiceRequestService serviceRequestService;
	
	private Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] {
	        Transaction.Status.AUTHORIZED,
	        Transaction.Status.AUTHORIZING,
	        Transaction.Status.SETTLED,
	        Transaction.Status.SETTLEMENT_CONFIRMED,
	        Transaction.Status.SETTLEMENT_PENDING,
	        Transaction.Status.SETTLING,
	        Transaction.Status.SUBMITTED_FOR_SETTLEMENT
	     };

	    /*@RequestMapping(value = "/", method = RequestMethod.GET)
	    public String root(Model model) {
	        return "redirect:/payment/checkouts";
	    }*/

	    @RequestMapping(value = "/checkouts", method = RequestMethod.GET)
	    public ResponseEntity<?> checkout() {
	        String clientToken = gateway.clientToken().generate();
	        return new ResponseEntity<String>(new StringBuffer("{\"clientToken\":").append("\"").append(clientToken).append("\"}").toString(), HttpStatus.OK);
	    }

	    @RequestMapping(value = "/checkouts", method = RequestMethod.POST)
	    public ResponseEntity<?> postForm(@RequestParam("serviceRequestId") Long serviceRequestId, @RequestParam("payment_method_nonce") String nonce, final RedirectAttributes redirectAttributes) {
	        BigDecimal decimalAmount;
	        Optional<ServiceRequests> serviceReqOpt=null;
	        try {
	        	 serviceReqOpt=serviceRequestService.findOne(serviceRequestId);
	        	if(!serviceReqOpt.isPresent()){
	        		return new ResponseEntity<String>("Payment Request Service is invalid,Payment Canceled", HttpStatus.BAD_REQUEST);
	        	}
	        	
	            decimalAmount = new BigDecimal(serviceReqOpt.get().getRate());
	        } catch (NumberFormatException e) {
	            redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
	            return new ResponseEntity<String>(" Amount is an invalid format.", HttpStatus.BAD_REQUEST);
	        }
	        
	        TransactionRequest request = new TransactionRequest()
	            .amount(decimalAmount)
	            .paymentMethodNonce(nonce)
	            
	            .options()
	                .submitForSettlement(true)
	                .done();
	        			
	        Result<Transaction> result = gateway.transaction().sale(request);
	        //System.out.println("transaction: "+ ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	        if (result.isSuccess()) {
	        	
	        	ServiceRequests serviceRequests= serviceReqOpt.get();
	        	serviceRequests.setServiceStatus(ServiceStatus.AMOUNT_PAYED);
	        	
	            Transaction transaction = result.getTarget();
	            serviceRequests.setTransactionId(transaction.getId());
	            PaymentResponseBody responseBody= new PaymentResponseBody();
	            responseBody.setTransactionId(transaction.getId());
	            responseBody.setAmount(decimalAmount);
	            responseBody.setServiceRequests(serviceReqOpt.get());
	            responseBody.setStatus(transaction.getStatus());
	            responseBody.setMessage("Payment has been completed.Our representetive will caontact you shorly.");
	            return new ResponseEntity<PaymentResponseBody>(responseBody,HttpStatus.ACCEPTED);
	            //return "redirect:/payment/checkouts/" + transaction.getId();
	        } else if (result.getTransaction() != null) {
	            Transaction transaction = result.getTransaction();
	            //System.out.println("transaction: "+ ToStringBuilder.reflectionToString(transaction,ToStringStyle.SHORT_PREFIX_STYLE));
	            PaymentResponseBody responseBody= new PaymentResponseBody();
	            responseBody.setTransactionId(transaction.getId());
	            responseBody.setAmount(decimalAmount);
	            responseBody.setServiceRequests(serviceReqOpt.get());
	            responseBody.setStatus(transaction.getStatus());
	            responseBody.setMessage("Payment failed, Please try again after few minutes.");
	            return new ResponseEntity<PaymentResponseBody>(responseBody,HttpStatus.FORBIDDEN);
	            
	        } else {
	            String errorString = "";
	            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
	               errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
	            }
	            PaymentResponseBody responseBody= new PaymentResponseBody();
	            //responseBody.setTransactionId(transaction.getId());
	            responseBody.setAmount(decimalAmount);
	            responseBody.setServiceRequests(serviceReqOpt.get());
	            //responseBody.setStatus(transaction.getStatus());
	            responseBody.setMessage(errorString);
	            return new ResponseEntity<PaymentResponseBody>(responseBody,HttpStatus.INTERNAL_SERVER_ERROR);
	            
	        }
	    }

	    @RequestMapping(value = "/checkouts/{transactionId}")
	    public String getTransaction(@PathVariable String transactionId, Model model) {
	        Transaction transaction;
	        CreditCard creditCard;
	        Customer customer;

	        try {
	            transaction = gateway.transaction().find(transactionId);
	            creditCard = transaction.getCreditCard();
	            customer = transaction.getCustomer();
	        } catch (Exception e) {
	            System.out.println("Exception: " + e);
	            return "redirect:/checkouts";
	        }

	        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
	        model.addAttribute("transaction", transaction);
	        model.addAttribute("creditCard", creditCard);
	        model.addAttribute("customer", customer);

	        return "static/checkouts/show";
	    }
	
}
