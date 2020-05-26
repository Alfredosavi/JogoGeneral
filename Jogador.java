import java.io.Serializable;

public class Jogador implements Serializable {
	private String nome;
	private JogoGeneral ficha;

	// CONSTRUTOR
	public Jogador(String nome) { 
		this.nome = nome;
		this.ficha = new JogoGeneral();
	}

	public String getNome() {
		return this.nome;
	}

	// Função para pegar as jogadas da ficha dos respectivos jogadores.
	public int getFichaJogada(int i) { 
		return this.ficha.getJogadas(i);
	}

	// Função para jogar os 5 dados ...
	public void jogarDados() { 
		System.out.println("rolando dados para " + this.nome + " ...");
		ficha.RolarDados();
	}

	public String toString() {
		return (this.nome);
	}

	// Função que retorna o valor de uma jogada especifica
	public void chamajogadas(int valor) { 
		this.ficha.jogadas(valor);
	}

	// Função para somar a pontuaçao de cada jogador (se -1 porque não iniciou jogada)
	public int chamaResultado() { 
		return (this.ficha.resultadoJogadas());
	}

	// MENU de jogadas
	public void jogadasFeitas() { 
		System.out.println("1\t" + "2\t" + "3\t" + "4\t" + "5\t" + "6\t" + "7(T)\t" + "8(Q)\t" + 
						   "9(F)\t" + "10(S+)\t" + "11(S-)\t" + "12(G)\t" + "13(X)\t");

		// Jogadas já feitas
		for (int i = 0 ; i < 13 ; i++) { 
			if(this.ficha.getJogadas(i) != -1) {
				System.out.print("X\t");
			} else {
				System.out.print("-\t");
			}
		}
		System.out.println("");
	}

	// Função para verificar se a jogada foi feita
	public boolean verificaJogadas(int opcao) { 
		if(opcao >= 1 && opcao <= 13) {
	 		if(this.ficha.getJogadas(opcao-1) == -1) {
				return true; // true para jogada não feita ainda ...
			}else{
				System.out.println("Essa jogada ja foi feita, escolha outra.");
				return false; // false para jogada já feita ... 
			}
		}
		// caso esteja fora do intervalo ... (else)
		System.out.println("Escolha um numero valido entre [1-13].");
		return false;
	}
}