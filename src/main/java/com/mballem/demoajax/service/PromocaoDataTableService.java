package com.mballem.demoajax.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mballem.demoajax.domain.Promocao;
import com.mballem.demoajax.reposotory.PromocaoRepository;

@Service
public class PromocaoDataTableService {
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(PromocaoDataTableService.class);
	

	private String[] cols = { "id", "titulo", "site", "linkPromocao", "descricao", "linkImagem", "preco", "likes",
			"dtCadastro"
			/*"categoria.titulo"*/ };

	public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request) {
		
		/*Existe um problema para resolver no campo Titulo da tabela, por algum motivo nao esta vindo o valor mesmo existindo na tabela*/
		
		Map<String, Object> json = new LinkedHashMap<>();

		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		
		
		int current = currentPage(start, length);

		String colunm = columnName(request);
		Sort.Direction direction = orderBy(request);

		String search = searchBy(request);

		Pageable pageable = PageRequest.of(current, length, direction, colunm);

		Page<Promocao> page = queryBy(search,repository, pageable);

		json.put("draw", draw);
		json.put("recordsTotal", page.getTotalElements());
		json.put("recordsFiltered", page.getTotalElements());
		json.put("data", page.getContent());
		
		return json;

	}

	private String searchBy(HttpServletRequest request) {
		return request.getParameter("search[value]").isEmpty() ? "" : request.getParameter("search[value]");
	}

	private Page<Promocao> queryBy(String search ,PromocaoRepository repository, Pageable pageable) {
		if(search.isEmpty()) {
			repository.findAll(pageable);
		}
		
		if(search.matches("^[0,9]+([.,][0-9]{2})?$")) {
			search = search.replace(",", ".");
			return repository.findByPreco(new BigDecimal(search), pageable);
		}
		return repository.findByTituloSiteOrCategoria(search, pageable);
	}

	private Direction orderBy(HttpServletRequest request) {
		String order = request.getParameter("order[0][dir]");
		Sort.Direction sort = Sort.Direction.ASC;
		if (order.equalsIgnoreCase("desc")) {
			sort = Sort.Direction.DESC;
		}
		return sort;
	}

	private String columnName(HttpServletRequest request) {
		int iCol = Integer.parseInt(request.getParameter("order[0][column]"));
		return cols[iCol];
	}

	private int currentPage(int start, int lenght) {
		// 0 1 2
		// 0-9 | 10-19 | 20-29
		return start / lenght;
	}
}
