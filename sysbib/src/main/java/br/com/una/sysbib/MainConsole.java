package br.com.una.sysbib; // Define o pacote (pasta lógica) onde esta classe está localizada.

import br.com.una.sysbib.dao.Conexao; // Importa a classe Conexao para gerenciar o banco de dados.
import br.com.una.sysbib.dao.EmprestimoDAO; // Importa a classe DAO para operações de Empréstimo.
import br.com.una.sysbib.dao.LivroDAO; // Importa a classe DAO para operações de Livro.
import br.com.una.sysbib.dao.UsuarioDAO; // Importa a classe DAO para operações de Usuário.
import br.com.una.sysbib.model.Emprestimo; // Importa o modelo (estrutura de dados) de Empréstimo.
import br.com.una.sysbib.model.Livro; // Importa o modelo (estrutura de dados) de Livro.
import br.com.una.sysbib.model.Usuario; // Importa o modelo (estrutura de dados) de Usuário.

import java.time.LocalDate; // Importa a classe LocalDate para trabalhar com datas atuais.
import java.time.format.DateTimeFormatter; // Importa o formatador para exibir datas.
import java.util.List; // Importa a interface List para trabalhar com coleções (listas de objetos).
import java.util.Scanner; // Importa a classe Scanner para ler dados de entrada do usuário (teclado).

public class MainConsole { // Declara a classe principal do programa.

    private static final Scanner scanner = new Scanner(System.in); // Cria um objeto Scanner estático para ler a entrada do console (System.in).
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Cria um formatador de data estático no padrão brasileiro (Dia/Mês/Ano).
    private static final LivroDAO livroDAO = new LivroDAO(); // Cria uma instância estática da classe LivroDAO.
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Cria uma instância estática da classe UsuarioDAO.
    private static final EmprestimoDAO emprestimoDAO = new EmprestimoDAO(); // Cria uma instância estática da classe EmprestimoDAO.

    public static void main(String[] args) { // Método principal onde a execução do programa começa.
        // 1. Inicializa o Banco de Dados (cria o arquivo e as tabelas)
        Conexao.inicializarBanco(); // Chama o método da classe Conexao para garantir que o arquivo .db e as tabelas existam.
        
        int opcao; // Declara a variável 'opcao' para armazenar a escolha do usuário.
        
        // 2. Loop PRINCIPAL: Usa o 'do-while' para repetir o menu.
        // A estrutura 'do-while' garante que o menu rode PELO MENOS UMA VEZ.
        do { // INÍCIO DO BLOCO A SER EXECUTADO:
            exibirMenu(); // Chama o método para mostrar o menu de opções.
            
            // Usando 'if/else' para garantir que a entrada seja um número antes de processar.
            if (scanner.hasNextInt()) { // Verifica SE o próximo token na entrada é um número inteiro.
                opcao = scanner.nextInt(); // Lê o número inteiro digitado pelo usuário.
                scanner.nextLine(); // Consome o resto da linha (a quebra de linha) para evitar problemas com leituras futuras de texto.
                processarOpcao(opcao); // Chama o método para executar a função escolhida (opção).
            } else { // SE NÃO (else) for um número inteiro:
                System.out.println("❌ Entrada inválida. Digite um número de 0 a 7."); // Imprime mensagem de erro.
                scanner.nextLine(); // Descarta a entrada inválida (ex: se o usuário digitou "abc").
                opcao = -1; // Atribui um valor inválido (-1) para garantir que o loop continue na próxima iteração.
            }
        } while (opcao != 0); // FIM DO BLOCO. O loop continua ENQUANTO (while) a opção for diferente de zero.
        
        System.out.println("\n👋 Sistema Encerrado. Até logo!"); // Mensagem de saída quando o loop 'do-while' termina (opcao é 0).
        Conexao.closeConnection(Conexao.getConnection()); // Chama o método para fechar a conexão ativa com o banco de dados.
    }
    
