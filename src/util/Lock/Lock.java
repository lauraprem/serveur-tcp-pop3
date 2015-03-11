package util.Lock;

import java.io.File;
import java.io.IOException;

import serverPop3.requete.Requete;

/**
 * Permet de g�rer le verrou sur les utilisateurs
 * 
 * @author Corinne & Laura
 *
 */
public class Lock {
	private static final String LOCK = "LOCK.txt";

	/**
	 * Permet de savoir si un utilisateur est v�rouill�
	 * 
	 * @param user
	 *            le nom de l'utilisateur
	 * @return true si v�rouill� false si non v�rouill�
	 */
	public static boolean isLocked(String user) {
		if(user != null && user != ""){
		File userFolder = new File(Requete.MAIL_PATH + user.toUpperCase());
			if (userFolder.exists() && userFolder.isDirectory()
					&& userFolder.list() != null) {
				String[] files = userFolder.list();
				for (int i = 0; i < files.length; i++) {
					if (files[i].equals(LOCK)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean existUser(String user)
	{
		File userFolder = new File(Requete.MAIL_PATH + user.toUpperCase());
		return userFolder.exists();
	}

	/**
	 * Permet de v�rouiller un utilisateur si possible
	 * 
	 * @param user
	 *            le nom de l'utilisateur
	 * @return LockStates.LOCKED si tout s'est bien pass� et qu'on a v�rouill�
	 *         LockStates.ALREADY_LOCKED si l'utilisateur �tait d�j� sous verrou
	 *         LockStates.ERROR s'il y a eu une erreur
	 */
	public static LockStates lock(String user) {
		if (isLocked(user)) {
			return LockStates.ALREADY_LOCKED;
		}
		File lock = new File(Requete.MAIL_PATH + user.toUpperCase() 
				+ "/LOCK.txt");
		try {
			if (lock.createNewFile()) {
				return LockStates.LOCKED;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return LockStates.ERROR;
	}

	/**
	 * Permet de d�v�rouiller un utilisateur si possible
	 * 
	 * @param user
	 *            le nom de l'utilisateur
	 * @return LockStates.UNLOCKED si tout s'est bien pass� et qu'on a v�rouill�
	 *         LockStates.ALREADY_UNLOCKED si l'utilisateur �tait d�j� sans
	 *         verrou LockStates.ERROR s'il y a eu une erreur
	 */
	public static LockStates unlock(String user) {
		if (!isLocked(user)) {
			return LockStates.ALREADY_UNLOCKED;
		}
		File lock = new File(Requete.MAIL_PATH + user.toUpperCase()
				+ "/LOCK.txt");
		if (lock.delete()) {
			return LockStates.UNLOCKED;
		}
		return LockStates.ERROR;
	}
}
