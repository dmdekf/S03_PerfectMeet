package com.POM.MatNam.dibs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.dibs.dto.Dibs;
import com.POM.MatNam.dibs.service.DibsService;
import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;
import com.POM.MatNam.review.DTO.Review;
import com.POM.MatNam.store.dao.StoreDao;
import com.POM.MatNam.store.dto.ResponseStore;
import com.POM.MatNam.store.dto.Store;
import com.POM.MatNam.user.dto.User;
import com.POM.MatNam.user.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dibs")
public class DibsController {
	@Autowired
	private DibsService dibsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreDao storeDao;
	
	@PostMapping
	@ApiOperation(value = "음식점 찜하기")
	public Object like(@RequestParam long sid,@RequestHeader(value = "nickname", required = true) String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		boolean check = dibsService.dibs(user.getId(), sid);
		if (!check) {
			errors.put("field", "dibs");
			errors.put("data", sid);
			final ErrorResponse result = setErrors("E-4301", "이미 찜한 상태입니다.", errors);
			response = new ResponseEntity<>(result, HttpStatus.CONFLICT);
		} else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> data = new HashMap<>();
			result.status = "S-200";
			result.message = "음식점을 찜했습니다.";
			response = new ResponseEntity<>(result, HttpStatus.CREATED);
		}
		return response;
	}
	
	@DeleteMapping
	@ApiOperation(value = "음식점 찜 삭제")
	public Object unlike(@RequestParam long sid,@RequestHeader(value = "nickname", required = true) String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		Optional<Dibs> dibsOpt = dibsService.selectByUidAndSid(user.getId(), sid);
		if(!dibsOpt.isPresent()) {
			errors.put("field", "dibs");
			errors.put("data", null);
			final ErrorResponse result = setErrors("E-4302", "존재하지 않는 찜입니다.", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> data = new HashMap<>();
			dibsService.deleteDibs(dibsOpt.get().getId());
			result.status = "S-200";
			result.message = "음식점 찜 삭제 완료했습니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping
	@ApiOperation(value = "내가 찜한 음식점 목록")
	public Object getLikeStores(@RequestHeader(value = "nickname", required = true) String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		List<Dibs> list = dibsService.dibsList(user.getId());
		List<ResponseStore> storeList  = new ArrayList<>();
		for(Dibs dibs:list) {
			long sid = dibs.getStoreId();
			Optional<Store> s = storeDao.findById(sid);
			Store store = s.get();
			storeList.add(new ResponseStore(store.getId(),store.getName(),store.getAddress(),store.getTel(),store.getImage()));
		}
		final BasicResponse result = new BasicResponse();
		result.status = "S-200";
		result.message = "음식점 찜 목록 반환.";
		result.data = storeList;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{sid}")
	@ApiOperation(value = "내가 찜한 음식점인지 확인")
	public Object getLikeStores(@RequestHeader(value = "nickname", required = true) String nickname,@PathVariable long sid) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		Optional<Dibs> dibs = dibsService.selectByUidAndSid(user.getId(), sid);
		final BasicResponse result = new BasicResponse();
		if(!dibs.isPresent()) {
			result.status = "E-4303";
			result.message = "찜 안한 상태.";
			result.data = false;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			result.status = "S-200";
			result.message = "찜한 상태.";
			result.data = true;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	private ErrorResponse setErrors(String status, String message, Map<String, Object> errors) {
		ErrorResponse res = new ErrorResponse();
		res.status = status;
		res.message = message;
		res.errors = errors;
		return res;
	}
}
