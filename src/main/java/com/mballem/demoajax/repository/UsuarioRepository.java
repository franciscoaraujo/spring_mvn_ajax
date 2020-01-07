package com.mballem.demoajax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoajax.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/* fazer busca por email, o framework vai criar essa busca automaticamente */
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);
}
