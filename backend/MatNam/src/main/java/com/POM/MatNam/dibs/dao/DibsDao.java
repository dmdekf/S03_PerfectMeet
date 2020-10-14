package com.POM.MatNam.dibs.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.POM.MatNam.dibs.dto.Dibs;

public interface DibsDao extends JpaRepository<Dibs, Long>{
	
	List<Dibs> findByUserId(long uid);
	
	Optional<Dibs> findByUserIdAndStoreId(long uid,long sid);
}
