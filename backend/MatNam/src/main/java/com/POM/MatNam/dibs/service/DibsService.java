package com.POM.MatNam.dibs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.POM.MatNam.dibs.dao.DibsDao;
import com.POM.MatNam.dibs.dto.Dibs;

@Service
@Transactional
public class DibsService {
	@Autowired
	private DibsDao dibsDao;
	
	public boolean dibs(long uid,long sid) {
		Optional<Dibs> likeOpt = dibsDao.findByUserIdAndStoreId(uid, sid);
		if(likeOpt.isPresent()) {
			return false;
		}else {
			Dibs like = new Dibs();
			like.setUserId(uid);
			like.setStoreId(sid);
			dibsDao.save(like);
			return true;
		}
	}
	public void deleteDibs(long id) {
		dibsDao.deleteById(id);
	}
	
	public List<Dibs> selectByUid(long uid){
		return dibsDao.findByUserId(uid);
	}
	
	public Optional<Dibs> selectByUidAndSid(long uid,long sid){
		return dibsDao.findByUserIdAndStoreId(uid, sid);
	}
	public List<Dibs> dibsList(long uid){
		return dibsDao.findByUserId(uid);
	}
}
