<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="${pageContext.request.contextPath}/scripts/register_validation.js"></script>
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
            <a href="${pageContext.request.contextPath}/login">Login</a>
        </nav>
    </header>
    
    <main class="container">
        <div class="auth-card">
            <h1>Crea account</h1>

            <c:if test="${not empty errors}">
                <div class="error-summary">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li><c:out value="${error}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form name="registerForm" action="${pageContext.request.contextPath}/registrazione" method="POST" onsubmit="return validateRegisterForm()">
                <fieldset>
                    <legend>I tuoi dati personali</legend>

                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" class="form-control" placeholder="Inserisci nome" 
                               onchange="validateName(this, document.getElementById('errorNome'))">
                        <span id="errorNome" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" class="form-control" placeholder="Inserisci cognome" 
                               onchange="validateName(this, document.getElementById('errorCognome'))">
                        <span id="errorCognome" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="nome@dominio.com" 
                               onchange="validateEmail(this, document.getElementById('errorEmail'))">
                        <span id="errorEmail" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="password">Password (min 8 caratteri)</label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="Crea una password"
                               onchange="validatePassword(this, document.getElementById('errorPassword'))">
                        <span id="errorPassword" class="error-alerts"></span>
                    </div>

                    <input type="submit" value="Registrati" class="btn-primary">
                </fieldset>
            </form>

            <div class="auth-links">
                <p>Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi</a></p>
            </div>
        </div>
    </main>
</body>
</html>