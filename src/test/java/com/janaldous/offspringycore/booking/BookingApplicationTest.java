package com.janaldous.offspringycore.booking;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringycore.activity.ActivityBusiness;
import com.janaldous.offspringycore.activity.EventBusiness;
import com.janaldous.offspringycore.activity.EventFullException;
import com.janaldous.offspringycore.activity.IActivityBusiness;
import com.janaldous.offspringycore.activity.IEventBusiness;
import com.janaldous.offspringycore.booking.operation.BookCommand;
import com.janaldous.offspringycore.booking.operation.BookOperation;
import com.janaldous.offspringycore.booking.operation.BookOperationException;
import com.janaldous.offspringycore.booking.operation.CancelBookingOperation;
import com.janaldous.offspringycore.customer.CustomerBusiness;
import com.janaldous.offspringycore.customer.ICustomerBusiness;
import com.janaldous.offspringycore.data.BookingRepository;
import com.janaldous.offspringycore.data.CustomerRepository;
import com.janaldous.offspringycore.data.IActivityRepository;
import com.janaldous.offspringycore.data.IEventRepository;
import com.janaldous.offspringycore.data.InMemoryActivityRepository;
import com.janaldous.offspringycore.data.InMemoryEventRepository;
import com.janaldous.offspringycore.domain.Activity;
import com.janaldous.offspringycore.domain.ActivityType;
import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.domain.Customer;
import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.domain.Parent;
import com.janaldous.offspringycore.domain.Ticket;
import com.janaldous.offspringycore.ticket.ITicket;
import com.janaldous.offspringycore.ticket.TicketBusiness;
import com.janaldous.offspringycore.util.EntityNotFoundException;
import com.janaldous.offspringycore.util.InMemoryRepository;
import com.janaldous.offspringycore.util.InvalidEntityException;

@RunWith(SpringRunner.class)
public class BookingApplicationTest {
	
	@Autowired
	private IBooking bookingApplication;
	
	@Autowired
	private ICustomerBusiness customerBusiness;
	
	@Autowired
	private IEventBusiness eventBusiness;
	
	@Autowired
	private IBookingBusiness bookingBusiness;
	
	@Autowired
	private ITicket ticketBusiness;
	
	@Autowired
	private IActivityBusiness activityBusiness;

	private Activity savedActivity;

	private Customer customer;

	private Booking booking;

	private Event event;
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public IBooking bookingApplication() {
            return new BookingApplication();
        }
        
        @Bean
        public IEventBusiness eventBusiness() {
            return new EventBusiness();
        }
        
        @Bean
        public IEventRepository eventRepository() {
            return new InMemoryEventRepository();
        }
        
        @Bean
        public ITicket ticketBusiness() {
            return new TicketBusiness();
        }
        
        @Bean
        public IActivityBusiness activityBusiness() {
            return new ActivityBusiness();
        }
        
        @Bean
        public IActivityRepository activityRepository() {
            return new InMemoryActivityRepository();
        }
        
        @Bean
        public IBookingBusiness bookingBusiness() {
            return new BookingBusiness();
        }
        
        @Bean
        public ICustomerBusiness customerBusiness() {
            return new CustomerBusiness();
        }
        
        @Bean
        public InMemoryRepository<Customer> customerRepository2() {
            return new CustomerRepository();
        }
        
