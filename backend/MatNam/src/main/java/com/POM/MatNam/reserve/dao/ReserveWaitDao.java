package com.POM.MatNam.reserve.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.POM.MatNam.reserve.dto.ReserveList;
import com.POM.MatNam.reserve.dto.ReserveWait;

public interface ReserveWaitDao extends JpaRepository <ReserveWait, Long> {
	// 예약 시간과 매점 id를 이용하여 예약 요청을 반환한다.
	@Query(value="SELECT * FROM reserve_wait WHERE store_id =:store_id AND reserve_date=:reserve_time AND nickname=:nickname", nativeQuery =true)
	Optional<ReserveWait> findReserveWaitByStoreAndTimeAndNickname(Long store_id, LocalDateTime reserve_time, String nickname);
	
	// 특정 가게에 존재하는 예약 요청 리스트 들을 받는다.
	@Query(value="SELECT * FROM reserve_wait WHERE store_id =:store_id", nativeQuery=true)
	Optional<List<ReserveWait>> findReserveWaitBytStoreID(Long store_id);
	
	@Query(value="SELECT * FROM reserve_wait WHERE nickname =:nickname", nativeQuery = true)
	Optional<List<ReserveWait>> findReserveWaitByNickname(String nickname);
	
}
