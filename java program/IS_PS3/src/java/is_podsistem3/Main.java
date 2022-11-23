/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem3;

import entiteti.PodaciDB_B;
import entiteti.PodaciDB_A;
import entitetiDB.*;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ma180130
 */
public class Main {

    
    @Resource( lookup = "IS_PodSistem3Fac3")
    private static ConnectionFactory connectionFactory;
    
    @Resource( lookup = "IS_PodSistemTopic1")
    private static Topic sistemTopic;
    
    @Resource( lookup = "IS_CSQueue1")
    private static Queue centralniSistemQueue;
    
    @Resource( lookup = "IS_PodSistem3Q")
    private static Queue podSistem3Queue;
    
    public static PodaciDB_A podAPosl = null;
    public static PodaciDB_B podBPosl = null;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        context.setClientID("podSistem3");
        JMSProducer producer = context.createProducer();
        
        JMSConsumer consumer = context.createDurableConsumer(sistemTopic, "sub3", "Sistem=3", false);
        
        for (int i = 0; i < 100; i++) {
            consumer.receiveNoWait();
        }
        
        
        JMSConsumer consumerMoj = context.createDurableConsumer(sistemTopic, "sub33", "Upit=25", false);
        for (int i = 0; i < 100; i++) {
            consumerMoj.receiveNoWait();
        }
        consumerMoj.setMessageListener( (Message msg)->{
            try {
                List<Object> lista = new ArrayList<Object>();
                if( podAPosl != null){
                    lista = dodajListuA(lista, podAPosl);
                }
                if( podBPosl != null){
                    lista = dodajListuB(lista, podBPosl);
                }
                ObjectMessage objMsg = context.createObjectMessage((Serializable) lista);
                objMsg.setIntProperty("Upit", 25);
                producer.send(centralniSistemQueue, objMsg); 
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
        
        JMSConsumer consumerMojRazlika = context.createDurableConsumer(sistemTopic, "sub34", "Upit=26", false);
        for (int i = 0; i < 100; i++) {
            consumerMojRazlika.receiveNoWait();
        }
        consumerMojRazlika.setMessageListener( (Message msg)->{
            try {
                List<Object> lista = new ArrayList<Object>();
                
                 PodaciDB_A podA;
                PodaciDB_B podB;
                if( podAPosl != null){
                    TextMessage txtMsg = context.createTextMessage("Dohvati podatke");
                    txtMsg.setIntProperty("Sistem", 1);
                    txtMsg.setIntProperty("Upit", 15);
                    producer.send(sistemTopic, txtMsg);
                
                    Message msgA = consumer.receive(10000);
                    if ( msgA != null && msgA instanceof ObjectMessage ){
                        ObjectMessage objMsg = (ObjectMessage)msgA;
                        Object obj = objMsg.getObject();
                        if( obj instanceof PodaciDB_A){
                        podA = (PodaciDB_A)objMsg.getObject();
                        System.out.println("Dosla poruka");
                        System.out.println(podA.getListaMesta());
                        System.out.println(podA.getListaKomitenta());
                        System.out.println(podA.getListaFilijala());
                        lista = razlikeDodajA(lista, podA);
                    }
                    if( obj instanceof PodaciDB_B){
                        podB = (PodaciDB_B)objMsg.getObject();
                        System.out.println("Dosla poruka");
                        System.out.println(podB.getListaIsplata());
                        System.out.println(podB.getListaKomitenta());
                        System.out.println(podB.getListaRacuna());
                        System.out.println(podB.getListaSaNa());
                        System.out.println(podB.getListaUplata());
                        lista = razlikeDodajB(lista, podB);
                    }
                }
                }
                if( podBPosl != null){
                    TextMessage txtMsg2 = context.createTextMessage("Dohvati podatke");
                txtMsg2.setIntProperty("Sistem", 2);
                txtMsg2.setIntProperty("Upit", 15);
                producer.send(sistemTopic, txtMsg2);
                Message msgB = consumer.receive(10000);
                if ( msgB != null && msgB instanceof ObjectMessage ){
                    ObjectMessage objMsg = (ObjectMessage)msgB;
                    Object obj = objMsg.getObject();
                    if( obj instanceof PodaciDB_A){
                    podA = (PodaciDB_A)objMsg.getObject();
                    System.out.println("Dosla poruka");
                    System.out.println(podA.getListaMesta());
                    System.out.println(podA.getListaKomitenta());
                    System.out.println(podA.getListaFilijala());
                    lista = razlikeDodajA(lista, podA);
                    }
                    if( obj instanceof PodaciDB_B){
                        podB = (PodaciDB_B)objMsg.getObject();
                    System.out.println("Dosla poruka");
                    System.out.println(podB.getListaIsplata());
                    System.out.println(podB.getListaKomitenta());
                    System.out.println(podB.getListaRacuna());
                    System.out.println(podB.getListaSaNa());
                    System.out.println(podB.getListaUplata());
                    lista = razlikeDodajB(lista, podB);
                    }
                }
                }
                
                ObjectMessage objMsg = context.createObjectMessage((Serializable) lista);
                objMsg.setIntProperty("Upit", 26);
                producer.send(centralniSistemQueue, objMsg); 
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
        
        
        
        while(true){
            try {
                System.out.println("Krenuo podsistem3");
                TextMessage txtMsg = context.createTextMessage("Dohvati podatke");
                txtMsg.setIntProperty("Sistem", 1);
                txtMsg.setIntProperty("Upit", 15);
                producer.send(sistemTopic, txtMsg);
                
                Message msg = consumer.receive(10000);
                if ( msg != null && msg instanceof ObjectMessage ){
                    ObjectMessage objMsg = (ObjectMessage)msg;
                    Object obj = objMsg.getObject();
                    if( obj instanceof PodaciDB_A){
                    PodaciDB_A podA = (PodaciDB_A)objMsg.getObject();
                    podAPosl = podA;
                    System.out.println("Dosla poruka");
                    System.out.println(podA.getListaMesta());
                    System.out.println(podA.getListaKomitenta());
                    System.out.println(podA.getListaFilijala());
                    dodajDBA(podA);
                    }
                    if( obj instanceof PodaciDB_B){
                        PodaciDB_B podB = (PodaciDB_B)objMsg.getObject();
                    podBPosl = podB;
                    System.out.println("Dosla poruka");
                    System.out.println(podB.getListaIsplata());
                    System.out.println(podB.getListaKomitenta());
                    System.out.println(podB.getListaRacuna());
                    System.out.println(podB.getListaSaNa());
                    System.out.println(podB.getListaUplata());
                    dodajDBB(podB);
                    }
                }
                TextMessage txtMsg2 = context.createTextMessage("Dohvati podatke");
                txtMsg2.setIntProperty("Sistem", 2);
                txtMsg2.setIntProperty("Upit", 15);
                producer.send(sistemTopic, txtMsg2);
                Message msg2 = consumer.receive(10000);
                if ( msg2 != null && msg2 instanceof ObjectMessage ){
                    ObjectMessage objMsg = (ObjectMessage)msg2;
                    Object obj = objMsg.getObject();
                    if( obj instanceof PodaciDB_A){
                    PodaciDB_A podA = (PodaciDB_A)objMsg.getObject();
                    podAPosl = podA;
                    System.out.println("Dosla poruka");
                    System.out.println(podA.getListaMesta());
                    System.out.println(podA.getListaKomitenta());
                    System.out.println(podA.getListaFilijala());
                    dodajDBA(podA);
                    }
                    if( obj instanceof PodaciDB_B){
                        PodaciDB_B podB = (PodaciDB_B)objMsg.getObject();
                    podBPosl = podB;
                    System.out.println("Dosla poruka");
                    System.out.println(podB.getListaIsplata());
                    System.out.println(podB.getListaKomitenta());
                    System.out.println(podB.getListaRacuna());
                    System.out.println(podB.getListaSaNa());
                    System.out.println(podB.getListaUplata());
                    dodajDBB(podB);
                    }
                }
                
                Thread.sleep(120000);
                
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
            
            
        }
        
    }
    
    public static void dodajDBA( PodaciDB_A podaci){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS3PU");
        EntityManager em = emf.createEntityManager();
    
        em.getTransaction().begin();
        Query q1 = em.createQuery("DELETE FROM Mesto");
        Query q2 = em.createQuery("DELETE FROM Filijala");
        Query q3 = em.createQuery("DELETE FROM Komitent");
        Query q4 = em.createQuery("DELETE FROM Racun");
        Query q6 = em.createQuery("DELETE FROM Isplata");
        Query q7 = em.createQuery("DELETE FROM Sana");
        Query q8 = em.createQuery("DELETE FROM Uplata");
        q8.executeUpdate();
        q7.executeUpdate();
        q6.executeUpdate();
        q4.executeUpdate();
        q3.executeUpdate();
        q2.executeUpdate();
        q1.executeUpdate();
        em.getTransaction().commit();
        
        for (entiteti.Mesto mesto : podaci.getListaMesta()) {
            
            Mesto novoMesto = new Mesto();
            novoMesto.setNaziv(mesto.getNaziv());
            novoMesto.setPostanskiBroj(Integer.toString(mesto.getPostanskiBroj()));
                
            em.getTransaction().begin();
            em.persist(novoMesto);
            em.getTransaction().commit();
        }
        
        for (entiteti.Filijala filijala : podaci.getListaFilijala()) {
            
            Filijala novaFlijala = new entitetiDB.Filijala();
            novaFlijala.setAdresa( filijala.getAdresa());
            
            TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
            List<Mesto> listaMesta = tq.setParameter("naziv", filijala.getMestoF()).getResultList();
            Mesto mesto = listaMesta.get(0);
            novaFlijala.setMesto(mesto);
            novaFlijala.setNaziv(filijala.getNaziv());
            novaFlijala.setIdFil(filijala.getIdFil());
            
            em.getTransaction().begin();
            em.persist(novaFlijala);
            em.getTransaction().commit();
            
        }
        
    }

    public static void dodajDBB( PodaciDB_B podaci){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS3PU");
        EntityManager em = emf.createEntityManager();
    
        em.getTransaction().begin();
        Query q1 = em.createQuery("DELETE FROM Racun");
        Query q2 = em.createQuery("DELETE FROM Komitent");
        Query q3 = em.createQuery("DELETE FROM Isplata");
        Query q4 = em.createQuery("DELETE FROM Sana");
        Query q5 = em.createQuery("DELETE FROM Uplata");
        q5.executeUpdate();
        q4.executeUpdate();
        q3.executeUpdate();
        q2.executeUpdate();
        q1.executeUpdate();
        em.getTransaction().commit();
        
        for (entiteti.Komitent komitent : podaci.getListaKomitenta()) {
            
            TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
            List<Mesto> listaMesta = tq.setParameter("naziv", komitent.getMesto()).getResultList();
            Mesto mesto = listaMesta.get(0);
            
            Komitent noviKomitent = new Komitent();
            noviKomitent.setAdresa(komitent.getAdresa());
            noviKomitent.setMesto(mesto);
            noviKomitent.setNaziv(komitent.getNaziv());
            noviKomitent.setIdKom(komitent.getIdKom());
                
            em.getTransaction().begin();
            em.persist(noviKomitent);
            em.getTransaction().commit();
            
        }
        
        for (entiteti.Racun racun : podaci.getListaRacuna()) {
            
            TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findByIdKom", Komitent.class);
            List<Komitent> listaKom = tq.setParameter("idKom", racun.getIdKom()).getResultList();
            Komitent komitent = listaKom.get(0);
            
            Racun novRacun = new Racun();
            novRacun.setBrTransakcija( racun.getBrTransakcija());
            novRacun.setDatumOtvaranja( racun.getDatumOtvaranja());
            novRacun.setDozvoljenMinus( racun.getDozvoljeniMinus());
            System.out.println(racun.getIdRac());
            novRacun.setIdRac( racun.getIdRac());
            novRacun.setStanje(racun.getStanje());
            novRacun.setStatus( racun.getStatus());
            novRacun.setKomitent(komitent);
            
            em.getTransaction().begin();
            em.persist(novRacun);
            em.getTransaction().commit();
            
        }
        
        for (entiteti.Isplata isplata : podaci.getListaIsplata()) {
            
            TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
            List<Racun> listaRac = tq.setParameter("idRac", isplata.getIdRac()).getResultList();
            Racun racun = listaRac.get(0);
            
            
            Isplata novIsplata = new Isplata();
            novIsplata.setBrTransakcije(isplata.getBrTransakcije());
            novIsplata.setDatum(isplata.getDatum());
            novIsplata.setIdTra(isplata.getIdTra());
            novIsplata.setIznos(isplata.getIznos());
            novIsplata.setNazivFilijale(isplata.getNazivFilijale());
            novIsplata.setRacun(racun);
            novIsplata.setSvrha(isplata.getSvrha());
            
            em.getTransaction().begin();
            em.persist(novIsplata);
            em.getTransaction().commit();
            
            
        }
        
        
        for (entiteti.Uplata uplata : podaci.getListaUplata()) {
            
            TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
            List<Racun> listaRac = tq.setParameter("idRac", uplata.getIdRac()).getResultList();
            Racun racun = listaRac.get(0);
            
            
            Uplata novUplata = new Uplata();
            novUplata.setBrTransakcije(uplata.getBrTransakcije());
            novUplata.setDatum(uplata.getDatum());
            novUplata.setIdTra(uplata.getIdTra());
            novUplata.setIznos(uplata.getIznos());
            novUplata.setNazivFilijale(uplata.getNazivFilijale());
            novUplata.setRacun(racun);
            novUplata.setSvrha(uplata.getSvrha());
            
            em.getTransaction().begin();
            em.persist(novUplata);
            em.getTransaction().commit();
            
        }
        
        for (entiteti.SaNa sana : podaci.getListaSaNa()) {
            
            TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
            List<Racun> listaRac = tq.setParameter("idRac", sana.getSaRac()).getResultList();
            Racun racunSa = listaRac.get(0);
            
            listaRac = tq.setParameter("idRac", sana.getNaRac()).getResultList();
            Racun racunNa = listaRac.get(0);
            
            Sana novSana = new Sana();
            
            novSana.setBrTransakcijeNa(sana.getBrTransakcijeNa());
            novSana.setBrTransakcijeSa(sana.getBrTransakcijeSa());
            novSana.setDatum(sana.getDatum());
            novSana.setIdTra(sana.getIdTra());
            novSana.setIznos(sana.getIznos());
            novSana.setRacun(racunSa);
            novSana.setRacun1(racunNa);
            novSana.setSvrha(sana.getSvrha());
            
            em.getTransaction().begin();
            em.persist(novSana);
            em.getTransaction().commit();
            
            
        }
    
    }

    public static List<Object> dodajListuA(List<Object> lista, PodaciDB_A podaci){
    
         for (entiteti.Mesto mesto : podaci.getListaMesta()) {
            
            lista.add((Object)mesto);
        }
        
        for (entiteti.Filijala filijala : podaci.getListaFilijala()) {
            
            lista.add((Object)filijala);
        }
    
        return lista;
    }

    public static List<Object> dodajListuB(List<Object> lista, PodaciDB_B podaci){
        
        for (entiteti.Komitent komitent : podaci.getListaKomitenta()) {
            
            lista.add((Object)komitent);
            
        }
        
        for (entiteti.Racun racun : podaci.getListaRacuna()) {
            
           lista.add((Object)racun);
            
        }
        
        for (entiteti.Isplata isplata : podaci.getListaIsplata()) {
            
            lista.add((Object)isplata);
            
        }
        
        for (entiteti.Uplata uplata : podaci.getListaUplata()) {
            
            lista.add((Object)uplata);
            
        }
        
        for (entiteti.SaNa sana : podaci.getListaSaNa()) {
            
            lista.add((Object)sana);
            
        }
        
        return lista;
    }

    public static List<Object> razlikeDodajA(List<Object> lista, PodaciDB_A podaci){
        boolean flag = true;
        for (entiteti.Mesto mesto : podaci.getListaMesta()) {
            
            for (entiteti.Mesto mesto1 : podAPosl.getListaMesta()) {
                if( mesto1.getIdMes()== mesto.getIdMes()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)mesto);
            }
            flag = true;
        }
        
        for (entiteti.Filijala filijala : podaci.getListaFilijala()) {
                
            for (entiteti.Filijala filijala1 : podAPosl.getListaFilijala()) {
                if( filijala1.getIdFil()== filijala.getIdFil()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)filijala);
            }
            flag = true;
        }
    
        return lista;
    }

    public static List<Object> razlikeDodajB(List<Object> lista, PodaciDB_B podaci){
        boolean flag = true;
        for (entiteti.Komitent komitent : podaci.getListaKomitenta()) {
            
            
            for (entiteti.Komitent komitent1 : podBPosl.getListaKomitenta()) {
                if( komitent1.getIdKom() == komitent.getIdKom()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)komitent);
            }
            flag = true;
            
        }
        
        for (entiteti.Racun racun : podaci.getListaRacuna()) {
            
           for (entiteti.Racun racun1 : podBPosl.getListaRacuna()) {
                if( racun1.getIdRac()== racun.getIdRac()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)racun);
            }
            flag = true;
            
        }
        
        for (entiteti.Isplata isplata : podaci.getListaIsplata()) {
            
            for (entiteti.Isplata isplata1 : podBPosl.getListaIsplata()) {
                if( isplata1.getIdTra()== isplata.getIdTra()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)isplata);
            }
            flag = true;
            
        }
        
        for (entiteti.Uplata uplata : podaci.getListaUplata()) {
            
            for (entiteti.Uplata uplata1 : podBPosl.getListaUplata()) {
                if( uplata1.getIdTra() == uplata.getIdTra()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)uplata);
            }
            flag = true;
            
        }
        
        for (entiteti.SaNa sana : podaci.getListaSaNa()) {
            
            for (entiteti.SaNa saNa1 : podBPosl.getListaSaNa()) {
                if( saNa1.getIdTra()== sana.getIdTra()){
                    flag = false;
                    break;
                }
            }
            
            if( flag){
                lista.add((Object)sana);
            }
            flag = true;
            
        }
        
        return lista;
    }
    
}