        @Bean
        public InMemoryRepository<Booking> bookingRepository() {
        	return new BookingRepository();
        }
        
    }
	
	@Before
	public void beforeEach() throws InvalidEntityException {
		if (savedActivity == null && customer == null) {
			Activity activity = Activity.builder()
					.name("name")
					.description("description")
					.type(ActivityType.BOOK_NOW)
					.build();
			savedActivity = activityBusiness.create(activity);
			
			customer = new Parent();
			customer.setName("parent");
			customer = customerBusiness.create(customer);
			
			event = new Event();
			event.setCancellable(true);
			event.setName("Event name not enough tickets");
			event.setCapacity(12);
			event = eventBusiness.create(savedActivity.getId(), event);
		}
		
		if (booking == null) {
			BookOperation bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, 1);
			bookOperation.execute();
			booking = bookOperation.getBooking();
		}
  	}
	
	@Test
	public void givenEnoughEventTickets_whenBooking_thenSuccess() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		
		assertNotNull(savedActivity);
		
		Event event = new Event();
		event.setName("Event name enough tickets");
		event.setCapacity(5);
		event = eventBusiness.create(savedActivity.getId(), event);
		
		Booking booking = createBooking(customer, event);
		
		assertNotNull(booking.getId());
		assertTrue(booking.getTickets().size() == 1);
	}
	
	@Test
	public void givenEnoughEventTicketsLowerBoundary_whenBooking_thenSuccess() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(savedActivity);
		assertNotNull(customer);
		
		Event event = new Event();
		event.setName("Event name enough tickets");
		event.setCapacity(1);
		event = eventBusiness.create(savedActivity.getId(), event);
		
		Booking booking = createBooking(customer, event);
		
		assertNotNull(booking.getId());
		assertTrue(booking.getTickets().size() == 1);
	}

	private Booking createBooking(Customer customer, Event event) {
		BookOperation bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, 1);
		bookOperation.execute();
		Booking booking = bookOperation.getBooking();
		return booking;
	}
	
	@Test(expected = IllegalStateException.class)
	public void given0Tickets_whenBooking_thenFail() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(savedActivity);
		assertNotNull(customer);
		
		Event event = new Event();
		event.setName("Event name not enough tickets");
		event.setCapacity(0);
		event = eventBusiness.create(savedActivity.getId(), event);
		
		BookOperation bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, 0);
		bookOperation.execute();
	}
	
	@Test(expected = BookOperationException.class)
	public void givenNotEnoughEventTickets_whenBooking_thenFail() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(savedActivity);
		assertNotNull(customer);
		
		Event event = new Event();
		event.setName("Event name not enough tickets");
		event.setCapacity(0);
		event = eventBusiness.create(savedActivity.getId(), event);
		
		BookOperation bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, 1);
		bookOperation.execute();
	}

	@Test(expected = BookOperationException.class)
	public void givenNotEnoughEventTicketsMultiple_whenBooking_thenFail() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(savedActivity);
		assertNotNull(customer);
		
		Event event = new Event();
		event.setName("Event name not enough tickets");
		event.setCapacity(1);
		event = eventBusiness.create(savedActivity.getId(), event);
		
		BookCommand bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, 2);
		bookOperation.execute();
	}
	
	@Test
	public void givenValidBooking_whenCancellingBooking_thenSuccess() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(booking);
		
		BookCommand cancelBookingOperation = new CancelBookingOperation(ticketBusiness, bookingBusiness, booking);
		cancelBookingOperation.execute();
		
		Booking foundBooking = bookingBusiness.read(booking.getId());
		assertTrue(foundBooking.isCancelled());
	}
	
	@Test(expected = BookOperationException.class)
	public void givenAlreadyCancelledBooking_whenCancellingBooking_thenFail() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		assertNotNull(booking);
		booking.setCancelled(true);
		
		CancelBookingOperation cancelBookingOperation = new CancelBookingOperation(ticketBusiness, bookingBusiness, booking);
		cancelBookingOperation.execute();
	}
	
	@Test(expected = BookOperationException.class)
	public void givenNonExistentBooking_whenCancellingBooking_thenFail() throws BookingException, EventFullException, InvalidEntityException, EntityNotFoundException {
		Booking booking = Booking.builder()
				.event(event)
				.customer(customer)
				.tickets(Arrays.asList(new Ticket()))
				.build();
		
		CancelBookingOperation cancelBookingOperation = new CancelBookingOperation(ticketBusiness, bookingBusiness, booking);
		cancelBookingOperation.execute();
	}
}
