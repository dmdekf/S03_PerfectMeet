package com.POM.MatNam.File.DAO;

import org.springframework.data.repository.CrudRepository;

import com.POM.MatNam.File.Payload.UploadFile;


 
public interface FileDAO extends CrudRepository<UploadFile, Integer>{
}
