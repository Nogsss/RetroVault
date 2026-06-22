const emailPattern = /^\S+@\S+\.\S+$/;

function validateEmail(inputElem, spanElem) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "Il campo Email non può essere vuoto.";
        inputElem.style.borderColor = "red";
        return false;
    } 
    else if (!inputElem.value.match(emailPattern)) {
        spanElem.innerHTML = "Formato email non valido (es: nome@dominio.com)";
        inputElem.style.borderColor = "red";
        return false;
    } 
    else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validatePassword(inputElem, spanElem) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "Il campo Password non può essere vuoto.";
        inputElem.style.borderColor = "red";
        return false;
    } else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validateLoginForm() {
    let isEmailValid = validateEmail(document.getElementById("email"), document.getElementById("errorEmail"));
    let isPasswordValid = validatePassword(document.getElementById("password"), document.getElementById("errorPassword"));

    return isEmailValid && isPasswordValid;
}