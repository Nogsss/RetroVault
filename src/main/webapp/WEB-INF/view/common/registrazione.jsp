<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrazione - RetroVault</title>
    <script src="${pageContext.request.contextPath}/scripts/register_validation.js"></script>
</head>
<body>
    <header>
        <h1>Crea il tuo account RetroVault</h1>
    </header>
    
    <main>
    	 <!-- Se ci sono stati errori generati dalla servlet li mostriamo all'utente -->
        <c:if test="${not empty errors}">
            <div class="error-alerts">
                <ul>
                <c:forEach items="${errors}" var="error">
                    <li><c:out value="${error}"/></li>
                </c:forEach>
                </ul>
            </div>
        </c:if>
		
		 <!-- Form di registrazione-->
        <form name="registerForm" action="${pageContext.request.contextPath}/registrazione" method="POST" onsubmit="return validateRegisterForm()">
            <fieldset>
                <legend>I tuoi dati</legend>

                <div>
                    <label for="nome">Nome:</label>
                    <input type="text" id="nome" name="nome" placeholder="Inserisci nome" 
                           onchange="validateName(this, document.getElementById('errorNome'))">
                    <span id="errorNome" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <label for="cognome">Cognome:</label>
                    <input type="text" id="cognome" name="cognome" placeholder="Inserisci cognome" 
                           onchange="validateName(this, document.getElementById('errorCognome'))">
                    <span id="errorCognome" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" placeholder="Inserisci email" 
                           onchange="validateEmail(this, document.getElementById('errorEmail'))">
                    <span id="errorEmail" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <label for="password">Password (min 8 caratteri):</label>
                    <input type="password" id="password" name="password" placeholder="Crea una password"
                           onchange="validatePassword(this, document.getElementById('errorPassword'))">
                    <span id="errorPassword" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <input type="submit" value="Registrati">
                </div>
            </fieldset>
        </form>
    </main>
</body>
</html>