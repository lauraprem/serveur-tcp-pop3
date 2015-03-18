package serverPop3.requete;

import java.io.BufferedOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import serverPop3.Etat;
import util.Lock.Lock;
import util.Lock.LockStates;
import util.MsgServer.MsgServer;

/**
 * 
 * @author Corinne & Laura
 *
 */
public class ActionAPOP extends ActionType {

	// private File mail;
	// private String user;

	public ActionAPOP(BufferedOutputStream outDonnees) {
		super(outDonnees);

	}

	public Etat Apop(String params, Timestamp timestamp) {
		// TODO authentfication 

		if(! verifMD5(params, timestamp)){
			super.sendMsg(super.reponseKo("password ko."));
			return Etat.AUTORISATION;
		}
		this.user = retrieveUser(params);
		// this.mail = new File(Requete.MAIL_PATH + this.user
		// + Requete.EXTENSION_MAIL);
		if (user == null || user == "") {
			super.sendMsg(super.reponseKo("no user was passed."));
			return Etat.AUTORISATION;
		}
		if (!Lock.existUser(user)) {
			super.sendMsg(super.reponseKo("the user doesn't exists."));
			return Etat.AUTORISATION;
		}
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
		if (!params.equals("")) {
			String[] paramList = params.split(" ");
			if (paramList[1] != null && MsgServer.isUserFormat(paramList[1])) {
				return paramList[1].toUpperCase();
			}
		}
		return null;
	}

	public static String retrivePass(String params) {
		if (!params.equals("")) {
			String[] paramList = params.split(" ");
			if (paramList[2] != null) {
				return paramList[2];
			}
		}
		return null;
	}

	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @return true si MD5 OK, false si MD5 KO
	 */
	public boolean verifMD5(String params, Timestamp timestamp) {
		if (user != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				String pass = user + timestamp.getTime();
				byte[] code = md.digest(pass.getBytes());
				return (code.equals(retrivePass(params).getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
