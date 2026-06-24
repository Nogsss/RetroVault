<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin - RetroVault</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css">
</head>
<body>

    <header class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/catalogo">RetroVault <span class="admin-badge">[Admin]</span></a>
        </div>
        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </nav>
    </header>

    <main class="container">
        <h1>Pannello dell'Amministratore</h1>
        <p class="intro-text">Benvenuto nel pannello gestionale di RetroVault. Scegli una delle funzionalità qui sotto per iniziare.</p>
        
        <div class="prodotti-grid">
            <!-- Card Gestione Catalogo -->
            <div class="prodotto-card">
                <div class="card-icon">📦</div>
                <h2>Gestione Catalogo</h2>
                <p>Visualizza la lista completa dei prodotti, aggiungi nuovi articoli, modifica i prezzi e le descrizioni o disattiva elementi obsoleti dal catalogo.</p>
                <div class="card-actions">
                    <a href="${pageContext.request.contextPath}/admin/prodotti" class="btn-primary">Gestisci Prodotti</a>
                </div>
            </div>

            <!-- Card Gestione Ordini -->
            <div class="prodotto-card">
                <div class="card-icon">📋</div>
                <h2>Gestione Ordini</h2>
                <p>Consulta gli ordini effettuati da tutti i clienti del sito, con filtri avanzati per intervallo di date e per singolo cliente.</p>
                <div class="card-actions">
                    <a href="${pageContext.request.contextPath}/admin/ordini" class="btn-primary">Gestisci Ordini</a>
                </div>
            </div>
        </div>
    </main>

</body>
</html>
