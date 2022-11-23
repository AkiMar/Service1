/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem1;

import entiteti.PodaciDB_A;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Goran
 */
public class Main {

    @Resource( lookup = "IS_PodSistem1Fac1")
    private static ConnectionFactory connectionFactory;
    
    @Resource( lookup = "IS_PodSistemTopic1")
    private static Topic sistemTopic;
    
    @Resource( lookup = "IS_CSQueue1")
    private static Queue centralniSistemQueue;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        context.setClientID("podSistem1");
        JMSProducer producer = context.createProducer();
        
        JMSConsumer consumer = context.createDurableConsumer(sistemTopic, "sub1", "Sistem=1", false);

        DohvatanjeUpit dohvatanjeUpit = new DohvatanjeUpit();
        KreiranjeUpit kreiranjeUpit = new KreiranjeUpit();
        for (int i = 0; i < 100; i++) {
            consumer.receiveNoWait();
        }
        while(true){
            System.out.println("Krenulo podSistem1");
            Message msg = consumer.receive();
            String odg = "";
            try {
                int Upit = msg.getIntProperty("Upit");
                
                switch(Upit) {
                    case 1:
                        odg = kreiranjeUpit.kreiranjeMesta(msg);
                        //odg = "Dobio poruku 1";
                        System.out.println(odg);
                        TextMessage txtMsg1 = context.createTextMessage(odg);
                        txtMsg1.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg1);
                        break;
                    case 2:
                        odg = kreiranjeUpit.kreiranjeFilijale(msg);
                        //odg = "Dobio poruku 2";
                        TextMessage txtMsg2 = context.createTextMessage(odg);
                        txtMsg2.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg2);
                        break;
                    case 3:
                        odg = kreiranjeUpit.kreiranjeKomitenta(msg);
                        //odg = "Dobio poruku 3";
                        TextMessage txtMsg3 = context.createTextMessage(odg);
                        txtMsg3.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg3);
                        break;
                    case 4:
                        odg = kreiranjeUpit.promenaSedista(msg);
                        //odg = "Dobio poruku 4";
                        TextMessage txtMsg4 = context.createTextMessage(odg);
                        txtMsg4.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg4);
                        break;
                    case 10:
                        List<entiteti.Mesto> listaMesta = dohvatanjeUpit.dohvatanjeMesta(msg);
                        ObjectMessage objMsgM = context.createObjectMessage((Serializable) listaMesta);
                        objMsgM.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, objMsgM);
                        
                        /*odg = "Dobio poruku 10";
                        TextMessage txtMsg10 = context.createTextMessage(odg);
                        txtMsg10.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg10);
                        */
                        break;
                    case 11:
                        List<entiteti.Filijala> listaFilijala = dohvatanjeUpit.dohvatanjeFilijala(msg);
                        ObjectMessage objMsgF = context.createObjectMessage((Serializable) listaFilijala);
                        objMsgF.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, objMsgF);
                        
                        /*odg = "Dobio poruku 11";
                        TextMessage txtMsg11 = context.createTextMessage(odg);
                        txtMsg11.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg11);
                        */
                        break;
                    case 12:
                        List<entiteti.Komitent> listaKomitenta = dohvatanjeUpit.dohvatanjeKomitenta(msg);
                        ObjectMessage objMsgK = context.createObjectMessage((Serializable) listaKomitenta);
                        objMsgK.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, objMsgK);
                        
                        /*odg = "Dobio poruku 12";
                        TextMessage txtMsg12 = context.createTextMessage(odg);
                        txtMsg12.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg12);
                        */
                        break;
                    case 15:
                        System.out.println("UPIT 15 podSistem1");
                        PodaciDB_A pod = new PodaciDB_A();
                        pod.setListaFilijala(dohvatanjeUpit.dohvatanjeFilijala(msg));
                        pod.setListaMesta(dohvatanjeUpit.dohvatanjeMesta(msg));
                        pod.setListaKomitenta(dohvatanjeUpit.dohvatanjeKomitenta(msg));
                        
                        ObjectMessage objMsg15 = context.createObjectMessage((Serializable) pod);
                        objMsg15.setIntProperty("Sistem", 3);
                        objMsg15.setIntProperty("Upit", Upit);
                        producer.send(sistemTopic, objMsg15);
                        System.out.println("Odgovorio");

                    default:
                        odg = " Los upit ste poslali";
                        TextMessage txtMsgD = context.createTextMessage(odg);
                        producer.send(centralniSistemQueue, txtMsgD);
                }
                
                
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                TextMessage txtMsgCatch = context.createTextMessage("Greska u prenosu podataka");
                producer.send(centralniSistemQueue, txtMsgCatch);
            }
            
        }
        
    }
    
}
