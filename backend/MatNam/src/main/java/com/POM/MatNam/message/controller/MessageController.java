package com.POM.MatNam.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.message.dto.Message;
import com.POM.MatNam.message.dto.MessageRequestDTO;
import com.POM.MatNam.message.service.MessageService;
import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/message")

public class MessageController {
	@Autowired
	MessageService messageService;
	
	@GetMapping("/getReceiver")
	@ApiOperation(value ="특정 수신자가 받는 메세지 가져오기")
	public Object getReceiverMessage(@RequestParam(value="receiver", required = true) String receiver) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		Object rmlist = messageService.getMessageByReceiver(receiver);
		if(rmlist.equals("empty")) {
			errors.put("field", "receiverMessage");
			errors.put("receiver", receiver);
			final ErrorResponse result = setErrors("E-4200", "해당 수신자에 대한 메세지가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object>data = new HashMap<>();
			List<Message>lists = (List<Message>)rmlist;
			result.status="200";
			result.message="수신자 메세지 조회 성공";
			data.put("list", lists);
			result.data = data;
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@GetMapping("/checkMessage")
	@ApiOperation(value ="특정 수신자가 받은 메세지 중 확인하지 않은 메세지 가져오기")
	public Object getReceiverMessageNoCheck(@RequestParam String receiver) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		Object rmlist = messageService.getMessageByReceiverAndCheck(receiver);
		if(rmlist.equals("empty")) {
			errors.put("field", "receiverMessage");
			errors.put("receiver", receiver);
			final ErrorResponse result = setErrors("E-4201", "해당 수신자가 체크하지 않은  메세지가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object>data = new HashMap<>();
			List<Message>lists = (List<Message>)rmlist;
			result.status="200";
			result.message="수신자 메세지 조회 성공";
			data.put("list", lists);
			result.data = data;
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	
	@GetMapping("/getSender")
	@ApiOperation(value ="특정 발신자가 보낸  메세지 리스트 가져오기")
	public Object getSenderMessage(@RequestParam String sender) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		Object smlist = messageService.getMessageBySender(sender);
		if(smlist.equals("empty")) {
			errors.put("field", "senderMessage");
			errors.put("receiver", sender);
			final ErrorResponse result = setErrors("E-4202", "해당 발신자가 보낸 메세지가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object>data = new HashMap<>();
			List<Message>lists = (List<Message>)smlist;
			result.status="200";
			result.message="발신자 메세지 조회 성공";
			data.put("list", lists);
			result.data = data;
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	
	@PostMapping("/sendMessage")
	@ApiOperation(value ="메세지 보내기")
	public Object sendMessage(@RequestBody MessageRequestDTO request) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		boolean sendResult = messageService.SendMessage(request);
		if(!sendResult) {
			errors.put("field", "sendMessage");
			errors.put("receiver", request.getReceiver());
			final ErrorResponse result = setErrors("E-4203", "메세지를 받을 수신자가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status="200";
			result.message="메세지 보내기 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PutMapping("/readMessage")
	@ApiOperation(value ="메세지 읽기")
	public Object readMessage(@RequestParam(value="message_id") Long message_id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		boolean readResult = messageService.readMessage(message_id);
		if(!readResult) {
			errors.put("field", "readMessage");
			errors.put("message_id", message_id);
			final ErrorResponse result = setErrors("E-4204", "읽고자 하는 메세지가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status="200";
			result.message="메세지 읽기 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		
		return response;
	}
	
	@DeleteMapping("/deleteMessage")
	@ApiOperation(value ="메세지 읽기")
	public Object deleteMessage(@RequestParam(value="message_id") Long message_id) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object>errors = new HashMap<>();
		boolean deleteResult = messageService.deleteMessage(message_id);
		if(!deleteResult) {
			errors.put("field", "deleteMessage");
			errors.put("message_id", message_id);
			final ErrorResponse result = setErrors("E-4205", "삭제하고자 하는 메세지가 없습니다.", errors);
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		}else {
			final BasicResponse result = new BasicResponse();
			result.status="200";
			result.message="메세지 삭제 성공";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
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
