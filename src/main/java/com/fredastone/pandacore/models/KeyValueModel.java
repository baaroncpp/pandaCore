package com.fredastone.pandacore.models;

import lombok.Data;

@Data
public class KeyValueModel {
	private String name;
	private Long value;

	public KeyValueModel(String name, long value) {
		this.name = name;
		this.value = value;
	}
}
