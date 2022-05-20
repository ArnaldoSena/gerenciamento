package com.teste.gerenciamento.adapter.controller;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Builder
public class PessoaResponse{
	private String nome;
	@Setter(AccessLevel.NONE)	
	private String identificador;
	private String tipoIdentificador;	
}
