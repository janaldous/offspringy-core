package com.janaldous.offspringycore.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Event extends Entity {
	private Date startDate;
	private Date endDate;
	private String name;
	private boolean isCancellable;
	private int capacity;
}
