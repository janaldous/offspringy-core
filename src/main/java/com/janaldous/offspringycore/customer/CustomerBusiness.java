package com.janaldous.offspringycore.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.util.EntityNotFoundException;
import com.janaldous.offspringycore.util.InMemoryRepository;
import com.janaldous.offspringycore.util.InvalidEntityException;

@Service
public class CustomerBusiness implements ICustomerBusiness {
	
	@Autowired
	private InMemoryRepository<Customer> repository;
	
	@Override
	public Customer create(Customer activity) throws InvalidEntityException {
		return repository.create(activity);
	}

	@Override
	public Customer update(Customer activity) throws InvalidEntityException,
			EntityNotFoundException {
		return repository.update(activity);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Customer read(Long id) throws EntityNotFoundException {
		return repository.find(id);
	}

	@Override
	public boolean exists(Long id) {
		return repository.find(id) != null;
	}

}
