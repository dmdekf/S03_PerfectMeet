package com.POM.MatNam.reserve.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.POM.MatNam.reserve.dao.ReserveListDao;
import com.POM.MatNam.reserve.dao.ReserveWaitDao;
import com.POM.MatNam.reserve.dto.ReserveList;
import com.POM.MatNam.reserve.dto.ReserveListRequestDTO;
import com.POM.MatNam.reserve.dto.ReserveWait;

@Service
@Transactional
public class ReserveService {
	@Autowired
	private ReserveListDao reserveListDao;
	
	@Autowired
	private ReserveWaitDao reserveWaitDao;
	
	// 특정 매장의 예약 리스트 내용을 모두 반환
	public List<ReserveList> reserveListStore(Long store_id){
		return reserveListDao.findReserveListBytStoreID(store_id);
	}
	
	// 특정 매장의 예약 요청 리스트들을 모두 반환
	public List<ReserveWait> reserveWaitStore(Long store_id){
		return reserveWaitDao.findReserveWaitBytStoreID(store_id);
	}
	
	// 예약 시간 만들기 (에약을 할 수 있는 시간을 리스트에 만들기)
	public String makeReservationList(ReserveListRequestDTO request) {
		
		String result ="";
		
		Optional<ReserveList> optList;
		optList = reserveListDao.findReserveListByStoreAndTime(request.getStore_id(), request.getDate());
		
		if(!optList.isPresent()) {
			ReserveList insertReserve = optList.get();
			insertReserve.setReserve(false);
			reserveListDao.save(insertReserve);
			result = "success";
		}else {
			result = "fail";
		}
			
		return result;
	}
	
	public String makeReservationWait(ReserveListRequestDTO request) {
		
		String result ="";
		
		Optional<ReserveWait> optWait;
		optWait = reserveWaitDao.findReserveWaitByStoreAndTime(request.getStore_id(), request.getDate());
		
		if(!optWait.isPresent()) {
			ReserveWait insertWait = optWait.get();
			reserveWaitDao.save(insertWait);
			result = "success";
		}else {
			result = "fail";
		}
		
		return result;
	}
	
	// 예약 시간의 날짜, 예약 여부를 변경 할 수 있는 기능
	public String updateReservationList(ReserveList request) {
		String result ="";
		
		Optional<ReserveList> optList;
		optList = reserveListDao.findById(request.getId());
		
		if(optList.isPresent()) {
			ReserveList updateList = optList.get();
			updateList.setReserve(request.isReserve());
			updateList.setReserve_date(request.getReserve_date());
			reserveListDao.save(updateList);
			result = "success";
			
		}else {
			// 존재 하지 않는 예약
			result = "no exist ReserveList";
		}
		
		return result;
	}
	
	// 예약 요청 리스트에서 시간을 바꿀 수 있도록 하는 기능
	public String updateReservationWait(ReserveWait request) {
		String result = "";
		
		Optional<ReserveWait> optWait;
		optWait = reserveWaitDao.findById(request.getId());
		
		if(optWait.isPresent()) {
			ReserveWait updateWait = optWait.get();
			updateWait.setReserve_date(request.getReserve_date());
			reserveWaitDao.save(updateWait);
			result = "success";
		}else {
			// 존재하지 않는 예약 요청
			result ="not exist ReserveWait";
		}
		
		return result;
	}
	
	public String deleteReservationList(Long list_id) {
		String result = "";
		
		Optional<ReserveList> optList;
		optList = reserveListDao.findById(list_id);
		
		if(optList.isPresent()) {
			reserveListDao.delete(optList.get());
			result = "success";
		}else {
			result = "no exist ReserveList";
		}
		
		return result;
	}
	
	public String deleteReservationWait(Long wait_id) {
		String result = "";
		
		Optional<ReserveList> optWait;
		optWait = reserveListDao.findById(wait_id);
		
		if(optWait.isPresent()) {
			reserveListDao.delete(optWait.get());
			result = "success";
		}else {
			result = "no exist ReserveWait";
		}
		
		return result;
	}
	
}
