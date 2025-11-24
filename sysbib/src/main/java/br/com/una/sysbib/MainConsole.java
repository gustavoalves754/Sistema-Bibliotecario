package br.com.una.sysbib; // pacote onde esta classe fica organizada dentro do projeto

import br.com.una.sysbib.dao.EmprestimoDAO; // importa o DAO para acessar dados de empréstimos
import br.com.una.sysbib.dao.LivroDAO;      // importa o DAO para acessar dados de livros
import br.com.una.sysbib.dao.UsuarioDAO;    // importa o DAO para acessar dados de usuários
import br.com.una.sysbib.model.Emprestimo;  // importa o modelo de Empréstimo para criar/manipular objetos de empréstimo
import br.com.una.sysbib.model.Livro;       // importa o modelo de Livro para criar/manipular objetos de livro
import br.com.una.sysbib.model.Usuario;     // importa o modelo de Usuário para criar/manipular objetos de usuário

import java.time.LocalDate;                  // classe usada para trabalhar com datas (somente dia/mês/ano)
import java.time.format.DateTimeFormatter;   // classe usada para definir o formato da data (ex: dd/MM/yyyy)
import java.util.List;                       // lista dinâmica usada para guardar vários objetos (ex: lista de livros)
import java.util.Scanner;                    // Scanner usado para ler texto digitado pelo usuário no console

public class MainConsole { // classe principal que controla menus e execução do sistema via console

    private static final Scanner sc = new Scanner(System.in); // cria um leitor único do teclado para o programa inteiro
    private static final LivroDAO livroDAO = new LivroDAO(); // cria objeto para acessar o banco e manipular livros
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO(); // cria objeto para acessar o banco e manipular usuários
    private static final EmprestimoDAO emprestimoDAO = new EmprestimoDAO(); // cria objeto para acessar o banco e manipular empréstimos

    public static void main(String[] args) { // método principal onde o programa realmente começa

        int opcao = -1; // armazena a opção digitada no menu; começa com -1 só para entrar no while

        while (opcao != 0) { // loop do menu principal; repete enquanto a opção for diferente de 0 (sair)

            System.out.println("\n===== SISTEMA BIBLIOTECÁRIO ====="); // imprime título do sistema
            System.out.println("1 - Gerenciar Livros");              // mostra opção de acesso ao menu de livros
            System.out.println("2 - Gerenciar Usuários");            // mostra opção de acesso ao menu de usuários
            System.out.println("3 - Gerenciar Empréstimos");         // mostra opção de acesso ao menu de empréstimos
            System.out.println("0 - Sair");                          // opção para encerrar o programa
            System.out.println("================================="); // linha visual de separação

            opcao = lerInteiro("Escolha uma opção: "); // lê a escolha do usuário convertendo o texto digitado para número inteiro

            if (opcao == 1) menuLivros();         // se o usuário digitou 1, abre o menu específico de livros
            else if (opcao == 2) menuUsuarios();  // se digitou 2, abre o menu específico de usuários
            else if (opcao == 3) menuEmprestimos(); // se digitou 3, abre o menu específico de empréstimos
            else if (opcao == 0) System.out.println("Encerrando..."); // se for 0, mostra mensagem antes de sair
            else System.out.println("Opção inválida!");               // qualquer outra entrada é tratada como opção inválida
        }
    }

    // ============================================================
    // FUNÇÕES AUXILIARES
    // ============================================================

    private static LocalDate lerDataValida(String mensagem) { // método para ler uma data do usuário e garantir que ela é válida
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // define o formato brasileiro de data

        while (true) { // loop infinito até o usuário digitar uma data correta
            try {
                System.out.print(mensagem); // mostra a mensagem solicitando a data
                String entrada = sc.nextLine(); // lê o texto digitado pelo usuário e guarda em uma variável
                return LocalDate.parse(entrada, formatoBR); // converte o texto para LocalDate usando o formato BR e retorna caso seja válido
            } catch (Exception e) { // se der erro na conversão (data inválida)
                System.out.println("❌ Data inválida! Use dd/MM/yyyy"); // alerta ao usuário que o formato é inválido
            }
        }
    }

