function validarKeyPress(e, tipo) {
	var key = e.keyCode || e.which;
	var key2 = e.charCode;
	var tecla = String.fromCharCode(key).toLowerCase();
	var letras = "";
	
	if (key2 == "0") {
		return true;
	}
	
	if (tipo == "letras") {
		letras = /^[a-zA-Z]+$/;
	}
	if (tipo == "numeros") {
		letras = /^[0-9]+$/;
	}
	if (tipo == "letrasynumeros") {
		letras = /^[0-9a-zA-Z]+$/;
	}
	if (tipo == "todos") {
		letras = /^[0-9a-zA-Z\ \u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC]+$/;
	}
	if (tipo == "letrasyguion"){
		letras = /^[0-9a-zA-Z\ _]+$/; 
	}
	if (tipo == "correo"){
		letras = /^[0-9a-zA-Z-_@.]+$/; 
	}
	
	if (!tecla.match(letras)) {
		return false;
	}
}

function validarOnChange(inputElement, tipo, dialogMessage) {
	var letras = "";
	if (tipo == "letras") {
		letras = /^[a-zA-Z]+$/;
	}
	if (tipo == "numeros") {
		letras = /^[0-9]+$/;
	}
	if (tipo == "letrasynumeros") {
		letras = /^[0-9a-zA-Z]+$/;
	}
	if (tipo == "todos") {
		letras = /^[0-9a-zA-Z\ \u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00f1\u00d1\u00FC\u00DC]+$/;
	}
	if (tipo == "letrasyguion"){
		letras = /^[0-9a-zA-Z\ _]+$/; 
	}
	if (tipo == "correo"){
		letras = /^[0-9a-zA-Z-_@.]+$/; 
	}
	if (inputElement.value != "" && !inputElement.value.match(letras)) {
		inputElement.value = "";
		PF(dialogMessage).show();
	}
}

function ChangeCase(elem) {
	elem.value = elem.value.toUpperCase();
}