package com.nwoc.a3gs.group.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nwoc.a3gs.group.app.dto.TransactionHistoryDTO;
import com.nwoc.a3gs.group.app.model.TransactionHistory;
import com.nwoc.a3gs.group.app.repository.TransactionHistoryRepository;

import javassist.NotFoundException;

@Service
public class TransactionHistoryServiceImpl {
	
	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;
	
	@Transactional
	public TransactionHistory create(TransactionHistoryDTO transactionHistoryDTO) {
		TransactionHistory transactionHistory = new TransactionHistory();
		BeanUtils.copyProperties(transactionHistoryDTO,transactionHistory);
		return transactionHistoryRepository.save(transactionHistory);
	}
	
	public List<TransactionHistory> findAll() {
		return transactionHistoryRepository.findAll();
	}
	
	public Optional<TransactionHistory> findOne(Long id) {
		return transactionHistoryRepository.findById(id);
	}
	
	public TransactionHistory update(TransactionHistoryDTO transactionHistoryDTO, Long id) throws NotFoundException {
		Optional<TransactionHistory> transactionHistory =findOne(id);
		if(transactionHistory.isPresent()){
			TransactionHistory transactionHist = transactionHistory.get();
			transactionHist.setServiceRequests(transactionHistoryDTO.getServiceRequests());
			transactionHist.setPaymentStatus(transactionHistoryDTO.getPaymentStatus());
			transactionHist.setAmount(transactionHistoryDTO.getAmount());
			transactionHist.setTransactionId(transactionHistoryDTO.getTransactionId());
			transactionHist.setType(transactionHistoryDTO.getType());
			return transactionHistoryRepository.saveAndFlush(transactionHist);
		}else{
			throw new NotFoundException("Transaction History Details Not found");
		}
		
	}
	
	public void delete(TransactionHistory transactionHistory) {
		transactionHistoryRepository.delete(transactionHistory);
	}

	public Page<TransactionHistory> findTransactionHistoryByPages(int pageNumber, int size) {
		Pageable pageable = new PageRequest(pageNumber, size);

		return transactionHistoryRepository.findAll(pageable);
	}



}
