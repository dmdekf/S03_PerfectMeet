package com.POM.MatNam.Board.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.Board.DTO.Board;

public interface BoardDao extends JpaRepository<Board, Integer> {
	//List<Board> findBoardBySubject(String title);
	List<Board> findBoardBySubjectLike(String title);
	List<Board> findBoardByContentLike(String content);
	List<Board> findBoardByNicknameLike(String nickname);
	Optional<Board> findById(int id);
	Optional<Board> deleteById(int id);  
}

