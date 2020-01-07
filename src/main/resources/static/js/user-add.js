//submit do formulario para o controller
$("#form-add-user").submit(function(evt) {
	//bloquear o comportamento padrão do submit
	evt.preventDefault();
	
	var usuario  = {};
	usuario.nome   = $("#nomeUsuario").val();
	usuario.email  = $("#emailUsuario").val();
	usuario.senha  = $("#senhaUsuario").val();
	usuario.perfil = $("#perfil").val();
	
	console.log('user > ', user);
	
	$.ajax({
		method: "POST",
		url: "/usuarios/save",
		data: promo,
		beforeSend: function() {
			// removendo as mensagens
			$("span").closest('.error-span').remove();
			
			//remover as bordas vermelhas
			$("#nomeUsuario").removeClass("is-invalid");
			$("#emailUsuario").removeClass("is-invalid");
			$("#senhaUsuario").removeClass("is-invalid");
			$("#perfil").removeClass("is-invalid");
			
			//habilita o loading
			$("#form-add-user").hide();
			$("#loader-form").addClass("loader").show();
		},
		success: function() {
			$("#form-add-user").each(function() {
				this.reset();
			});
			
			$("#alert")
				.removeClass("alert alert-danger")
				.addClass("alert alert-success")
				.text("OK! Usuario cadastrada com sucesso.");
		},
		statusCode: {
			422: function(xhr) {
				console.log('status error:', xhr.status);
				var errors = $.parseJSON(xhr.responseText);
				$.each(errors, function(key, val){
					$("#" + key).addClass("is-invalid");
					$("#error-" + key)
						.addClass("invalid-feedback")
						.append("<span class='error-span'>" + val + "</span>")
				});
			}
		},
		error: function(xhr) {
			console.log("> error: ", xhr.responseText);
			$("#alert").addClass("alert alert-danger").text("Não foi possível cadastrar usuario.");
		},
		complete: function() {
			$("#loader-form").fadeOut(800, function() {
				$("#form-add-user").fadeIn(250);
				$("#loader-form").removeClass("loader");
			});
		}
	});
});
