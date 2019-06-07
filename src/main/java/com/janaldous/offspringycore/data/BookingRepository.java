package com.janaldous.offspringycore.data;

import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.domain.Booking;
import com.janaldous.offspringycore.util.InMemoryRepository;

@Component
public class BookingRepository extends InMemoryRepository<Booking> {

}
