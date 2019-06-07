package com.janaldous.offspringycore.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.data.IEventRepository;
import com.janaldous.offspringycore.domain.Event;

@Component
public class EventBusiness implements IEventBusiness {

	@Autowired
	private IEventRepository eventRepository;

	@Autowired
	private IActivityBusiness activityBusiness;

	@Override
	public Event create(Long activityId, Event event) throws ActivityNotFoundException {
		if (activityBusiness.exists(activityId)) {
			return eventRepository.create(event);
		} else {
			throw new ActivityNotFoundException("activity id is not valid");
		}
	}

	@Override
	public Event update(Event event) throws EventNotFoundException {
		if (eventRepository.find(event.getId()) != null) {
			return eventRepository.update(event);
		} else {
			throw new EventNotFoundException();
		}
	}

	@Override
	public void delete(Long id) {
		eventRepository.delete(id);
	}

	@Override
	public Event read(Long id) {
		return eventRepository.find(id);
	}

	@Override
	public boolean exists(Long id) {
		return eventRepository.find(id) != null;
	}

}
