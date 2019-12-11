var pageNumber = 0;

$(document).ready(function(){
	$("#loader-img").hide();
	$("#fim-btn").hide();
});

// efeito infinte scroll
$(window).scroll(function() {
	
	var scrollTop = $(this).scrollTop();
	var conteudo = $(document).height() - $(window).height();
	
	//console.log('scrollTop: ', scrollTop, ' | ', 'conteudo', conteudo);
	
	if (scrollTop >= conteudo) {
		pageNumber++;
		setTimeout(function(){
			loadByScrollBar(pageNumber);
		}, 200);
	}
	
});

function loadByScrollBar(pageNumber) {
	var site = $("#autocomplete-input").val();
	$.ajax({
		method: "GET",
		url: "/promocao/list/ajax",
		data: {
			page: pageNumber,
			site: site
		},
		beforeSend: function() {
			$("#loader-img").show();
		},
		success: function( response ) {
			//console.log("resposta > ", response);
			//console.log("lista > ", response.length);			
			if (response.length > 150) {
			
				$(".row").fadeIn(250, function() {
					$(this).append(response);
				});
			
			} else {
				$("#fim-btn").show();
				$("#loader-img").removeClass("loader");
			}
		},
		error: function(xhr) {
			alert("Ops, ocorreu um erro: " + xhr.status + " - " + xhr.statusText);
		},
		complete: function() {
			$("#loader-img").hide();
		}
	})  
}

// autocomplete
$("#autocomplete-input").autocomplete({
	source: function(request, response) {
		$.ajax({
			method: "GET",
			url: "/promocao/site",
			data: {
				termo: request.term
			},
			success: function(result) {
				response(result);
			}
		});
	}
});

$("#autocomplete-submit").on("click", function() {
	var site = $("#autocomplete-input").val();
	$.ajax({
		method: "GET",
		url: "/promocao/site/list",
		data: {
			site : site
		},
		beforeSend: function() {
			pageNumber = 0;
			$("#fim-btn").hide();
			$(".row").fadeOut(400, function(){
				$(this).empty();
			});
		},
		success: function(response) {
			$(".row").fadeIn(250, function(){
				$(this).append(response);
			});
		},
		error: function(xhr) {
			alert("Ops, algo deu errado: " + xhr.status + ", " + xhr.statusText);
		}
	});
});

//adicionar likes
$(document).on("click", "button[id*='likes-btn-']", function() {
	var id = $(this).attr("id").split("-")[2];
	console.log("id: ", id);
	
	$.ajax({
		method: "POST",
		url: "/promocao/like/" + id,
		success: function(response) {
			$("#likes-count-" + id).text(response);
		},
		error: function(xhr) {
			alert("Ops, ocorreu um erro: " + xhr.status + ", " + xhr.statusText);
		}
	});
});

// AJAX REVERSE
var totalOfertas = 0;

$(document).ready(function() {
	init();
});

function init() {
	console.log("dwr init...");
	
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setErrorHandler(error);
	
	DWRAlertaPromocoes.init();
}

function error(excpetion) {
	console.log("dwr error: ", excpetion);
}

function showButton(count) {
	totalOfertas = totalOfertas + count;
	$("#btn-alert").show(function() {
		$(this)
			.attr("style", "display: block;")
			.text("Veja " + totalOfertas + " nova(s) oferta(s)!");
	});
}

$("#btn-alert").on("click", function() {
	
	$.ajax({
		method: "GET",
		url: "/promocao/list/ajax",
		data: {
			page : 0
		},
		beforeSend: function() {
			pageNumber = 0;
			totalOfertas = 0;
			$("#fim-btn").hide();
			$("#loader-img").addClass("loader");
			$("#btn-alert").attr("style", "display: none;");
			$(".row").fadeOut(400, function(){
				$(this).empty();
			});
		},
		success: function(response) {
			$("#loader-img").removeClass("loader");
			$(".row").fadeIn(250, function(){
				$(this).append(response);
			});
		},
		error: function(xhr) {
			alert("Ops, algo deu errado: " + xhr.status + ", " + xhr.statusText);
		}
	});
});











