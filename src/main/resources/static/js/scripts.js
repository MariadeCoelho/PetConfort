function validarFormulario {
    const nomeProduto = document.getElementById("nomeProduto").value.trim();
    const dataEntrada = document.getElementById("dataEntrada").value;
    const tipoProduto = document.getElementById("tipoProduto").value.trim();
    
 
    // Verificar se o nome do produto contém apenas letras e espaços
    const nomeRegex = /^[a-zA-Z\s]+$/; // Aceita apenas letras e espaços
    if (!nomeRegex.test(nomeProduto)) {
        alert("O nome do produto deve conter apenas letras.");
        return false;
    }

    // Validação da data de entrada
    if (dataEntrada === "") {
        alert("Por favor, insira a data de entrada.");
        return false;
    }

    // Validação do tipo de produto
    if (tipoProduto === "") {
        alert("Por favor, selecione o tipo de produto.");
        return false;
    }

    // Verificar se o tipo do produto contém apenas letras e espaços
    if (!nomeRegex.test(tipoProduto)) {
        alert("O tipo de produto deve conter apenas letras.");
        return false;
    }

    // Exibe mensagem de sucesso e redireciona para a página inicial
    alert("Cadastro de produto realizado com sucesso!");
    window.location.href = "/"; // Redireciona para a página inicial
    return false; // Impede o envio do formulário
}

function validarFormularioAnimal() {
    

    // Verifica se a idade é um número positivo
    if (isNaN(idadePet) || idadePet <= 0) {
        alert("Por favor, insira uma idade válida para o pet (número positivo).");
        return false;
    }

    // Verifica se o nome do pet e a raça contêm apenas letras
    const nomePetRegex = /^[a-zA-Z\s]+$/; // Aceita apenas letras e espaços
    if (!nomePetRegex.test(nomePet) || !nomePetRegex.test(racaPet)) {
        alert("O nome do pet e a raça devem conter apenas letras.");
        return false;
    }

    return true;
}

function carregarTabelaProdutos(produtos) {
    let tabelaProdutos = $('#tabelaProdutos tbody');
    tabelaProdutos.empty(); // Limpa a tabela antes de preencher

    // Itera sobre os produtos e preenche a tabela
    produtos.forEach(function (produto) {
        tabelaProdutos.append(`
            <tr>
                <td>${produto.nome}</td>
                <td>${produto.dataEntrada}</td>
                <td>${produto.tipo}</td>
                <td>
                    <button class="btn btn-warning editarProduto" data-id="${produto.id}">Editar</button>
                    <button class="btn btn-danger excluirProduto" data-id="${produto.id}">Excluir</button>
                </td>
            </tr>
        `);
    });
}

// GET para buscar todos os produtos
$(document).ready(function () {
    axios.get('/api/produtos')
        .then(response => carregarTabelaProdutos(response.data))
        .catch(() => exibirMensagemErro("Erro ao carregar produtos."));
});


// Envia o produto para o backend via POST
    axios.post('/api/produtos', novoProduto)
        .then(() => {
            alert("Produto cadastrado com sucesso!");
            $('#formCadastrarProduto')[0].reset(); // Limpa o formulário
            location.reload(); // Recarrega a tabela de produtos
        })
        .catch(() => exibirMensagemErro("Erro ao cadastrar produto."));





// POST para cadastrar um novo produto
$('#formCadastrarProduto').submit(function (e) {
    e.preventDefault(); // Previne o envio tradicional do formulário

    let novoProduto = {
        nome: $('#nomeProduto').val(),
        dataEntrada: $('#dataEntrada').val(),
        tipo: $('#tipoProduto').val()
    };

    // Validação do formulário
    if (!novoProduto.nome || !novoProduto.dataEntrada || !novoProduto.tipo) {
        exibirMensagemErro("Todos os campos são obrigatórios!");
        return;
    }

    // Verificar se o nome ou tipo começa com um número
    const regex = /^[0-9]/; // Expressão regular que verifica se o valor começa com número
    if (regex.test(novoProduto.nome)) {
        exibirMensagemErro("O nome do produto não pode começar com números!");
        return;
    }
    
    if (regex.test(novoProduto.tipo)) {
        exibirMensagemErro("O tipo de produto não pode começar com números!");
        return;
    }

    if (!validarData(novoProduto.dataEntrada)) {
        exibirMensagemErro("A data de entrada não pode ser no futuro!");
        return;
    }

    // Aqui você pode enviar o novoProduto para o servidor ou realizar outras ações
    console.log('Produto cadastrado:', novoProduto);
});



