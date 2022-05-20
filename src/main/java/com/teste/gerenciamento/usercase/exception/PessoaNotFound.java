package com.teste.gerenciamento.usercase.exception;

import lombok.Builder;

public class PessoaNotFound extends Exception{
	
	private static final long serialVersionUID = 1L;

	@Builder
	public PessoaNotFound(String message) {
		super(message);
	}
}
