package serverPop3.requete;
/**
*
* @author Corinne & Laura
*/
public class ActionRETR extends ActionType {

	public String numMsg;
	
	public ActionRETR(String params) {
		super();
		
		numMsg = params.trim();
		
		// vérification si le numéro du massage existe
		// TODO si num n'existe pas => numMsg = null
//		numMsg = null;
	}
}