    private static int lerInteiro(String msg) { // método para garantir que o usuário digitou um número inteiro válido
        while (true) { // repete até o valor digitado ser um número válido
            try {
                System.out.print(msg); // mostra mensagem pedindo um número
                return Integer.parseInt(sc.nextLine()); // lê o texto e tenta converter para inteiro; se conseguir, retorna o valor
            } catch (Exception e) { // se não conseguir converter (ex: digitou letras)
                System.out.println("Digite um número válido."); // informa o erro e repete a pergunta
            }
        }
    }

// ============================================================
// MENU LIVROS
// ============================================================

private static void menuLivros() { // método que exibe e controla o menu de livros
    int op = -1; // variável que guarda a opção escolhida dentro do menu de livros

    while (op != 0) { // loop do menu, só para quando o usuário digita 0
        System.out.println("\n===== MENU LIVROS ====="); // imprime título do menu
        System.out.println("1 - Cadastrar Livro"); // mostra opção 1
        System.out.println("2 - Listar Livros"); // mostra opção 2
        System.out.println("3 - Atualizar Livro"); // mostra opção 3
        System.out.println("4 - Deletar Livro"); // mostra opção 4
        System.out.println("5 - Buscar por Autor"); // mostra opção 5
        System.out.println("0 - Voltar"); // mostra opção para voltar ao menu principal
        System.out.println("========================"); // linha visual

        op = lerInteiro("Escolha: "); // lê o número digitado e converte para int

        if (op == 1) cadastrarLivro(); // chama o método para cadastrar livro
        else if (op == 2) listarLivros(); // chama listar livros
        else if (op == 3) atualizarLivro(); // chama atualizar livro
        else if (op == 4) deletarLivro(); // chama deletar livro
        else if (op == 5) buscarLivrosPorAutor(); // chama busca por autor
        else if (op == 0) System.out.println("Voltando..."); // mensagem ao sair do menu
        else System.out.println("Opção inválida!"); // caso o número não seja reconhecido
    }
}

private static void cadastrarLivro() { // método que cadastra um livro novo
    System.out.println("\n--- Cadastro de Livro ---"); // título do processo

    System.out.print("ISBN: "); // pede ISBN
    String isbn = sc.nextLine(); // lê o texto digitado e salva em 'isbn'

    System.out.print("Título: "); // pede título
    String titulo = sc.nextLine(); // lê e salva o título

    System.out.print("Autor: "); // pede autor
    String autor = sc.nextLine(); // lê e salva o autor

    System.out.print("CDD: "); // pede CDD
    String cdd = sc.nextLine(); // lê e salva o CDD

    System.out.print("CDU: "); // pede CDU
    String cdu = sc.nextLine(); // lê e salva o CDU

    Livro livro = new Livro(isbn, titulo, autor, cdd, cdu); // cria um objeto Livro com as informações digitadas

    if (livroDAO.inserir(livro)) // chama o DAO para salvar no banco; se retornar true:
        System.out.println("Livro cadastrado com sucesso!"); // sucesso
    else
        System.out.println("Erro ao cadastrar livro."); // erro no insert
}

private static void listarLivros() { // método que lista todos os livros cadastrados
    System.out.println("\n--- Lista de Livros ---"); // título da seção

    List<Livro> lista = livroDAO.listarTodos(); // chama o DAO que retorna uma lista de livros

    if (lista.isEmpty()) { // se a lista vier vazia
        System.out.println("Nenhum livro encontrado."); // informa ausência de registros
        return; // sai do método
    }

    for (Livro l : lista) { // percorre cada livro da lista
        System.out.println("ID: " + l.getId() + // imprime ID
                " | Título: " + l.getTitulo() + // imprime título
                " | Autor: " + l.getAutor() + // imprime autor
                " | ISBN: " + l.getIsbn() + // imprime ISBN
                " | Disponível: " + (l.isDisponivel() ? "Sim" : "Não")); // imprime disponibilidade
    }
}

private static void atualizarLivro() { // método para atualizar dados de um livro já existente
    System.out.println("\n--- Atualizar Livro ---"); // título da ação

    int id = lerInteiro("ID do livro: "); // lê o ID do livro a ser atualizado
    Livro livro = livroDAO.buscarPorId(id); // busca o livro no banco pelo ID

    if (livro == null) { // se não encontrou o livro
        System.out.println("Livro não encontrado."); // mensagem de erro
        return; // sai do método
    }

    System.out.print("Novo título (" + livro.getTitulo() + "): "); // mostra título atual como referência
    String tit = sc.nextLine(); // lê novo valor
    if (!tit.isBlank()) livro.setTitulo(tit); // se não estiver vazio, altera o valor

    System.out.print("Novo autor (" + livro.getAutor() + "): "); // referência do autor
    String aut = sc.nextLine(); // lê novo valor
    if (!aut.isBlank()) livro.setAutor(aut); // altera se não estiver vazio

    System.out.print("Novo ISBN (" + livro.getIsbn() + "): "); // referência do ISBN atual
    String isbn = sc.nextLine(); // lê novo valor
    if (!isbn.isBlank()) livro.setIsbn(isbn); // altera se não estiver vazio

    if (livroDAO.atualizar(livro)) // tenta salvar alterações no banco
        System.out.println("Atualizado!"); // sucesso
    else
        System.out.println("Erro ao atualizar."); // falha no update
}

private static void deletarLivro() { // método que deleta um livro pelo ID
    System.out.println("\n--- Deletar Livro ---"); // título da ação
    int id = lerInteiro("ID: "); // lê o ID do livro

    if (livroDAO.deletar(id)) // tenta deletar pelo DAO
        System.out.println("Livro deletado."); // sucesso
    else
        System.out.println("Erro ao deletar."); // falha (provavelmente ID inválido)
}

private static void buscarLivrosPorAutor() { // método para buscar livros pelo nome do autor
    System.out.println("\n--- Buscar Livros por Autor ---"); // título da ação

    System.out.print("Nome do autor: "); // pede nome
    String autor = sc.nextLine(); // lê o nome digitado

    List<Livro> lista = livroDAO.buscarPorAutor(autor); // busca livros que contenham o nome no autor

    if (lista.isEmpty()) { // se não encontrou nenhum
        System.out.println("Nenhum livro encontrado."); // mensagem de retorno
        return; // sai
    }

    for (Livro l : lista) { // percorre lista encontrada
        System.out.println("ID: " + l.getId() + " | " + l.getTitulo()); // imprime ID e título
    }
}

// ============================================================
// MENU USUÁRIOS
// ============================================================

private static void menuUsuarios() { // método que controla o menu de usuários
    int op = -1; // guarda a escolha feita dentro do menu

    while (op != 0) { // loop do menu; só para quando o usuário digita 0
        System.out.println("\n===== MENU USUÁRIOS ====="); // título do menu
        System.out.println("1 - Cadastrar Usuário"); // opção 1
        System.out.println("2 - Listar Usuários"); // opção 2
        System.out.println("3 - Atualizar Usuário"); // opção 3
        System.out.println("4 - Deletar Usuário"); // opção 4
        System.out.println("0 - Voltar"); // voltar ao menu principal
        System.out.println("=========================="); // linha visual

        op = lerInteiro("Escolha: "); // lê opção digitada

        if (op == 1) cadastrarUsuario(); // cadastrar
        else if (op == 2) listarUsuarios(); // listar
        else if (op == 3) atualizarUsuario(); // atualizar
        else if (op == 4) deletarUsuario(); // deletar
        else if (op == 0) System.out.println("Voltando..."); // mensagem de saída
        else System.out.println("Opção inválida!"); // erro de entrada
    }
}

private static void cadastrarUsuario() { // método que cria um novo usuário
    System.out.println("\n--- Cadastro de Usuário ---"); // título

    System.out.print("Nome: "); // pede nome
    String nome = sc.nextLine(); // lê e armazena o nome digitado

    System.out.print("Email: "); // pede e-mail
    String email = sc.nextLine(); // lê e salva o e-mail

    Usuario u = new Usuario(nome, email); // cria objeto Usuario com essas informações

    if (usuarioDAO.inserir(u)) // tenta salvar no banco via DAO
        System.out.println("Usuário cadastrado!"); // sucesso
    else
        System.out.println("Erro ao cadastrar."); // falha
}

private static void listarUsuarios() { // lista todos os usuários cadastrados
    System.out.println("\n--- Lista de Usuários ---"); // título

    List<Usuario> lista = usuarioDAO.listarTodos(); // pega do banco todos os usuários

    if (lista.isEmpty()) { // verifica se a lista está vazia
        System.out.println("Nenhum usuário encontrado."); // mensagem caso não tenha nenhum
        return; // sai do método
    }

    for (Usuario u : lista) { // percorre a lista
        System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome()); // mostra id + nome
    }
}

