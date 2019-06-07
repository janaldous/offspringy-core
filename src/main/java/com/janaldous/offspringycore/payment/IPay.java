package com.janaldous.offspringycore.payment;

import com.janaldous.offspringycore.domain.Customer;

public interface IPay {
	Payment pay(Order order, Customer customer);
}
