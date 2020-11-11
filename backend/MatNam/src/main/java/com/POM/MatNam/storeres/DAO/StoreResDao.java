package com.POM.MatNam.storeres.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.Board.DTO.Board;
import com.POM.MatNam.storeres.DTO.StoreRes;

public interface StoreResDao extends JpaRepository<StoreRes, Long> {
	//List<Board> findBoardBySubject(String title);
	Optional<StoreRes> findById(long id);
	Optional<StoreRes> deleteById(long id);  
}

