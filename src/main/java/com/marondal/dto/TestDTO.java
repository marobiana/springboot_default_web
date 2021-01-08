package com.marondal.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@JsonSerialize
public class TestDTO {
	@JsonProperty
	private int id;
	@JsonProperty
	private String name;
}
