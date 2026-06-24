const namePattern = /^[a-zA-Z\s]+$/;
const phonePattern = /^\d{9,11}$/;
const capPattern = /^\d{5}$/;
const provPattern = /^[a-zA-Z]{2}$/;

function validateRequiredField(inputElem, spanElem, fieldName) {
    if (inputElem.value.trim() === "") {
        spanElem.innerHTML = "Il campo " + fieldName + " è obbligatorio.";
        inputElem.style.borderColor = "red";
        return false;
    } else {
        spanElem.innerHTML = "";
        inputElem.style.borderColor = "black";
        return true;
    }
}

function validateName(inputElem, spanElem, fieldName) {
    if (!validateRequiredField(inputElem, spanElem, fieldName)) {
        return false;
    }
    if (!inputElem.value.match(namePattern)) {
        spanElem.innerHTML = "Sono ammesse solo lettere e spazi.";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validatePhone(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "Telefono")) {
        return false;
    }
    if (!inputElem.value.match(phonePattern)) {
        spanElem.innerHTML = "Il numero di telefono deve contenere da 9 a 11 cifre.";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validateCap(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "CAP")) {
        return false;
    }
    if (!inputElem.value.match(capPattern)) {
        spanElem.innerHTML = "Il CAP deve essere composto da esattamente 5 cifre.";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validateProvincia(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "Provincia")) {
        return false;
    }
    if (!inputElem.value.match(provPattern)) {
        spanElem.innerHTML = "La provincia deve essere di esattamente 2 lettere (es: SA).";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validateCheckoutForm() {
    let isNomeValid = validateName(document.getElementById("nome"), document.getElementById("errorNome"), "Nome");
    let isCognomeValid = validateName(document.getElementById("cognome"), document.getElementById("errorCognome"), "Cognome");
    let isTelefonoValid = validatePhone(document.getElementById("telefono"), document.getElementById("errorTelefono"));
    let isViaValid = validateRequiredField(document.getElementById("via_num"), document.getElementById("errorViaNum"), "Via e Numero");
    let isCittaValid = validateName(document.getElementById("citta"), document.getElementById("errorCitta"), "Città");
    let isCapValid = validateCap(document.getElementById("cap"), document.getElementById("errorCap"));
    let isProvinciaValid = validateProvincia(document.getElementById("provincia"), document.getElementById("errorProvincia"));
    let isMetodoValid = validateRequiredField(document.getElementById("metodo_pagamento"), document.getElementById("errorMetodo"), "Metodo di Pagamento");

    return isNomeValid && isCognomeValid && isTelefonoValid && isViaValid && isCittaValid && isCapValid && isProvinciaValid && isMetodoValid;
}
