package com.janaldous.offspringycore.util;



public interface IEntityBusiness<T> extends IEntityBusinessBasic<T> {
	
	T create(T t) throws InvalidEntityException;
	
}
