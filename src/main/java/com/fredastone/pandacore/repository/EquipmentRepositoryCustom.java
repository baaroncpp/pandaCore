package com.fredastone.pandacore.repository;

import com.fredastone.pandacore.entity.Equipment;

public interface EquipmentRepositoryCustom {

	Equipment findEquipmentBySerial(String serial);
}
