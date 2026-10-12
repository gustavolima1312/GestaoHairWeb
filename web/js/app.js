const formLogin = document.querySelector("#formLogin");
const campoEmail = document.querySelector("#email");
const campoSenha = document.querySelector("#senha");
const mensagemLogin = document.querySelector("#mensagemLogin");
const btnMostrarSenha = document.querySelector("#btnMostrarSenha");
const btnSair = document.querySelector("#btnSair");

function mostrarMensagem(texto, tipo) {
    if (!mensagemLogin) {
        return;
    }

    mensagemLogin.textContent = texto;
    mensagemLogin.className = `mensagem ${tipo}`;
}

if (btnMostrarSenha && campoSenha) {
    btnMostrarSenha.addEventListener("click", () => {
        const senhaVisivel = campoSenha.type === "text";

        campoSenha.type = senhaVisivel ? "password" : "text";
        btnMostrarSenha.textContent = senhaVisivel ? "Mostrar" : "Ocultar";

        btnMostrarSenha.setAttribute(
            "aria-label",
            senhaVisivel ? "Mostrar senha" : "Ocultar senha"
        );
    });
}

if (formLogin && campoEmail && campoSenha) {
    formLogin.addEventListener("submit", (evento) => {
        evento.preventDefault();

        const email = campoEmail.value.trim();
        const senha = campoSenha.value.trim();

        if (!email || !senha) {
            mostrarMensagem("Preencha o e-mail e a senha para continuar.", "erro");
            return;
        }

        if (!email.includes("@") || !email.includes(".")) {
            mostrarMensagem("Digite um endereço de e-mail válido.", "erro");
            campoEmail.focus();
            return;
        }

        if (email === "admin@gestaohair.com" && senha === "123456") {
            mostrarMensagem(
                "Login realizado com sucesso. Redirecionando...",
                "sucesso"
            );

            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 900);

            return;
        }

        mostrarMensagem(
            "Dados inválidos. Use o acesso demonstrativo informado abaixo.",
            "erro"
        );
    });
}

if (btnSair) {
    btnSair.addEventListener("click", () => {
        const confirmarSaida = window.confirm(
            "Deseja realmente sair do GestãoHair?"
        );

        if (confirmarSaida) {
            window.location.href = "index.html";
        }
    });
}

const formCliente = document.querySelector("#formCliente");
const nomeCliente = document.querySelector("#nomeCliente");
const telefoneCliente = document.querySelector("#telefoneCliente");
const emailCliente = document.querySelector("#emailCliente");
const dataNascimentoCliente = document.querySelector("#dataNascimentoCliente");
const observacaoCliente = document.querySelector("#observacaoCliente");
const mensagemCliente = document.querySelector("#mensagemCliente");
const listaClientes = document.querySelector("#listaClientes");
const contadorClientes = document.querySelector("#contadorClientes");

function mostrarMensagemCliente(texto, tipo) {
    if (!mensagemCliente) {
        return;
    }

    mensagemCliente.textContent = texto;
    mensagemCliente.className = `mensagem ${tipo}`;
}

function formatarData(data) {
    const partes = data.split("-");

    if (partes.length !== 3) {
        return data;
    }

    return `${partes[2]}/${partes[1]}/${partes[0]}`;
}

function atualizarContadorClientes() {
    if (!listaClientes || !contadorClientes) {
        return;
    }

    const quantidade = listaClientes.querySelectorAll("tr").length;

    contadorClientes.textContent =
        quantidade === 1 ? "1 cliente" : `${quantidade} clientes`;
}

