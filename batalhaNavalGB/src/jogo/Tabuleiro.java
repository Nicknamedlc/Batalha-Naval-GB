package jogo;

import java.util.ArrayList;

public class Tabuleiro {
	private Navio[] naviosTabuleiro = new Navio[6];
	private ArrayList<int[]> tiro = new ArrayList<int[]>();
	private int[][] grade = new int[10][10];
	private boolean isAdv = false;

	/*
	 * Lista da grade int 0 = Agua 1 = Tentativa 2 = Navio 3 = Acerto navio
	 */

	public Tabuleiro() {
		for (int i = 0; i < grade.length; i++) {
			for (int j = 0; j < grade[i].length; j++) {
				grade[i][j] = 0;
			}
		}
	}

	public Tabuleiro(boolean adv) {
		this.isAdv = adv;
		for (int i = 0; i < grade.length; i++) {
			for (int j = 0; j < grade[i].length; j++) {
				grade[i][j] = 0;
			}
		}
	}

	public Tabuleiro(boolean adv, Navio[] navios) {
		for (int i = 0; i < grade.length; i++) {
			for (int j = 0; j < grade[i].length; j++) {
				grade[i][j] = 0;
			}
		}

		this.isAdv = adv;
		for (Navio navio : navios) {
			adcionarNavio(navio);
		}
	}

	public Tabuleiro(Navio[] navios) {
		for (int i = 0; i < grade.length; i++) {
			for (int j = 0; j < grade[i].length; j++) {
				grade[i][j] = 0;
			}
		}

		for (Navio navio : navios) {
			adcionarNavio(navio);
		}
	}

	// Getters e Setters
	public boolean isAdv() {
		return isAdv;
	}

	public void setAdv(boolean isAdv) {
		this.isAdv = isAdv;
	}

	public int[][] getGrade() {
		return grade;
	}

	public void setGrade(ArrayList<int[]> arrayList, int item) {
		for (int i = 0; i < arrayList.size(); i++) {
			grade[arrayList.get(i)[0]][arrayList.get(i)[1]] = item;
		}
	}

	public Navio[] getNaviosTabuleiro() {
		return naviosTabuleiro;
	}

	public void setNaviosTabuleiro(Navio[] naviosTabuleiro) {
		this.naviosTabuleiro = naviosTabuleiro;
		for (int i = 0; i < this.naviosTabuleiro.length; i++) {
			setGrade(this.naviosTabuleiro[i].getPos(), 2);
		}
	}

	public ArrayList<int[]> getTiro() {
		return tiro;
	}

	public void setTiro(ArrayList<int[]> tiro) {
		this.tiro = tiro;
	}

	/*
	 * Lista da grade int 0 = Agua 1 = Tentativa 2 = Navio 3 = Acerto navio
	 */

	public boolean adicionaTiro(int x, int y) {
		int[] tiroNovo = { x, y };
		tiro.add(tiroNovo);
		switch (grade[x][y]) {
		case 0: {
			grade[x][y] = 1;
			System.out.println("Água!!!");
			return true;
		}
		case 1: {
			System.out.println("Tentativa já feita");
			tiro.removeLast();
			return false;
		}
		case 2: {
			grade[x][y] = 3;
			for (int i = 0; i < naviosTabuleiro.length; i++) {
				for (int j = 0; j < naviosTabuleiro[i].getPos().size(); j++)
					if (naviosTabuleiro[i].getPos().get(j) == new int[] { x, y })
						naviosTabuleiro[i].removePos(j);
			}
			System.out.println("Acerto!!!");

		}
		case 3: {
			System.out.println("Tentativa já feita");
			tiro.removeLast();
			return false;
		}
		default: {
			return false;
		}
		}
	}

	public void adcionarNavio(Navio n1) {
		ArrayList<int[]> slotsNavio = n1.getPos();
		int posJ, posI;
		for (int i = 0; i < slotsNavio.size(); i++) {
			posI = slotsNavio.get(i)[0];
			posJ = slotsNavio.get(i)[1];
			colocaNavios(posI, posJ);
		}
		for (int i = 0; i < naviosTabuleiro.length; i++) {
			if (naviosTabuleiro[i] != null)
				continue;
			else
				naviosTabuleiro[i] = n1;
			break;
		}

	}

	public void colocaNavios(int posI, int posJ) {
		grade[posI][posJ] = 2;
	}

	@Override
	public String toString() {
		String sGrade = "";
		if (isAdv) {
			sGrade += """
					Tabuleiro aversário
					--------- -------
					—🏼0🏼1🏼2🏼3🏼4🏼5🏼6🏼7🏼8🏼9
					""";
			for (int i = 0; i < grade.length; i++) {
				sGrade += i + " ";
				for (int j = 0; j < grade[i].length; j++) {
					switch (grade[i][j]) {
					case 0: {
						sGrade += "🌊 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						continue;
					}
					case 1: {
						sGrade += "💣 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						continue;
					}
					case 2: {
						sGrade += "🌊 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						continue;
					}
					case 3: {
						sGrade += "🔥 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						continue;
					}
					}
				}
			}
		} else {
			sGrade += """
					Tabuleiro Jogador
					--------- -------
					—🏼0🏼1🏼2🏼3🏼4🏼5🏼6🏼7🏼8🏼9
					""";
			for (int i = 0; i < grade.length; i++) {
				sGrade += i + " ";
				for (int j = 0; j < grade[i].length; j++) {
					switch (grade[i][j]) {
					case 0: {
						sGrade += "🌊 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						break;
					}
					case 1: {
						sGrade += "💣 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						break;
					}
					case 2: {
						sGrade += "🚢 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						break;
					}
					case 3: {
						sGrade += "🔥 ";
						if (j == grade[i].length - 1)
							sGrade += "\n";
						break;
					}
					}
				}

			}
		}
		return "\nGrade:\n" + sGrade;
	}
}
