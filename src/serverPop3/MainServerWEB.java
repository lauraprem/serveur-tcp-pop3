/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serverPop3;

import serverPop3.requete.ActionAPOP;
import util.Lock;

/**
 *
 * @author Corinne & Laura
 */
public class MainServerWEB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Server s = new Server();
//        s.run();
//    	ActionAPOP apop = new ActionAPOP("test");
    	System.out.println(Lock.isLocked("laura"));
    	System.out.println(Lock.lock("laura"));
    	System.out.println(Lock.isLocked("laura"));
    	System.out.println(Lock.unlock("laura"));
    	System.out.println(Lock.isLocked("laura"));
    }
}
