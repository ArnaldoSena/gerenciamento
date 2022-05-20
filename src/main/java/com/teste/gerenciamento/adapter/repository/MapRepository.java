package com.teste.gerenciamento.adapter.repository;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.teste.gerenciamento.entity.Pessoa;
import com.teste.gerenciamento.usercase.port.PessoaRepository;




@Repository
public class MapRepository implements PessoaRepository {

	private final Map<String, Pessoa> mapDB = new HashMap<>();
	
	@Override
	public Pessoa create(Pessoa newPessoa) {
		mapDB.put(newPessoa.getIdentificador(), newPessoa);
		return newPessoa;
	}

	@Override
	public Optional<Pessoa> findByIdentificador(String identificador){
		return Optional.ofNullable(mapDB.get(identificador));
	}

	@Override
	public Optional<Pessoa> findByNome(String nome) {
		return mapDB.entrySet().stream()
				.filter(map -> nome.equals(map.getValue().getNome()))
				.map(map -> map.getValue())
				.findFirst();
	}
	
	@Override
	public List<Pessoa> findAll() {
		return mapDB.values().stream().collect(Collectors.toList());
	}


	@Override
	public Pessoa update(Pessoa pessoa) {
		return mapDB.put(pessoa.getIdentificador(), pessoa);
	}

	@Override
	public void delete(String identificador) {
		mapDB.remove(identificador);
	}

	@Override
	public Page<Pessoa> findAll(Pageable pageable) {
		List<Pessoa> pessoas = mapDB.values().stream().collect(Collectors.toList());
		return new PageImpl<>(pessoas, PageRequest.of(6, 50), 400L);
	}


}
