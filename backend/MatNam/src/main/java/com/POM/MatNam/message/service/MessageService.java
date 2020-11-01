package com.POM.MatNam.message.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.POM.MatNam.message.dao.MessageDao;
import com.POM.MatNam.message.dto.Message;
import com.POM.MatNam.message.dto.MessageRequestDTO;
import com.POM.MatNam.user.dto.User;
import com.POM.MatNam.user.service.UserService;

@Service
@Transactional
public class MessageService {
	
	@Autowired
	MessageDao messageDao;
	
	@Autowired
	UserService userService;
	
	// 수신자가 받을 전 메세지를 가져옴 (이미 체크한 메세지도 같이 가져옴)
	public Object getMessageByReceiver(String receiver) {
		Object result;
		
		Optional<List<Message>> optMessage = messageDao.findMessageByReceiver(receiver);
		
		if(optMessage.isPresent()) {
			result = optMessage;
		}else {
			result ="empty";
		}
		
		return result;
	}
	
	// 수진자가 받은 메세지중 체크를 안한 것 만 가져온다
	public Object getMessageByReceiverAndCheck(String receiver) {
		Object result;
		
		Optional<List<Message>> optMessage = messageDao.findMessageByReceiverAndNoCheck(receiver);
		
		if(optMessage.isPresent()) {
			result = optMessage;
		}else {
			result ="empty";
		}
		
		return result;
	}
	
	// 발신자가 보낸 모든 메세지를 가져온다.
	public Object getMessageBySender(String sender) {
		Object result;
		
		Optional<List<Message>> optMessage = messageDao.findMessageBySender(sender);
		
		if(optMessage.isPresent()) {
			result = optMessage;
		}else {
			result ="empty";
		}
		
		return result;
	}
	
	// 메세지 보내기
	public boolean SendMessage(MessageRequestDTO request) {
		boolean result = true;
		
		//수진자 확인
		User fuser = userService.selectByNickname(request.getReceiver());
		if(fuser != null) {
			Message sendMessage = new Message();
			sendMessage.setContent(request.getContent());
			sendMessage.setReceiver(request.getReceiver());
			sendMessage.setSender(request.getSender());
			messageDao.save(sendMessage);
		}else {
			result = false;
		}
		return result;
	}
	
	// 메세지 읽기
	public boolean readMessage(Long message_id) {
		
		boolean result = true;
		
		Optional<Message>optMessage = messageDao.findById(message_id);
		if(optMessage.isPresent()) {
			Message rMessage = optMessage.get();
			rMessage.setCheck(true);
			messageDao.save(rMessage);
		}else {
			result = false;
		}
		
		return result;
	}
	
	// 메세지 삭제
	public boolean deleteMessage(Long message_id) {
		
		boolean result = true;
		
		Optional<Message> optMessage = messageDao.findById(message_id);
		if(optMessage.isPresent()) {
			messageDao.delete(optMessage.get());
		}else {
			result = false;
		}
		return result;
		
	}
	
}
