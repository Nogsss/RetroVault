<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login - RetroVault</title>
    <script src="${pageContext.request.contextPath}/scripts/login_validation.js"></script>
</head>
<body>
    <header>
        <h1>Login RetroVault</h1>
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

        <!-- Form di login-->
        <form name="loginForm" action="${pageContext.request.contextPath}/login" method="POST" onsubmit="return validateLoginForm()">
            <fieldset>
                <legend>Accedi al tuo account</legend>

                <div>
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" placeholder="Inserisci email" 
                           onchange="validateEmail(this, document.getElementById('errorEmail'))">
                    <span id="errorEmail" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" placeholder="Inserisci password"
                           onchange="validatePassword(this, document.getElementById('errorPassword'))">
                    <span id="errorPassword" class="error-alerts"></span>
                </div>
                <br>
                <div>
                    <input type="submit" value="Login">
                </div>
            </fieldset>
        </form>
    </main>
</body>
</html>