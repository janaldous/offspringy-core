package com.janaldous.offspringycore.booking;

import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.util.IEntityBusiness;

public interface IBookingBusiness extends IEntityBusiness<Booking> {
	
	Booking find(Customer customer, Event event);
	
	void cancel(Booking booking) throws BookingNotFoundException, BookingException;
	
}
