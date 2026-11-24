function renderizarCardMusicaBiblioteca(musica) {
    return `
        <div class="card">
            <img src="${musica.imagemUrl}" alt="Capa da música">
            <p class="titulo">${musica.titulo}</p>
            <p class="artista">${musica.artista}</p>
            <a href="${musica.linkUrl}" target="_blank">
                <button>▶ Tocar</button>
            </a>
            <button onclick="favoritar(this)">
                <i class="fa-solid fa-heart"></i> Favoritar
            </button>
        </div>
    `;
}

function carregarMusicasBiblioteca() {
    let container = document.getElementById("cardsMaisOuvidas");
    if (!container) return;

    fetch('http://localhost:8080/musica?categoria=MAIS_OUVIDA')
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (musicas) {
            container.innerHTML = "";

            if (musicas.length === 0) {
                container.innerHTML = "<p>Nenhuma música cadastrada ainda. Peça pro administrador adicionar!</p>";
                return;
            }

            musicas.forEach(function (musica) {
                container.innerHTML += renderizarCardMusicaBiblioteca(musica);
            });

            marcarFavoritos();
        })
        .catch(function (error) {
            console.error(error);
        });
}

// =====================
// PLAYLISTS (vem do banco, gerenciado pelo admin)
// =====================
function renderizarCardPlaylist(playlist) {
    return `
        <div class="card">
            <img src="${playlist.imagemUrl}" alt="Capa da playlist">
            <p class="titulo">${playlist.titulo}</p>
            <p class="artista">${playlist.descricao || ""}</p>
            <a href="${playlist.linkUrl}" target="_blank">
                <button>▶ Abrir</button>
            </a>
        </div>
    `;
}

function carregarPlaylistsBiblioteca() {
    let container = document.getElementById("cardsPlaylists");
    if (!container) return;

    fetch('http://localhost:8080/playlist')
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (playlists) {
            container.innerHTML = "";

            if (playlists.length === 0) {
                container.innerHTML = "<p>Nenhuma playlist cadastrada ainda. Peça pro administrador adicionar!</p>";
                return;
            }

            playlists.forEach(function (playlist) {
                container.innerHTML += renderizarCardPlaylist(playlist);
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}
