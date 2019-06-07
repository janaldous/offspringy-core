package com.janaldous.offspringycore.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Booking extends Entity {
	private boolean confirmed;
	private boolean cancelled;
	private Customer customer;
	private Event event;
	private List<Ticket> tickets;
}
