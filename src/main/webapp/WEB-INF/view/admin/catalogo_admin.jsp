<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Catalogo - RetroVault</title>
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
        <h1>Gestione Catalogo</h1>

        <div class="admin-actions-bar">
            <a href="${pageContext.request.contextPath}/admin/prodotti?action=prepareInsert" class="btn-primary">
                + Aggiungi Nuovo Prodotto
            </a>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn-secondary">
                Torna alla Dashboard
            </a>
        </div>

        <c:choose>
            <c:when test="${not empty prodotti}">
                <div class="table-responsive">
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Immagine</th>
                                <th>Prodotto</th>
                                <th>Categoria</th>
                                <th>Prezzo</th>
                                <th>Disp.</th>
                                <th>Stato</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${prodotti}" var="p">
                                <tr class="${not p.attivo ? 'row-inactive' : ''}">
                                    <td><strong>#${p.id}</strong></td>
                                    <td>
                                        <img src="${pageContext.request.contextPath}/images/${p.imgPath}" alt="${p.nome}" class="cart-thumb">
                                    </td>
                                    <td>
                                        <h3><c:out value="${p.nome}"/></h3>
                                        <p class="category desc-small"><c:out value="${p.descrizione}"/></p>
                                    </td>
                                    <td><c:out value="${p.categoria}"/></td>
                                    <td class="price-cell">${p.prezzo} &euro;</td>
                                    <td><strong>${p.quantitàDisponibile}</strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.attivo}">
                                                <span class="status-active">Attivo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-inactive">Disattivato</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="cart-actions">
                                            <a href="${pageContext.request.contextPath}/admin/prodotti?action=prepareUpdate&id=${p.id}" class="btn-secondary btn-small">
                                                Modifica
                                            </a>
                                            <c:if test="${p.attivo}">
                                                <a href="${pageContext.request.contextPath}/admin/prodotti?action=delete&id=${p.id}" class="btn-remove btn-small"
                                                   onclick="return confirm('Sicuro di voler disattivare questo prodotto?')">
                                                    Disattiva
                                                </a>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-cart-message">
                    <p>Nessun prodotto presente nel catalogo.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

</body>
</html>
