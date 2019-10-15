package com.mballem.demoajax.reposotory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoajax.domain.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
	
}
