package util.FileMail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FileMails implements Serializable {

	// ATTRIBUTS
	private String name;
	private String location;
	private ArrayList<Mail> listMail;

	// private String DEFAULT_ENCODING = "utf-8";

	// CONSTRUCTEURS
	public FileMails(String name, String location, ArrayList<Mail> listMail) {
		this.name = name;
		this.location = location;
		this.listMail = listMail;
	}

	public FileMails(String name, String location) {
		super();
		this.name = name;
		this.location = location;
		this.listMail = new ArrayList<Mail>();
	}

	// METHODES
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<Mail> getListMail() {
		return listMail;
	}

	public void setListMail(ArrayList<Mail> listMail) {
		this.listMail = listMail;
	}

	public Mail getMail(int num) {
		for (Mail mail : listMail) {
			if (mail.getId() == num) {
				return mail;
			}
		}
		return null;
	}

	public int CalculationSizeMail(int num) {

		// Recherche du mail
		Mail mail = getMail(num);

		return mail.CalculationSizeMail();
	}

	public void extractMails() {
		String path = location + name;
		File fichier = new File(path);
		ObjectInputStream ois;
		boolean check = true;
		
		// R�cup�ration des mails
		while (check) {
			
			try {
				ois = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(fichier)));
				
				// R�cup�ration d'un mail
				Mail mail = (Mail) ois.readObject();
				this.listMail.add(mail);
				// this.listMail.set(mail.getId(), mail);
				ois.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				check = false;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				check = false;
			} catch (IOException e) {
				e.printStackTrace();
				check = false;
			}
		}
		int i = 1;
	}

	public void saveMails() {
		String path = location + name;
		File fichier = new File(path);
		ObjectOutputStream oos;

		try {
			// premi�re �criture lors cr�ation du fichier
//			 oos = new ObjectOutputStream(new BufferedOutputStream(
//			 new FileOutputStream(fichier)));

			// Ecrit � la suite
			oos = new AppendObjectOutputStream(new FileOutputStream(fichier));

			// Ecrire chaque objet Mail dans le fichier
			ArrayList<String> listHeader = new ArrayList<String>();
			listHeader.add("Message-ID: <1234@local.machine.example><CR><LF>");
			listHeader.add("From: John Doe <jdoe@machine.example><CR><LF>");
			listHeader.add("Subject: C'est un essai<CR><LF>");
			ArrayList<String> listLine = new ArrayList<String>();
			listLine.add("C'est un message juste pour tester. Alors, \"Test\".<CR><LF>");
			oos.writeObject(new Mail(1234, listHeader, listLine));

			// ArrayList<String> listHeader = new ArrayList<String>();
			// listHeader.add("Message-ID: <1234@local.machine.example><CR><LF>");
			// listHeader.add("From: John Doe <jdoe@machine.example><CR><LF>");
			// listHeader.add("Subject: C'est un essai<CR><LF>");
			// ArrayList<String> listLine = new ArrayList<String>();
			// listLine.add("C'est un message juste pour tester. Alors, \"Test\".<CR><LF>");
			// oos.writeObject(new Mail("1234", listHeader,listLine));
			//
			// ArrayList<String> listHeader = new ArrayList<String>();
			// listHeader.add("Message-ID: <1234@local.machine.example><CR><LF>");
			// listHeader.add("From: John Doe <jdoe@machine.example><CR><LF>");
			// listHeader.add("Subject: C'est un essai<CR><LF>");
			// ArrayList<String> listLine = new ArrayList<String>();
			// listLine.add("C'est un message juste pour tester. Alors, \"Test\".<CR><LF>");
			// oos.writeObject(new Mail("1234", listHeader,listLine));

			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CreateSaveMails() {

		String path = location + name;
		// TODO Cr�er le fichier
		File fichier = new File(path);
		ObjectOutputStream oos;

		try {
			// premi�re �criture lors cr�ation du fichier
			oos = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(fichier)));

			// Ecrire chaque objet Mail dans le fichier
			// ArrayList<String> listLine = new ArrayList<String>();
			// listLine.add("C'est un message juste pour tester. Alors, \"Test\".<CR><LF>");
			// oos.writeObject(new Mail(1234, listHeader, listLine));

			oos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// public void extractMails() {
	// // Creer un fichier
	// String path = location+name;
	// File fichier = new File(path);
	//
	// FileInputStream fileIn = null;
	//
	// // Creer une liste de byte pour enregister les donnees du fichier
	// byte[] fichierDonnees = new byte[(int) fichier.length()];
	//
	// try {
	// // On ouvre le fichier et on le lit
	// fileIn = new FileInputStream(fichier);
	// fileIn.read(fichierDonnees);
	// fileIn.close();
	//
	// // Convertion en String
	// String content = new String(fichierDonnees,DEFAULT_ENCODING);
	//
	// return true;
	// } catch (FileNotFoundException ex) {
	// } catch (IOException ex) {
	// }
	// return false;
	// }

}