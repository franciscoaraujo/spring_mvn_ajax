var pageNumber = 0;

$(document).ready(function(){
	$('#loader-img').hide();
	$('#fim-btn').hide();
	
});

/*Efeito infinit scroll*/
$(window).scroll(function(){
	
	let scrollTop = $(this).scrollTop() + 1;
	let conteudo = $(document).height() - $(window).height();
	
	if(scrollTop >= conteudo){
		pageNumber++;
		setTimeout(function(){/*especie de delay no javascript*/
			loadByScrollBar(pageNumber);
		}, 200);
	}
});

function loadByScrollBar(pageNumber){
	let site = $('#autocomplete-input').val();
	$.ajax({
		method : 'GET',
		url: '/promocao/list/ajax',
		data: {
			page: pageNumber,
			site: site
			
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

//autocomplete
$('#autocomplete-input').autocomplete({
	source: function(request, response){
		$.ajax({
			method: 'GET',
			url: '/promocao/site',
			data:{
				termo: request.term
			},
			success: function(result){
				response(result);
			}
		});
	}
});


$('#autocomplete-submit').on('click', function(){
	let site = $('#autocomplete-input').val();
	$.ajax({
		method: 'GET',
		url: '/promocao/site/list',
		data:{
			site: site
		},
		beforeSend: function(){
			pageNumber = 0;
			$('#fim-btn').hide();
			$('.row').fadeOut(400, function(){
				$(this).empty();	
			});
		},
		success: function(response){
			$('.row').fadeIn(250, function(){
				$(this).append(response);
			});
		},
		error: function(xhr){
			alert('Ops algo deu errado: ' + xhr.status +", " + xhr.statusText);
		}
	});
});


//Adicionar likes
$(document).on('click','button[id*=\'likes-btn-\']' ,function(){
	let id = $(this).attr('id').split('-')[2];
	$.ajax({
		method: 'POST',
		url: '/promocao/like/'+id,
		success:function(response){
			$('#likes-count-' + id).text(response);
		},
		error:function(xhr){
			alert('Ops, ocorreu um erro: ' + xhr.status + ", " + xhr.statustText);
		}
		
	 });
	 
});




