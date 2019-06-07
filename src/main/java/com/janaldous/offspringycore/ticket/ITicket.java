package com.janaldous.offspringycore.ticket;

import java.util.List;

import com.janaldous.offspringycore.activity.EventFullException;
import com.janaldous.offspringycore.activity.EventNotFoundException;
import com.janaldous.offspringycore.domain.Ticket;


public interface ITicket {

	boolean hasAvailableTickets(Long id) throws EventNotFoundException;
	
	List<Ticket> takeTicket(Long id, int numOfTickets) throws EventFullException, EventNotFoundException;
	
	void returnTicket(Long id, int numOfTickets) throws EventNotFoundException;
	
}
