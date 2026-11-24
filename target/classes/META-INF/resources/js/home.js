// =====================
// PLAYER SIMPLES (abre o link da música; sem áudio de verdade)
// =====================
let listaTocavel = [];
let indiceAtual = -1;

function atualizarTextoPlayer() {
    let texto = document.getElementById("player-texto");
    if (!texto) return;

    if (indiceAtual >= 0 && listaTocavel[indiceAtual]) {
        texto.innerText = "🎵 " + listaTocavel[indiceAtual].titulo;
    } else {
        texto.innerText = "🎵 Nenhuma música tocando";
    }
}

function tocarMusica(titulo, url) {
    indiceAtual = listaTocavel.findIndex(function (m) { return m.titulo === titulo; });
    atualizarTextoPlayer();
    window.open(url, "_blank");
}

function tocarAtual() {
    if (indiceAtual >= 0 && listaTocavel[indiceAtual]) {
        window.open(listaTocavel[indiceAtual].linkUrl, "_blank");
    }
}

function anterior() {
    if (listaTocavel.length === 0) return;
    indiceAtual = (indiceAtual - 1 + listaTocavel.length) % listaTocavel.length;
    atualizarTextoPlayer();
}

function proxima() {
    if (listaTocavel.length === 0) return;
    indiceAtual = (indiceAtual + 1) % listaTocavel.length;
    atualizarTextoPlayer();
}

// =====================
// CARREGAR MÚSICAS RECENTES (vem do banco, gerenciado pelo admin)
// =====================
function escaparAspas(texto) {
    return String(texto || "").replace(/'/g, "\\'");
}

function renderizarCardMusica(musica) {
    return `
        <div class="card">
            <img src="${musica.imagemUrl}" alt="Capa da música">
            <p class="titulo">${musica.titulo}</p>
            <p class="artista">${musica.artista}</p>
            <a href="${musica.linkUrl}" target="_blank">
                <button onclick="tocarMusica('${escaparAspas(musica.titulo)}', '${escaparAspas(musica.linkUrl)}')">▶ Tocar</button>
            </a>
            <button onclick="favoritar(this)">
                <i class="fa-solid fa-heart"></i> Favoritar
            </button>
        </div>
    `;
}

function carregarMusicasHome() {
    let container = document.getElementById("cardsRecentes");
    if (!container) return;

    fetch('http://localhost:8080/musica?categoria=RECENTE')
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (musicas) {
            listaTocavel = musicas;
            container.innerHTML = "";

            if (musicas.length === 0) {
                container.innerHTML = "<p>Nenhuma música cadastrada ainda. Peça pro administrador adicionar!</p>";
                return;
            }

            musicas.forEach(function (musica) {
                container.innerHTML += renderizarCardMusica(musica);
            });

            marcarFavoritos();
        })
        .catch(function (error) {
            console.error(error);
        });
}
