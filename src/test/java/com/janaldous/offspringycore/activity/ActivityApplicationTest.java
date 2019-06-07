package com.janaldous.offspringycore.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringycore.data.IActivityRepository;
import com.janaldous.offspringycore.data.InMemoryActivityRepository;
import com.janaldous.offspringycore.domain.Activity;
import com.janaldous.offspringycore.domain.ActivityType;
import com.janaldous.offspringycore.util.EntityNotFoundException;
import com.janaldous.offspringycore.util.InvalidEntityException;

@RunWith(SpringRunner.class)
public class ActivityApplicationTest {

	@Autowired
	private IActivityBusiness activityApplication;
	
	@Autowired
	private IActivityRepository activityRepository;
	
	@TestConfiguration
    static class ActivityApplicationTestContextConfiguration {
  
        @Bean
        public IActivityBusiness activityApplication() {
            return new ActivityBusiness();
        }
        
        @Bean
        public IActivityRepository activityRepository() {
            return new InMemoryActivityRepository();
        }
    }
	
	@BeforeEach
	public void setUp() {
		activityRepository = new InMemoryActivityRepository();
	}
	
	@Test
	public void testCreateValidActivity() throws InvalidEntityException, EntityNotFoundException {
		Activity activity = Activity.builder()
				.name("Swimming summer classes")
				.description("Swimming classes for beginners")
				.type(ActivityType.BOOK_NOW)
				.build();
		
		Activity savedActivity = activityApplication.create(activity);
		assertNotNull(savedActivity.getId());
		Activity foundActivity = activityApplication.read(savedActivity.getId());
		assertEquals(savedActivity.getId(), foundActivity.getId());
		assertEquals(activity.getName(), foundActivity.getName());
		assertEquals(activity.getDescription(), foundActivity.getDescription());
		assertEquals(activity.getType(), foundActivity.getType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidActivity_noDetails() throws InvalidEntityException {
		Activity activity = new Activity();
		
		activityApplication.create(activity);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidActivity_noName() throws InvalidEntityException {
		Activity activity = new Activity();
		activity.setDescription("activity description");
		activity.setType(ActivityType.BOOK_NOW);
		
		activityApplication.create(activity);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidActivity_noDescription() throws InvalidEntityException {
		Activity activity = new Activity();
		activity.setName("activity name");
		activity.setType(ActivityType.BOOK_NOW);
		
		activityApplication.create(activity);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInvalidActivity_noType() throws InvalidEntityException {
		Activity activity = new Activity();
		activity.setName("activity name");
		activity.setDescription("activity description");
		
		activityApplication.create(activity);
	}
	
	@Test
	public void testUpdateValidActivity() throws InvalidEntityException, EntityNotFoundException {
		// given
		Activity activity = Activity.builder()
				.name("Swimming summer classes")
				.description("Swimming classes for beginners")
				.type(ActivityType.BOOK_NOW)
				.build();
		activityRepository.create(activity);
		
		// when
		activity.setName("new name");
		activity.setDescription("new description");
		activity.setType(ActivityType.FREE);
		Activity activityUpdated = activityApplication.update(activity);
		assertEquals(activity.getId(), activityUpdated.getId());
		assertEquals(activity.getName(), activityUpdated.getName());
		assertEquals(activity.getDescription(), activityUpdated.getDescription());
		assertEquals(activity.getType(), activityUpdated.getType());
	}

}
