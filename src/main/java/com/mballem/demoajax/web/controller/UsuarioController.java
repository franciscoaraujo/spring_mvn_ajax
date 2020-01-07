package com.mballem.demoajax.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mballem.demoajax.domain.Usuario;
import com.mballem.demoajax.dto.UsuarioNewDTO;
import com.mballem.demoajax.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> salvarUsuario(@Valid @RequestBody UsuarioNewDTO usuarioNewDTO, BindingResult result) {
		
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		Usuario user = usuarioService.fromDTO(usuarioNewDTO);
		usuarioService.cadastrarNovoUsario(user);
		
		return ResponseEntity.ok().build();
	}
	
	//Adicionar usuarios
	@GetMapping("/add")
	public String abrirCadastro() {
		
		return "user-add";
	}
	
}
