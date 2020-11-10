package com.POM.MatNam.storeres.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;
import com.POM.MatNam.storeres.DAO.StoreResDao;
import com.POM.MatNam.storeres.DTO.StoreRes;
import com.POM.MatNam.storeres.DTO.StoreResRequest;
import com.POM.MatNam.user.dao.UserDao;
import com.POM.MatNam.user.dto.User;
import com.POM.MatNam.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/feature/storeres")
public class StoreResController {
   @Autowired
   StoreResDao storeResDao;
  
   @Autowired
   UserService userService;
   
   @Autowired
   UserDao userDao;
   
//   @ApiOperation(value="수정하기", response = StoreResController.class)
//   @PutMapping("/update")
//   public Object like(@RequestBody Board request) throws Exception {
//      Optional<Board> board = boardDao.findById(request.getId());
//      ResponseEntity response = null;
//      final BasicResponse result = new BasicResponse();
//      if (board.isPresent()) {
//         Board b = board.get();
//         b.setSubject(request.getSubject());
//         b.setContent(request.getContent());
//         boardDao.save(b);
//         result.status = true;
//         result.data = "success";
//         response = new ResponseEntity<>(result, HttpStatus.OK);
//         return response;
//      }
//      result.status = false;
//      result.data = "fail to Update";
//      response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//      return response;
//   }
	
	@ApiOperation(value = "모든 예약 가능한 가게 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public List<StoreRes> getStoreResList() throws Exception {
		
		List<StoreRes> list = storeResDao.findAll();
		return list;
	}
	
	@ApiOperation(value ="상세 보기")
	@GetMapping("/detail")
	public Object getDeatilRes(@RequestParam Long id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		
		Optional<StoreRes>res = storeResDao.findById(id);
		if(!res.isPresent()) {
			errors.put("field", "getStoreResDetail");
			errors.put("id", id);
			final ErrorResponse result = setErrors("E-4700", "해당 가게 정보가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status = "200";
			result.message="가게 등록 성공";
			result.data = res.get();
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		
		return response;
	}
	

//	@ApiOperation(value = "가게번호에 해당하는 게시글의 정보를 반환한다.", response = String.class)
//	@GetMapping("/list/{id}") 
//	public Object detailBoard(@PathVariable int id) {
//		System.out.println(id);
//		Optional<StoreRes> board = storeResDao.findById(id);
//
//		return board;
//	}  

	@ApiOperation(value = "새로운 가게정보를 입력한다.")
	@PostMapping("/write")
	public Object addStoreRes(@RequestBody StoreResRequest storeres) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		
		StoreRes temp = new StoreRes();
		temp.setAddress(storeres.getAddress());
		temp.setName(storeres.getName());
		temp.setTel(storeres.getTel());
		
		User s_User = userService.selectByNickname(storeres.getNickname());
		
		if(s_User==null) { 
			errors.put("field", "addStoreUser");
			errors.put("nickname", storeres.getNickname());
			final ErrorResponse result = setErrors("E-4701", "가게 주인으로 등록하고자 하는 유저가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			StoreRes s_temp = storeResDao.save(temp);
			s_User.setStore_id(s_temp.getId());
			userDao.save(s_User);
			result.status = "200";
			result.message="가게 등록 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		
		return response;
	}

	
	@ApiOperation(value = "가게 아이디에 해당하는 정보를 삭제한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public Optional<StoreRes> deleteStoreRes(@PathVariable("id") long id) {
		Optional<StoreRes> storeId = storeResDao.findById(id);

		storeResDao.deleteById(id);
		return null;
	}
	
	private ErrorResponse setErrors(String status, String message, Map<String, Object> errors) {
		ErrorResponse res = new ErrorResponse();
		res.status = status;
		res.message = message;
		res.errors = errors;
		return res;
	}

}
