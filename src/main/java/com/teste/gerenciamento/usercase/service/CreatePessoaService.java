package com.teste.gerenciamento.usercase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.gerenciamento.entity.Pessoa;
import com.teste.gerenciamento.erro.Erro;
import com.teste.gerenciamento.usercase.port.PessoaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@AllArgsConstructor
public class CreatePessoaService {
	@Autowired
	private final PessoaRepository repository;
	
	public Pessoa create(final Pessoa pessoa){
		if(pessoa == null) {
			log.error(Erro.IDENTIFICADOR_NULO);
		}
		var newPessoa = Pessoa.builder()
				.nome(pessoa.getNome())
				.identificador(pessoa.getIdentificador())
				.tipoIdentificador(pessoa.getIdentificador())
				.build();
		return repository.create(newPessoa);
	}
}
