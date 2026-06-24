<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="${pageContext.request.contextPath}/scripts/cart_ajax.js"></script>
</head>
<body class="catalog-body">

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault</a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo" class="active">Catalogo</a>
            <a href="${pageContext.request.contextPath}/carrello">
                🛒 Carrello (<span id="cart-counter">${not empty sessionScope.carrello ? sessionScope.carrello.numeroElementi : '0'}</span>)
            </a>
            <c:if test="${sessionScope.role == 'admin'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            </c:if>
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
        <h1>Catalogo RetroVault</h1>
        
        <div class="prodotti-grid">
            <c:choose>
                <c:when test="${not empty prodotti}">
                    <c:forEach items="${prodotti}" var="prodotto">
                        <div class="prodotto-card">
                            <img src="${pageContext.request.contextPath}/images/${prodotto.imgPath}" alt="<c:out value='${prodotto.nome}'/>">
                            
                            <h2><c:out value="${prodotto.nome}"/></h2>
                            <p class="category">Categoria: <c:out value="${prodotto.categoria}"/></p>
                            <p><c:out value="${prodotto.descrizione}"/></p>
                            <h3>Prezzo: <c:out value="${prodotto.prezzo}"/> &euro;</h3>
                            
                            <div class="card-actions">
                                <input type="number" id="quantita_${prodotto.id}" name="quantita" value="1" min="1" class="qty-input">
                                <button onclick="aggiungiAlCarrello(${prodotto.id}, document.getElementById('quantita_${prodotto.id}').value, '${pageContext.request.contextPath}')" class="btn-primary">
                                    Aggiungi
                                </button>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-cart-message">
                        <p>Nessun prodotto disponibile al momento nel catalogo.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

</body>
</html>