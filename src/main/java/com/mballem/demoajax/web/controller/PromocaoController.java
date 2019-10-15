package com.mballem.demoajax.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mballem.demoajax.domain.Categoria;
import com.mballem.demoajax.domain.Promocao;
import com.mballem.demoajax.reposotory.CategoriaRepository;
import com.mballem.demoajax.reposotory.PromocaoRepository;


@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(PromocaoController.class);
	
	@Autowired
	private PromocaoRepository  promocaoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	
	@GetMapping("/add")
	public String abrirCadatro() {
		return "promo-add";
	}
	
	@PostMapping("/save")
	public ResponseEntity<Promocao>salvarPromocao(Promocao promocao){
		
		log.info("Promocao {} ", promocao.toString());
		promocao.setDtCadastro(LocalDate.now());
		promocaoRepository.save(promocao);
		return ResponseEntity.ok().build();
	}

	
	@ModelAttribute("categorias")//faz referencia da categoria na pagina 
	public List<Categoria> getCategorias() {
		return categoriaRepository.findAll();
	}
	
}
