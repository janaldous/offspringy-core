package com.janaldous.offspringycore.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringycore.activity.EventFullException;
import com.janaldous.offspringycore.activity.EventNotFoundException;
import com.janaldous.offspringycore.activity.IEventBusiness;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.domain.Ticket;
import com.janaldous.offspringycore.util.EntityNotFoundException;
import com.janaldous.offspringycore.util.InvalidEntityException;

@Service
public class TicketBusiness implements ITicket {

	@Autowired
	private IEventBusiness eventBusiness;
	
	@Override
	public List<Ticket> takeTicket(Long id, int numOfTickets) throws EventFullException, EventNotFoundException {
		Event event = getEvent(id);
		if (event.getCapacity() - numOfTickets < 0) {
			throw new EventFullException();
		}
		int newCapacity = event.getCapacity() - numOfTickets;
		event.setCapacity(newCapacity);
		
		try {
			eventBusiness.update(event);
		} catch (InvalidEntityException | EntityNotFoundException e) {
			throw new EventNotFoundException();
		}
		
		List<Ticket> tickets = new ArrayList<>();
		for (int i = 0; i < numOfTickets; i++) {
			tickets.add(new Ticket());
		}
		
		return tickets;
	}

	@Override
	public boolean hasAvailableTickets(Long id) throws EventNotFoundException {
		return getEvent(id).getCapacity() > 0; 
	}

	@Override
	public void returnTicket(Long id, int numOfTickets) throws EventNotFoundException {
		Event event = getEvent(id);
		int newCapacity = event.getCapacity() + numOfTickets;
		event.setCapacity(newCapacity);
		
		try {
			eventBusiness.update(event);
		} catch (InvalidEntityException | EntityNotFoundException e) {
			throw new EventNotFoundException();
		}
	}

	private Event getEvent(Long id) throws EventNotFoundException {
		try {
			return eventBusiness.read(id);
		} catch (EntityNotFoundException e) {
			throw new EventNotFoundException();
		}
	}

}
