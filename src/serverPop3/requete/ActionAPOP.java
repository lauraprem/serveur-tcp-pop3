package serverPop3.requete;

import java.io.File;

/**
 * 
 * @author Corinne & Laura
 *
 */
public class ActionAPOP extends ActionType {
	
	public File mail;
	
	public ActionAPOP(String User)
	{
		this.mail = new File(MAIL_PATH+User.toUpperCase()+EXTENSION_MAIL);
	}
	
	
		
}
