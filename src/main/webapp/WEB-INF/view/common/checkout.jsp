<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="${pageContext.request.contextPath}/scripts/checkout_validation.js"></script>
</head>
<body>

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault</a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/carrello">
                🛒 Carrello (<span id="cart-counter">${not empty sessionScope.carrello ? sessionScope.carrello.numeroElementi : '0'}</span>)
            </a>
            <c:choose>
                <c:when test="${not empty sessionScope.utente}">
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <main class="container">
        <div class="auth-card" style="max-width: 600px;">
            <h1>Spedizione & Pagamento</h1>

            <c:if test="${not empty errors}">
                <div class="error-summary">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li><c:out value="${error}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form name="checkoutForm" action="${pageContext.request.contextPath}/common/checkout" method="POST" onsubmit="return validateCheckoutForm()">
                <fieldset>
                    <legend>Dettagli dell'Ordine</legend>

                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" class="form-control" 
                               value="${not empty requestScope.nome ? requestScope.nome : sessionScope.utente.nome}"
                               onchange="validateName(this, document.getElementById('errorNome'), 'Nome')">
                        <span id="errorNome" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" class="form-control" 
                               value="${not empty requestScope.cognome ? requestScope.cognome : sessionScope.utente.cognome}"
                               onchange="validateName(this, document.getElementById('errorCognome'), 'Cognome')">
                        <span id="errorCognome" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="telefono">Telefono</label>
                        <input type="text" id="telefono" name="telefono" class="form-control" 
                               value="${requestScope.telefono}" placeholder="Es: 3451234567"
                               onchange="validatePhone(this, document.getElementById('errorTelefono'))">
                        <span id="errorTelefono" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="via_num">Indirizzo (Via/Piazza e Numero)</label>
                        <input type="text" id="via_num" name="via_num" class="form-control" 
                               value="${requestScope.via_num}" placeholder="Es: Via Roma 12"
                               onchange="validateRequiredField(this, document.getElementById('errorViaNum'), 'Via e Numero')">
                        <span id="errorViaNum" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="citta">Città</label>
                        <input type="text" id="citta" name="citta" class="form-control" 
                               value="${requestScope.citta}" placeholder="Es: Salerno"
                               onchange="validateName(this, document.getElementById('errorCitta'), 'Città')">
                        <span id="errorCitta" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="cap">CAP (5 cifre)</label>
                        <input type="text" id="cap" name="cap" class="form-control" 
                               value="${requestScope.cap}" placeholder="Es: 84084"
                               onchange="validateCap(this, document.getElementById('errorCap'))">
                        <span id="errorCap" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="provincia">Provincia (2 lettere)</label>
                        <input type="text" id="provincia" name="provincia" class="form-control" 
                               value="${requestScope.provincia}" placeholder="Es: SA" maxlength="2"
                               onchange="validateProvincia(this, document.getElementById('errorProvincia'))">
                        <span id="errorProvincia" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="metodo_pagamento">Metodo di Pagamento</label>
                        <select id="metodo_pagamento" name="metodo_pagamento" class="form-control"
                                onchange="validateRequiredField(this, document.getElementById('errorMetodo'), 'Metodo di Pagamento')">
                            <option value="">Seleziona un metodo...</option>
                            <option value="Carta di Credito" ${requestScope.metodo_pagamento == 'Carta di Credito' ? 'selected' : ''}>Carta di Credito</option>
                            <option value="PayPal" ${requestScope.metodo_pagamento == 'PayPal' ? 'selected' : ''}>PayPal</option>
                            <option value="Bonifico Bancario" ${requestScope.metodo_pagamento == 'Bonifico Bancario' ? 'selected' : ''}>Bonifico Bancario</option>
                        </select>
                        <span id="errorMetodo" class="error-alerts"></span>
                    </div>

                    <input type="submit" value="Conferma Ordine" class="btn-primary" style="margin-top: 1.5rem;">
                </fieldset>
            </form>
        </div>
    </main>

</body>
</html>
