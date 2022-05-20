package com.teste.gerenciamento.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {
	private String identificador;
	private String nome;
	private String tipoIdentificador;
}