const formProduto = document.querySelector("#formProduto");
const nomeProduto = document.querySelector("#nomeProduto");
const categoriaProduto = document.querySelector("#categoriaProduto");
const precoProduto = document.querySelector("#precoProduto");
const quantidadeProduto = document.querySelector("#quantidadeProduto");
const estoqueMinimoProduto = document.querySelector("#estoqueMinimoProduto");
const fornecedorProduto = document.querySelector("#fornecedorProduto");
const mensagemProduto = document.querySelector("#mensagemProduto");
const listaProdutos = document.querySelector("#listaProdutos");
const contadorProdutos = document.querySelector("#contadorProdutos");
const valorEstoque = document.querySelector("#valorEstoque");

function mostrarMensagemProduto(texto, tipo) {
    if (!mensagemProduto) {
        return;
    }

    mensagemProduto.textContent = texto;
    mensagemProduto.className = `mensagem ${tipo}`;
}

function formatarMoeda(valor) {
    return valor.toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function atualizarResumoProdutos() {
    if (!listaProdutos || !contadorProdutos || !valorEstoque) {
        return;
    }

    const produtos = listaProdutos.querySelectorAll("tr");
    let totalEstoque = 0;

    produtos.forEach((produto) => {
        const preco = Number(produto.dataset.preco);
        const quantidade = Number(produto.dataset.quantidade);

        totalEstoque += preco * quantidade;
    });

    contadorProdutos.textContent =
        produtos.length === 1 ? "1 produto" : `${produtos.length} produtos`;

    valorEstoque.textContent = `Estoque: ${formatarMoeda(totalEstoque)}`;
}

if (listaProdutos) {
    atualizarResumoProdutos();
}

const formAgendamento = document.querySelector("#formAgendamento");
const clienteAgendamento = document.querySelector("#clienteAgendamento");
const servicoAgendamento = document.querySelector("#servicoAgendamento");
const profissionalAgendamento = document.querySelector("#profissionalAgendamento");
const statusAgendamento = document.querySelector("#statusAgendamento");
const dataAgendamento = document.querySelector("#dataAgendamento");
const horaAgendamento = document.querySelector("#horaAgendamento");
const mensagemAgendamento = document.querySelector("#mensagemAgendamento");
const listaAgendamentos = document.querySelector("#listaAgendamentos");
const contadorAgendamentos = document.querySelector("#contadorAgendamentos");

function mostrarMensagemAgendamento(texto, tipo) {
    if (!mensagemAgendamento) {
        return;
    }

    mensagemAgendamento.textContent = texto;
    mensagemAgendamento.className = `mensagem ${tipo}`;
}

function formatarDataAgendamento(data) {
    const partes = data.split("-");

    if (partes.length !== 3) {
        return data;
    }

    return `${partes[2]}/${partes[1]}/${partes[0]}`;
}

function obterDataAtual() {
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, "0");
    const dia = String(hoje.getDate()).padStart(2, "0");

    return `${ano}-${mes}-${dia}`;
}

function atualizarContadorAgendamentos() {
    if (!listaAgendamentos || !contadorAgendamentos) {
        return;
    }

    const quantidade = listaAgendamentos.querySelectorAll("tr").length;

    contadorAgendamentos.textContent =
        quantidade === 1
            ? "1 agendamento"
            : `${quantidade} agendamentos`;
}

function obterClasseStatusAgendamento(status) {
    if (status === "Confirmado") {
        return "confirmado";
    }

    if (status === "Pendente") {
        return "pendente";
    }

    return "concluido";
}

function existeConflitoDeHorario(data, hora, profissional) {
    if (!listaAgendamentos) {
        return false;
    }

    const agendamentos = listaAgendamentos.querySelectorAll("tr");

    return Array.from(agendamentos).some((agendamento) => {
        return (
            agendamento.dataset.data === data &&
            agendamento.dataset.hora === hora &&
            agendamento.dataset.profissional === profissional
        );
    });
}

if (dataAgendamento) {
    dataAgendamento.min = obterDataAtual();
}

const API_CLIENTES = "api/clientes";

