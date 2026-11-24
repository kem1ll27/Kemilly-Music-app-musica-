// =====================
// SESSÃO SIMPLES VIA URL (sem localStorage, sem cookie)
// Depois do login, a URL fica: /home?email=...&nome=...&perfil=...
// =====================

function obterParametroURL(nome) {
    let params = new URLSearchParams(window.location.search);
    return params.get(nome);
}

function obterUsuarioURL() {
    let email = obterParametroURL("email");

    if (!email) {
        return null;
    }

    return {
        email: email,
        nome: obterParametroURL("nome"),
        perfil: obterParametroURL("perfil")
    };
}

function construirQueryString(usuario) {
    if (!usuario) return "";

    return "?email=" + encodeURIComponent(usuario.email)
        + "&nome=" + encodeURIComponent(usuario.nome || "")
        + "&perfil=" + encodeURIComponent(usuario.perfil || "");
}

// Reescreve os links do menu para carregarem o email/nome/perfil junto
function propagarSessaoNosLinks() {
    let usuario = obterUsuarioURL();
    if (!usuario) return;

    let query = construirQueryString(usuario);

    document.querySelectorAll(".menu a").forEach(function (link) {
        let base = link.getAttribute("href").split("?")[0];
        link.setAttribute("href", base + query);
    });
}

// =====================
// LOGIN
// =====================
window.login = function (event) {
    event.preventDefault();

    let email = document.getElementById("email").value;
    let senha = document.getElementById("senha").value;

    if (email === "" || senha === "") {
        alert("Preencha todos os campos!");
        return false;
    }

    let autenticacaoDTO = {
        email: email,
        senha: senha
    };

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(autenticacaoDTO)
    })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Credenciais inválidas");
        })
        .then(function (usuario) {
            let destino = (usuario.perfil === "ADMIN") ? "/admin" : "/home";
            window.location.href = destino + construirQueryString(usuario);
        })
        .catch(function (error) {
            console.log(error);
            alert("Erro ao realizar login! Verifique e-mail e senha.");
        });

    return false;
};

// =====================
// VERIFICAÇÕES SIMPLES
// =====================
function verificarLogin() {
    if (!obterUsuarioURL()) {
        window.location.href = "/login";
    }
}

function verificarAdmin() {
    let usuario = obterUsuarioURL();
    if (!usuario || usuario.perfil !== "ADMIN") {
        window.location.href = "/home";
    }
}

function verificarSeJaEstaLogado() {
    if (obterUsuarioURL()) {
        window.location.href = "/home";
    }
}

function mostrarUsuario() {
    let usuario = obterUsuarioURL();
    let texto = document.getElementById("nomeUsuario");

    if (usuario && texto) {
        texto.innerText = "Olá, " + (usuario.nome || "usuário");
    }
}

function mostrarMenuAdmin() {
    let usuario = obterUsuarioURL();
    let item = document.getElementById("menuAdmin");

    if (item) {
        item.style.display = (usuario && usuario.perfil === "ADMIN") ? "block" : "none";
    }
}

// =====================
// LOGOUT
// =====================
function logout() {
    window.location.href = "/login";
}
