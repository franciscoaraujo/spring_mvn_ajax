package com.mballem.demoajax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.demoajax.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
