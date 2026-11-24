// =====================
// MOSTRAR DADOS NO PERFIL
// =====================
function mostrarPerfil() {

    let usuario = obterUsuarioURL();
    if (!usuario) return;

    let nome = document.getElementById("nomePerfil");
    let email = document.getElementById("emailPerfil");

    if (nome) {
        nome.innerText = usuario.nome || "Usuário";
    }

    if (email) {
        email.innerText = usuario.email;
    }
}

// =====================
// MOSTRAR FAVORITOS NO PERFIL (vem do banco)
// =====================
function mostrarFavoritosPerfil() {

    let lista = document.getElementById("listaFavoritosPerfil");
    if (!lista) return;

    let usuario = obterUsuarioURL();
    if (!usuario) return;

    fetch('http://localhost:8080/favorito/' + encodeURIComponent(usuario.email))
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (favoritos) {

            lista.innerHTML = "";

            if (favoritos.length === 0) {
                lista.innerHTML = "<p>Nenhuma música favoritada</p>";
                return;
            }

            favoritos.forEach(function (musica) {
                lista.innerHTML += `<p>🎵 ${musica}</p>`;
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}
