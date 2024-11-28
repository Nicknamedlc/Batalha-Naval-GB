package jogo;

import java.util.ArrayList;

public class Navio {
	
	private ArrayList<int[]> pos = new ArrayList<int[]>();
	private String modelo;
	
	public Navio(ArrayList<int[]> pos, String modelo) {
		this.pos = pos;
		this.modelo = modelo;
	}

	public ArrayList<int[]> getPos() {
		return pos;
	}

	public void setPos(ArrayList<int[]> pos) {
		this.pos = pos;
	}

	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void removePos(int x) {
		pos.remove(x);
	}
	
	public boolean isAlive() {
		return (pos.isEmpty());
	}
	
	@Override
	public String toString() {
		String posString = "";
		for(int i=0; i<pos.size();i++) {
			for(int j = 0; j<pos.get(i).length;j++) {
				
				if(j == 0) posString +="[";
				
				posString += pos.get(i)[j];

				if(j<pos.get(i).length-1) posString +=",";
				
				else continue;
			}
			if(i<pos.size()-1) posString += "],";
			else continue;
			
		}
		posString+="]";
		String sTipo = '"' + "tipo" + '"';
		String sPosicao = '"' + "posicao" + '"';

		return("{\n "+sTipo+":"+ modelo +"\n "+sPosicao+": ["+posString +"]\n}");
	}
	
	
}
