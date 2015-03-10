package serverPop3.requete;

import java.io.BufferedOutputStream;
import java.io.File;

import serverPop3.Etat;
import util.Lock.Lock;
import util.Lock.LockStates;

/**
 * 
 * @author Corinne & Laura
 *
 */
public class ActionAPOP extends ActionType {

	public File mail;
	public String user;

	public ActionAPOP(String user, BufferedOutputStream outDonnees,String MAIL_PATH, String EXTENSION_MAIL) {
		super(outDonnees);
		this.user = user.toUpperCase();
		this.mail = new File(MAIL_PATH + this.user + EXTENSION_MAIL);
	}

	public Etat Apop(BufferedOutputStream out) {
		// TODO authentfication
		LockStates lockState = Lock.lock(user);
		switch(lockState){
			case LOCKED : 
				super.sendMsg(super.reponseOk(""));
				return Etat.TRANSACTION;
			case ALREADY_LOCKED : 
				super.sendMsg(super.reponseKo("lock aldready in use."));
				return Etat.AUTORISATION;
			default : 
				super.sendMsg(super.reponseKo("error."));
				return Etat.AUTORISATION;
		}		
	}
}