    private static void exibirMenu() { // Método privado para apenas mostrar o menu.
        System.out.println("\n=============================================="); // Imprime separador.
        System.out.println("            📚 SISTEMA BIBLIOTECÁRIO 📚"); // Imprime o título.
        System.out.println("=============================================="); // Imprime separador.
        System.out.println("1.  Cadastrar Novo Livro"); // Opção 1.
        System.out.println("2.  Listar Todos os Livros"); // Opção 2.
        System.out.println("3.  Cadastrar Novo Usuário"); // Opção 3.
        System.out.println("4.  Listar Todos os Usuários"); // Opção 4.
        System.out.println("----------------------------------------------"); // Separador.
        System.out.println("5.  Registrar Emprestimo"); // Opção 5.
        System.out.println("6.  Finalizar Emprestimo (Devolução)"); // Opção 6.
        System.out.println("7.  Listar Emprestimos Ativos"); // Opção 7.
        System.out.println("----------------------------------------------"); // Separador.
        System.out.println("0.  Sair"); // Opção 0 para encerrar o loop.
        System.out.println("=============================================="); // Imprime separador.
        System.out.print("Escolha uma opção: "); // Solicita a entrada do usuário.
    }
    
    // Método com IF/ELSE para controle de fluxo
    private static void processarOpcao(int opcao) { // Método que recebe a opção numérica e executa a ação.
        // Início da cadeia de 'if/else if/else' (se/se não/se não):
        if (opcao == 1) { // SE a opção for 1:
            cadastrarLivro(); // Chama o método de cadastro.
        } else if (opcao == 2) { // SE NÃO (else) SE a opção for 2:
            listarLivros(); // Chama o método de listagem.
        } else if (opcao == 3) { // SE NÃO SE a opção for 3:
            cadastrarUsuario(); // Chama o método de cadastro de usuário.
        } else if (opcao == 4) { // SE NÃO SE a opção for 4:
            listarUsuarios(); // Chama o método de listagem de usuários.
        } else if (opcao == 5) { // SE NÃO SE a opção for 5:
            registrarEmprestimo(); // Chama o método de registrar empréstimo.
        } else if (opcao == 6) { // SE NÃO SE a opção for 6:
            finalizarEmprestimo(); // Chama o método de devolução.
        } else if (opcao == 7) { // SE NÃO SE a opção for 7:
            listarEmprestimos(); // Chama o método de listar empréstimos ativos.
        } else if (opcao == 0) { // SE NÃO SE a opção for 0:
            // Não faz nada; o loop 'do-while' no main() se encarregará de sair.
        } else { // SE NENHUMA das condições acima for verdadeira (else):
            System.out.println("❌ Opção inválida. Digite um número de 0 a 7."); // Mensagem de erro para opção fora do menu.
        }
    }
    
    private static void listarLivros() { // Método para listar todos os livros.
        System.out.println("\n--- Lista de Livros ---"); // Imprime título.
        List<Livro> livros = livroDAO.buscarTodos(); // Chama o DAO para buscar todos os livros do banco de dados e armazena na lista 'livros'.

        if (livros.isEmpty()) { // Verifica SE a lista de livros está vazia.
            System.out.println("Nenhum livro cadastrado."); // Mensagem de lista vazia.
        } else { // SE NÃO estiver vazia (else):
            // Loop FOR-EACH: Itera sobre a lista de livros, um por um.
            // Para cada objeto 'Livro' (chamado 'l') na coleção 'livros', execute o bloco.
            for (Livro l : livros) { 
                String status = l.isDisponivel() ? "[Disponível]" : "[EMPRESTADO]"; // Usa o operador ternário (if/else simplificado) para definir o status do livro.
                System.out.printf("ID: %-3d | Título: %-40s | Autor: %-25s | Status: %s\n", // Imprime os dados formatados.
                                  l.getId(), l.getTitulo(), l.getAutor(), status); // Chama os métodos getters (obter valores) do objeto 'l'.
            }
        }
    }
    
    private static void cadastrarLivro() { // Método para obter dados e cadastrar um livro.
        System.out.println("\n--- Cadastro de Livro ---"); // Imprime título.
        System.out.print("Título do Livro: "); // Solicita o título.
        String titulo = scanner.nextLine(); // Lê a linha completa (título) digitada pelo usuário.
        System.out.print("Autor: "); // Solicita o autor.
        String autor = scanner.nextLine(); // Lê a linha completa (autor) digitada pelo usuário.

        Livro novoLivro = new Livro(titulo, autor); // Cria um novo objeto Livro com os dados lidos.
        if (livroDAO.inserir(novoLivro)) { // SE o método 'inserir' do DAO retornar VERDADEIRO (cadastro bem-sucedido):
            System.out.println("✅ Livro cadastrado com sucesso! ID: " + novoLivro.getId()); // Mensagem de sucesso, exibindo o ID gerado pelo banco.
        } else { // SE NÃO (else) retornar FALSO:
            System.out.println("❌ Erro ao cadastrar livro."); // Mensagem de erro.
        }
    }

