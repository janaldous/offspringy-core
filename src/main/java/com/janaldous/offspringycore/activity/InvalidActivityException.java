package com.janaldous.offspringycore.activity;

import com.janaldous.offspringycore.util.InvalidEntityException;

@SuppressWarnings("serial")
public class InvalidActivityException extends InvalidEntityException {
	public InvalidActivityException(String message) {
		super(message);
	}
}
