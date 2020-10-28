package com.POM.MatNam.reserve.service;

import java.time.LocalDateTime;
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
import com.POM.MatNam.reserve.dto.ReserveWaitRequestDTO;

@Service
@Transactional
public class ReserveService {
	@Autowired
	private ReserveListDao reserveListDao;
	
	@Autowired
	private ReserveWaitDao reserveWaitDao;
	
	// 특정 매장의 예약 리스트 내용을 모두 반환
	public Object reserveListStore(Long store_id){
		
		Object result;
		Optional<List<ReserveList>>optlist = reserveListDao.findReserveListBytStoreID(store_id);
		
		if(optlist.isPresent()) {
			result = optlist.get();
		}else {
			result = "empty";
		}
		
		return result;
	}
	
	// 특정 매장의 예약 요청 리스트들을 모두 반환
	public Object reserveWaitStore(Long store_id){
		
		Object result;
		Optional<List<ReserveWait>>optwait = reserveWaitDao.findReserveWaitBytStoreID(store_id);
		
		if(optwait.isPresent()) {
			result = optwait.get();
		}else {
			result = "empty";
		}
		
		return result;
	}
	
	public Object reserveListNickname(String nickname) {
		
		Object result;
		Optional<List<ReserveList>>optlist = reserveListDao.findReserveListByNickname(nickname);
		
		if(optlist.isPresent()) {
			result = optlist.get();
		}else {
			result = "empty";
		}
		
		return result;
	}
	
		public Object reserveWaitNickname(String nickname) {
		
		Object result;
		Optional<List<ReserveWait>>optwait = reserveWaitDao.findReserveWaitByNickname(nickname);
		
		if(optwait.isPresent()) {
			result = optwait.get();
		}else {
			result = "empty";
		}
		
		return result;
	}
	
	public Object getList(Long store_id, LocalDateTime reserve_time, String nickname) {
		
		Object result;
		Optional<ReserveList> list = reserveListDao.findReserveListByStoreAndTimeAndNickname(store_id, reserve_time, nickname);
		if(list.isPresent()) {
			result = list.get();
			
		}else {
			result ="notFound";
		}
		return result;
	}
	
	public Object getWait(Long store_id, LocalDateTime reserve_time, String nickname) {
		
		Object result;
		Optional<ReserveWait> wait = reserveWaitDao.findReserveWaitByStoreAndTimeAndNickname(store_id, reserve_time, nickname);
		if(wait.isPresent()) {
			result = wait.get();
			
		}else {
			result ="notFound";
		}
		return result;
	}
	
	// 유저의 과거 예약 현황 
	public Object getPastReserve(String nickname) {
		
		Object result;
		Optional<List<ReserveList>> list = reserveListDao.findPastList(nickname);
		
		if(list.isPresent()) {
			result = list.get();
		}else {
			result="notFound";
		}
		
		return result;
	}
	
	// 예약 만들기 (요청 받은 예약을 가게 관리자가 확정)
	public String makeReservationList(ReserveWaitRequestDTO request) {
		
		String result ="";
		
		Optional<ReserveWait> optWait;
		optWait = reserveWaitDao.findReserveWaitByStoreAndTimeAndNickname(request.getStore_id(), request.getDate(), request.getNickname());
		
		if(optWait.isPresent()) {
			ReserveList insertReserve = new ReserveList();
			insertReserve.setNickname(request.getNickname());
			insertReserve.setPeople_num(request.getPeople_num());
			insertReserve.setReserve_date(request.getDate());
			insertReserve.setStore_id(request.getStore_id());
			reserveListDao.save(insertReserve);
			//예약을 만들고 해당 요청을 wait 테이블에서 삭제
			reserveWaitDao.delete(optWait.get());
			
			result = "success";
		}else {
			result = "fail";
		}
			
		return result;
	}
	
	public String makeReservationWait(ReserveWaitRequestDTO request) {
		
		String result ="";
		
		Optional<ReserveWait> optWait;
		optWait = reserveWaitDao.findReserveWaitByStoreAndTimeAndNickname(request.getStore_id(), request.getDate(), request.getNickname());
		
		if(!optWait.isPresent()) {
			ReserveWait insertWait = new ReserveWait();
			insertWait.setNickname(request.getNickname());
			insertWait.setPeople_num(request.getPeople_num());
			insertWait.setReserve_date(request.getDate());
			insertWait.setStore_id(request.getStore_id());
			reserveWaitDao.save(insertWait);
			result = "success";
		}else {
			result = "fail";
		}
		
		return result;
	}
	
	// 예약 시간의 날짜, 예약 인원을 변경 할 수 있는 기능
	public void updateReservationList(ReserveList request) {
		
		reserveListDao.save(request);
			
	}
	
	// 예약 요청 리스트에서 시간, 인원을 바꿀 수 있도록 하는 기능
	public void updateReservationWait(ReserveWait request) {
		
		reserveWaitDao.save(request);
			
	}
	
	public void deleteReservationList(Long list_id) {
		
		Optional<ReserveList> optList;
		optList = reserveListDao.findById(list_id);
		
		reserveListDao.delete(optList.get());	
		
	}
	
	public void deleteReservationWait(Long wait_id) {
		
		Optional<ReserveList> optWait;
		optWait = reserveListDao.findById(wait_id);
		
		reserveListDao.delete(optWait.get());	
		
	}
	
}