private static void atualizarUsuario() { // método para atualizar dados de um usuário
    System.out.println("\n--- Atualizar Usuário ---"); // título

    int id = lerInteiro("ID do usuário: "); // lê ID do usuário
    Usuario u = usuarioDAO.buscarPorId(id); // busca no banco

    if (u == null) { // se o usuário não existir
        System.out.println("Usuário não encontrado."); // mensagem de erro
        return; // sai
    }

    System.out.print("Novo nome (" + u.getNome() + "): "); // mostra nome atual para referência
    String nome = sc.nextLine(); // lê novo nome
    if (!nome.isBlank()) u.setNome(nome); // altera somente se digitou algo

    System.out.print("Novo email (" + u.getEmail() + "): "); // referência do e-mail atual
    String email = sc.nextLine(); // lê novo valor
    if (!email.isBlank()) u.setEmail(email); // altera se não estiver vazio

    if (usuarioDAO.atualizar(u)) // tenta atualizar no banco
        System.out.println("Usuário atualizado!"); // sucesso
    else
        System.out.println("Erro ao atualizar."); // erro
}

private static void deletarUsuario() { // método para deletar um usuário
    System.out.println("\n--- Deletar Usuário ---"); // título

    int id = lerInteiro("ID do usuário: "); // pede ID do usuário

    if (usuarioDAO.deletar(id)) // chama o DAO para remover do banco
        System.out.println("Usuário deletado."); // sucesso
    else
        System.out.println("Erro ao deletar."); // falha
}

 // ============================================================