function escaparHtml(texto) {
    if (texto === null || texto === undefined) {
        return "";
    }

    return String(texto)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

async function carregarClientesDoBanco() {
    if (!listaClientes) {
        return;
    }

    try {
        const resposta = await fetch(API_CLIENTES);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os clientes.");
        }

        const clientes = await resposta.json();

        listaClientes.innerHTML = "";

        clientes.forEach((cliente) => {
            const novaLinha = document.createElement("tr");

            novaLinha.innerHTML = `
                <td>${escaparHtml(cliente.nome)}</td>
                <td>${escaparHtml(cliente.telefone)}</td>
                <td>${escaparHtml(cliente.email)}</td>
                <td>${formatarData(cliente.dataNascimento)}</td>
                <td>${escaparHtml(cliente.observacao || "Sem observações.")}</td>
            `;

            listaClientes.appendChild(novaLinha);
        });

        atualizarContadorClientes();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemCliente(
            "Não foi possível carregar os clientes do banco.",
            "erro"
        );
    }
}

async function cadastrarClienteNoBanco(evento) {
    evento.preventDefault();

    const nome = nomeCliente.value.trim();
    const telefone = telefoneCliente.value.trim();
    const email = emailCliente.value.trim();
    const dataNascimento = dataNascimentoCliente.value;
    const observacao = observacaoCliente.value.trim();

    if (!nome || !telefone || !email || !dataNascimento) {
        mostrarMensagemCliente(
            "Preencha nome, telefone, e-mail e data de nascimento.",
            "erro"
        );
        return;
    }

    if (nome.length < 3) {
        mostrarMensagemCliente(
            "Digite um nome válido com pelo menos 3 caracteres.",
            "erro"
        );
        nomeCliente.focus();
        return;
    }

    if (!email.includes("@") || !email.includes(".")) {
        mostrarMensagemCliente(
            "Digite um endereço de e-mail válido.",
            "erro"
        );
        emailCliente.focus();
        return;
    }

    const telefoneNumeros = telefone.replace(/\D/g, "");

    if (telefoneNumeros.length < 10 || telefoneNumeros.length > 11) {
        mostrarMensagemCliente(
            "Digite um telefone válido com DDD.",
            "erro"
        );
        telefoneCliente.focus();
        return;
    }

    const dadosCliente = new URLSearchParams();
    dadosCliente.append("nome", nome);
    dadosCliente.append("telefone", telefone);
    dadosCliente.append("email", email);
    dadosCliente.append("dataNascimento", dataNascimento);
    dadosCliente.append("observacao", observacao);

    try {
        const resposta = await fetch(API_CLIENTES, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body: dadosCliente.toString()
        });

        const resultado = await resposta.json();

        if (!resposta.ok) {
            throw new Error(resultado.erro || "Não foi possível cadastrar o cliente.");
        }

        formCliente.reset();

        mostrarMensagemCliente(
            resultado.mensagem || `${nome} foi cadastrado(a) com sucesso.`,
            "sucesso"
        );

        await carregarClientesDoBanco();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemCliente(erro.message, "erro");
    }
}

if (formCliente && listaClientes) {
    formCliente.addEventListener("submit", cadastrarClienteNoBanco);

    carregarClientesDoBanco();
}

const API_PRODUTOS = "api/produtos";

function criarLinhaProduto(produto) {
    const estoqueBaixo =
        Number(produto.quantidade) <= Number(produto.estoqueMinimo);

    const status = estoqueBaixo ? "Estoque baixo" : "Em estoque";
    const classeStatus = estoqueBaixo ? "baixo" : "normal";

    const novaLinha = document.createElement("tr");

    novaLinha.dataset.preco = produto.preco;
    novaLinha.dataset.quantidade = produto.quantidade;

    novaLinha.innerHTML = `
        <td>${escaparHtml(produto.nome)}</td>
        <td>${escaparHtml(produto.categoria)}</td>
        <td>${formatarMoeda(Number(produto.preco))}</td>
        <td>${produto.quantidade}</td>
        <td>${produto.estoqueMinimo}</td>
        <td>${escaparHtml(produto.fornecedor)}</td>
        <td>
            <span class="status-estoque ${classeStatus}">
                ${status}
            </span>
        </td>
    `;

    return novaLinha;
}

