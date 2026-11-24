// =====================
// CADASTRO
// =====================
function cadastrar(event) {
    event.preventDefault();

    let nome = document.getElementById("nome").value;
    let email = document.getElementById("emailCadastro").value;
    let senha = document.getElementById("senhaCadastro").value;

    if (nome === "" || email === "" || senha === "") {
        alert("Preencha todos os campos!");
        return;
    }

    let cadastroDTO = {
        nome: nome,
        email: email,
        senha: senha
    };

    // CadastroController está em @Path("/"), então o POST vai pra "/"
    fetch("/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cadastroDTO)
    })
        .then(function (response) {
            if (response.ok) {
                alert("Cadastro realizado com sucesso!");
                window.location.href = "/login";
            } else if (response.status === 409) {
                alert("E-mail já cadastrado!");
            } else {
                alert("Erro ao realizar cadastro!");
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}
