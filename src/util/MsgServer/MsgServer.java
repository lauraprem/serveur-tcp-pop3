package util.MsgServer;

public abstract class MsgServer {
	
	public static String msgError(String msg, String getMessage, String user) {
		String msgTemp = UserForm(user)+" Error"+" : " + msg;
		System.out.println(msgTemp);
		
		return msgTemp;
	}
	
	public static String msgWarnning(String msg, String getMessage, String user) {
		String msgTemp = UserForm(user)+" Warnning"+" : " + msg;
		System.out.println(msgTemp);
		
		return msgTemp;
	}
	
	public static String msgInfo(String obj, String msg, String user) {
		String msgTemp = UserForm(user)+" "+obj+" : " + msg;
		System.out.println(msgTemp);
		
		return msgTemp;
	}
	
	public static String msgConnect(boolean connected, String user) {
		String msgTemp;
		if(connected == true){
			msgTemp = "Connected : " + UserForm(user);
		}else{
			msgTemp = "Disconnected : " + UserForm(user);
		}
		System.out.println(msgTemp);
		
		return msgTemp;
	}
	
	private static String UserForm(String user){
		if(user.split(",localport=").length == 1){
			user = "["+user+"]";
		}
		
		return user;
	}
}
