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
		
		// v�rification si le num�ro du massage existe
		// TODO si num n'existe pas => numMsg = null
//		numMsg = null;
	}
	
	public boolean ProcessingRETR() {
		if(numMsg != null){
			// TODO R�cup�ration du message+ calcul taille en octet
			// TODO envoie message : +OK {nb octets}
			// TODO envoie message : {contenu du message}
			return true;
		}else{
			// TODO envoie message : erreur
			return false;
		}
	}
}