    private static void cadastrarUsuario() { // Método para obter dados e cadastrar um usuário.
        System.out.println("\n--- Cadastro de Usuário ---"); // Imprime título.
        System.out.print("Nome do Usuário: "); // Solicita o nome.
        String nome = scanner.nextLine(); // Lê o nome.
        System.out.print("Email: "); // Solicita o email.
        String email = scanner.nextLine(); // Lê o email.

        Usuario novoUsuario = new Usuario(nome, email); // Cria um novo objeto Usuario.
        if (usuarioDAO.inserir(novoUsuario)) { // SE a inserção no DAO for bem-sucedida:
            System.out.println("✅ Usuário cadastrado com sucesso! ID: " + novoUsuario.getId()); // Mensagem de sucesso.
        } else { // SE NÃO:
            System.out.println("❌ Erro ao cadastrar usuário."); // Mensagem de erro.
        }
    }
    
    private static void listarUsuarios() { // Método para listar todos os usuários.
        System.out.println("\n--- Lista de Usuários ---"); // Imprime título.
        List<Usuario> usuarios = usuarioDAO.buscarTodos(); // Busca todos os usuários do banco.
        if (usuarios.isEmpty()) { // SE a lista estiver vazia:
            System.out.println("Nenhum usuário cadastrado."); // Mensagem de lista vazia.
        } else { // SE NÃO (else):
            // Loop FOR-EACH para listar:
            for (Usuario u : usuarios) { // Para cada objeto 'u' na lista 'usuarios'.
                System.out.printf("ID: %-3d | Nome: %-30s | Email: %s\n", // Imprime os dados formatados.
                                  u.getId(), u.getNome(), u.getEmail()); // Chama os métodos getters do objeto 'u'.
            }
        }
    }

    private static void registrarEmprestimo() { // Método para registrar um empréstimo.
        System.out.println("\n--- Registro de Empréstimo ---"); // Imprime título.
        System.out.print("ID do Livro a ser emprestado: "); // Solicita o ID do livro.
        int idLivro = scanner.nextInt(); // Lê o ID do livro.
        System.out.print("ID do Usuário: "); // Solicita o ID do usuário.
        int idUsuario = scanner.nextInt(); // Lê o ID do usuário.
        scanner.nextLine(); // Consome a quebra de linha.

        Livro livro = livroDAO.buscarPorId(idLivro); // Busca o objeto Livro pelo ID no banco.
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario); // Busca o objeto Usuario pelo ID no banco.

