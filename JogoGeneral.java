import java.util.Arrays;
import java.io.Serializable;

public class JogoGeneral implements Serializable {
	private Dado[] dados = new Dado[5];
	private int[] jogadas = new int[13];

	// CONSTRUTOR
	public JogoGeneral() {
		int i;

		// Criando os 5 dados para o jogador
		for(i = 0; i < 5; i++) { 
			dados[i] = new Dado();
		}

		// Setando tabela de jogadas para -1(jogadas não feitas)
		for(i = 0 ; i < 13 ; i++) { 
			jogadas[i] = -1;
		}
	}

	public int getJogadas(int i) {
		return this.jogadas[i];
	}

	// FUNÇÃO PARA ROLAR TODOS OS 5 DADOS
	public void RolarDados() { 
		for(int i = 0 ; i < 5 ; i++) {
			dados[i].roll();
		}
		// Imprimindo os valores das faces superiores dos Dados
		System.out.println("Valores Obtidos: " + dados[0] + "-" + dados[1] + "-" + dados[2] + "-" 
			+ dados[3] + "-" + dados[4]);
	}

	public void jogadas(int valor) {
		int cont = 0; // contador para jogadas de [1-6] (numero de faces iguais)
		int resultado = 0; // vai somar a pontuação e atualizar no vetor de jogadas

		// 1 - 6
		if(valor >= 1 && valor <= 6) {			
			for(int i = 0 ; i < 5 ; i++) {
				if(this.dados[i].getFaceSup() == valor) { cont++; };
			}
			resultado = cont * valor;
		}
		
		// TRINCA (T)
		else if(valor == 7) {
			if(Ocorrencias(3) == true) { // 3 para trinca
				for(int k = 0 ; k < 5 ; k++) {
					resultado = resultado + this.dados[k].getFaceSup();
				}
			}
		}

		// QUADRA (Q)
		else if(valor == 8) {
			if(Ocorrencias(4) == true) { // 4 para quadra
				for(int k = 0 ; k < 5 ; k++) {
					resultado = resultado + this.dados[k].getFaceSup();
				}
			}
		}

		// FULL-HOUSE (F)
		else if(valor == 9) {
			// vetor aux de numeros de [1-6] que recebe as faces superiores
			int[] vet = {0,0,0,0,0,0}; 

			// INDICE 0 --> FACESUP 1 && INDICE 1 --> FACESUP 2 && INDICE 2 --> FACESUP 3 ... INDICE 5 --> FACESUP 6   

			// vetor vet[] recebendo o numero de faces para o indice respectivo
			for(int i = 0 ; i < 5 ; i++) { 
				vet[(this.dados[i].getFaceSup()) - 1] += 1;
			}

			int quant = 0; // para ser um Full House é necessario ter quatro indices igual a 0 e dois indices com resultados 3 e 2.
			boolean aux = false;
			for(int i = 0 ; i < 6 ; i++) {
				if(vet[i] == 0) {
					quant++;
				}
				if(vet[i] == 3)
				{
					aux = true;
				}
			}

			if(quant == 4 && aux == true) {
				resultado = 25;
			}
		}

		// SEQUENCIA ALTA (S+)
		else if(valor == 10) {	
			int[] vet = new int[5];

			for(int i = 0 ; i < 5 ;i++) {
				vet[i] = this.dados[i].getFaceSup();
			}

			// ordenando o vetor ...
			Arrays.sort(vet); 

			if(vet[0] == 2 && vet[1] == 3 && vet[2] == 4 && vet[3] == 5 && vet[4] == 6) {
				resultado = 30;
			}
		}

		// SEQUENCIA BAIXA (S-)
		else if(valor == 11) {
			int[] vet = new int[5];

			for(int i = 0 ; i < 5 ; i++) {
				vet[i] = this.dados[i].getFaceSup();
			}
			// ordenando o vetor ...
			Arrays.sort(vet);

			if(vet[0] == 1 && vet[1] == 2 && vet[2] == 3 && vet[3] == 4 && vet[4] == 5) {
				resultado = 40;
			}
		}

		// GENERAL (G)
		else if(valor == 12) {
			// cinco ocorrencias iguais para ser General (G)
			if(Ocorrencias(5) == true) { 
				resultado = 50;
			}
		}

		// JOGADA ALEATORIA (X)
		else if(valor == 13) {
			// somando todos os valores das faces superiores
			for(int i = 0 ; i < 5 ; i++) { 
				resultado = resultado + this.dados[i].getFaceSup();
			}
		}
		
		// Atualizando a pontuação obtida na jogada X
		this.jogadas[valor-1] = resultado;
		System.out.println("Pontuacao da Jogada: " + resultado);
		System.out.println("");
	}

	public int resultadoJogadas() { // TOTAL
		int soma = 0;

		for(int i = 0 ; i < 13 ; i++) {
			if(jogadas[i] != -1) {
				soma = soma + jogadas[i]; // somando as jogadas ...
			} else {
				return -1; // não teve jogadas ainda ...
			}
		}
		return soma;
	}

	// funcao para contar quantas vezes o numero se repete ... retornando true se faces_iguais
	// for satisfeito
	private boolean Ocorrencias(int faces_iguais) {
		int indice, aux;
		boolean valido = false;


		for(int i = 0 ; i < 5 ; i++) {
			aux=0;
			indice = this.dados[i].getFaceSup();

			for(int j=0 ; j<5 ; j++) {
				if(indice == this.dados[j].getFaceSup()) {
					aux++;
				}
			}

			if(aux >= faces_iguais) {
				valido = true;
			}
		}

		if(valido == true) {
			return true;
		} else {
			return false;
		}
	}
}