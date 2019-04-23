package com.fredastone.pandacore.service;

import com.fredastone.pandacore.entity.Lease;

public interface LeaseService {
	
	Lease addNewLease(Lease lease);
	Lease updateLease(Lease lease);

}
