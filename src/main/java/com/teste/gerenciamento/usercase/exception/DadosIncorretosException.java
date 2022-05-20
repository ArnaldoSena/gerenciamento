package com.teste.gerenciamento.usercase.exception;

import lombok.Builder;

public class DadosIncorretosException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Builder
	public DadosIncorretosException(String message){
		super(message);
	}

}
