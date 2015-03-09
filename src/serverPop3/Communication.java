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

import serverPop3.requete.*;
import util.FileMail.FileMails;

/**
 * @author Corinne & Laura
 */
public class Communication extends Thread {

	private Socket socket;
	private int SO_TIMEOUT;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedOutputStream outDonnees;

	private String finRequete;
	private Etat etatCourant;
	private String user;

	private static final int uneMinute = 60000; // Verif + ou = 10 mins

	// private static final File SERVEUR_Mail = new File("./StockMail/");

	// demande client
	// private String commande;
	// private String fichierDemande;
	// private byte[] fichierDonnees;

	public Communication(Socket connexion) {
		// TODO timeout : comment le gérer ?
		SO_TIMEOUT = 10 * uneMinute; // 10 minutes
		socket = connexion;
		try {
			socket.setSoTimeout(SO_TIMEOUT);
		} catch (SocketException ex) {
			// TODO message erreur tempo expire
			System.out.println("Error socket time-out : " + ex.getMessage());
		}

		// flux
		in = null;
		out = null;
		outDonnees = null;

		// Autre
		finRequete = "\r\n";
		etatCourant = Etat.AUTORISATION;
	}

	@Override
	public void run() {
		
		// Console connexion TCP correct
		System.out.println("Connected : " + socket.toString());

		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outDonnees = new BufferedOutputStream(socket.getOutputStream());

			// Envoi Message de bienvenue
			String msg = "+OK Serveur POP3 ready"+finRequete;
			outDonnees.write(msg.getBytes(), 0, (int) msg.getBytes().length);
			outDonnees.flush();
			System.out.println(msg);
			
			// Permet de savoir si la connexion est à clôturé
			boolean isQuit = false;
			while(!isQuit){
				
				// recupere la premiere ligne de la requete du client
				String ligne = in.readLine();
				System.out.println("Request receive :" + ligne);
				isQuit = processingRequest(ligne);
			}

		} catch (SocketTimeoutException e) {
			System.out.println("time_out dépassé : " + e.getMessage());
			// TODO gestion erreur
			// erreur(408);
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
	 * Traite la demande du client
	 * 
	 * @param requete du client
	 */
	public boolean processingRequest(String requete) {
		String[] requeteCut = requete.split(finRequete);
		
		// Permet de savoir si la connexion est à clôturé
		boolean isQuit = false;

		// Vérification de la forme de la requête
		if (requete.contains(finRequete) && requeteCut.length == 1
				&& requeteCut[0].length() >= 4) {

			// Récupération et validation de la commande en fonction de l'état
			// courrent
			String command = requeteCut[0].substring(0, 4);
			String params = requeteCut[0].substring(4);
			System.out.println("Command receive : " + command);
			System.out.println("Params receive : " + params);

			switch (etatCourant) {
			case AUTORISATION:
				switch (command) {
				case "APOP":
					System.out.println("processing : APOP ...");
					ActionAPOP apop = new ActionAPOP(user);
					etatCourant = apop.Apop(outDonnees);
					break;
				case "QUIT":
					System.out.println("processing : QUIT ...");
					ActionQUIT actionQuit = new ActionQUIT();
					isQuit = actionQuit.PrecessingDefault(outDonnees,user);
					break;
				default:
					System.out.println("Unidentified command : " + command);
					break;
				}
				break;

			case TRANSACTION:
				switch (command) {
				case "RETR":
					System.out.println("processing : RETR ...");
					ActionRETR actionRetr = new ActionRETR(params);
					isQuit = !actionRetr.PrecessingDefault(outDonnees,user);
					break;
				case "QUIT":
					System.out.println("processing : QUIT ...");
					ActionQUIT actionQuit = new ActionQUIT();
					isQuit = actionQuit.PrecessingDefault(outDonnees,user);
					break;
				default:
					System.out.println("Unidentified command : " + command);
					break;
				}
				break;
				
			default:
				System.out.println("Unidentified etat : " + etatCourant);
				break;
			}

		} else {
			System.out.println("Invalid request form");
		}
		
		return isQuit;
	}

	/**
	 * Ferme les flux.
	 *
	 * @param stream
	 *            flux qui va être fermé
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
				// TODO gestion erreur
				System.err.println("Unable to close object: " + stream);
			}
		} catch (Exception e) {
			System.err.println("Error closing stream: " + e);
		}
	}
}
