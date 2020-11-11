package com.POM.MatNam.storeres.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.storeres.DTO.StoreRes;

public interface StoreResDao extends JpaRepository<StoreRes, Long> {
	//List<Board> findBoardBySubject(String title);
	Optional<StoreRes> findById(long id);
	Optional<StoreRes> deleteById(long id);
	
	Optional<StoreRes> findByNameAndAddress(String name, String address);
	
	Optional<StoreRes> findByNickname(String nickname);
}

