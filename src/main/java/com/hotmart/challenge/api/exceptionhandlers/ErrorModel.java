package com.hotmart.challenge.api.exceptionhandlers;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorModel {
	private Integer status;	
	private OffsetDateTime offsetDateTime;
	private String title;
	
	private List<Field> listFileds;
	
	@Getter
	@Setter
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