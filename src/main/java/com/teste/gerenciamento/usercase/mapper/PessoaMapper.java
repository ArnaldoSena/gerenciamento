package com.teste.gerenciamento.usercase.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.teste.gerenciamento.adapter.controller.PessoaResponse;
import com.teste.gerenciamento.entity.Pessoa;



public class PessoaMapper {

	public static PessoaResponse mapper(Optional<Pessoa> pessoaOpt) {
		Pessoa pessoa = pessoaOpt.get();
		return mapper(pessoa);
	}
	
	/**
	 * 
	 * @param pessoa
	 * @return pessoa com tratamento no cpf
	 */
	public static PessoaResponse mapper(Pessoa pessoa) {
		return	PessoaResponse.builder()
				.nome(pessoa.getNome())
				.identificador(pessoa.getIdentificador())
				.tipoIdentificador(pessoa.getTipoIdentificador())
				.build();
	}
	/**
	 * 
	 * @param pessoas
	 * @return Lista de pessoas
	 */
	public static List<PessoaResponse> mapper(List<Pessoa> pessoas){
		return pessoas.stream().map(c -> mapper(c))
				.collect(Collectors.toList());
	}
}
