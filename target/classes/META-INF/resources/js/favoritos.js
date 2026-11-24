// =====================
// FAVORITAR / DESFAVORITAR (persistido no banco, via /favorito)
// =====================
function favoritar(botao) {

    let usuario = obterUsuarioURL();
    if (!usuario) return;

    let nomeMusica = botao.parentElement.querySelector(".titulo").innerText;
    let favoritado = botao.innerHTML.includes("Favoritado");

    fetch('http://localhost:8080/favorito', {
        method: favoritado ? 'DELETE' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: usuario.email, musica: nomeMusica })
    })
        .then(function (response) {

            if (!response.ok) {
                alert("Erro ao atualizar favorito!");
                return;
            }

            if (favoritado) {
                botao.innerHTML = '<i class="fa-solid fa-heart"></i> Favoritar';
                botao.style.backgroundColor = "#4682B4";
            } else {
                botao.innerHTML = '<i class="fa-solid fa-heart"></i> Favoritado';
                botao.style.backgroundColor = "green";
            }
        })
        .catch(function (error) {
            console.error(error);
        });
}

// =====================
// MARCAR MÚSICAS JÁ FAVORITADAS (chame isso DEPOIS de renderizar os cards)
// =====================
function marcarFavoritos() {

    let usuario = obterUsuarioURL();
    if (!usuario) return;

    let botoes = document.querySelectorAll("button[onclick^='favoritar']");
    if (botoes.length === 0) return;

    fetch('http://localhost:8080/favorito/' + encodeURIComponent(usuario.email))
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (favoritos) {
            botoes.forEach(function (botao) {
                let titulo = botao.parentElement.querySelector(".titulo").innerText;
                if (favoritos.includes(titulo)) {
                    botao.innerHTML = '<i class="fa-solid fa-heart"></i> Favoritado';
                    botao.style.backgroundColor = "green";
                }
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}
