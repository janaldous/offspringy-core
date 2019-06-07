package com.janaldous.offspringycore.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.janaldous.offspringycore.domain.Entity;

public class InMemoryRepository<T extends Entity> implements IEntityRepository<T> {

	private Map<Long, T> map;
	private static Long idCtr;
	
	public InMemoryRepository() {
		map = new HashMap<>();
		idCtr = 1L;
	}
	
	@Override
	public T create(T t) {
		if (map.put(idCtr, t) == null && t.getId() == null) {
			t.setId(idCtr);
			idCtr++;
			return t;
		}
		
		return null;
	}

	@Override
	public T update(T t) {
		return map.put(t.getId(), t);
	}

	@Override
	public void delete(Long id) {
		map.remove(id);
	}

	@Override
	public T find(Long id) {
		return map.get(id);
	}

	@Override
	public Collection<T> findAll() {
		return map.values();
	}

}
