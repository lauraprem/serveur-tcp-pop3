package serverPop3.requete;

import java.io.File;

public class ActionAPOP extends ActionType {
	private static final String MAIL_PATH = "./StockMail/";
	
	public File mail;// = new File(MAIL_PATH+"Test.txt");
	
	public ActionAPOP(String User)
	{
		this.mail = new File(MAIL_PATH+User);
		//TODO if( !file.exists())
	}
}
