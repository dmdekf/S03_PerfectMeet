package com.POM.MatNam.store.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.POM.MatNam.store.dto.Store;

public interface StoreDao extends JpaRepository<Store, Long>{
	@Query(value="select * from store where address like %:location% and purpose = :pur order by 1",nativeQuery = true)
	List<Store> findbyloc(@Param("location")String location,@Param("pur")String pur);
}
