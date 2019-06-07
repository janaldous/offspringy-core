package com.janaldous.offspringycore.booking;

import com.janaldous.offspringycore.util.InvalidEntityException;

@SuppressWarnings("serial")
class InvalidBookingException extends InvalidEntityException {

	public InvalidBookingException(String message) {
		super(message);
	}

}
