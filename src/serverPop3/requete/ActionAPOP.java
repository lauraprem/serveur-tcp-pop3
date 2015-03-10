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

//	private File mail;
	private String user;

	public ActionAPOP(BufferedOutputStream outDonnees) {
		super(outDonnees);

	}

	public Etat Apop(String params) {
		// TODO authentfication + verif user
		this.user = retrieveUser(params);
//		this.mail = new File(Requete.MAIL_PATH + this.user
//				+ Requete.EXTENSION_MAIL);
		LockStates lockState = Lock.lock(user);
		switch (lockState) {
		case LOCKED:
			super.sendMsg(super.reponseOk(""));
			return Etat.TRANSACTION;
		case ALREADY_LOCKED:
			super.sendMsg(super.reponseKo("lock aldready in use."));
			return Etat.AUTORISATION;
		default:
			super.sendMsg(super.reponseKo("error."));
			return Etat.AUTORISATION;
		}
	}

	public String retrieveUser(String params) {
		// TODO d�couper string
		return "laura";
	}
	
	public String getUser() {
		return user;
	}
}
