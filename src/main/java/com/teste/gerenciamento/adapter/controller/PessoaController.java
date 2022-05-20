package com.teste.gerenciamento.adapter.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teste.gerenciamento.entity.Pessoa;
import com.teste.gerenciamento.erro.Erro;
import com.teste.gerenciamento.usercase.exception.PessoaNotFound;
import com.teste.gerenciamento.usercase.mapper.PessoaMapper;
import com.teste.gerenciamento.usercase.service.CreatePessoaService;
import com.teste.gerenciamento.usercase.service.DeletePessoaService;
import com.teste.gerenciamento.usercase.service.FindPessoaService;
import com.teste.gerenciamento.usercase.service.UpdatePessoaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/gerenciamento/")
public class PessoaController {
	//Neste controller faremos uso do CRUD. Injetamos todos serviços.
	@Autowired private CreatePessoaService createService;
	@Autowired private FindPessoaService findService;
	@Autowired private UpdatePessoaService updateService;
	@Autowired private DeletePessoaService deleteService;
	
	@GetMapping("pessoas")
	@ResponseStatus(HttpStatus.OK)
	Page<PessoaResponse> getAllPessoas(@PageableDefault(page = 0, size = 5, sort = "nome",
				direction = Sort.Direction.ASC) Pageable pageable){
		log.info("Listando todos as pessoas.");
		List<PessoaResponse> clResponse = PessoaMapper.mapper(findService.findAll());
		return new PageImpl<>(clResponse, pageable, 400L);
	}

	@GetMapping("pessoas/{identificador}")
	@ResponseStatus(HttpStatus.OK)
	public PessoaResponse getPessoaById(@PathVariable("identificador") String identificador) {
		log.info("Listando dados da pessoa {}", identificador);
		Optional<Pessoa> pessoa = findService.findByIdentificador(identificador);
		if(pessoa != null) {
			return PessoaMapper.mapper(findService.findByIdentificador(identificador));
		}else {
			throw new PessoaNotFound(Erro.IDENTIFICADOR_NAO_ENCONTRADO);
		}
	}
	
	@GetMapping("pessoas/nome/{nome}")
	@ResponseStatus(HttpStatus.OK)
	public PessoaResponse getPessoaByNome(@PathVariable("nome") String nome){
		log.info("Listando dados da pessoa {}", nome);
		Optional<Pessoa> pessoa = findService.findByNome(nome);
		if(pessoa != null) {
			return PessoaMapper.mapper(pessoa);
		}else {
			throw new PessoaNotFound(Erro.PESSOA_INEXISTENTE);
		}
	}
	
	@PostMapping("pessoas")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Response<PessoaResponse>> savePessoa(@RequestBody Pessoa pessoa,
			BindingResult result) throws NoSuchAlgorithmException{	
		log.info("Cadastrando o pessoa {}.", pessoa.getNome());
		Response<PessoaResponse> response = new Response<PessoaResponse>();
		validarPessoaExistente(pessoa, result);
		if(result.hasErrors()) {
			log.error(Erro.IDENTIFICADOR_JA_EXITE, result.getAllErrors());
			result.getAllErrors()
				.forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(PessoaMapper.mapper(createService.create(pessoa)));
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("pessoas")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Response<PessoaResponse>> updatePessoa(@RequestBody Pessoa pessoa,
			BindingResult result) throws NoSuchAlgorithmException{
		log.info("Atualizando pessoae {}", pessoa.getNome());
		Response<PessoaResponse> response = new Response<PessoaResponse>();
		validarPessoaInexistente(pessoa, result);
		if(result.hasErrors()) {
			log.error(Erro.PESSOA_INEXISTENTE, pessoa.getIdentificador());
			result.getAllErrors()
			.forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(PessoaMapper.mapper(updateService.update(pessoa)));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("pessoas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePessoa(@PathVariable("id") String id) {
		deleteService.delete(id);	
	}
	
	private void validarPessoaExistente(Pessoa pessoa, BindingResult result) {
		this.findService.findByIdentificador(pessoa.getIdentificador())
			.ifPresent(pess -> result.addError(new ObjectError("Pessoa", Erro.IDENTIFICADOR_JA_EXITE)));
	}
	
	private void validarPessoaInexistente(Pessoa pessoa, BindingResult result) {
		if(this.findService.findByIdentificador(pessoa.getIdentificador()).isEmpty()) {
			result.addError(new ObjectError("Pessoa", Erro.PESSOA_INEXISTENTE));
		}
	}
}
