/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem2;

import entiteti.PodaciDB_B;
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

    @Resource( lookup = "IS_PodSistem2Fac2")
    private static ConnectionFactory connectionFactory;
    
    @Resource( lookup = "IS_PodSistemTopic1")
    private static Topic sistemTopic;
    
    @Resource( lookup = "IS_CSQueue1")
    private static Queue centralniSistemQueue;
   
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        context.setClientID("podSistem2");
        JMSProducer producer = context.createProducer();
        
        JMSConsumer consumer = context.createDurableConsumer(sistemTopic, "sub2", "Sistem=2", false);
        
        ObradaUpit obradaUpit = new ObradaUpit();
        DohvatanjeUpit dohvatanjeUpit = new DohvatanjeUpit();
        
        for (int i = 0; i < 100; i++) {
            consumer.receiveNoWait();
        }
        while(true){
            System.out.println("Krenuo podsistem2");
            Message msg = consumer.receive();
            String odg;
            try {
                int Upit = msg.getIntProperty("Upit");
                
                switch(Upit) {
                    case 5:
                        //odg = "Dobio poruku 5";
                        odg = obradaUpit.otvaranjeRacuna(msg);
                        System.out.println(odg);
                        TextMessage txtMsg5 = context.createTextMessage(odg);
                        txtMsg5.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg5);
                        break;
                    case 6:
                        //odg = "Dobio poruku 6";
                        odg = obradaUpit.zatvaranjeRacuna(msg);
                        TextMessage txtMsg6 = context.createTextMessage(odg);
                        txtMsg6.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg6);
                        break;
                    case 7:
                        //odg = "Dobio poruku 7";
                        odg = obradaUpit.transakcija(msg);
                        TextMessage txtMsg7 = context.createTextMessage(odg);
                        txtMsg7.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg7);
                        break;
                    case 8:
                        //odg = "Dobio poruku 8";
                        odg = obradaUpit.uplataNaRacun(msg);
                        TextMessage txtMsg8 = context.createTextMessage(odg);
                        txtMsg8.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg8);
                        break;
                    case 9:
                        //odg = "Dobio poruku 9";
                        odg = obradaUpit.isplataSaRacuna(msg);
                        TextMessage txtMsg9 = context.createTextMessage(odg);
                        txtMsg9.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, txtMsg9);
                        break;
                    case 13:
                        List<entiteti.Racun> listaRacuna = dohvatanjeUpit.dohvatanjeRacunaKomitenta(msg);
                        ObjectMessage objMsgR = context.createObjectMessage((Serializable) listaRacuna);
                        objMsgR.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, objMsgR);
                        break;
                    case 14:
                        List<Object> listaTransakcija = dohvatanjeUpit.dohvatanjeSvihTransakcija(msg);
                        ObjectMessage objMsgT = context.createObjectMessage((Serializable) listaTransakcija);
                        objMsgT.setIntProperty("Upit", Upit);
                        producer.send(centralniSistemQueue, objMsgT);  
                        break;
                    case 15:
                        System.out.println("UPIT 15 podSistem2");
                        PodaciDB_B pod = new PodaciDB_B();
                        pod.setListaIsplata( dohvatanjeUpit.dohvatanjeIsplata());
                        pod.setListaKomitenta( dohvatanjeUpit.dohvatanjeKomitenta());
                        pod.setListaRacuna( dohvatanjeUpit.dohvatanjeRacuna());
                        pod.setListaSaNa( dohvatanjeUpit.dohvatanjeSaNa());
                        pod.setListaUplata( dohvatanjeUpit.dohvatanjeUplata());
                        
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
