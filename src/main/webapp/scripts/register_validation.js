const emailPattern = /^\S+@\S+\.\S+$/;
const namePattern = /^[a-zA-Z\s]+$/;

function validateName(inputElem, spanElem) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "Questo campo è obbligatorio";
        inputElem.style.borderColor = "red";
        return false;
    } else if (!inputElem.value.match(namePattern)) {
        spanElem.innerHTML = "Sono ammesse solo lettere e spazi";
        inputElem.style.borderColor = "red";
        return false;
    } else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validateEmail(inputElem, spanElem) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "L'email è obbligatoria";
        inputElem.style.borderColor = "red";
        return false;
    } else if (!inputElem.value.match(emailPattern)) {
        spanElem.innerHTML = "Formato email non valido (es: nome@dominio.com)";
        inputElem.style.borderColor = "red";
        return false;
    } else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validatePassword(inputElem, spanElem) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "La password è obbligatoria";
        inputElem.style.borderColor = "red";
        return false;
    } else if (inputElem.value.length < 8) {
        spanElem.innerHTML = "La password deve contenere almeno 8 caratteri";
        inputElem.style.borderColor = "red";
        return false;
    } else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validateRegisterForm() {
    let isNomeValid = validateName(document.getElementById("nome"), document.getElementById("errorNome"));
    let isCognomeValid = validateName(document.getElementById("cognome"), document.getElementById("errorCognome"));
    let isEmailValid = validateEmail(document.getElementById("email"), document.getElementById("errorEmail"));
    let isPasswordValid = validatePassword(document.getElementById("password"), document.getElementById("errorPassword"));

    return isNomeValid && isCognomeValid && isEmailValid && isPasswordValid;
}