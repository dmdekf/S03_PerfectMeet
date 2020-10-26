package com.POM.MatNam.reserve.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.POM.MatNam.reserve.dto.ReserveList;


public interface ReserveListDao extends JpaRepository<ReserveList, Long>{
	// 예약 시간과 매점 id를 이용하여 예약 리스트에서 해당 예약을 반환한다.
	@Query(value="SELECT * FROM reserve_list WHERE store_id =:store_id AND date=:reserve_time", nativeQuery =true)
	Optional<ReserveList> findReserveListByStoreAndTime(Long store_id, LocalDateTime reserve_time);
	
	// 특정 가게에 존재하는 예약 리스트 들을 받는다.
	@Query(value="SELECT * FROM reserve_list WHERE store_id =:store_id", nativeQuery=true)
	List<ReserveList> findReserveListBytStoreID(Long store_id);
}
