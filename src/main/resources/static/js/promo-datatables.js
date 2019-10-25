$(document).ready(function(){
//	moment.locale('pt-br');
	
	var table =	$('#table-server').DataTable({
		
		processing: true,
		serverSide:  true,
		responsive: true,
		lengthMenu: [10, 15, 20, 25],
		
		columns:[
			{data: 'id'},
			{data: 'titulo'},
			{data: 'site'},
			{data: 'linkPromocao'},
			{data: 'descricao'},
			{data: 'linkImagem'},
			{data: 'preco', render: $.fn.dataTable.render.number('.',',' , 2, 'R$ ')},
			{data: 'likes'},
			{data: 'dtCadastro'},
			{data: 'promocao.categoria.titulo'} //verifica porque nao esta vindo esse valor na requisicao
		],
		ajax:{
			url: '/promocao/datatables/server',
			data: 'data'
		},
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'editar',
				attr: {
					id:'btn-editar',
					type: 'button'
				},
				enabled: false
			},
			{
				text: 'Excluir',
				attr:{
					id: 'btn-excluir',
					type: 'button'
				},
				enabled: false
			}
		]
	});
});
 	
//acao para marcar/desmarcar botoes ao clicar na ordenacao
$('#table-server thead').on('click','tr', function(){
	table.buttons().disable();
});


//acao para marcar/desmarcar linha clicadas
$('#table-server tbody').on('click','tr', function(){
	if($(this).hasClass('selected')){
		$(this).removeClass('selected');
		table.buttons().disable();
	}else{
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
		table.buttons().enable();
	}
});
	
//acao do botao de editar (abrir)
$('#btn-editar').on('click', function(){
	if(isSelectedRow()){
		let id = getPromoId();
		$.ajax({
			method: 'GET',
			url: '/promocao/edit/' + id,
			beforeSend: function(){
				$('#modal-form').modal('show');
			},
			success: function(data){
				$('#edt_id').val(data.id);
				$('#edt_site').text(data.site);
				$('#edt_titulo').val(data.titulo);
				$('#edt_descricao').val(data.descricao);
				$('#edt_preco').val(data.preco.toLocaleString('pt-BR', {minimunFractionDigits:2, maximunFractionDigits:2}));
				$('#edt_categoria').val(data.categoria.id);
				$('#edt_linkImagem').val(data.linkImagem);
				$('#edt_imagem').attr('src', data.linkImagem);
			},
			error: function(){
				alert.log('ops..ocorreu um erro tente mais tarde.');
			}
		});
	}
});

/*submit do formulario para editar*/
$('#btn-edit-modal').on('click', function () {
	
	let promo = {};
	promo.descricao = $('#edt_descricao').val();
	promo.preco = $('#edt_preco').val();
	promo.titulo = $('#edt_titulo').val();
	promo.categoria = $('#edt_categoria').val();
	promo.linkImagem = $('#edt_linkImagem').val();
	promo.id = $("#edt_id").val();
	
	$.ajax({
		method: 'POST',
		url: '/promocao/edit',
		data: promo,
		success: function(){
			$('#modal-form').modal('hide');
			table.ajax.reload();
		},
		
		statusCode: function(xhr){
			let errors = $.parserJson(xhr.responseText);
			$.each(errors, function(key, val){
				$('#edt_'+key).addClass('is-invalid');
				$('#error-'+key)
						.addClass('invalid-feedback')
						.addClass('<span class=\'error-span\'>' + val + '</span>');
			});
		}
	});
});

/*Alterar a imagem no componente <img> da modal*/
$('#edt_linkImagem').on('change', function(){
	let link = $('this').val();
	$('#edt_imagem').attr('src', link);
});

/*apresentando o modal para exclusao*/
$('#btn-excluir').on('click', function(){
	if(isSelectedRow()){
		$('#modal-delete').modal('show');
	}
});	

//exclusao de uma promocao
$('#btn-del-modal').on('click', function(){
	//recuperar o id da linha selecionada
	let id = getPromoId();
	$.ajax({
		method: 'GET',
		url: '/promocao/delete/' + id,
		success: function(){
			$('#modal-delete').modal('hide');
			table.ajax.reload();
		},
		erro: function(){
			alert('Ops... ocorreu um erro tente mais tarde. ');
		}
	});
});

function getPromoId(){
	 return table.row(table.$('tr.selected')).data().id;
}

function isSelectedRow(){
	let trow = table.row(table.$('tr.selected'));
	return trow.data() !== undefined;
} 
	
