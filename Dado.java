import java.util.Random;
import java.io.Serializable;

public class Dado implements Serializable {
	// Face superior do dado
	private int FaceSup;

	// CONSTRUTOR SEM ARGUMENTOS
	public Dado() {
		this.FaceSup = 1;
	}

	// CONSTRUTOR COM ARGUMENTOS
	public Dado(int FaceSup) {
		this.FaceSup = FaceSup;
	}
	
	public int getFaceSup() {
		return this.FaceSup;
	}

	public void setFaceSup(int s) {
		this.FaceSup = s;
	}

	//Função para rolar um único Dado
	public void roll() {
		Random aleatorio = new Random();
		setFaceSup(aleatorio.nextInt(6) + 1); // 0-5 + 1 por conta do 0
	}

	public String toString() {
		return(Integer.toString(FaceSup));
	}
}