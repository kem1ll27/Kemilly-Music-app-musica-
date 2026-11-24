// =====================
// USUÁRIOS
// =====================
function mostrarUsuariosAdmin() {

    let tabela = document.getElementById("listaUsuarios");
    if (!tabela) return;

    let admin = obterUsuarioURL();
    if (!admin) return;

    fetch('http://localhost:8080/admin/usuario?email=' + encodeURIComponent(admin.email))
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (usuarios) {

            tabela.innerHTML = "";

            if (!usuarios || usuarios.length === 0) {
                tabela.innerHTML = `<tr><td colspan="4">Nenhum usuário cadastrado</td></tr>`;
                return;
            }

            usuarios.forEach(function (usuario) {

                let botaoPerfil = (usuario.perfil === "ADMIN")
                    ? `<button onclick="rebaixarUsuario(${usuario.id})">⬇ Rebaixar</button>`
                    : `<button onclick="promoverUsuario(${usuario.id})">⬆ Promover a admin</button>`;

                tabela.innerHTML += `
                    <tr>
                        <td>${usuario.nome}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.perfil || ""}</td>
                        <td>
                            ${botaoPerfil}
                            <button onclick="removerUsuario('${usuario.email}')">❌ Remover</button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}

function removerUsuario(email) {
    let admin = obterUsuarioURL();
    if (!admin) return;

    if (!confirm("Tem certeza que deseja remover este usuário?")) return;

    fetch('http://localhost:8080/admin/usuario?email=' + encodeURIComponent(admin.email), {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email })
    })
        .then(function (response) {
            if (response.ok) {
                alert("Usuário removido!");
                mostrarUsuariosAdmin();
            } else {
                alert("Erro ao remover usuário!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

function promoverUsuario(id) {
    let admin = obterUsuarioURL();
    if (!admin) return;

    fetch('http://localhost:8080/admin/promover/' + id + '?email=' + encodeURIComponent(admin.email), {
        method: 'POST'
    })
        .then(function (response) {
            if (response.ok) {
                mostrarUsuariosAdmin();
            } else {
                alert("Erro ao promover usuário!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

function rebaixarUsuario(id) {
    let admin = obterUsuarioURL();
    if (!admin) return;

    if (!confirm("Tirar as permissões de administrador desse usuário?")) return;

    fetch('http://localhost:8080/admin/rebaixar/' + id + '?email=' + encodeURIComponent(admin.email), {
        method: 'POST'
    })
        .then(function (response) {
            if (response.ok) {
                mostrarUsuariosAdmin();
            } else {
                alert("Erro ao rebaixar usuário!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

// =====================
// RASTREABILIDADE (log vem do banco)
// =====================
function mostrarHistorico() {

    let div = document.getElementById("historico");
    if (!div) return;

    let admin = obterUsuarioURL();
    if (!admin) return;

    fetch('http://localhost:8080/admin/log?email=' + encodeURIComponent(admin.email))
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (logs) {

            div.innerHTML = "";

            if (!logs || logs.length === 0) {
                div.innerHTML = "<p>Nenhuma ação registrada.</p>";
                return;
            }

            logs.forEach(function (log) {
                div.innerHTML += `<p>📌 ${log.usuarioNome} (${log.usuarioEmail}) | ${log.acao} | ${new Date(log.dataHora).toLocaleString()}</p>`;
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}

// =====================
// GERENCIAR MÚSICAS
// =====================
function mostrarMusicasAdmin() {

    let lista = document.getElementById("listaMusicasAdmin");
    if (!lista) return;

    fetch('http://localhost:8080/musica')
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (musicas) {

            lista.innerHTML = "";

            if (musicas.length === 0) {
                lista.innerHTML = "<p>Nenhuma música cadastrada</p>";
                return;
            }

            musicas.forEach(function (m) {
                lista.innerHTML += `
                    <div class="item-admin">
                        <span><b>${m.titulo}</b> — ${m.artista} <i>(${m.categoria})</i></span>
                        <button onclick="removerMusica(${m.id})">❌</button>
                    </div>
                `;
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}

function adicionarMusica(event) {
    event.preventDefault();

    let admin = obterUsuarioURL();
    if (!admin) return;

    let musicaDTO = {
        titulo: document.getElementById("musicaTitulo").value,
        artista: document.getElementById("musicaArtista").value,
        imagemUrl: document.getElementById("musicaImagem").value,
        linkUrl: document.getElementById("musicaLink").value,
        categoria: document.getElementById("musicaCategoria").value
    };

    if (musicaDTO.titulo === "") {
        alert("Preencha ao menos o título da música!");
        return;
    }

    fetch('http://localhost:8080/musica?email=' + encodeURIComponent(admin.email), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(musicaDTO)
    })
        .then(function (response) {
            if (response.ok) {
                document.getElementById("formMusica").reset();
                mostrarMusicasAdmin();
            } else {
                alert("Erro ao adicionar música!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

function removerMusica(id) {
    let admin = obterUsuarioURL();
    if (!admin) return;

    if (!confirm("Remover esta música do catálogo?")) return;

    fetch('http://localhost:8080/musica/' + id + '?email=' + encodeURIComponent(admin.email), {
        method: 'DELETE'
    })
        .then(function (response) {
            if (response.ok) {
                mostrarMusicasAdmin();
            } else {
                alert("Erro ao remover música!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

// =====================
// GERENCIAR PLAYLISTS
// =====================
function mostrarPlaylistsAdmin() {

    let lista = document.getElementById("listaPlaylistsAdmin");
    if (!lista) return;

    fetch('http://localhost:8080/playlist')
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (playlists) {

            lista.innerHTML = "";

            if (playlists.length === 0) {
                lista.innerHTML = "<p>Nenhuma playlist cadastrada</p>";
                return;
            }

            playlists.forEach(function (p) {
                lista.innerHTML += `
                    <div class="item-admin">
                        <span><b>${p.titulo}</b> — ${p.descricao || ""}</span>
                        <button onclick="removerPlaylist(${p.id})">❌</button>
                    </div>
                `;
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}

function adicionarPlaylist(event) {
    event.preventDefault();

    let admin = obterUsuarioURL();
    if (!admin) return;

    let playlistDTO = {
        titulo: document.getElementById("playlistTitulo").value,
        descricao: document.getElementById("playlistDescricao").value,
        imagemUrl: document.getElementById("playlistImagem").value,
        linkUrl: document.getElementById("playlistLink").value
    };

    if (playlistDTO.titulo === "") {
        alert("Preencha ao menos o título da playlist!");
        return;
    }

    fetch('http://localhost:8080/playlist?email=' + encodeURIComponent(admin.email), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(playlistDTO)
    })
        .then(function (response) {
            if (response.ok) {
                document.getElementById("formPlaylist").reset();
                mostrarPlaylistsAdmin();
            } else {
                alert("Erro ao adicionar playlist!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

function removerPlaylist(id) {
    let admin = obterUsuarioURL();
    if (!admin) return;

    if (!confirm("Remover esta playlist do catálogo?")) return;

    fetch('http://localhost:8080/playlist/' + id + '?email=' + encodeURIComponent(admin.email), {
        method: 'DELETE'
    })
        .then(function (response) {
            if (response.ok) {
                mostrarPlaylistsAdmin();
            } else {
                alert("Erro ao remover playlist!");
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}
