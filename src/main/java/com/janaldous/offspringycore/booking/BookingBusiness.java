package com.janaldous.offspringycore.booking;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.util.InMemoryEntityBusiness;

@Component
public class BookingBusiness extends InMemoryEntityBusiness<Booking> implements IBookingBusiness {

	@Override
	public Booking find(Customer customer, Event event) {
		return repository.findAll().stream()
			.filter(booking -> booking.getEvent().equals(event) && booking.getCustomer().equals(customer))
			.collect(Collectors.toList())
			.get(0);
	}

	@Override
	public void cancel(Booking booking) throws BookingNotFoundException, BookingException {
		if (repository.find(booking.getId()) == null) {
			throw new BookingNotFoundException();
		}
		
		if (booking.isCancelled()) {
			throw new BookingException("Booking is already cancelled");
		}
		
		booking.setCancelled(true);
		repository.update(booking);
	}

}
