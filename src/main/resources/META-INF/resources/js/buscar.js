// =====================
// BUSCAR MÚSICAS (o back-end faz a busca e registra o log)
// =====================
function buscarMusica() {

    let campo = document.getElementById("campoBusca");
    let resultados = document.getElementById("resultados");
    if (!campo || !resultados) return;

    let texto = campo.value.trim();

    if (texto === "") {
        resultados.innerHTML = "<p>Digite algo para buscar</p>";
        return;
    }

    let usuario = obterUsuarioURL();

    fetch('http://localhost:8080/buscar', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            texto: texto,
            email: usuario ? usuario.email : null
        })
    })
        .then(function (response) {
            return response.ok ? response.json() : [];
        })
        .then(function (musicas) {
            resultados.innerHTML = "";

            if (!musicas || musicas.length === 0) {
                resultados.innerHTML = "<p>Nenhuma música encontrada</p>";
                return;
            }

            musicas.forEach(function (musica) {
                resultados.innerHTML += "<p>🎵 " + musica + "</p>";
            });
        })
        .catch(function (error) {
            console.error(error);
        });
}
