package com.marondal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marondal.dao.TestDAO;
import com.marondal.dto.TestDTO;

@RestController
public class TestController {
	
	@Autowired
	private TestDAO testDAO;
	
	@ResponseBody
	@RequestMapping(value = "/hello")
	public Map<String, String> hello() {
		Map<String, String> map = new HashMap<>();
		map.put("hello", "world");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/hello2")
	public String hello2() {
		return "hello world!";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test")
	public List<TestDTO> tests() throws Exception {
		List<TestDTO> tests = testDAO.selectTests();
		return tests;
	}
}
