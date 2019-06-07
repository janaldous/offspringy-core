package com.janaldous.offspringycore.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.janaldous.offspringycore.domain.Entity;

public class InMemoryEntityBusiness<T extends Entity> implements IEntityBusiness<T> {
	
	@Autowired
	protected InMemoryRepository<T> repository;
	
	@Override
	public T update(T t) throws InvalidEntityException,
			EntityNotFoundException {
		return repository.update(t);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public T read(Long id) throws EntityNotFoundException {
		return repository.find(id);
	}

	@Override
	public boolean exists(Long id) {
		return repository.find(id) != null;
	}

	@Override
	public T create(T t) throws InvalidEntityException {
		return repository.create(t);
	}

}
