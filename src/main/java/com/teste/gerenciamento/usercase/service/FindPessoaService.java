package com.teste.gerenciamento.usercase.service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.teste.gerenciamento.entity.Pessoa;
import com.teste.gerenciamento.erro.Erro;
import com.teste.gerenciamento.usercase.exception.PessoaNotFound;
import com.teste.gerenciamento.usercase.port.PessoaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FindPessoaService {
	
	@Autowired
	private final PessoaRepository repository;
	
	public Optional<Pessoa> findByIdentificador(final String identificador){
		if(identificador == null || identificador.isBlank())
			log.error(Erro.IDENTIFICADOR_NULO);
		try {
			return repository.findByIdentificador(identificador);
		} catch(NoSuchElementException nsee) {
			throw new PessoaNotFound(Erro.IDENTIFICADOR_NAO_ENCONTRADO);
		} catch(Exception e) {
			throw new PessoaNotFound(Erro.ERRO_GERAL);
		}
	}
	
	public List<Pessoa> findAll(){
		return repository.findAll();
	}
		
	public Optional<Pessoa> findByNome(final String nome){
		return repository.findByNome(nome);
	}

	public Page<Pessoa> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
