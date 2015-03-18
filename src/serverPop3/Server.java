package serverPop3;

import java.io.IOException;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import util.CipherSuites.CipherSuite;

/**
 *
 * @author Corinne & Laura
 */
public class Server extends Thread {

    // ATTRIBUTS
//    private ServerSocket socketServeur;
    private SSLServerSocket socketServeur;
    private int portServeur;
    
    // CONSTRUCTEUR
    public Server() {
        portServeur = 995;
        initSocketServeur();
    }

    // METHODES
    public void initSocketServeur() {
        try {
        	ServerSocketFactory fabrique = SSLServerSocketFactory.getDefault();
        	socketServeur =  (SSLServerSocket) fabrique.createServerSocket(portServeur);
//        	socketServeur.getEnabledCipherSuites();
        	//get supported, cherche anone => met à true
        	CipherSuite cipher = new CipherSuite(socketServeur);
//        	String[] Test = cipher.getCipher();
//        	String[] Test2 = cipher.SetCipherAcChain("anon");
    	
//            socketServeur = new ServerSocket(portServeur, 6);// nombre maximum de client connecté
    		System.out.println("Server started ......");
        } catch (IOException ex) {
            System.err.println("Port déjà occupé : " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connexion;

                // Attente de demande de connexion
                connexion = socketServeur.accept();
                

                // Création d'une communication
                Communication com = new Communication(connexion);
                
                // Activation de la communication
                com.start();

            } catch (IOException ex) {
                System.out.println("Problème de connection : " + ex.getMessage());
            }
        }
    }
}
