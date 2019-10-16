package com.mballem.demoajax.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;



@SuppressWarnings("serial")
@Entity
@Table(name="promocoes")
public class Promocao implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message="Um titulo é requerido")
	@Column(name="titulo", nullable = false)
	private String titulo;
	
	@NotBlank(message="Um link da promoção é requerido")
	@Column(name="link_promocao", nullable = false)
	private String linkPromocao;
	
	@Column(name="site_promocao", nullable = false)
	private String site;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="link_imagem", nullable = false)
	private String linkImagem;
	
	@NotNull(message="O preço é requerido")
	@NumberFormat(style = Style.CURRENCY , pattern = "#,##0.00")
	@Column(name="preco_promocao", nullable = false)
	private BigDecimal preco;
	
	@Column(name="total_likes", nullable = false)
	private int likes;
	
	@Column(name="data_cadastro", nullable = false)
	private LocalDate dtCadastro;
	
	@NotNull(message="Uma categoria é requerida")
	@ManyToOne
	@JoinColumn(name="categoria_fk")
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


	public String getLinkPromocao() {
		return linkPromocao;
	}


	public void setLinkPromocao(String linkPromocao) {
		this.linkPromocao = linkPromocao;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
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


	public int getLikes() {
		return likes;
	}


	public void setLikes(int likes) {
		this.likes = likes;
	}


	public LocalDate getDtCadastro() {
		return dtCadastro;
	}


	public void setDtCadastro(LocalDate dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	
	


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promocao other = (Promocao) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
}
