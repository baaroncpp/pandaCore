package com.fredastone.pandacore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.Installation;
import com.fredastone.pandacore.repository.InstallationRepository;
import com.fredastone.pandacore.service.InstallationService;


@Service
public class InstallationServiceImpl implements InstallationService{

	private InstallationRepository installDao;
	
	@Autowired
	public InstallationServiceImpl(InstallationRepository installDao) {
		// TODO Auto-generated constructor stub
		this.installDao = installDao;
	}
	
	@Override
	public Installation makeNewInstallation(Installation installation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Installation updateInstallation(Installation installation) {
		// TODO Auto-generated method stub
		return null;
	}

}
