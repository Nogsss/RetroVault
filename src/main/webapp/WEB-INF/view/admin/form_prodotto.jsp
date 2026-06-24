<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${mode == 'insert'}">Nuovo Prodotto - RetroVault</c:when>
            <c:otherwise>Modifica Prodotto - RetroVault</c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="${pageContext.request.contextPath}/scripts/product_validation.js"></script>
</head>
<body>

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault <span class="admin-badge">[Admin]</span></a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/prodotti">Prodotti</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </header>

    <main class="container">
        <div class="auth-card product-form-card">
            <h1>
                <c:choose>
                    <c:when test="${mode == 'insert'}">Aggiungi Prodotto</c:when>
                    <c:otherwise>Modifica Prodotto #${prodotto.id}</c:otherwise>
                </c:choose>
            </h1>

            <c:if test="${not empty errors}">
                <div class="error-summary">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li><c:out value="${error}"/></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form name="productForm" action="${pageContext.request.contextPath}/admin/prodotti" method="POST" onsubmit="return validateProductForm()">
                <fieldset>
                    <legend>Dati del Prodotto</legend>

                    <input type="hidden" name="action" value="${mode}">
                    <c:if test="${mode == 'update'}">
                        <input type="hidden" name="id" value="${prodotto.id}">
                    </c:if>

                    <div class="form-group">
                        <label for="nome">Nome Prodotto</label>
                        <input type="text" id="nome" name="nome" class="form-control" value="${prodotto.nome}"
                               onchange="validateName(this, document.getElementById('errorNome'))">
                        <span id="errorNome" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="descrizione">Descrizione</label>
                        <textarea id="descrizione" name="descrizione" class="form-control form-textarea" rows="4"
                                  onchange="validateDescription(this, document.getElementById('errorDescrizione'))">${prodotto.descrizione}</textarea>
                        <span id="errorDescrizione" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="categoria">Categoria</label>
                        <select id="categoria" name="categoria" class="form-control"
                                onchange="validateCategory(this, document.getElementById('errorCategoria'))">
                            <option value="">Seleziona una categoria...</option>
                            <option value="Console" ${prodotto.categoria == 'Console' ? 'selected' : ''}>Console</option>
                            <option value="Gioco" ${prodotto.categoria == 'Gioco' ? 'selected' : ''}>Gioco</option>
                            <option value="Accessorio" ${prodotto.categoria == 'Accessorio' ? 'selected' : ''}>Accessorio</option>
                        </select>
                        <span id="errorCategoria" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="prezzo">Prezzo (€)</label>
                        <input type="text" id="prezzo" name="prezzo" class="form-control" value="${prodotto.prezzo}" placeholder="Es: 49.99"
                               onchange="validatePrice(this, document.getElementById('errorPrezzo'))">
                        <span id="errorPrezzo" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="quantita">Quantità Disponibile</label>
                        <input type="number" id="quantita" name="quantita" class="form-control" value="${prodotto.quantitàDisponibile}" min="0"
                               onchange="validateQuantity(this, document.getElementById('errorQuantita'))">
                        <span id="errorQuantita" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="img_path">Nome File Immagine</label>
                        <input type="text" id="img_path" name="img_path" class="form-control" value="${prodotto.imgPath}" placeholder="Es: nintendo.jpg">
                        <span id="errorImg" class="error-alerts"></span>
                    </div>

                    <div class="form-group">
                        <label for="attivo">Stato Prodotto</label>
                        <select id="attivo" name="attivo" class="form-control">
                            <option value="true" ${prodotto.attivo or empty prodotto.attivo ? 'selected' : ''}>Attivo (Visibile al pubblico)</option>
                            <option value="false" ${not prodotto.attivo and not empty prodotto.attivo ? 'selected' : ''}>Disattivato (Nascosto)</option>
                        </select>
                    </div>

                    <div class="cart-actions cart-actions-center">
                        <input type="submit" value="Salva Prodotto" class="btn-primary flex-grow-1">
                        <a href="${pageContext.request.contextPath}/admin/prodotti" class="btn-secondary">Annulla</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </main>

</body>
</html>
