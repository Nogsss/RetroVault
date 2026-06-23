<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Catalogo - RetroVault</title>
    <script src="${pageContext.request.contextPath}/scripts/cart_ajax.js"></script>
</head>
<body>

    <header>
    	<h1>Catalogo RetroVault</h1>
    	
        <nav>
        	<a href="${pageContext.request.contextPath}/carrello">
        	    🛒 Carrello (<span id="cart-counter">${not empty sessionScope.carrello ? sessionScope.carrello.numeroElementi : '0'}</span>)
        	</a>
        
        	<c:if test="${sessionScope.role == 'admin'}">
        		<a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
       		</c:if>
        	<c:if test="${not empty sessionScope.utente}">
        	        <a href="${pageContext.request.contextPath}/logout">Logout</a>
        	</c:if>
    	</nav>
    </header>

    <main>
        <div class="prodotti-grid">
            <!-- Controlliamo se la lista prodotti non è vuota -->
            <c:choose>
                <c:when test="${not empty prodotti}">
                    <!-- Iteriamo sulla lista passata dalla Servlet -->
                    <c:forEach items="${prodotti}" var="prodotto">
                        <div class="prodotto-card">
                            <img src="${pageContext.request.contextPath}/images/${prodotto.imgPath}" alt="<c:out value='${prodotto.nome}'/>">
                            
                            <h2><c:out value="${prodotto.nome}"/></h2>
                            <p>Categoria: <c:out value="${prodotto.categoria}"/></p>
                            <p><c:out value="${prodotto.descrizione}"/></p>
                            <h3>Prezzo: <c:out value="${prodotto.prezzo}"/> &euro;</h3>
                            
                            <a href="${pageContext.request.contextPath}/dettaglio?id=${prodotto.id}">Vedi Dettagli</a>
                            <button onclick="aggiungiAlCarrello(${prodotto.id}, document.getElementById('quantita_${prodotto.id}').value))">Aggiungi al Carrello</button>
                            <input type="number" id="quantita_${prodotto.id}" name="quantita" value="1" min="1">
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>Nessun prodotto disponibile al momento nel catalogo.</h2>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

</body>
</html>