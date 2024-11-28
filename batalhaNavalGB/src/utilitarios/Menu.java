package utilitarios;

import java.io.IOException;
import java.nio.file.attribute.FileStoreAttributeView;
import java.util.ArrayList;

import conexao.Pareamento;
import jogo.Navio;
import jogo.Tabuleiro;

public interface Menu {

	public static void run() {

		/*
		 * Tipos de Navio 1 porta-avioes (5 células) 1 encouraçado (4 células) 2
		 * cruzadores (3 células cada) 2 destróieres (2 células cada)
		 */

		boolean isRunning = true;
		while (isRunning) {
			switch (option()) {
			case 1:
				jogaLocal();
				break;
			case 2:
				jogaOnline();
				break;
			case 3:
				isRunning = false;
			}
		}
	}

	public static int[] traduz(String texto) {
		int[] pos = new int[2];
		String[] corte = texto.split(",");
		pos[0] = Integer.parseInt(corte[0]);
		pos[1] = Integer.parseInt(corte[1]);
		return pos;
	}

	public static void jogaOnline() {
		// TODO joga as verda em rede

		String atira = null;
		boolean inGame = true;
		int porta = 8080;
		int op = UserInput.anIntBetween("""
				Qual opção ?
				1.Conectar
				2.Hospedar
				3.Continuar jogo salvo
				4.Teste;
				""", 1, 4);
		Pareamento peer = new Pareamento();
		System.out.println();

		switch (op) {
		case 1: {
			String host = UserInput.aString("Digite o endereço do servidor:");
			String sTabAdv = "";
			int naviosj = 0, aux = 0;

			// try coneção
			try {
				peer.conecta(host, porta);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String jsonMentirinha = "[\n ";
			Tabuleiro jogador = new Tabuleiro();
			for (int i = 0; i < jogador.getNaviosTabuleiro().length; i++) {
				switch (naviosj) {
				case 0, 1:
					aux = 1;
					break;
				case 2, 3:
					aux = 2;
					break;
				case 4:
					aux = 3;
					break;
				case 5:
					aux = 4;
					break;
				}
				jogador.adcionarNavio(criaNavio(aux));
				System.out.println(jogador);
				naviosj++;
				if (i < jogador.getNaviosTabuleiro().length - 1)
					jsonMentirinha += jogador.getNaviosTabuleiro()[i].toString();
				else
					jsonMentirinha += jogador.getNaviosTabuleiro()[i].toString() + ",";
			}
			peer.enviaString(jsonMentirinha);
			try {
				sTabAdv = peer.recebeString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Tabuleiro adv = traduzTabuleiro(sTabAdv, true);
			while (inGame) {

				// try receber mensagem
				try {
					atira = peer.recebeString();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Cliente: " + atira);
				if (atira.equalsIgnoreCase("exit")) {
					break;
				}
			}
		}
		case 2: {
			String sTabAdv = "";
			try {
				peer.iniciaServer(porta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				sTabAdv = peer.recebeString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Tabuleiro adv = traduzTabuleiro(sTabAdv, true);
			int naviosJ = 0, aux = 0, naviosAdv = 0;

			String jsonMentirinha = "[\n ";
			Tabuleiro jogador = new Tabuleiro();
			for (int i = 0; i < jogador.getNaviosTabuleiro().length; i++) {
				switch (naviosJ) {
				case 0, 1:
					aux = 1;
					break;
				case 2, 3:
					aux = 2;
					break;
				case 4:
					aux = 3;
					break;
				case 5:
					aux = 4;
					break;
				}
				jogador.adcionarNavio(criaNavio(aux));
				System.out.println(jogador);
				naviosJ++;
				if (i < jogador.getNaviosTabuleiro().length - 1)
					jsonMentirinha += jogador.getNaviosTabuleiro()[i].toString();
				else
					jsonMentirinha += jogador.getNaviosTabuleiro()[i].toString() + ",";
			}
			peer.enviaString(jsonMentirinha);
			while (inGame) {

				// try receber mensagem
				try {
					atira = peer.recebeString();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Cliente: " + atira);
				if (atira.equalsIgnoreCase("exit")) {
					break;
				}
			}

		}
		case 4: {
			System.out.println(criaNavio());
			return;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + op);
		}

	}

	public static Tabuleiro traduzTabuleiro(String sTab, boolean isAdv) {
		// TODO devolver tabuleiro
		Navio[] arrNavio = new Navio[6];
		String[] slice1 = sTab.split(sTab);
		if (isAdv)
			return new Tabuleiro(isAdv, arrNavio);
		else
			return new Tabuleiro(arrNavio);
	}

	public static void jogaLocal() {

		// TODO jogar as verda em local
		int[] auxArr = new int[2];
		boolean inGame = true;
		int naviosj1 = 0, naviosj2 = 0, aux = 0;
		Tabuleiro jogador1 = new Tabuleiro();
		Tabuleiro jogador2 = new Tabuleiro();

		System.out.println("""
				Jogador 1 coloca os navios no tabuleiro
				""");

		for (int i = 0; i < jogador1.getNaviosTabuleiro().length; i++) {
			switch (naviosj1) {
			case 0, 1:
				aux = 1;
				break;
			case 2, 3:
				aux = 2;
				break;
			case 4:
				aux = 3;
				break;
			case 5:
				aux = 4;
				break;
			}
			jogador1.adcionarNavio(criaNavio(aux));
			System.out.println(jogador1);
			naviosj1++;
		}
		System.out.println("""
				Jogador 2 coloca os navios no tabuleiro
				""");

		for (int i = 0; i < jogador2.getNaviosTabuleiro().length; i++) {
			switch (naviosj2) {
			case 0, 1:
				aux = 1;
				break;
			case 2, 3:
				aux = 2;
				break;
			case 4:
				aux = 3;
				break;
			case 5:
				aux = 4;
				break;
			}
			jogador2.adcionarNavio(criaNavio(aux));
			System.out.println(jogador2);
			naviosj2++;
		}
		int contadorj1 = 0;
		int contadorj2 = 0;
		while (inGame) {
			// conta os navios do j1 em campo
			contadorj1 = 0;
			for (int i = 0; i < jogador1.getNaviosTabuleiro().length; i++) {
				if (jogador1.getNaviosTabuleiro()[i].isAlive())
					contadorj1++;
			}
			naviosj1 = contadorj1;

			contadorj2 = 0;
			for (int i = 0; i < jogador2.getNaviosTabuleiro().length; i++) {
				if (jogador2.getNaviosTabuleiro()[i].isAlive())
					contadorj2++;
			}
			naviosj2 = contadorj2;

			// print teste
			System.out.println(jogador1 + "\n");
			System.out.println("\n" + jogador2);
			if (naviosj1 < 1) {
				System.out.println("Jogador 2 Ganhou!!!");
				System.out.println("Tabuleiro do jogador 1:\n" + jogador1);
				System.out.println("Tabuleiro do jogador 2:\n" + jogador2);
				inGame = false;
			} else if (naviosj2 < 1) {
				System.out.println("Jogador 1 Ganhou!!!");
				System.out.println("Tabuleiro do jogador 1:\n" + jogador1);
				System.out.println("Tabuleiro do jogador 2:\n" + jogador2);
				inGame = false;
			}
			System.out.println("""
					Hora dos disparos!!!
					""");
			// jogador 1
			auxArr = traduz(UserInput.aString("""
					Jogador 1 atira
					x,y
					em ordem
					"""));
			jogador2.adicionaTiro(auxArr[0], auxArr[1]);

			// jogador 2
			auxArr = traduz(UserInput.aString("""
					Jogador 2 atira
					x,y
					em ordem
					"""));
			jogador1.adicionaTiro(auxArr[0], auxArr[1]);
			// saida teste
		}
	}

	public static Navio criaNavio() {
		ArrayList<int[]> pos = new ArrayList<int[]>();
		int[] posIni, posFin;
		int linhaIni = 0, colunaIni = 0, linhaFin = 0, colunaFin = 0, modelo = 0, tamanho = 0, aux = 0, auxF = 0;
		boolean algoErrado = true;
		String sModel = "", textIni, textFin;

		// Cuidado com os dados que entram
		modelo = UserInput.anIntBetween(
				"Digite o modelo do navio N1:\nModelos:\n4:Porta avioes\n3:Encouraçado\n2:Cruzador\n1:destroier", 1, 4);
		switch (modelo) {
		case 4:
			sModel = "Porta-aviões";
			tamanho = 5;
			break;
		case 3:
			sModel = "Encouraçado";
			tamanho = 4;
			break;
		case 2:
			sModel = "Cruzador";
			tamanho = 3;
			break;
		case 1:
			sModel = "Destróier";
			tamanho = 2;
			break;
		}

		while (algoErrado) {
			textIni = UserInput.aString("""
					Digite a posição inicial do navio (Separando x e y por virgula, os numeros sendo de 0 até 9):
					ex.: x,y

					""");
			posIni = traduz(textIni);
			linhaIni = posIni[0];
			colunaIni = posIni[1];
			// Deus no comando !!!
			try {
				textFin = UserInput.aString("""
						Digite a posição final do navio (Separando x e y por virgula, os numeros sendo de 0 até 9):
						ex.: x,y

						""");
			} catch (Exception e) {
				textFin = UserInput.aString(
						"""
								Posição invalida ❌
								Digite a posição final valida do navio (Separando x e y por virgula, os numeros sendo de 0 até 9):
								ex.: x,y

								""");

			}
			posFin = traduz(textFin);
			linhaFin = posFin[0];
			colunaFin = posFin[1];

			// Se o final for menor que o inicial
			aux = linhaIni;
			if (aux > linhaFin) {
				linhaIni = linhaFin;
				linhaFin = aux;
			}
			aux = colunaIni;
			if (aux > colunaFin) {
				colunaIni = colunaFin;
				colunaFin = aux;
			}

			// Se ta mudando a coluna ou a linha
			if (colunaIni == colunaFin) {
				aux = linhaIni;
				auxF = linhaFin;
			} else {
				aux = colunaIni;
				auxF = colunaFin;
			}

			// Se o tamanho do navio for diferente do tamanho possivel
			if ((tamanho - 1) == (auxF - aux))
				algoErrado = false;
		}

		for (; aux <= auxF; aux++) {
			pos.add(new int[] { linhaFin, aux });
		}

		return new Navio(pos, sModel);
	}

	public static Navio criaNavio(int modelo) {

		ArrayList<int[]> pos = new ArrayList<int[]>();

		int[] posIni = new int[2], posFin = new int[2];

		int linhaIni = 0, colunaIni = 0, linhaFin = 0, colunaFin = 0, tamanho = 0, aux = 0, auxF = 0;

		boolean algoErrado = true;

		String sModel = "", textIni = "", textFin = "";

		// Cuidado com os dados que entram
		switch (modelo) {
		case 4:
			sModel = "Porta-aviões";
			tamanho = 5;
			break;
		case 3:
			sModel = "Encouraçado";
			tamanho = 4;
			break;
		case 2:
			sModel = "Cruzador";
			tamanho = 3;
			break;
		case 1:
			sModel = "Destróier";
			tamanho = 2;
			break;
		}

		System.out.println("Criando um " + sModel + " de tamanho " + tamanho);

		while (algoErrado) {
			textIni = UserInput.aString("""
					Digite a posição inicial do navio (Separando x e y por virgula, os numeros sendo de 0 até 9):
					ex.: x,y

					""");
			posIni = traduz(textIni);
			linhaIni = posIni[0];
			colunaIni = posIni[1];
			// Deus no comando !!!
			textFin = UserInput.aString("""
					Digite a posição final do navio (Separando x e y por virgula, os numeros sendo de 0 até 9):
					ex.: x,y

					""");
			posFin = traduz(textFin);
			linhaFin = posFin[0];
			colunaFin = posFin[1];

			// Se o final for menor que o inicial
			aux = linhaIni;
			if (aux > linhaFin) {
				linhaIni = linhaFin;
				linhaFin = aux;
			}
			aux = colunaIni;
			if (aux > colunaFin) {
				colunaIni = colunaFin;
				colunaFin = aux;
			}

			// Se ta mudando a coluna ou a linha
			if (colunaIni == colunaFin) {
				aux = linhaIni;
				auxF = linhaFin;
			} else {
				aux = colunaIni;
				auxF = colunaFin;
			}

			// Se o tamanho do navio for diferente do tamanho possivel
			if ((tamanho - 1) == (auxF - aux))
				algoErrado = false;
		}

		for (; aux <= auxF; aux++) {
			if (colunaIni != colunaFin) {
				pos.add(new int[] { linhaIni, aux });
			} else {
				pos.add(new int[] { aux, colunaIni });
			}
		}

		return new Navio(pos, sModel);
	}

	public static int option() {
		System.out.println("""

				     __|_|__|_|__
				   _|____________|__
				  |o o o o o o o o /
				~'`~'`~'`~'`~'`~'`~'`~
				▄▄▄▄·  ▄▄▄· ▄▄▄▄▄ ▄▄▄· ▄▄▌   ▄ .▄ ▄▄▄·
				▐█ ▀█▪▐█ ▀█ •██  ▐█ ▀█ ██•  ██▪▐█▐█ ▀█
				▐█▀▀█▄▄█▀▀█  ▐█.▪▄█▀▀█ ██▪  ██▀▐█▄█▀▀█
				██▄▪▐█▐█ ▪▐▌ ▐█▌·▐█ ▪▐▌▐█▌▐▌██▌▐▀▐█ ▪▐▌
				·▀▀▀▀  ▀  ▀  ▀▀▀  ▀  ▀ .▀▀▀ ▀▀▀ · ▀  ▀
				 ▐ ▄  ▄▄▄·  ▌ ▐· ▄▄▄· ▄▄▌
				•█▌▐█▐█ ▀█ ▪█·█▌▐█ ▀█ ██•
				▐█▐▐▌▄█▀▀█ ▐█▐█•▄█▀▀█ ██▪
				██▐█▌▐█ ▪▐▌ ███ ▐█ ▪▐▌▐█▌▐▌
				▀▀ █▪ ▀  ▀ . ▀   ▀  ▀ .▀▀▀

								""");
		return UserInput.anIntBetween("""
				Selecione O que deseja fazer:

				1: Iniciar Jogo (local)
				2: Iniciar Jogo (multiplayer)
				3: Sair

				""", 1, 3);
	}
}
