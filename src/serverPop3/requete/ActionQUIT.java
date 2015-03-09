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
		// TODO Lib�ration du verrou s'il est � lib�rer
		//if(Lock.unlock(user) v�rif si bien lib�r�
		
		// Envoi du message au client
		String msg = super.reponseOk("POP3 server signing off");
		if(sendMsg(msg, outDonnees)){
			return true;
		}
		
		return false;
	}
}