// MENU EMPRÉSTIMOS
// ============================================================

private static void menuEmprestimos() { // abre o menu específico de empréstimos
    int op = -1; // guarda a opção escolhida pelo usuário dentro deste menu

    while (op != 0) { // mantém o menu funcionando até o usuário digitar 0
        System.out.println("\n===== MENU EMPRÉSTIMOS ====="); // título do menu
        System.out.println("1 - Registrar Empréstimo"); // opção 1
        System.out.println("2 - Listar Ativos"); // lista apenas empréstimos em aberto
        System.out.println("3 - Listar Todos"); // lista empréstimos ativos + finalizados
        System.out.println("4 - Finalizar Empréstimo"); // devolução de livro
        System.out.println("5 - Deletar Empréstimo"); // remover registro
        System.out.println("0 - Voltar"); // voltar para o menu principal
        System.out.println("==============================="); // linha visual

        op = lerInteiro("Escolha: "); // lê a opção digitada e converte para int

        if (op == 1) registrarEmprestimo(); // chama tela de registrar empréstimo
        else if (op == 2) listarEmprestimosAtivos(); // chama listagem de ativos
        else if (op == 3) listarTodosEmprestimos(); // chama listagem geral
        else if (op == 4) finalizarEmprestimo(); // chama função de devolução
        else if (op == 5) deletarEmprestimo(); // chama função de deletar registro
        else if (op == 0) System.out.println("Voltando..."); // mensagem ao sair
        else System.out.println("Opção inválida!"); // caso escolha inválida
    }
}

private static void registrarEmprestimo() { // método que cria um novo empréstimo
    System.out.println("\n--- Registrar Empréstimo ---"); // título

    int idLivro = lerInteiro("ID do livro: "); // lê ID informado
    Livro livro = livroDAO.buscarPorId(idLivro); // busca livro no banco pelo ID

    if (livro == null || !livro.isDisponivel()) { // verifica se existe e está disponível
        System.out.println("Livro inválido ou indisponível."); // erro
        return; // volta ao menu
    }

    int idUsuario = lerInteiro("ID do usuário: "); // lê ID do usuário
    Usuario usuario = usuarioDAO.buscarPorId(idUsuario); // busca usuário no banco

    if (usuario == null) { // valida usuário
        System.out.println("Usuário não encontrado."); // erro
        return; // sai
    }

    LocalDate dataEmp = lerDataValida("Data de empréstimo (dd/MM/yyyy): "); // lê e valida data
    LocalDate dataPrev = lerDataValida("Data de devolução prevista (dd/MM/yyyy): "); // lê e valida data prevista

    Emprestimo e = new Emprestimo(); // cria objeto empréstimo vazio
    e.setIdLivro(idLivro); // salva ID do livro
    e.setIdUsuario(idUsuario); // salva ID do usuário
    e.setTituloLivro(livro.getTitulo()); // grava título diretamente no registro
    e.setNomeUsuario(usuario.getNome()); // grava nome diretamente no registro
    e.setDataEmprestimo(dataEmp); // define data do empréstimo
    e.setDataDevolucaoPrevista(dataPrev); // define data prevista
    e.setMultaAtrasoDia(2.50); // define multa diária fixa
    e.setMultaDano(100.00); // define multa por dano

    if (emprestimoDAO.registrarEmprestimo(e)) { // tenta salvar no banco
        livroDAO.atualizarDisponibilidade(idLivro, false); // marca livro como indisponível
        System.out.println("Empréstimo registrado! ID: " + e.getId()); // sucesso
    } else {
        System.out.println("Erro ao registrar empréstimo."); // falha
    }
}