async function carregarProdutosDoBanco() {
    if (!listaProdutos) {
        return;
    }

    try {
        const resposta = await fetch(API_PRODUTOS);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os produtos.");
        }

        const produtos = await resposta.json();

        listaProdutos.innerHTML = "";

        produtos.forEach((produto) => {
            listaProdutos.appendChild(criarLinhaProduto(produto));
        });

        atualizarResumoProdutos();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemProduto(
            "Não foi possível carregar os produtos do banco.",
            "erro"
        );
    }
}

async function cadastrarProdutoNoBanco(evento) {
    evento.preventDefault();

    const nome = nomeProduto.value.trim();
    const categoria = categoriaProduto.value;
    const preco = precoProduto.value.trim();
    const quantidade = quantidadeProduto.value.trim();
    const estoqueMinimo = estoqueMinimoProduto.value.trim();
    const fornecedor = fornecedorProduto.value.trim();

    if (
        !nome ||
        !categoria ||
        !preco ||
        quantidade === "" ||
        estoqueMinimo === "" ||
        !fornecedor
    ) {
        mostrarMensagemProduto(
            "Preencha todos os campos para cadastrar o produto.",
            "erro"
        );
        return;
    }

    if (nome.length < 3) {
        mostrarMensagemProduto(
            "Digite um nome de produto válido com pelo menos 3 caracteres.",
            "erro"
        );
        nomeProduto.focus();
        return;
    }

    if (Number(preco) <= 0) {
        mostrarMensagemProduto(
            "O preço do produto deve ser maior que zero.",
            "erro"
        );
        precoProduto.focus();
        return;
    }

    if (
        !Number.isInteger(Number(quantidade))
        || Number(quantidade) < 0
    ) {
        mostrarMensagemProduto(
            "Informe uma quantidade válida em estoque.",
            "erro"
        );
        quantidadeProduto.focus();
        return;
    }

    if (
        !Number.isInteger(Number(estoqueMinimo))
        || Number(estoqueMinimo) < 0
    ) {
        mostrarMensagemProduto(
            "Informe um estoque mínimo válido.",
            "erro"
        );
        estoqueMinimoProduto.focus();
        return;
    }

    const dadosProduto = new URLSearchParams();
    dadosProduto.append("nome", nome);
    dadosProduto.append("categoria", categoria);
    dadosProduto.append("preco", preco);
    dadosProduto.append("quantidade", quantidade);
    dadosProduto.append("estoqueMinimo", estoqueMinimo);
    dadosProduto.append("fornecedor", fornecedor);

    try {
        const resposta = await fetch(API_PRODUTOS, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body: dadosProduto.toString()
        });

        const resultado = await resposta.json();

        if (!resposta.ok) {
            throw new Error(resultado.erro || "Não foi possível cadastrar o produto.");
        }

        formProduto.reset();

        mostrarMensagemProduto(
            resultado.mensagem || `${nome} foi cadastrado com sucesso.`,
            "sucesso"
        );

        await carregarProdutosDoBanco();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemProduto(erro.message, "erro");
    }
}

if (formProduto && listaProdutos) {
    formProduto.addEventListener("submit", cadastrarProdutoNoBanco);

    carregarProdutosDoBanco();
}

const API_AGENDAMENTOS = "api/agendamentos";

