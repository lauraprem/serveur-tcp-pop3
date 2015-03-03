package serverPop3;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import serverPop3.requete.ActionType;

/**
 *
 * @author Corinne & Laura
 */
public class Communication extends Thread {

    private Socket socket;
    private int SO_TIMEOUT;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream outDonnees;

    private static final int uneMinute = 60000; // Verif + ou = 10 mins
//    private static final File SERVEUR_Mail = new File("./StockMail/");

    // demande client
//    private String commande;
//    private String fichierDemande;
//    private byte[] fichierDonnees;

    public Communication(Socket connexion) {
    	//TODO timeout : comment le g�rer ?
        SO_TIMEOUT = 10 * uneMinute; // 10 minutes
        socket = connexion;
        try {
            socket.setSoTimeout(SO_TIMEOUT);
        } catch (SocketException ex) {
        	//TODO message erreur tempo expire
            System.out.println("Error socket time-out : " + ex.getMessage());
        }
        in = null;
        out = null;
        outDonnees = null;
    }

    @Override
    public void run() {

        // Console connexion TCP correct
        System.out.println("Connected : " + socket.toString());
        
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outDonnees = new BufferedOutputStream(socket.getOutputStream());
            
            // Envoi Message de bienvenue
            String msg = "OK + Serveur POP3 ready";
            outDonnees.write(msg.getBytes(), 0, (int) msg.getBytes().length);
            outDonnees.flush();
            System.out.println(msg);
            
            //recupere la premiere ligne de la requete du client
            String ligne = in.readLine();
            System.out.println(ligne);
            //TODO ligne
            
        } catch (SocketTimeoutException e) {
            System.out.println("time_out d�pass� : " + e.getMessage());
            //TODO gestion erreur
            //erreur(408);
        } catch (IOException ex) {
            System.out.println("Error : " + ex.getMessage());
           // erreur(500);
        } finally {
            close(in);
            close(out);
            close(outDonnees);
            close(socket);
        }
        System.out.println("Disconnected : " + socket.toString());
    }


    /**
     * Ferme les flux.
     *
     * @param stream flux qui va �tre ferm�
     */
    public void close(Object stream) {
        if (stream == null) {
            return;
        }
        try {
            if (stream instanceof Reader) {
                ((Reader) stream).close();
            } else if (stream instanceof Writer) {
                ((Writer) stream).close();
            } else if (stream instanceof InputStream) {
                ((InputStream) stream).close();
            } else if (stream instanceof OutputStream) {
                ((OutputStream) stream).close();
            } else if (stream instanceof Socket) {
                ((Socket) stream).close();
            } else {
            	//TODO gestion erreur
                System.err.println("Unable to close object: " + stream);
            }
        } catch (Exception e) {
            System.err.println("Error closing stream: " + e);
        }
    }
}
