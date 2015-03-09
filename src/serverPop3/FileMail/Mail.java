package serverPop3.FileMail;

import java.io.Serializable;
import java.util.ArrayList;

public class Mail implements Serializable{
	
	// ATTRIBUTS
	private int id;
	private ArrayList<String> listHeader;
	private String interline = "<CR><LF>";
	private ArrayList<String> listLine;
	private String endLine = ".<CR><LF>";
	
	// CONSTRUCTEURS
	public Mail(int id, ArrayList<String> listHeader, ArrayList<String> listLine) {
		super();
		
		this.id = id;
		this.listHeader = listHeader;
		this.listLine = listLine;
	}
	
	// METHODES
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public ArrayList<String> getListHeader() {
		return listHeader;
	}



	public void setListHeader(ArrayList<String> listHeader) {
		this.listHeader = listHeader;
	}



	public String getInterline() {
		return interline;
	}



	public void setInterline(String interline) {
		this.interline = interline;
	}



	public ArrayList<String> getListLine() {
		return listLine;
	}



	public void setListLine(ArrayList<String> listLine) {
		this.listLine = listLine;
	}



	public String getEndLine() {
		return endLine;
	}



	public void setEndLine(String endLine) {
		this.endLine = endLine;
	}



	public int CalculationSizeMail(){
		int nbOctet = 0;
		
		nbOctet = interline.getBytes().length;
		nbOctet = nbOctet + endLine.getBytes().length;
		
		for(int i = 0; i < listHeader.size(); i++)
	    {
			nbOctet = nbOctet + listHeader.get(i).length();
		}
		
		for(int i = 0; i < listLine.size(); i++)
	    {
			nbOctet = nbOctet + listLine.get(i).length();
		}
		
		return nbOctet;
	}
}
