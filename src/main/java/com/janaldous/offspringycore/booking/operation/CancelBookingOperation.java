package com.janaldous.offspringycore.booking.operation;

import com.janaldous.offspringycore.activity.EventNotFoundException;
import com.janaldous.offspringycore.booking.BookingException;
import com.janaldous.offspringycore.booking.BookingNotFoundException;
import com.janaldous.offspringycore.booking.IBookingBusiness;
import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.ticket.ITicket;


public class CancelBookingOperation implements BookCommand {
	
	private ITicket ticketBusiness;
	private IBookingBusiness bookingBusiness;
	private Booking booking;
	
	public CancelBookingOperation(ITicket ticketBusiness, IBookingBusiness bookingBusiness, Booking booking) {
		this.ticketBusiness = ticketBusiness;
		this.bookingBusiness = bookingBusiness;
		this.booking = booking;
	}
	
	@Override
	public void execute() {
		Event event = booking.getEvent();
		
		if (!event.isCancellable()) {
			throw new BookOperationException("Event is not cancellable");
		}
		
		int numOfTickets = booking.getTickets().size();
		
		try {
			ticketBusiness.returnTicket(event.getId(), numOfTickets);
			bookingBusiness.cancel(booking);
		} catch (EventNotFoundException | BookingNotFoundException | BookingException e) {
			throw new BookOperationException(e);
		}
	}

}
