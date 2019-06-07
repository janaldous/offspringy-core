package com.janaldous.offspringycore.activity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.data.IActivityRepository;
import com.janaldous.offspringycore.domain.Activity;

@Component
public class ActivityBusiness implements IActivityBusiness {

	@Autowired
	private IActivityRepository activityRepository;
	
	@Override
	public Activity create(Activity activity) throws InvalidActivityException {
		if (activity.getName() == null || activity.getName().isEmpty()) {
			throw new IllegalArgumentException("activity name is required");
		}
		if (activity.getDescription() == null || activity.getDescription().isEmpty()) {
			throw new IllegalArgumentException("activity description is required");
		}
		if (activity.getType() == null) {
			throw new IllegalArgumentException("activity type is required");
		}
		
		Activity a = activityRepository.create(activity);
		if (a != null) {
			return a;
		} else {
			throw new InvalidActivityException("activity has missing fields");
		}
	}

	@Override
	public Activity update(Activity activity) throws InvalidActivityException {
		Activity a = activityRepository.update(activity);
		if (a != null) {
			return a;
		} else {
			throw new InvalidActivityException("activity has missing fields");
		}
	}

	@Override
	public void delete(Long id) {
		activityRepository.delete(id);
	}

	@Override
	public Activity read(Long id) {
		Activity a = activityRepository.find(id);
		if (a != null) {
			return a;
		} else {
			throw new ActivityNotFoundException("activity has missing fields");
		}
	}

	@Override
	public boolean exists(Long id) {
		return activityRepository.find(id) != null;
	}

}
