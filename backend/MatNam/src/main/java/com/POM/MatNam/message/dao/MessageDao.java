package com.POM.MatNam.message.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.POM.MatNam.message.dto.Message;

public interface MessageDao extends JpaRepository<Message, Long> {
	
	//특정 수신자로 메세지 검색
	@Query("SELECT * FROM message WHERE receiver=:receiver")
	Optional<List<Message>> findMessageByReceiver(String receiver);
	
	//특정 전송자로 메세지 검색
	@Query("SELECT * FROM message WHERE sender=:sender")
	Optional<List<Message>> findMessageBySender(String sender);
	
	// 특정 유저가 체크 안한 메세지만 받아 오기
	@Query("SELECT * FROM message WHERE receiver=:receiver AND check = FALSE")
	Optional<List<Message>> findMessageByReceiverAndNoCheck(String receiver);
}
