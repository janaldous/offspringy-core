package com.janaldous.offspringycore.data;

import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.domain.Event;
import com.janaldous.offspringycore.util.InMemoryRepository;

@Component
public class InMemoryEventRepository extends InMemoryRepository<Event> implements IEventRepository {

}
