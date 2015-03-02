package serverPop3;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 *
 * @author Corinne
 */
public class Communication extends Thread {

    private Socket socket;
    private int SO_TIMEOUT;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedOutputStream outDonnees;

    // Root est le chemin courrent
    private static final File SERVEUR_ROOT = new File("./src/serveurweb/Serveur/Contenue/");
    private static final String FICHIER_DEFAUT = "fichierRacine.html";

    // demande client
    private String commande;
    private String fichierDemande;
    private byte[] fichierDonnees;

    public Communication(Socket connexion) {
        SO_TIMEOUT = 60000; // 1 minute
        socket = connexion;
        try {
            socket.setSoTimeout(SO_TIMEOUT);
        } catch (SocketException ex) {
            System.out.println("Erreur socket time-out : " + ex.getMessage());
        }
        in = null;
        out = null;
        outDonnees = null;
    }

    @Override
    public void run() {

        //Information
        System.out.println("Connecté : " + socket.toString());

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            outDonnees = new BufferedOutputStream(socket.getOutputStream());

            //recupere la premiere ligne de la requete du client
            String ligne = in.readLine();
        System.out.println(ligne);

            DecoupageRequeteClient(ligne);

            //Verification que la commande existe
            //Il n'y a que les requetes GET qui est implemente
            if (!commande.equals("GET")) {
                if (VerificationCommandeExiste(commande) == 200) {
                    erreur(501);
                } else {
                    erreur(400);
                }
            } else {
                // Traitement de la requete
                //Si la commande est GET, on envoie le contenue du fichier
                if (commande.equals("GET")) {
                    int code = recuperationFichier();
                    switch (code) {
                        case 200:
                            envoiFichier();
                            break;
                        case 404:
                            erreurFichierNonTrouve();
                            break;
                        case 500:
                            erreur(500);
                            break;
                    }
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("time_out dépassé : " + e.getMessage());
            erreur(408);
        } catch (IOException ex) {
            System.out.println("Erreur : " + ex.getMessage());
            erreur(500);
        } finally {
            close(in);
            close(out);
            close(outDonnees);
            close(socket);
        }
        System.out.println("Deconnecté : " + socket.toString());
    }

    /**
     * Decoupe la requete client
     *
     * @param ligne ligne du client qui est lue sur le flux
     */
    private void DecoupageRequeteClient(String ligne) {
        try {
            if (ligne != null) {
                //creer StringTokenizer pour parser la requete
                StringTokenizer parse = new StringTokenizer(ligne);
                //recupere la commande du client
                commande = parse.nextToken().toUpperCase();
                //recupere le fichier demande par le client
                fichierDemande = parse.nextToken().toLowerCase();
            } else {
                commande = "";
            }
        } catch (NoSuchElementException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Envoi une erreur au client en fonction du status_code
     *
     * @param code status_code
     */
    private void erreur(int code) {
        //Reponse au client : commande non implemente
        out.println("HTTP/1.0 " + getNomCode(code));
        out.println("Server: Java HTTP Server CorinneLaura 1.0");
        out.println("Date: " + new Date());
        out.println("Connection: close");
        out.println("Content-Type: text/html");
        out.println();
        out.println("<HTML>");
        out.println("<HEAD><TITLE>" + getNomCode(code) + "</TITLE>"
                + "</HEAD>");
        out.println("<BODY>");
        out.println("<H2>" + getNomCode(code) + ": " + commande
                + " method.</H2>");
        out.println("</BODY></HTML>");
        out.flush();
    }

    /**
     * Retourne les Status_codes + Reason_phrases en fonction du status_code
     *
     * @param code status_code
     * @return Status_codes + Reason_phrases
     */
    private String getNomCode(int code) {
        String str;
        switch (code) {
            // Success 2XX
            case 200:
                str = code + " OK";
                break;
            // Erreur Client 4XX
            case 404:
                str = code + " File Not Found";
                break;
            case 400:
                str = code + " Bad Request";
                break;
            case 408:
                str = code + " Request Time-out";
                break;
            // Erreur serveur 5XX
            case 501:
                str = code + " Not Implemented";
                break;
            case 500:
                str = code + " Internal Server Error";
                break;
            default:
                str = code + " Bad Request";
        }

        return str;
    }

    /**
     * recupere le fichier demandé par le client
     *
     * @return Status_codes
     */
    private int recuperationFichier() {
        int code = 0;
        if (fichierDemande.endsWith("/")) {
            //file par defaut
            fichierDemande += FICHIER_DEFAUT;
        }

        //Creer un fichier
        File fichier = new File(SERVEUR_ROOT, fichierDemande);

        FileInputStream fileIn = null;
        //creeer une liste de byte pour enregister les donnees du fichier
        fichierDonnees = new byte[(int) fichier.length()];

        try {
            //on ouvre le fichier et on le lit
            fileIn = new FileInputStream(fichier);
            fileIn.read(fichierDonnees);

            close(fileIn); //close file input stream
            return 200;
        } catch (FileNotFoundException ex) {
            return 404;
        } catch (IOException ex) {
            return 500;
        }
    }

    /**
     * Retourne le (MIME) content type qui correspond à l'extension du fichier.
     *
     * @param fichierDemande fichier demandé par le client
     * @return MIME du fichier demandé
     */
    public String getContentType(String fichierDemande) {
        if (fichierDemande.endsWith(".htm")
                || fichierDemande.endsWith(".html")) {
            return "text/html";
        } else if (fichierDemande.endsWith(".gif")) {
            return "image/gif";
        } else if (fichierDemande.endsWith(".jpg")
                || fichierDemande.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fichierDemande.endsWith(".class")
                || fichierDemande.endsWith(".jar")) {
            return "applicaton/octet-stream";
        }else if(fichierDemande.endsWith(".pdf")){
            return "application/pdf";
        } else {
            return "text/plain";
        }
    }

    /**
     * Ferme les flux.
     *
     * @param stream flux qui va être fermé
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
                System.err.println("Unable to close object: " + stream);
            }
        } catch (Exception e) {
            System.err.println("Error closing stream: " + e);
        }
    }

    /**
     * Envoi le fichier
     */
    private void envoiFichier() throws IOException {
        int code = 200;
        // Entete
        out.println("HTTP/1.0 " + getNomCode(code));
        out.println("Date: " + new Date());
        out.println("Server: Java HTTP Server CorinneLaura 1.0");
        out.println("Connection: close");
        out.println("Content-Type: "+ getContentType(fichierDemande));
        out.println("Content-length: " + (int) fichierDonnees.length);
        out.println(); //ligne blanche entre l'entête et le contenue
        out.flush(); //flush les characteres (output stream buffer)

        // Contenue
        outDonnees.write(fichierDonnees, 0, (int) fichierDonnees.length); //ecrit le fichier
        outDonnees.flush(); //flush binary
    }

    /**
     * Informe le client que le fichier demande n'existe pas
     */
    private void erreurFichierNonTrouve() {
        int code = 404; // code erreur

        //Enete
        out.println("HTTP/1.0 " + getNomCode(code));
        out.println("Server: Java HTTP Server CorinneLaura");
        out.println("Date: " + new Date());
        out.println("Connection: close");
        out.println("Content-Type: text/html");
        out.println();

        //Contenue
        out.println("<HTML>");
        out.println("<HEAD><TITLE>File Not Found</TITLE>"
                + "</HEAD>");
        out.println("<BODY>");
        out.println("<H2>" + getNomCode(code) + ": " + fichierDemande + "</H2>");
        out.println("</BODY>");
        out.println("</HTML>");

        // envoi sur le flux
        out.flush();
    }

    /**
     * Verifie si la commande existe
     *
     * @param commande
     * @return status-codes
     */
    private int VerificationCommandeExiste(String commande) {
        int code = 200;
        switch (commande) {
            case "GET":
                break;
            case "HEAD":
                break;
            case "POST":
                break;
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
                code = 400;
                break;
        }
        return code;
    }
}
