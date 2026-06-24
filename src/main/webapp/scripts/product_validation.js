const categoryPattern = /^[a-zA-Z\s]+$/;
const pricePattern = /^\d+(\.\d{1,2})?$/;
const quantityPattern = /^\d+$/;

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

function validateName(inputElem, spanElem) {
    return validateRequiredField(inputElem, spanElem, "Nome");
}

function validateDescription(inputElem, spanElem) {
    return validateRequiredField(inputElem, spanElem, "Descrizione");
}

function validateCategory(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "Categoria")) {
        return false;
    }
    if (!inputElem.value.match(categoryPattern)) {
        spanElem.innerHTML = "La categoria deve contenere solo lettere e spazi.";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validatePrice(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "Prezzo")) {
        return false;
    }
    if (!inputElem.value.match(pricePattern) || parseFloat(inputElem.value) <= 0) {
        spanElem.innerHTML = "Prezzo non valido (inserire un numero decimale positivo, es: 19.99).";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validateQuantity(inputElem, spanElem) {
    if (!validateRequiredField(inputElem, spanElem, "Quantità")) {
        return false;
    }
    if (!inputElem.value.match(quantityPattern)) {
        spanElem.innerHTML = "La quantità deve essere un numero intero non negativo.";
        inputElem.style.borderColor = "red";
        return false;
    }
    spanElem.innerHTML = "";
    inputElem.style.borderColor = "black";
    return true;
}

function validateProductForm() {
    let isNameValid = validateName(document.getElementById("nome"), document.getElementById("errorNome"));
    let isDescriptionValid = validateDescription(document.getElementById("descrizione"), document.getElementById("errorDescrizione"));
    let isCategoryValid = validateCategory(document.getElementById("categoria"), document.getElementById("errorCategoria"));
    let isPriceValid = validatePrice(document.getElementById("prezzo"), document.getElementById("errorPrezzo"));
    let isQuantityValid = validateQuantity(document.getElementById("quantita"), document.getElementById("errorQuantita"));

    return isNameValid && isDescriptionValid && isCategoryValid && isPriceValid && isQuantityValid;
}
