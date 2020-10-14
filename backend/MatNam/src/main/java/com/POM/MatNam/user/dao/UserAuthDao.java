package com.POM.MatNam.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.user.dto.UserAuth;

public interface UserAuthDao extends JpaRepository<UserAuth, Long>{
	Optional<UserAuth> findByIdAndToken(Long id,String token);
	
	Optional<UserAuth> findByEmail(String email);
	
	Optional<UserAuth> findByNickname(String nickname);
}
