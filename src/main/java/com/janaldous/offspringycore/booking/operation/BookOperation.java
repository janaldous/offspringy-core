package com.janaldous.offspringycore.booking.operation;

import java.util.List;

import com.janaldous.offspringycore.activity.EventFullException;
import com.janaldous.offspringycore.activity.EventNotFoundException;
import com.janaldous.offspringycore.booking.IBookingBusiness;
import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.domain.Ticket;
import com.janaldous.offspringycore.ticket.ITicket;
import com.janaldous.offspringycore.util.InvalidEntityException;

public class BookOperation implements BookCommand {
	
	private ITicket ticketBusiness;
	private IBookingBusiness bookingBusiness;
	
	private Customer customer;
	private Event event;
	private int numOfTickets;
	private Booking savedBooking;
	
	public BookOperation(ITicket ticketBusiness, IBookingBusiness bookingBusiness, Customer customer, 
			Event event, int numOfTickets) {
		if (numOfTickets <= 0) {
			throw new IllegalStateException("Cannot book 0 or less tickets");
		}
		
		this.ticketBusiness = ticketBusiness;
		this.bookingBusiness = bookingBusiness;
		this.customer = customer;
		this.event = event;
		this.numOfTickets = numOfTickets;
	}
	
	@Override
	public void execute() {
		try {
			List<Ticket> tickets = ticketBusiness.takeTicket(event.getId(), numOfTickets);
			
			Booking booking = Booking.builder()
					.confirmed(false)
					.customer(customer)
					.event(event)
					.tickets(tickets)
					.build();
			
			savedBooking = bookingBusiness.create(booking);
		} catch (InvalidEntityException | EventNotFoundException | EventFullException e) {
			throw new BookOperationException(e);
		}
	}
	
	public Booking getBooking() {
		return this.savedBooking;
	}
	
}
