package com.teste.gerenciamento.usercase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.gerenciamento.erro.Erro;
import com.teste.gerenciamento.usercase.port.PessoaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class DeletePessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public void delete(String id){
		if(id == null) {
			log.error(Erro.IDENTIFICADOR_NULO);
		}
		try {
			repository.delete(id);
		}catch(NullPointerException npe) {
			log.error(Erro.PESSOA_INEXISTENTE, id);
			npe.printStackTrace();
		}catch(Exception e) {
			log.error(Erro.ERRO_GERAL);
			e.printStackTrace();
		}
	}
}
