<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Ordini - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault <span class="admin-badge">[Admin]</span></a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </header>

    <main class="container">
        <h1>Gestione Ordini Complessivi</h1>

        <!-- Filtri di ricerca -->
        <div class="filter-box">
            <form action="${pageContext.request.contextPath}/admin/ordini" method="GET" class="filter-grid">
                
                <div class="filter-group">
                    <label for="idCliente">ID Cliente</label>
                    <input type="number" id="idCliente" name="idCliente" class="form-control" value="${idCliente}" placeholder="Es: 1">
                </div>

                <div class="filter-group">
                    <label for="dataInizio">Da Data</label>
                    <input type="date" id="dataInizio" name="dataInizio" class="form-control" value="${dataInizio}">
                </div>

                <div class="filter-group">
                    <label for="dataFine">A Data</label>
                    <input type="date" id="dataFine" name="dataFine" class="form-control" value="${dataFine}">
                </div>

                <div class="filter-actions cart-actions">
                    <input type="submit" value="Filtra" class="btn-primary">
                    <a href="${pageContext.request.contextPath}/admin/ordini" class="btn-secondary">Reset</a>
                </div>
            </form>
        </div>

        <c:if test="${not empty errors}">
            <div class="error-summary">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li><c:out value="${error}"/></li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <div class="admin-actions-bar">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn-secondary">
                Torna alla Dashboard
            </a>
        </div>

        <c:choose>
            <c:when test="${not empty ordini}">
                <div class="table-responsive">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>Ordine</th>
                                <th>Cliente ID</th>
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
                                    <td>
                                        <strong>#${ordine.idUtente}</strong>
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
                    <p>Nessun ordine trovato con i criteri di ricerca selezionati.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

</body>
</html>
