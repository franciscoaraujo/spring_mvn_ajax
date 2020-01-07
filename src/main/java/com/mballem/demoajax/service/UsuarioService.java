package com.mballem.demoajax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mballem.demoajax.domain.Usuario;
import com.mballem.demoajax.dto.UsuarioNewDTO;
import com.mballem.demoajax.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Usuario cadastrarNovoUsario(Usuario obj) {
		obj.setId(null);
		repository.save(obj);
		return obj; 
	}
	
	public Usuario fromDTO(UsuarioNewDTO obj) {
		return new Usuario(null, obj.getNome(), obj.getEmail(), pe.encode(obj.getSenha()));
	}
	
}
