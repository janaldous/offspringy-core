package com.janaldous.offspringycore.util;

import java.util.Collection;

import com.janaldous.offspringycore.domain.Entity;


public interface IEntityRepository<T extends Entity> {
	T create(T t);
	T update(T t);
	void delete(Long id);
	T find(Long id);
	Collection<T> findAll();
}
