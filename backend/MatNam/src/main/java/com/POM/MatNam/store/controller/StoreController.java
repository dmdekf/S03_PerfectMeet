package com.POM.MatNam.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.Board.DTO.Board;
import com.POM.MatNam.dibs.dto.Dibs;
import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;
import com.POM.MatNam.store.dto.ResponseStore;
import com.POM.MatNam.store.dto.Store;
import com.POM.MatNam.store.service.StoreService;
import com.POM.MatNam.user.dto.User;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/stores")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	@GetMapping
	@ApiOperation(value = "음식점 추천")
	public Object getLikeStores(@RequestParam String loc, @RequestParam String pur) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		List<Store> storeList = storeService.recommand(loc, pur);
		final BasicResponse result = new BasicResponse();
		result.status = "S-200";
		result.message = "음식점 추천 목록 반환.";
		result.data = storeList;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "음식점 디테일")
	public Object getLikeStores(@PathVariable long id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		Store store = storeService.selectById(id);
		if(store==null) {
			errors.put("field", "id");
			errors.put("data", id);
			final ErrorResponse result = setErrors("E-4401", "없는 음식점 번호입니다.", errors);

			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		}else {
		final BasicResponse result = new BasicResponse();
			result.status = "S-200";
			result.message = "음식점 추천 목록 반환.";
			result.data = store;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}
	@ApiOperation(value = "새로운 가게 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/add")
	public Store addNewStore(@RequestBody Store store) {

		return storeService.addStore(store);
	}
	
	private ErrorResponse setErrors(String status, String message, Map<String, Object> errors) {
		ErrorResponse res = new ErrorResponse();
		res.status = status;
		res.message = message;
		res.errors = errors;
		return res;
	}
}
