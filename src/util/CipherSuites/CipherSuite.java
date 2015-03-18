package util.CipherSuites;

import java.util.ArrayList;

import javax.net.ssl.SSLServerSocket;

public class CipherSuite {
	private SSLServerSocket socket;
	private String[] cipher;

	public CipherSuite(SSLServerSocket socket) {
		super();
		this.socket = socket;
		this.cipher = this.socket.getSupportedCipherSuites();
	}

	public String[] getCipher() {
		return cipher;
	}

	public void setCipher(String[] cipher) {
		this.cipher = cipher;
	}
	
	public String[] SetCipherAcChain(String chaine){
		ArrayList<String> newCipher = new ArrayList<String>();
		
		for(int i=0; i<cipher.length; i++){
			if(cipher[i].contains(chaine)){
				newCipher.add(cipher[i]);
			}
		}
		
		cipher = newCipher.toArray(new String[newCipher.size()]);
		socket.setEnabledCipherSuites(cipher);
		
		return cipher;
	}
}
