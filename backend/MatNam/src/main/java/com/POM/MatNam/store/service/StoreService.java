package com.POM.MatNam.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.POM.MatNam.store.dao.StoreDao;
import com.POM.MatNam.store.dto.Store;

@Service
@Transactional
public class StoreService {
	@Autowired
	private StoreDao storeDao;
	
	public List<Store> recommand(String location, String pur){
		return storeDao.findbyloc(location,pur);
	}
	
	public Store selectById(Long id){
		return storeDao.findById(id).orElse(null);
	}
}
