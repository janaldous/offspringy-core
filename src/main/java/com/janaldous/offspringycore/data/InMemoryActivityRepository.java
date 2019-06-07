package com.janaldous.offspringycore.data;

import org.springframework.stereotype.Component;

import com.janaldous.offspringycore.domain.Activity;
import com.janaldous.offspringycore.util.InMemoryRepository;

@Component
public class InMemoryActivityRepository extends InMemoryRepository<Activity> implements IActivityRepository {

}
