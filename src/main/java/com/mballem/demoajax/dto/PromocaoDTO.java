package com.mballem.demoajax.dto;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.mballem.demoajax.domain.Categoria;

public class PromocaoDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message="Um titulo é requerido")
	private String titulo;
	
	
	private String descricao;
	
	@NotBlank
	private String linkImagem;
	
	@NotNull(message="O preço é requerido")
	@NumberFormat(style = Style.CURRENCY , pattern = "#,##0.00")
	private BigDecimal preco;
	
	
	
	@NotNull(message="Uma categoria é requerida")
	private Categoria categoria;



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getLinkImagem() {
		return linkImagem;
	}



	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}



	public BigDecimal getPreco() {
		return preco;
	}



	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}



	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
}
