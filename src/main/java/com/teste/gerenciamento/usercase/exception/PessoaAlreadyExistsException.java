package com.teste.gerenciamento.usercase.exception;

import lombok.Builder;

public class PessoaAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Builder
	public PessoaAlreadyExistsException(String identificador) {
		super(identificador);
	}
}
