package com.teste.gerenciamento.usercase.port;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.teste.gerenciamento.entity.Pessoa;


public interface PessoaRepository {
	public Pessoa create(Pessoa newPessoa);
	public Optional<Pessoa> findByIdentificador(String identificador);
	public Optional<Pessoa> findByNome(String nome);
	public List<Pessoa> findAll();
	public Page<Pessoa> findAll(Pageable pageble);
	public Pessoa update(Pessoa pessoa);
	public void delete(String identificador);
}
