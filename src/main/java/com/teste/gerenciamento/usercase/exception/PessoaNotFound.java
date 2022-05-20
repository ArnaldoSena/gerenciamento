package com.teste.gerenciamento.usercase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Builder;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaNotFound extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	@Builder
	public PessoaNotFound(String message) {
		super(message);
	}

}
