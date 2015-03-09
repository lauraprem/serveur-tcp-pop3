package serverPop3.requete;

import java.io.BufferedOutputStream;

import util.Lock.Lock;

/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public ActionQUIT() {
		super();
	}
	
	public boolean ProcessingQUIT(BufferedOutputStream outDonnees,String user) {
		// TODO Libération du verrou s'il est à libérer
		//if(Lock.unlock(user) vérif si bien libéré
		
		// Envoi du message au client
		String msg = super.reponseOk("POP3 server signing off");
		if(sendMsg(msg, outDonnees)){
			return true;
		}
		
		return false;
	}
}
