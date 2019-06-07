package com.janaldous.offspringycore.booking;

import com.janaldous.offspringycore.activity.EventFullException;
import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.domain.Event;

public interface IBooking {
	
	Booking book(Customer customer, Event event, int numOfTickets) throws BookingException, EventFullException;
	
	Booking get(Long id) throws BookingNotFoundException;
	
	void cancelBooking(Long bookingId) throws BookingNotFoundException, BookingNotCancellableException;
	
}
