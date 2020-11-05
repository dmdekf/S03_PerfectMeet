package com.POM.MatNam.reserve.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.reserve.dto.ReserveList;
import com.POM.MatNam.reserve.dto.ReserveWait;
import com.POM.MatNam.reserve.dto.ReserveWaitRequestDTO;
import com.POM.MatNam.reserve.service.ReserveService;
import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/reserve")

public class ReserveController {
	
	@Autowired
	private ReserveService reserveService;
	
	@GetMapping("/reserveList")
	@ApiOperation(value ="가게 예약 리스트 가져오기")
	public Object getReserveList(@RequestParam Long store_id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		Object reservelist = reserveService.reserveListStore(store_id);
		if(reservelist.equals("empty")) {
			errors.put("field", "store_id");
			errors.put("data", store_id);
			final ErrorResponse result = setErrors("E=4101", "매장에 해당 되는 리스트가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> data = new HashMap<>();
			List<ReserveList>lists = (List<ReserveList>)reservelist;
			result.status ="S-200";
			result.message = "가게 예약 리스트 조회 성공";
			data.put("list", lists);
			result.data = data;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	@GetMapping("/reserveWait")
	@ApiOperation(value ="가게 예약 요청 목록 가져오기")
	public Object getReserveWait(@RequestParam Long store_id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		Object reserveWait = reserveService.reserveWaitStore(store_id);
		if(reserveWait.equals("empty")) {
			errors.put("field", "store_id");
			errors.put("data", store_id);
			final ErrorResponse result = setErrors("E=4102", "매장에 해당 되는 요청 리스트가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> data = new HashMap<>();
			List<ReserveWait> lists = (List<ReserveWait>)reserveWait;
			result.status ="S-200";
			result.message = "가게 요청 목록 조회 성공";
			data.put("list", lists);
			result.data = data;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping("/addList")
	@ApiOperation(value ="예약 리스트 추가")
	public Object makeReservrList(@Valid @RequestBody ReserveWaitRequestDTO request) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		String mresult = reserveService.makeReservationList(request);
		if(mresult.equals("success")) {
			final BasicResponse result = new BasicResponse();
			result.status = "S-200";
			result.message = "예약 리스트 추가 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CREATED);
		}else {
			errors.put("field", "makeList");
			errors.put("store_id", request.getStore_id());
			errors.put("date", request.getDate());
			errors.put("nickname", request.getNickname());
			final ErrorResponse result = setErrors("E-4103", "존재하지 않는 예약 요청입니다", errors);
			
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		}
		
		return response;
	}
	
	@PostMapping("/addWait")
	@ApiOperation(value ="예약 요청 추가")
	public Object makeReservrWait(@Valid @RequestBody ReserveWaitRequestDTO request) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		String mresult = reserveService.makeReservationWait(request);
		if(mresult.equals("success")) {
			final BasicResponse result = new BasicResponse();
			result.status = "S-200";
			result.message = "예약 요청 추가 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CREATED);
		}else {
			errors.put("field", "makeWait");
			errors.put("store_id", request.getStore_id());
			errors.put("date", request.getDate());
			errors.put("nickname", request.getNickname());
			final ErrorResponse result = setErrors("E-4104", "이미 존재하는 예약 요청", errors);
			
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		}
		
		return response;
	}
	
	@PutMapping("/updateList")
	@ApiOperation(value="예약 리스트 변경")
	public Object updateReserveList(@Valid @RequestBody ReserveList request, @RequestHeader(value="nickname", required =true)String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		Object list = reserveService.getListById(request.getId());
		// 예약 리스트가 존재 하는지 여부 확인
		if(!list.equals("notFound")) {
			// 예약리스트가 존재 할 경우 해당 예약리스트를 변경 할 수 있는 유저인지 확인
			if(((ReserveList)list).getNickname().equals(nickname)) {
				final BasicResponse result = new BasicResponse();
				((ReserveList)list).setPeople_num(request.getPeople_num());
				((ReserveList)list).setReserve_date(request.getReserve_date());
				reserveService.updateReservationList((ReserveList)list);
				result.status = "S-200";
				result.message = "예약 정보 변경 완료";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				errors.put("field", "no Authorization");
				errors.put("nickname", request.getNickname());
				final ErrorResponse result = setErrors("E-4105", "예약 변경 권한이 없습니다.", errors);
				response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
			}
		
		}else {
			errors.put("field", "noList");
			errors.put("store_id", request.getStore_id());
			errors.put("date", request.getReserve_date());
			errors.put("nickname", request.getNickname());
			final ErrorResponse result = setErrors("E-4106", "존재 하지 않는 예약 리스트", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	@PutMapping("/updateWait")
	@ApiOperation(value="예약 요청 변경")
	public Object updateReserveWait(@Valid @RequestBody ReserveWait request, @RequestHeader(value="nickname", required =true)String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		Object wait = reserveService.getWaitById(request.getId());
		// 예약 요청이 존재 하는지 여부 확인
		if(!wait.equals("notFound")) {
			// 예약리스트가 존재 할 경우 해당 예약리스트를 변경 할 수 있는 유저인지 확인
			if(((ReserveWait)wait).getNickname().equals(nickname)) {
				final BasicResponse result = new BasicResponse();
				
				((ReserveWait)wait).setReserve_date(request.getReserve_date());
				((ReserveWait)wait).setPeople_num(request.getPeople_num());
				reserveService.updateReservationWait((ReserveWait)wait);
				result.status = "S-200";
				result.message = "예약 요청 정보 변경 완료";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				errors.put("field", "no Authorization");
				errors.put("nickname", request.getNickname());
				final ErrorResponse result = setErrors("E-4105", "예약 요청 변경 권한이 없습니다.", errors);
				response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
			}
		
		}else {
			errors.put("field", "noWait");
			errors.put("store_id", request.getStore_id());
			errors.put("date", request.getReserve_date());
			errors.put("nickname", request.getNickname());
			final ErrorResponse result = setErrors("E-4106", "존재 하지 않는 예약 요청 리스트", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	@DeleteMapping("/removeList")
	@ApiOperation(value = "예약 리스트 제거")
	public Object deleteList(@RequestParam Long store_id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)	LocalDateTime reserve_time, 
			@RequestHeader(value="nickname", required =true)String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		Object list = reserveService.getList(store_id, reserve_time, nickname);
		if(!list.equals("notFound")) {
			if(((ReserveList)list).getNickname().equals(nickname)) {
				final BasicResponse result = new BasicResponse();
				reserveService.deleteReservationList(((ReserveList)list).getId());
				result.status = "S-200";
				result.message = "예약 리스트 삭제 완료";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				errors.put("field", "no Authorization");
				errors.put("nickname", nickname);
				final ErrorResponse result = setErrors("E-4105", "예약 삭제 권한이 없습니다.", errors);
				response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
			}
		}else {
			errors.put("field", "noList");
			errors.put("store_id", store_id);
			errors.put("date", reserve_time);
			errors.put("nickname", nickname);
			final ErrorResponse result = setErrors("E-4106", "존재 하지 않는 예약 리스트", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@DeleteMapping("/removeWait")
	@ApiOperation(value = "예약 요청 제거")
	public Object deleteWait(@RequestParam Long store_id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reserve_time, 
			@RequestHeader(value="nickname", required =true)String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		Object wait = reserveService.getWait(store_id, reserve_time, nickname);
		if(!wait.equals("notFound")) {
			if(((ReserveWait)wait).getNickname().equals(nickname)) {
				final BasicResponse result = new BasicResponse();
				reserveService.deleteReservationWait(((ReserveWait)wait).getId());
				result.status = "S-200";
				result.message = "예약 요청 삭제 완료";
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				errors.put("field", "no Authorization");
				errors.put("nickname", nickname);
				final ErrorResponse result = setErrors("E-4105", "예약  요청 삭제 권한이 없습니다.", errors);
				response = new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
			}
		}else {
			errors.put("field", "noList");
			errors.put("store_id", store_id);
			errors.put("date", reserve_time);
			errors.put("nickname", nickname);
			final ErrorResponse result = setErrors("E-4106", "존재 하지 않는 예약 리스트", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
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
