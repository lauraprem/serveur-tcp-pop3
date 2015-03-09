package serverPop3.requete;

import java.io.BufferedOutputStream;

import serverPop3.FileMail.FileMails;

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
	
	public boolean ProcessingRETR(BufferedOutputStream outDonnees,String user) {
		if(numMsg != null){
			FileMails fileMails = new FileMails(user+EXTENSION_MAIL,MAIL_PATH);
			fileMails.extractMails();
			// TODO Récupération du message+ calcul taille en octet
			// TODO envoie message : +OK {nb octets}
			// TODO envoie message : {contenu du message}
			return true;
		}else{
			// TODO envoie message : erreur
			return false;
		}
	}
	
	public boolean NumExist(String num){
		return false;
	}
	
	public String mailMsgExtract(){
		return "";
	}
}