function criarLinhaAgendamento(agendamento) {
    const classeStatus = obterClasseStatusAgendamento(agendamento.status);
    const novaLinha = document.createElement("tr");

    novaLinha.dataset.data = agendamento.data;
    novaLinha.dataset.hora = agendamento.hora;
    novaLinha.dataset.profissional = agendamento.profissional;

    novaLinha.innerHTML = `
        <td>${formatarDataAgendamento(agendamento.data)}</td>
        <td>${escaparHtml(agendamento.hora)}</td>
        <td>${escaparHtml(agendamento.cliente)}</td>
        <td>${escaparHtml(agendamento.servico)}</td>
        <td>${escaparHtml(agendamento.profissional)}</td>
        <td>
            <span class="status-agendamento ${classeStatus}">
                ${escaparHtml(agendamento.status)}
            </span>
        </td>
    `;

    return novaLinha;
}

async function carregarAgendamentosDoBanco() {
    if (!listaAgendamentos) {
        return;
    }

    try {
        const resposta = await fetch(API_AGENDAMENTOS);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os agendamentos.");
        }

        const agendamentos = await resposta.json();

        listaAgendamentos.innerHTML = "";

        agendamentos.forEach((agendamento) => {
            listaAgendamentos.appendChild(
                criarLinhaAgendamento(agendamento)
            );
        });

        atualizarContadorAgendamentos();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemAgendamento(
            "Não foi possível carregar os agendamentos do banco.",
            "erro"
        );
    }
}

async function cadastrarAgendamentoNoBanco(evento) {
    evento.preventDefault();

    const cliente = clienteAgendamento.value;
    const servico = servicoAgendamento.value;
    const profissional = profissionalAgendamento.value;
    const status = statusAgendamento.value;
    const data = dataAgendamento.value;
    const hora = horaAgendamento.value;

    if (!cliente || !servico || !profissional || !data || !hora) {
        mostrarMensagemAgendamento(
            "Preencha todos os campos do agendamento.",
            "erro"
        );
        return;
    }

    if (data < obterDataAtual()) {
        mostrarMensagemAgendamento(
            "Não é possível cadastrar um agendamento em data passada.",
            "erro"
        );
        dataAgendamento.focus();
        return;
    }

    if (hora < "08:00" || hora > "20:00") {
        mostrarMensagemAgendamento(
            "Selecione um horário entre 08:00 e 20:00.",
            "erro"
        );
        horaAgendamento.focus();
        return;
    }

    const dadosAgendamento = new URLSearchParams();

    dadosAgendamento.append("cliente", cliente);
    dadosAgendamento.append("servico", servico);
    dadosAgendamento.append("profissional", profissional);
    dadosAgendamento.append("status", status);
    dadosAgendamento.append("data", data);
    dadosAgendamento.append("hora", hora);

    try {
        const resposta = await fetch(API_AGENDAMENTOS, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body: dadosAgendamento.toString()
        });

        const resultado = await resposta.json();

        if (!resposta.ok) {
            throw new Error(
                resultado.erro || "Não foi possível salvar o agendamento."
            );
        }

        formAgendamento.reset();

        mostrarMensagemAgendamento(
            resultado.mensagem
                || `Agendamento de ${cliente} salvo com sucesso.`,
            "sucesso"
        );

        await carregarAgendamentosDoBanco();

    } catch (erro) {
        console.error(erro);
        mostrarMensagemAgendamento(erro.message, "erro");
    }
}

if (formAgendamento && listaAgendamentos) {
    formAgendamento.addEventListener(
        "submit",
        cadastrarAgendamentoNoBanco
    );

    carregarAgendamentosDoBanco();
}

const API_DASHBOARD = "api/dashboard";

