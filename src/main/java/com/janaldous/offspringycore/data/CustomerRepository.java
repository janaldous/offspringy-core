package com.janaldous.offspringycore.data;

import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.util.InMemoryRepository;

@Component
public class CustomerRepository extends InMemoryRepository<Customer> {

}
