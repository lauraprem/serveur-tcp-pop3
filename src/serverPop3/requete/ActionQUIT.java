package serverPop3.requete;
/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public String ProcessingQUIT() {
		// TODO Libération du verrou
		
		return super.reponseOk("POP3 server signing off");
		
		// TODO Clôture connexion après son appel
	}
}
