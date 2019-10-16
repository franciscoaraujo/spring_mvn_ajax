var pageNumber = 0;

$(document).ready(function(){
	$('#loader-img').hide();
	$('#fim-btn').hide();
	
});

/*Efeito infinit scroll*/
$(window).scroll(function(){
	
	let scrollTop = $(this).scrollTop() + 1;
	let conteudo = $(document).height() - $(window).height();
	
	//console.log('scrollTop ',Math.trunc(scrollTop) , ' | ', 'conteudo', Math.trunc(conteudo));
	
	if(scrollTop >= conteudo){
		pageNumber++;
		setTimeout(function(){/*especie de delay no javascript*/
			loadByScrollBar(pageNumber);
		}, 200);
	}
});

function loadByScrollBar(pageNumber){
	$.ajax({
			
		method : 'GET',
		url: '/promocao/list/ajax',
		data: {
			page: pageNumber
		},
		
		beforeSend: function(){
			$('#loader-img').show();
		},
		
		success: function(response){
			//console.log('resposta', response);
			if(response.length > 150){
				$('.row').fadeIn(250, function(){
					$(this).append(response);
				});
				
			}else{
				$('#fim-btn').show();
				$('#loader-img').removeClass();
			}
		},
		
		error: function(xhr){
			alert('Ops, ocorreu um erro: ' + xhr.status + ' - ' + xhr.statusText)
		},
		
		complete: function(){
			$('#loader-img').hide();
		}
	});
}
