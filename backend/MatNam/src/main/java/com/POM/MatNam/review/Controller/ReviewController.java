package com.POM.MatNam.review.Controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.review.DAO.ReviewDao;
import com.POM.MatNam.review.DTO.Review;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("feature/review")

public class ReviewController {
	@Autowired
	ReviewDao reviewDao;
	
	@ApiOperation(value = "선택한 가게에 해당하는 리뷰들을 반환한다.", response = List.class)
	@GetMapping("{sid}/list")
	public List<Review> getReviewList(@PathVariable long sid) throws Exception {
		
		List<Review> list = reviewDao.findReviewByStoreIdLike(sid);
//		List<Post> plist = new ArrayList<Post>();
//		for (Board b : list) {
//			int lnt = heartDao.findHeartByBid(b.getId() + "").size();
//			int cnt = commentDao.findByBoardIdx(b.getId()).size();
//			List<Bimg> bi = bimgDao.findByBid(b.getId());
//			String bimg = (bi!=null && bi.size()>0)?bi.get(0).getUimage():"";
//			plist.add(new Post(b, lnt, cnt, false, bimg));
//		}
//		plist.sort((a, b) -> b.getId() - a.getId());
		return list;
	}

	@ApiOperation(value = "리뷰번호에 해당하는 게시글의 정보를 반환한다.", response = ReviewController.class)
	@GetMapping("{sid}/list/{id}")
	public Object getReviewDetail(@PathVariable long sid, @PathVariable long id) {

		
		Optional<Review> rev = reviewDao.findById(id);
		
		return rev;
	}

	@ApiOperation(value = "새로운 리뷰를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public Review writeReview(@RequestBody Review review) {

		return reviewDao.save(review);
	}

	@ApiOperation(value = "번호에 해당하는 리뷰를 삭제한다.", response = String.class)
	@DeleteMapping("delete/{id}")
	public Optional<Review> deleteBoard(@PathVariable("id") long id) {
		Optional<Review> revId = reviewDao.findById(id);
//		   System.out.println(id); 
//		   System.out.println(boardId);
		reviewDao.deleteById(id);
		return null;
	}

}
