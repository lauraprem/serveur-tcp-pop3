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

import serverPop3.requete.Requete;
import util.MsgServer.MsgServer;

/**
 * @author Corinne & Laura
 */
public class Communication extends Thread {

	private Socket socket;
	private int SO_TIMEOUT;
	private BufferedReader in;
	private PrintWriter out;
	private BufferedOutputStream outDonnees;

	private Requete requete;
	private String finRequete;
	private Etat etatCourant;
	private String user;

	private static final int uneMinute = 60000;

	public Communication(Socket connexion) {
		SO_TIMEOUT = 10 * uneMinute;
		socket = connexion;
		try {
			socket.setSoTimeout(SO_TIMEOUT);
		} catch (SocketException ex) {
			MsgServer.msgError("socket time-out",ex.getMessage(), user);
		}

		// flux
		in = null;
		out = null;
		outDonnees = null;

		// Autre
		requete = null;
		// finRequete = "\r\n";
		finRequete = "<CR><LF>";
		user = socket.toString();
		etatCourant = Etat.AUTORISATION;
	}

	@Override
	public void run() {

		// Console connexion TCP correct
		System.out.println("Connected : " + user);

		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outDonnees = new BufferedOutputStream(socket.getOutputStream());

			requete = new Requete(outDonnees);
			// requete.set => port

			// Envoi Message de bienvenue
			String msg = "+OK Serveur POP3 ready" + finRequete;
			outDonnees.write(msg.getBytes(), 0, (int) msg.getBytes().length);
			outDonnees.flush();
			MsgServer.msgInfo("Send", msg, user);

			// Permet de savoir si la connexion est à clôturer
			boolean isQuit = false;
			while (!isQuit) {

				// recupere la premiere ligne de la requete du client
				String ligne = in.readLine();
//				if(user.equals(socket))
				MsgServer.msgInfo("Request receive", ligne, user);

				if (ligne != null) {
					isQuit = processingRequest(ligne);
				} else {
					isQuit = true;
				}
			}

		} catch (SocketTimeoutException e) {
			System.out.println(user+" time_out dépassé : " + e.getMessage());
			// TODO gestion erreur
			// erreur(408);
		} catch (IOException ex) {
			System.out.println(user+" Error : " + ex.getMessage());
			// erreur(500);
		} finally {
			close(in);
			close(out);
			close(outDonnees);
			close(socket);
		}
		MsgServer.msgConnect(false, user);
	}

	/**
	 * Traite la demande du client
	 * 
	 * @param requete
	 *            du client
	 */
	public boolean processingRequest(String receive) {
		// Initialisation des actions
		// ActionAPOP apop = new ActionAPOP(user,outDonnees); // Initialiser
		// Lock => pas user partout

		String[] requeteCut = receive.split(finRequete);

		// Permet de savoir si la connexion est à clôturer
		boolean isQuit = false;

		// Vérification de la forme de la requête
		if (receive.contains(finRequete) && requeteCut.length == 1
				&& requeteCut[0].length() >= 4) {

			// Récupération et validation de la commande en fonction de l'état
			// courrent
			String command = requeteCut[0].substring(0, 4);
			String params = requeteCut[0].substring(4);
			MsgServer.msgInfo("Command receive", command, user);
			MsgServer.msgInfo("Params receive", params, user);

			switch (etatCourant) {
			case AUTORISATION:
				switch (command) {
				case "APOP":
					MsgServer.msgInfo("processing","APOP ...", user);
					etatCourant = Etat.TRANSACTION; // = apop.Apop(outDonnees);

					// Récupération des mails
					if (etatCourant == Etat.TRANSACTION) {
						// TODO A enlever le user
						user = "TEST";
						requete.setUserRequete("TEST");
					}
					break;
				case "QUIT":
					MsgServer.msgInfo("processing","QUIT ...", user);
					isQuit = requete.processingQuit();
					break;
				default:
					MsgServer.msgWarnning("Unidentified command", command, user);
					break;
				}
				break;

			case TRANSACTION:
				switch (command) {
				case "RETR":
					MsgServer.msgInfo("processing","RETR ...", user);
					isQuit = !requete.processingRetr(params);
					break;
				case "QUIT":
					MsgServer.msgInfo("processing","QUIT ...", user);
					isQuit = requete.processingQuit();
					break;
				default:
					MsgServer.msgWarnning("Unidentified command", command, user);
					break;
				}
				break;

			default:
				MsgServer.msgWarnning("Unidentified etat", etatCourant.toString(), user);
				break;
			}

		} else {
			MsgServer.msgWarnning("Invalid request form", null, user);
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
