package com.POM.MatNam.reserve.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.POM.MatNam.reserve.dto.ReserveList;


public interface ReserveListDao extends JpaRepository<ReserveList, Long>{
	// 예약 시간과 매점 id를 이용하여 예약 리스트에서 해당 예약을 반환한다.
	@Query(value="SELECT * FROM reserve_list WHERE store_id =:store_id AND date=:reserve_time AND nickname=:nickname", nativeQuery =true)
	Optional<ReserveList> findReserveListByStoreAndTimeAndNickname(Long store_id, LocalDateTime reserve_time, String nickname);
	
	// 특정 가게에 존재하는 예약 리스트 들을 받는다.
	@Query(value="SELECT * FROM reserve_list WHERE store_id =:store_id", nativeQuery=true)
	Optional<List<ReserveList>> findReserveListBytStoreID(Long store_id);
	
	// 특정 유저의 예약 리스트 현황을 받는다.
	@Query(value="SELECT * FROM reserve_list WHERE nickname =:nickname AND reserve_time >=now()", nativeQuery = true)
	Optional<List<ReserveList>> findReserveListByNickname(String nickname);
	
	// 유저의 과거 예약 이력을 볼 수 있게 해줌
	@Query(value="SELECT * FROM reserve_list WHERE nickname =:nickname AND reserve_time <= now()", nativeQuery = true)
	Optional<List<ReserveList>> findPastList(String nickname);
	
}
