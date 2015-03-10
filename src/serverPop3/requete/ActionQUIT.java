package serverPop3.requete;

import java.io.BufferedOutputStream;
import java.util.ArrayList;

import util.Lock.Lock;
import util.Lock.LockStates;

/**
*
* @author Corinne & Laura
*/
public class ActionQUIT extends ActionType {
	
	public ActionQUIT(BufferedOutputStream outDonnees) {
		super(outDonnees);
	}

	public boolean PrecessingDefault() {
		// TODO A enlever ou laisser si static (voir avec Laura)		
		// Libération du verrou s'il est à libérer
//		if(lock.unlock(user) != LockStates.ERROR){

		// Envoi du message au client
		String msg = super.reponseOk("POP3 server signing off");
		if(sendMsg(msg)){
			return true;
		}
//	}else
		
		return false;
	}
}