function carregarTabelaAnimais(animais) {
    let tabelaAnimais = $('#tabelaAnimais tbody');
    tabelaAnimais.empty(); // Limpa a tabela antes de preencher

    // Itera sobre os animais e preenche a tabela
    animais.forEach(function (animal) {
        tabelaAnimais.append(`
            <tr>
                <td>${animal.nomePet}</td>
                <td>${animal.idadePet}</td>
                <td>${animal.racaPet}</td>
                <td>${animal.especiePet}</td>
                <td>${animal.nomeTutor}</td>
                <td>
                    <button class="btn btn-warning editarAnimal" data-id="${animal.id}">Editar</button>
                    <button class="btn btn-danger excluirAnimal" data-id="${animal.id}">Excluir</button>
                </td>
            </tr>
        `);
    });


// PUT para atualizar um produto
$(document).on('click', '.editarProduto', function () {
    let produtoId = $(this).data('id');

    axios.get(`/api/produtos/${produtoId}`)
        .then(response => {
            const produto = response.data;
            $('#nomeProduto').val(produto.nome);
            $('#dataEntrada').val(produto.dataEntrada);
            $('#tipoProduto').val(produto.tipo);

            $('#formCadastrarProduto').off('submit').submit(function (e) {
                e.preventDefault();

                let produtoAtualizado = {
                    nome: $('#nomeProduto').val(),
                    dataEntrada: $('#dataEntrada').val(),
                    tipo: $('#tipoProduto').val()
                };

                axios.put(`/api/produtos/${produtoId}`, produtoAtualizado)
                    .then(() => {
                        alert("Produto atualizado com sucesso!");
                        location.reload(); // Recarrega a página para atualizar a tabela
                    })
                    .catch(() => exibirMensagemErro("Erro ao atualizar produto."));
            });
        })
        .catch(() => exibirMensagemErro("Erro ao carregar dados do produto."));
});

// DELETE para excluir um produto
$(document).on('click', '.excluirProduto', function () {
    let produtoId = $(this).data('id');

    if (confirm("Tem certeza que deseja excluir este produto?")) {
        axios.delete(`/api/produtos/${produtoId}`)
            .then(() => {
                alert("Produto excluído com sucesso!");
                location.reload(); // Recarrega a tabela
            })
            .catch(() => exibirMensagemErro("Erro ao excluir produto."));
    }
});

// Função de validação de data (não permite data futura)
function validarData(data) {
    const dataAtual = new Date();
    const dataSelecionada = new Date(data);
    dataAtual.setHours(23, 59, 59, 999); // Ajuste a hora da data atual para o final do dia
    return dataSelecionada <= dataAtual;
}

// GET para buscar todos os animais
$(document).ready(function () {
    axios.get('/api/animais')
        .then(response => carregarTabelaAnimais(response.data))
        .catch(() => exibirMensagemErro("Erro ao carregar animais."));
});


$('#formCadastrarAnimal').submit(function (e) {
    e.preventDefault(); // Previne o envio tradicional do formulário

    let novoAnimal = {
        nomePet: $('#nomePet').val(),
        idadePet: $('#idadePet').val(),
        racaPet: $('#racaPet').val(),
        especiePet: $('#especiePet').val(),
        nomeTutor: $('#nomeTutor').val()
    };

    // Validação
    if (!novoAnimal.especiePet) {
        return exibirMensagemErro("O campo 'especiePet' não pode ser nulo.");
    }

    axios.post('/api/animais', novoAnimal)
        .then(response => {
            alert("Animal cadastrado com sucesso!");
            $('#formCadastrarAnimal')[0].reset(); // Limpa o formulário
            adicionarLinhaTabela(response.data); // Atualiza a tabela dinamicamente
        })
        .catch(error => {
            console.error(error);
            exibirMensagemErro("Erro ao cadastrar animal.");
        });
        
    }
});


$('#formCadastrarAnimal').off('submit').submit(function (e) {
    e.preventDefault();

    let animalAtualizado = {
        nomePet: $('#nomePet').val(),
        idadePet: $('#idadePet').val(),
        racaPet: $('#racaPet').val(),
        especiePet: $('#especiePet').val(),
        nomeTutor: $('#nomeTutor').val()
    };

    // Validação
    if (!animalAtualizado.especiePet) {
        return exibirMensagemErro("O campo 'especiePet' não pode ser nulo.");
    }

    axios.put(`/api/animais/${animalId}`, animalAtualizado)
        .then(() => {
            alert("Animal atualizado com sucesso!");
            location.reload(); // Recarrega a página para atualizar a tabela
        })
        .catch(() => exibirMensagemErro("Erro ao atualizar animal."));
});

// DELETE para excluir um animal
$(document).on('click', '.excluirAnimal', function () {
    let animalId = $(this).data('id');

    if (confirm("Tem certeza que deseja excluir este animal?")) {
        axios.delete(`/api/animais/${animalId}`)
            .then(() => {
                alert("Animal excluído com sucesso!");
                location.reload(); // Recarrega a tabela
            })
            .catch(() => exibirMensagemErro("Erro ao excluir animal."));
    }
});


// Função para exibir mensagens de erro
function exibirMensagemErro(mensagem) {
    $('#mensagemErro').text(mensagem).show();
}




