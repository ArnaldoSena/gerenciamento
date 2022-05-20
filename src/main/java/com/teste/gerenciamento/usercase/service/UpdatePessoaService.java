package com.teste.gerenciamento.usercase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.gerenciamento.entity.Pessoa;
import com.teste.gerenciamento.erro.Erro;
import com.teste.gerenciamento.usercase.port.PessoaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UpdatePessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public Pessoa update(final Pessoa upPessoa) {
		if(upPessoa == null) {
			log.error(Erro.PESSOA_NULO);
			return null; //TODO tratar erro
		}
		if(upPessoa.getIdentificador()==null) {
			log.error(Erro.IDENTIFICADOR_NULO);
			return null; 
		}
		try {
			Pessoa pessoa = repository.findByIdentificador(upPessoa.getIdentificador()).get();
			log.info("Atualizando pessoae id:{}", pessoa.getIdentificador());
			return repository.update(mapper(pessoa, upPessoa));
		}catch(NullPointerException npe) {
			log.error(Erro.PESSOA_INEXISTENTE, upPessoa.getIdentificador());
			npe.printStackTrace();
			return null;
		}
	}
	private Pessoa mapper(Pessoa pessoa, Pessoa upPessoa) {
		pessoa.setNome(upPessoa.getNome());
		pessoa.setIdentificador(upPessoa.getIdentificador());
		pessoa.setTipoIdentificador(upPessoa.getTipoIdentificador());
		return pessoa;
	}
}
