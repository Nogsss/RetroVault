<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ordine Successo - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
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
                	<a href="${pageContext.request.contextPath}/common/ordini">I miei ordini</a>
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <main class="container">
        <div class="empty-cart-message">
            <div class="success-icon">✓</div>
            <h1>Ordine Completato!</h1>
            <p>Grazie per aver acquistato su RetroVault. Il tuo ordine è stato registrato con successo nel nostro database ed il carrello è stato svuotato.</p>
            
            <div class="cart-actions cart-actions-center">
                <a href="${pageContext.request.contextPath}/catalogo" class="btn-primary">
                    Torna al Catalogo
                </a>
                <a href="${pageContext.request.contextPath}/common/ordini" class="btn-secondary">
                    I miei Ordini
                </a>
            </div>
        </div>
    </main>

</body>
</html>