private static void listarEmprestimosAtivos() { // lista empréstimos em aberto
    System.out.println("\n--- Empréstimos Ativos ---"); // título

    List<Emprestimo> lista = emprestimoDAO.listarAtivos(); // busca apenas ativos no banco

    if (lista.isEmpty()) { // verifica se não há registros
        System.out.println("Nenhum empréstimo ativo."); // mensagem
        return; // sai
    }

    for (Emprestimo e : lista) { // percorre lista
        System.out.println("ID: " + e.getId() + // mostra ID
                " | Livro: " + e.getTituloLivro() + // mostra título
                " | Usuário: " + e.getNomeUsuario() + // mostra usuário
                " | Empréstimo: " + e.getDataEmprestimo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // mostra data formatada
    }
}

private static void listarTodosEmprestimos() { // lista todos os empréstimos
    System.out.println("\n--- Todos os Empréstimos ---"); // título

    List<Emprestimo> lista = emprestimoDAO.listarTodos(); // busca geral (ativos + finalizados)

    if (lista.isEmpty()) { // se estiver vazio
        System.out.println("Nenhum registro encontrado."); // mensagem
        return;
    }

    for (Emprestimo e : lista) { // percorre lista
        System.out.println("ID: " + e.getId() + // ID
                " | Livro: " + e.getTituloLivro() + // título
                " | Usuário: " + e.getNomeUsuario() + // usuário
                " | Emprestado em: " + e.getDataEmprestimo() + // data inicial
                " | Previsto: " + e.getDataDevolucaoPrevista() + // data prevista
                " | Real: " + (e.getDataDevolucaoReal() == null ? "Não devolvido" : e.getDataDevolucaoReal())); // data real (ou aviso)
    }
}

private static void finalizarEmprestimo() { // método para devolver um livro
    System.out.println("\n--- Finalizar Empréstimo ---"); // título

    int idEmp = lerInteiro("ID do empréstimo: "); // lê o ID

    Emprestimo emp = emprestimoDAO.buscarPorId(idEmp); // busca no banco

    if (emp == null) { // valida se existe
        System.out.println("Empréstimo não encontrado."); // aviso
        return;
    }

    System.out.println("Livro: " + emp.getTituloLivro()); // mostra qual livro é
    System.out.println("Usuário: " + emp.getNomeUsuario()); // mostra quem pegou

    LocalDate dataReal = lerDataValida("Data de devolução REAL (dd/MM/yyyy): "); // lê data real da devolução

    System.out.print("Houve dano ao livro (s/n): "); // pergunta se teve dano
    boolean dano = sc.nextLine().trim().equalsIgnoreCase("s"); // converte resposta para boolean

    if (emprestimoDAO.finalizarEmprestimo(idEmp, dataReal, dano)) { // tenta finalizar no banco
        livroDAO.atualizarDisponibilidade(emp.getIdLivro(), true); // marca livro como disponível novamente
        Emprestimo atualizado = emprestimoDAO.buscarPorId(idEmp); // busca o registro atualizado

        System.out.println("Devolução registrada!"); // sucesso
        System.out.printf("Multa total: R$ %.2f%n", atualizado.getValorMultaTotal()); // exibe multa calculada
    } else {
        System.out.println("Erro ao finalizar."); // falha
    }
}

private static void deletarEmprestimo() { // método para excluir empréstimo do banco
    System.out.println("\n--- Deletar Empréstimo ---"); // título

    int id = lerInteiro("ID do empréstimo: "); // lê ID informado

    if (emprestimoDAO.deletar(id)) // tenta deletar no banco
        System.out.println("Empréstimo deletado."); // sucesso
    else
        System.out.println("Erro ao deletar."); // falha
}
}
