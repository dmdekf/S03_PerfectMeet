package com.POM.MatNam.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Object getLikeStores(@RequestParam String loc, @RequestParam int pur) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		List<Store> storeList = storeService.recommand(loc, pur);
		List<ResponseStore> resList = new ArrayList<>();
		for(int i=0;i<3;i++) {
			resList.add(new ResponseStore(storeList.get(i).getId(),storeList.get(i).getName(),storeList.get(i).getAddress(),storeList.get(i).getTel(),storeList.get(i).getImage()));
		}
		final BasicResponse result = new BasicResponse();
		result.status = "S-200";
		result.message = "음식점 추천 목록 반환.";
		result.data = resList;
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
	
	private ErrorResponse setErrors(String status, String message, Map<String, Object> errors) {
		ErrorResponse res = new ErrorResponse();
		res.status = status;
		res.message = message;
		res.errors = errors;
		return res;
	}
}