async function carregarIndicadoresDashboard() {
    const elementoClientes = document.getElementById("totalClientes");
    const elementoProdutos = document.getElementById("totalProdutos");
    const elementoEstoqueBaixo = document.getElementById("produtosEstoqueBaixo");
    const elementoAgendamentos = document.getElementById("totalAgendamentos");
    const elementoAgendamentosHoje = document.getElementById("agendamentosHoje");
    const listaProximos = document.getElementById("proximosAgendamentos");

    if (!elementoClientes && !elementoProdutos && !elementoAgendamentos) {
        return;
    }

    try {
        const resposta = await fetch(API_DASHBOARD);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os indicadores.");
        }

        const dados = await resposta.json();

        if (elementoClientes) {
            elementoClientes.textContent = dados.totalClientes;
        }

        if (elementoProdutos) {
            elementoProdutos.textContent = dados.totalProdutos;
        }

        if (elementoEstoqueBaixo) {
            elementoEstoqueBaixo.textContent = dados.produtosEstoqueBaixo;
        }

        if (elementoAgendamentos) {
            elementoAgendamentos.textContent = dados.totalAgendamentos;
        }

        if (elementoAgendamentosHoje) {
            elementoAgendamentosHoje.textContent = dados.agendamentosHoje;
        }

        if (listaProximos) {
            await carregarProximosAgendamentos(listaProximos);
        }

    } catch (erro) {
        console.error(erro);
    }
}

async function carregarProximosAgendamentos(listaProximos) {
    try {
        const resposta = await fetch(API_AGENDAMENTOS);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os agendamentos.");
        }

        const agendamentos = await resposta.json();
        const hoje = obterDataAtual();

        const proximos = agendamentos
            .filter((agendamento) => agendamento.data >= hoje)
            .sort((a, b) => {
                if (a.data !== b.data) {
                    return a.data.localeCompare(b.data);
                }

                return a.hora.localeCompare(b.hora);
            })
            .slice(0, 5);

        listaProximos.innerHTML = "";

        if (proximos.length === 0) {
            const item = document.createElement("div");
            item.className = "item-agendamento";

            item.innerHTML = `
                <span class="horario-agendamento">--:--</span>
                <div>
                    <strong>Nenhum agendamento futuro</strong>
                    <span>Cadastre novos atendimentos na agenda.</span>
                </div>
            `;

            listaProximos.appendChild(item);
            return;
        }

        proximos.forEach((agendamento) => {
            const item = document.createElement("div");
            item.className = "item-agendamento";
            const classeStatus = obterClasseStatusAgendamento(
                agendamento.status
            );

            item.innerHTML = `
                <span class="horario-agendamento">
                    ${escaparHtml(agendamento.hora)}
                </span>

                <div>
                    <strong>${escaparHtml(agendamento.cliente)}</strong>
                    <span>${escaparHtml(agendamento.servico)}</span>
                </div>

                <span class="status-agendamento ${classeStatus}">
                    ${escaparHtml(agendamento.status)}
                </span>
            `;

            listaProximos.appendChild(item);
        });

    } catch (erro) {
        console.error(erro);
    }
}

async function carregarProdutosEstoqueBaixo() {
    const listaEstoqueBaixo = document.getElementById("listaEstoqueBaixo");

    if (!listaEstoqueBaixo) {
        return;
    }

    try {
        const resposta = await fetch(API_PRODUTOS);

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os produtos.");
        }

        const produtos = await resposta.json();

        const produtosBaixos = produtos.filter(
            (produto) =>
                Number(produto.quantidade) <= Number(produto.estoqueMinimo)
        );

        listaEstoqueBaixo.innerHTML = "";

        if (produtosBaixos.length === 0) {
            const item = document.createElement("div");
            item.className = "item-estoque";

            item.innerHTML = `
                <span>Nenhum produto está com estoque baixo.</span>
                <strong>Tudo em ordem</strong>
            `;

            listaEstoqueBaixo.appendChild(item);
            return;
        }

        produtosBaixos.forEach((produto) => {
            const item = document.createElement("div");
            item.className = "item-estoque";

            item.innerHTML = `
                <span>${escaparHtml(produto.nome)}</span>
                <strong>${produto.quantidade} unidades</strong>
            `;

            listaEstoqueBaixo.appendChild(item);
        });

    } catch (erro) {
        console.error(erro);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    carregarIndicadoresDashboard();
    carregarProdutosEstoqueBaixo();
});