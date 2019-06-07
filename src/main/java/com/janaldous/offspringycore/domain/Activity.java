package com.janaldous.offspringycore.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Activity extends Entity {
	@NonNull
	private String name;
	@NonNull
	private ActivityType type;
	@NonNull
	private String description;
	private List<Event> event;
	private Provider provider;
}
