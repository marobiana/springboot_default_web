package com.marondal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marondal.dao.TestDAO;
import com.marondal.dto.TestDTO;

@Controller
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
	@RequestMapping(value = "/test1")
	public List<TestDTO> tests() throws Exception {
		List<TestDTO> tests = testDAO.selectTests();
		return tests;
	}
	
	// view
	@RequestMapping(value = "/test")
	public String test() throws Exception {
		return "test/test";
	}
}
