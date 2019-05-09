package com.fredastone.pandacore.models;

import java.util.List;

import com.fredastone.pandacore.entity.Parish;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCounty {

	private SubCounty subcounty;
	private List<Parish> parish;
}
