import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Campeonato {
	
	// ******************* INICIALIZANDO VARIAVEIS *******************
	private static int njogadores = 0; // variavel controle para numero de jogadores 
	private static Jogador[] jogadores = new Jogador[5]; // vetor dos jogadores presentes para o campeonato
	// ***************************************************************
	
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in); // Funcao para "scanf" versao java ...
		String s;

		// variavel para selecionar as opções do MENU
		char opcao; 

		// Inciando MENU de Opções (interativo)
		do
		{
			System.out.println("(a) Incluir Jogador");
			System.out.println("(b) Remover Jogador");
			System.out.println("(c) Iniciar/Reiniciar o Campeonato");
			System.out.println("(d) Mostrar a Cartela de Resultados");
			System.out.println("(e) Gravar os Dados do Campeonato em Arquivo");
			System.out.println("(f) Ler os Dados do Campeonato em Arquivo");
			System.out.println("(g) Sair da Aplicação");

			System.out.print("Entre com a opção do menu: ");
			s = teclado.next();
    		opcao = s.charAt(0); // convertendo string para char

			switch(opcao) {

				// INCLUIR JOGADOR 
				case 'a':
				{
					System.out.println("");

					// Validando para maximo de 5 jogadores
					if(njogadores < 5) { 
						String nome;
						System.out.print("Nome do Jogador(a): ");
						nome = teclado.nextLine(); // Pega o enter do anterior
						nome = teclado.nextLine();

						jogadores[njogadores] = new Jogador(nome);
						njogadores++;
					} else {
						System.out.println("Não é possivel inserir mais jogadores !!!");
					}
					break;
				}

				// REMOVE JOGADOR
				case 'b': 
				{
					if(njogadores != 0) {
						int escolha; // para escolher o jogador

						System.out.println("");
						// Função para mostrar os jogadores presentes no campeonato
						mostraJogadores(jogadores, njogadores); 
						System.out.print("Escolha o numero do jogador(a) a ser removido: ");
						escolha = teclado.nextInt(); // leitura da escolha
		
						if(escolha >= 1 && escolha <= njogadores) {
							int aux = njogadores; //para nao perder o numero de jogadores na condição do for 
							for(int i = (escolha-1); i < (aux-1); i++){
								jogadores[i] = jogadores[i+1]; // "Puxando" jogadores para o inicio do vetor
							}
							njogadores--;
						} else {
							System.out.println("Jogador inexistente . Existem apenas " + njogadores + " Jogadores presentes.");
						}
					} else {
						System.out.println("");
						System.out.println("Não há jogadores no Campeonato!");
					}
						for(int i = njogadores ; i<5 ; i++) {
							jogadores[i] = null;
						}

					break;
				}

				// INICIA OU REINICIA CAMPEONATO
				case 'c': 
				{
					System.out.println("");

					int escolha;
					if(njogadores == 0) {
						System.out.println("Não há jogadores para iniciar o Campeonato.");
					} else {
						// CASO SEJA PARA REINICIAR O CAMPEONATO 
						if(jogadores[0].chamaResultado() != -1) { 
							// chamaResultado --> Função para somar a pontuaçao de cada jogador (se -1 porque não iniciou jogada)
							String nome;

							// zerando as pontuações dos jogadores
							for(int i=0 ; i<njogadores ; i++) { 
								nome = jogadores[i].getNome();
								jogadores[i] = new Jogador(nome); // chamando construtor para zerar pontuação dos jogadores
							}
							System.out.println("Campeonato Reiniciado !!!");
						}

						for(int rodadas = 0; rodadas < 13; rodadas++) { // LOOP DAS JOGADAS
							for(int j=0; j <= njogadores-1; j++){
								System.out.println("");
								jogadores[j].jogarDados();
								System.out.println("--------------------------------");
								System.out.print("Para qual jogada deseja marcar: [1 - 13] ");
								System.out.println(jogadores[j] + "?");
								jogadores[j].jogadasFeitas(); // Mostra Menu de jogadas e suas respectivas escolhas ja feitas

								// validação do intervalo da escolha ...
								do{ 
									System.out.print("Sua escolha: ");
									escolha = teclado.nextInt();
								}while(jogadores[j].verificaJogadas(escolha) == false);

								// validação se a escolha já foi feita ou não ...
								jogadores[j].chamajogadas(escolha);
							}
						}
					}
					break;
				}

				// MOSTRAR CARTELA DOS RESULTADOS
				case 'd': 
				{
					System.out.println("");

					// mostra o resultado do campeonato ... caso existam jogadores
					mostraCartela(jogadores, njogadores); 
					break;
				}

				// GRAVAR DADOS DO CAMPEONATO
				case 'e': 
				{
					System.out.println("");
					File arquivo = new File("Campeonato.dat");
					try {
						FileOutputStream fout = new FileOutputStream(arquivo);
						ObjectOutputStream oos = new ObjectOutputStream(fout);

						oos.writeObject(jogadores);

						oos.flush();
						oos.close();
						fout.close();
						System.out.println("Arquivo campeonato Gravado com sucesso !!!");
					}catch(Exception ex) {
						System.err.println("erro: " + ex.toString());

					}
					break;
				}

				// LER DADOS DO CAMPEONATO
				case 'f': 
				{
					System.out.println("");
					File arquivo = new File("Campeonato.dat");

					try {
						FileInputStream fin = new FileInputStream(arquivo);
						ObjectInputStream oin = new ObjectInputStream(fin);

						jogadores = (Jogador[]) oin.readObject();
						oin.close();
						fin.close();

						int i=0;
						for(Jogador p : jogadores) {		
							if(p != null){
								i++;
							}
						}
						njogadores = i;	
						System.out.println("Leitura dos dados feita com sucesso !!!");
					}catch (Exception ex) {
						System.err.println("erro: " + ex.toString());
					}
 				
					break;
				}

				// SAIR DO MENU
				case 'g': 
				{
					// Funcao para sair do MENU
					break;
				}

				// caso opção for diferente do intervalo [a-g]
				default: 
				{
					System.out.println("\nOpção Invalida !!!");
				}
			}
			System.out.println("");
		}while(opcao != 'g');
	}


	// **************************************** FUNCÕES AUXILIARES ******************************************
	public static void mostraJogadores(Jogador[] j, int cont) {
		System.out.println("-- Lista de Jogadores --");
		for (int i = 0; i < cont; i++) {
			System.out.println("Jogador(a) " + (i+1) + ": " + j[i]);
		}
		System.out.println("------------------------");
	}

	public static void mostraCartela(Jogador[] jog, int nj) {

		if(nj == 0) {
			System.out.println("Não há jogadores para existir uma cartela de Campeonato.");
		} else {
			int i;
			String[] s = {"1", "2", "3", "4", "5", "6", "7(T)", "8(Q)", "9(F)", "10(S+)", "11(S-)", "12(G)", "13(X)"};
			
			System.out.println("-- Cartela de Resultados --");
			System.out.print("\t"); // Cabeçalho
			
			// Mostrar os nomes dos jogadores
			for(i = 0 ; i < nj ; i++) { 
				System.out.print(jog[i] + "  ");
			}
			System.out.println("");
			
			// Mostra a pontuação da respectiva jogada
			for (i = 0 ; i < 13 ; i++) {
				System.out.print(s[i] + "\t");

				for (int j = 0 ; j < nj ; j++) {
					// getFichaJogada --> Função para pegar as jogadas da ficha dos respectivos jogadores.
					System.out.print(jog[j].getFichaJogada(i) + "   \t");
				}
				System.out.println("");
			}
			System.out.println("--------------------------------------------------------");

			// MOSTRAR O TOTAL DE PONTOS DE CADA JOGADOR
			System.out.print("Total" + "\t");
			for(i = 0 ; i < nj ; i++) {
				System.out.print(jog[i].chamaResultado() + "   \t");
			}
			System.out.println("");
		}
	}
}