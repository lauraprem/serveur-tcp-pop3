package serverPop3.requete;
/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public String reponseOkQUIT() {
		// TODO Libération du verrou (close stream fichier)
		
		return super.reponseOk("POP3 server signing off");
		
		// TODO Clôture connexion après son appel
	}
}
