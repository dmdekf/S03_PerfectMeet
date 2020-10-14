package com.POM.MatNam.review.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.review.DTO.Review;

public interface ReviewDao extends JpaRepository<Review, Long>{
	List<Review> findReviewByStoreIdLike(long sid);
	
	List<Review> findByNickname(String nickname);
}
