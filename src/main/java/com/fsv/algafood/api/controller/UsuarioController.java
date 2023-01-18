package com.fsv.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fsv.algafood.api.assembler.UsuarioInputDisassembler;
import com.fsv.algafood.api.assembler.UsuarioModelAssembler;
import com.fsv.algafood.api.model.UsuarioModel;
import com.fsv.algafood.api.model.input.SenhaInput;
import com.fsv.algafood.api.model.input.UsuarioInput;
import com.fsv.algafood.domain.model.Usuario;
import com.fsv.algafood.domain.repository.UsuarioRepository;
import com.fsv.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioModel> listar() {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(todosUsuarios);
	}
	
	@GetMapping("/{grupoId}")
	public UsuarioModel buscar(@PathVariable Long grupoId) {
		Usuario usuario = usuarioService.buscarOuFalhar(grupoId);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		usuario = usuarioService.salvar(usuario);
		
		return usuarioModelAssembler.toModel(usuario);
	}
	
	@PutMapping("/{grupoId}")
	public UsuarioModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(grupoId);
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		
		return usuarioModelAssembler.toModel(usuarioAtual);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
}
