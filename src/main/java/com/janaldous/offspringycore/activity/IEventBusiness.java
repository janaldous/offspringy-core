package com.janaldous.offspringycore.activity;

import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.util.IEntityBusinessBasic;

public interface IEventBusiness extends IEntityBusinessBasic<Event> {
	
	Event create(Long activityId, Event event) throws ActivityNotFoundException;
	
}
