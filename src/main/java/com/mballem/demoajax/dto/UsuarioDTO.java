package com.mballem.demoajax.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;

import com.mballem.demoajax.domain.Usuario;



public class UsuarioDTO {
	
	@NotNull
	private Long id;
	
	@NotEmpty(message = "Preechimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preechimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioDTO(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static Collection<UsuarioDTO> converterToDTO(Collection<Usuario> list) {
		Collection<UsuarioDTO> listCliDTO = new ArrayList<UsuarioDTO>();
		for (Usuario usuario : list) {
			UsuarioDTO usDto = new UsuarioDTO();
			usDto.setId(usuario.getId());
			usDto.setNome(usuario.getNome());
			usDto.setEmail(usuario.getEmail());
			listCliDTO.add(usDto);
		}
		return listCliDTO;

	}
	
	public static Page<UsuarioDTO> converterToDTO(Page<Usuario> clientePage) {
		Page<UsuarioDTO> listDTO = clientePage.map(obj -> new UsuarioDTO(obj));
		return listDTO;
	}
}