        // Uso do IF/ELSE ANINHADO para validação:
        if (livro == null) { // SE o livro for nulo (ID não encontrado):
            System.out.println("❌ Livro com ID " + idLivro + " não encontrado."); // Erro.
        } else if (usuario == null) { // SE NÃO SE o usuário for nulo (ID não encontrado):
            System.out.println("❌ Usuário com ID " + idUsuario + " não encontrado."); // Erro.
        } else if (!livro.isDisponivel()) { // SE NÃO SE o livro NÃO estiver disponível:
            System.out.println("❌ Livro indisponível (já emprestado)."); // Erro.
        } else { // ELSE final: se TUDO deu certo (livro e usuário existem e livro está disponível).
            // Lógica de datas:
            String dataEmprestimo = LocalDate.now().format(dateFormatter); // Obtém a data atual e formata.
            String dataDevolucaoPrevista = LocalDate.now().plusDays(7).format(dateFormatter); // Obtém a data atual + 7 dias e formata.

            Emprestimo novoEmprestimo = new Emprestimo(idLivro, idUsuario, dataEmprestimo, dataDevolucaoPrevista); // Cria o objeto Emprestimo.

            if (emprestimoDAO.registrar(novoEmprestimo) && livroDAO.atualizarDisponibilidade(idLivro, false)) { // SE o registro no EmprestimoDAO for bem-sucedido E a atualização de disponibilidade for bem-sucedida:
                System.out.println("✅ Empréstimo registrado com sucesso!"); // Sucesso.
                System.out.println("   Devolução prevista para: " + dataDevolucaoPrevista); // Exibe a data prevista.
            } else { // SE NÃO (else):
                System.out.println("❌ Erro ao registrar empréstimo ou atualizar disponibilidade do livro."); // Mensagem de erro.
            }
        }
    }

    private static void finalizarEmprestimo() { // Método para finalizar um empréstimo (devolver).
        System.out.println("\n--- Finalizar Emprestimo (Devolução) ---"); // Imprime título.
        System.out.print("ID do Registro de Empréstimo a ser finalizado: "); // Solicita o ID do registro (não o ID do livro).
        int idEmprestimo = scanner.nextInt(); // Lê o ID.
        scanner.nextLine(); 

        List<Emprestimo> emprestimos = emprestimoDAO.buscarTodos(); // Busca todos os registros de empréstimo.
        Emprestimo emprestimoParaDevolucao = null; // Variável para guardar o objeto de empréstimo encontrado, inicializada como nula.
        int idLivroDevolvido = -1; // Variável para guardar o ID do livro, inicializada com valor inválido.

        // Loop FOR para encontrar o empréstimo pelo ID (Simulando uma busca):
        for (Emprestimo e : emprestimos) { // Para cada registro 'e' na lista de 'emprestimos'.
            if (e.getId() == idEmprestimo) { // SE o ID do registro 'e' for igual ao ID digitado:
                emprestimoParaDevolucao = e; // Atribui o objeto encontrado à variável.
                idLivroDevolvido = e.getIdLivro(); // Pega o ID do livro envolvido no empréstimo.
                break; // Usa 'break' para sair do loop FOR imediatamente, pois o item foi encontrado.
            }
        }

        if (emprestimoParaDevolucao == null) { // SE o objeto continuar nulo (não foi encontrado no loop for):
            System.out.println("❌ Registro de empréstimo ID " + idEmprestimo + " não encontrado."); // Erro.
            return; // Sai do método.
        }

        // Deleta o registro e atualiza a disponibilidade:
        if (emprestimoDAO.deletar(idEmprestimo) && livroDAO.atualizarDisponibilidade(idLivroDevolvido, true)) { // SE a deleção do registro for bem-sucedida E a atualização do livro para 'disponível' for bem-sucedida:
            System.out.println("✅ Devolução finalizada com sucesso!"); // Sucesso.
        } else { // SE NÃO (else):
            System.out.println("❌ Erro ao finalizar empréstimo ou atualizar disponibilidade."); // Mensagem de erro.
        }
    }

    private static void listarEmprestimos() { // Método para listar todos os empréstimos ativos.
        System.out.println("\n--- Lista de Empréstimos Ativos ---"); // Imprime título.
        List<Emprestimo> emprestimos = emprestimoDAO.buscarTodos(); // Busca todos os registros de empréstimo no banco.
        
        if (emprestimos.isEmpty()) { // SE a lista estiver vazia:
            System.out.println("Nenhum empréstimo ativo."); // Mensagem de lista vazia.
        } else { // SE NÃO (else):
            // Loop FOR-EACH para listar os empréstimos:
            for (Emprestimo e : emprestimos) { // Para cada objeto 'e' na lista 'emprestimos'.
                System.out.printf("ID: %-3d | Livro ID: %-3d | Usuário ID: %-3d | Empréstimo: %s | Devolução Prevista: %s\n", // Imprime os dados formatados.
                                  e.getId(), // Chama o getter para obter o ID do registro de empréstimo.
                                  e.getIdLivro(), // Chama o getter para obter o ID do Livro envolvido.
                                  e.getIdUsuario(), // Chama o getter para obter o ID do Usuário envolvido.
                                  e.getDataEmprestimo(), // Chama o getter para obter a Data em que o empréstimo foi feito.
                                  e.getDataDevolucaoPrevista()); // Chama o getter para obter a Data Prevista de Devolução.
            }
        }
    }
}