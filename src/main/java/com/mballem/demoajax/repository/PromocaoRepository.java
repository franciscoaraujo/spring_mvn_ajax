package com.mballem.demoajax.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoajax.domain.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long> {
	
	@Query("select count(p.id) as count, max(p.dtCadastro) as lastDate "
			+ "from Promocao p where p.dtCadastro > :data")
	Map<String, Object> totalAndUltimaPromocaoByDataCadastro(@Param("data") LocalDateTime data);
	
	@Query("select p.dtCadastro from Promocao p")
	Page<LocalDateTime> findUltimaDataDePromocao(Pageable pageable);
	
	@Query("select p from Promocao p where p.preco = :preco")
	Page<Promocao> findByPreco(@Param("preco") BigDecimal preco, Pageable pageable);
	
	@Query("select p from Promocao p where p.titulo like %:search% "
			+ "or p.site like %:search% "
			+ "or p.categoria.titulo like %:search%")
	Page<Promocao> findByTituloOrSiteOrCategoria(@Param("search") String search, Pageable pageable);
	
	@Query("select p from Promocao p where p.site like :site")
	Page<Promocao> findBySite(@Param("site") String site, Pageable pageable);
	
	@Query("select distinct p.site from Promocao p where p.site like %:site%")
	List<String> findSitesByTermo(@Param("site") String site);

	@Transactional(readOnly = false)
	@Modifying
	@Query("update Promocao p set p.likes = p.likes + 1 where p.id = :id")
	void updateSomarLikes(@Param("id") Long id);
	
	@Query("select p.likes from Promocao p where p.id = :id")
	int findLikesById(@Param("id") Long id);
}
 