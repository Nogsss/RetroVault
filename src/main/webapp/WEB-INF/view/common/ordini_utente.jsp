<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>I Miei Ordini - RetroVault</title>
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
                    <a href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <main class="container">
        <h1>I tuoi Ordini</h1>

        <c:choose>
            <c:when test="${not empty ordini}">
                <div class="table-responsive">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>Ordine</th>
                                <th>Data</th>
                                <th>Destinatario e Spedizione</th>
                                <th>Articoli Ordinati</th>
                                <th>Totale</th>
                                <th>Pagamento</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${ordini}" var="ordine">
                                <tr>
                                    <td>
                                        <strong>#${ordine.idOrdine}</strong>
                                    </td>
                                    <td>${ordine.data}</td>
                                    <td>
                                        <strong><c:out value="${ordine.nome} ${ordine.cognome}"/></strong><br>
                                        <span class="category">
                                            <c:out value="${ordine.viaNum}, ${ordine.citta} (${ordine.provincia}) - ${ordine.cap}"/><br>
                                            Tel: <c:out value="${ordine.telefono}"/>
                                        </span>
                                    </td>
                                    <td>
                                        <ul class="order-items-list">
                                            <c:forEach items="${ordine.dettagli}" var="dettaglio">
                                                <li>
                                                    <span class="item-name">
                                                        <c:out value="${not empty dettaglio.nomeProdotto ? dettaglio.nomeProdotto : 'Prodotto #'.concat(dettaglio.idProdotto)}"/>
                                                    </span>
                                                    <strong>x${dettaglio.quantita}</strong>
                                                    <span class="category">(${dettaglio.prezzoAcquisto} &euro;)</span>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td class="subtotal-cell">
                                        ${ordine.totale} &euro;
                                    </td>
                                    <td>
                                        <c:out value="${ordine.metodoPagamento}"/><br>
                                        <span class="category">ID: <code><c:out value="${ordine.idTransazione}"/></code></span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart-message">
                    <p>Non hai ancora effettuato nessun ordine.</p>
                    <a href="${pageContext.request.contextPath}/catalogo" class="btn-primary">Inizia gli Acquisti</a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

</body>
</html>
