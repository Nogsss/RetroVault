<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il tuo Carrello - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="${pageContext.request.contextPath}/scripts/cart_ajax.js"></script>
</head>
<body>

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault</a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/carrello" class="active">
                🛒 Carrello (<span id="cart-counter">${not empty sessionScope.carrello ? sessionScope.carrello.numeroElementi : '0'}</span>)
            </a>
            <c:if test="${sessionScope.role == 'admin'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            </c:if>
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
        <h1>Il tuo Carrello</h1>

        <div id="cart-content">
            <c:choose>
                <c:when test="${not empty sessionScope.carrello and not empty sessionScope.carrello.prodotti}">
                    <div class="table-responsive">
                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th>Prodotto</th>
                                    <th>Prezzo Unitario</th>
                                    <th>Quantità</th>
                                    <th>Subtotale</th>
                                    <th>Azioni</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${sessionScope.carrello.prodotti}" var="item">
                                    <tr id="riga_${item.prodotto.id}">
                                        <td class="product-info">
                                            <img src="${pageContext.request.contextPath}/images/${item.prodotto.imgPath}" alt="${item.prodotto.nome}" class="cart-thumb">
                                            <div>
                                                <h3><c:out value="${item.prodotto.nome}"/></h3>
                                                <p class="category"><c:out value="${item.prodotto.categoria}"/></p>
                                            </div>
                                        </td>
                                        <td class="price-cell">
                                            <span id="prezzo_unitario_${item.prodotto.id}" data-valore="${item.prodotto.prezzo}">
                                                ${item.prodotto.prezzo}
                                            </span> &euro;
                                        </td>
                                        <td>
                                            <div class="quantity-control">
                                                <button class="qty-btn" onclick="decrementa(${item.prodotto.id}, '${pageContext.request.contextPath}')">-</button>
                                                <input type="number" id="quantita_${item.prodotto.id}" value="${item.quantità}" min="1" 
                                                       onchange="modificaQuantita(${item.prodotto.id}, this.value, '${pageContext.request.contextPath}')" class="qty-input">
                                                <button class="qty-btn" onclick="incrementa(${item.prodotto.id}, '${pageContext.request.contextPath}')">+</button>
                                            </div>
                                        </td>
                                        <td class="subtotal-cell">
                                            <span id="subtotale_${item.prodotto.id}">${item.prezzoTot}</span> &euro;
                                        </td>
                                        <td>
                                            <button onclick="eliminaElemento(${item.prodotto.id}, '${pageContext.request.contextPath}')" class="btn-remove">
                                                Rimuovi
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="cart-summary">
                        <div class="total-section">
                            <h2>Totale Ordine: <span id="prezzo-totale">${sessionScope.carrello.prezzoTotale()}</span> &euro;</h2>
                        </div>
                        <div class="cart-actions">
                            <button onclick="svuotaInteroCarrello('${pageContext.request.contextPath}')" class="btn-secondary">
                                Svuota Carrello
                            </button>
                            <a href="${pageContext.request.contextPath}/catalogo" class="btn-secondary">
                                Continua lo Shopping
                            </a>
                            <a href="${pageContext.request.contextPath}/common/checkout" class="btn-primary">
                                Procedi al Checkout
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-cart-message">
                        <p>Il tuo carrello è vuoto. Cerca qualcosa nel catalogo!</p>
                        <a href="${pageContext.request.contextPath}/catalogo" class="btn-primary">Vai al Catalogo</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

</body>
</html>
