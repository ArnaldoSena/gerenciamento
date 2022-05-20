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
	private static final int TAMANHO_CPF = 11;
	private static final int TAMANHO_CNPJ = 14;
	private static final String CPF = "CPF";
	private static final String CNPJ = "CNPJ";
	@Autowired
	private final PessoaRepository repository;
	
	public Pessoa create(final Pessoa pessoa){
		if(pessoa == null) {
			log.error(Erro.IDENTIFICADOR_NULO);
			return null;
		}
		
		String identificador = pessoa.getIdentificador().replace(" ", "")
				.replace(".", "")
				.replace("-", "")
				.replace("/", "");
		
		if(identificador.length() == TAMANHO_CPF) {
			pessoa.setTipoIdentificador(CPF);
		} else if(identificador.length() == TAMANHO_CNPJ) {
			pessoa.setTipoIdentificador(CNPJ);
		} else {
			log.error(Erro.IDENTIFICADOR_MAL_FORMATADO);
			return null;
		}
		var newPessoa = Pessoa.builder()
				.nome(pessoa.getNome())
				.identificador(pessoa.getIdentificador())
				.tipoIdentificador(pessoa.getTipoIdentificador())
				.build();
		return repository.create(newPessoa);
	}
}
