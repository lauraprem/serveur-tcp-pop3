package serverPop3.requete;
/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public String reponseOk() {
		// TODO Lib�ration du verrou (close stream fichier)
		
		return super.reponseOk("POP3 server signing off");
		
		// TODO Cl�ture connexion apr�s son appel
	}
}
