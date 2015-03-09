package util;

import java.io.File;
import java.io.IOException;

import serverPop3.requete.ActionType;

/**
 * 
 * @author Corinne & Laura
 *
 */
public class Lock {
	private static final String LOCK = "LOCK.txt";

	public static boolean isLocked(String user) {
		File userFolder = new File(ActionType.MAIL_PATH + user.toUpperCase());
		if (userFolder.exists() && userFolder.isDirectory()
				&& userFolder.list() != null) {
			String[] files = userFolder.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].equals(LOCK)) {
					return true;
				}
			}
		}
		return false;
	}

	public static LockStates lock(String user) {
		if (isLocked(user)) {
			return LockStates.ALREADY_LOCKED;
		}
		File lock = new File(ActionType.MAIL_PATH + user.toUpperCase()
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

	public static LockStates unlock(String user)
	{
		if(!isLocked(user))
		{return LockStates.ALREADY_UNLOCKED;}
		File lock = new File(ActionType.MAIL_PATH + user.toUpperCase()
				+ "/LOCK.txt");
		if(lock.delete()){
			return LockStates.UNLOCKED;
		}
		return LockStates.ERROR;
	}
}
