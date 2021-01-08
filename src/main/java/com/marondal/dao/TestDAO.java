package com.marondal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.marondal.dto.TestDTO;

@Repository
@Mapper
public interface TestDAO {
	
	public List<TestDTO> selectTests();
}
