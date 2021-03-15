package com.hotmart.challenge.api.exceptionhandlers;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ErrorModel {
	private Integer status;	
	private OffsetDateTime offsetDateTime;
	private String title;
	
	private List<Field> listFileds;
	
	@Getter
	@Setter
	@JsonInclude(Include.NON_NULL)
	public static class Field{
		private String field;
		private String message;
		
		public Field(String field, String message) {
			super();
			this.field = field;
			this.message = message;
		}
	}
}