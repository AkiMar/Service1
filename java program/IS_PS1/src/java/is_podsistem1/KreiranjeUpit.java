/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem1;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import entitetiDB.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.jms.TextMessage;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Goran
 */
public class KreiranjeUpit {

    // 1 upit
    public String kreiranjeMesta(Message msg){
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.Mesto mesto = (entiteti.Mesto)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
                EntityManager em = emf.createEntityManager();
                System.out.println(mesto.getNaziv());
                TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
                List<Mesto> listaMesta = tq.setParameter("naziv", mesto.getNaziv()).getResultList();
                
                if( listaMesta.isEmpty() == false){
                    System.out.println( "Vec postoji mesto" );
                    return "Vec postoji Mesto";
                }
                
                Mesto novoMesto = new Mesto();
                novoMesto.setNaziv(mesto.getNaziv());
                novoMesto.setPostanskiBroj(mesto.getPostanskiBroj());
                
                em.getTransaction().begin();
                em.persist(novoMesto);
                em.getTransaction().commit();
                
                return "Dodato mesto";
                
            } catch (JMSException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            } catch (SecurityException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
        return "Greska u prenosu podatka";
    }
    
    // 2 upit RADI
    public String kreiranjeFilijale(Message msg){
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.Filijala filijala = (entiteti.Filijala)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Filijala> tq = em.createNamedQuery("Filijala.findByNaziv", Filijala.class);
                List<Filijala> listaFlilijala = tq.setParameter("naziv", filijala.getNaziv()).getResultList();
                
                if( listaFlilijala.isEmpty() == false){
                    System.out.println("Vec postoji Flilijala");
                    return "Vec postoji Flilijala";
                }
                
                TypedQuery<Mesto> tq2 = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
                List<Mesto> listaMesta = tq2.setParameter("naziv", filijala.getMestoF()).getResultList();
                
                if( listaMesta.isEmpty()){
                    System.out.println("Ne postoji dato mesto u bazi podataka dodajte ga");
                    return "Ne postoji dato mesto u bazi podataka dodajte ga";
                }
                Mesto mesto = listaMesta.get(0);
                
                
                Filijala novaFlijala = new Filijala();
                novaFlijala.setAdresa( filijala.getAdresa());
                novaFlijala.setMesto(mesto);
                novaFlijala.setNaziv(filijala.getNaziv());
                
                em.getTransaction().begin();
                em.persist(novaFlijala);
                em.getTransaction().commit();
                System.out.println("Dodata Filijala");
                return "Dodata Filijala";
                
            } catch (JMSException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    }
    
    // 3 upit RADI
    public String kreiranjeKomitenta(Message msg){
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.Komitent komitent = (entiteti.Komitent)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findByNaziv", Komitent.class);
                List<Komitent> listaKomitenta = tq.setParameter("naziv", komitent.getNaziv()).getResultList();
                
                if( listaKomitenta.isEmpty() == false){
                    return "Vec postoji Komitent";
                }
                
                TypedQuery<Mesto> tq2 = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
                List<Mesto> listaMesta = tq2.setParameter("naziv", komitent.getMesto()).getResultList();
                
                if( listaMesta.isEmpty()){
                    return "Ne postoji dato mesto u bazi podataka dodajte ga";
                }
                Mesto mesto = listaMesta.get(0);
                
                Komitent noviKomitent = new Komitent();
                noviKomitent.setAdresa(komitent.getAdresa());
                noviKomitent.setMesto(mesto);
                noviKomitent.setNaziv(komitent.getNaziv());
                
                em.getTransaction().begin();
                em.persist(noviKomitent);
                em.getTransaction().commit();
                
                return "Dodat Komitent";
                
            } catch (JMSException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
        }else{
            return "Doslo do greske u prenosu podataka";
        }
    }
    
    // 4 upit
    public String promenaSedista(Message msg){
        
        if( msg instanceof TextMessage ){
            try {
                TextMessage objMsg = (TextMessage)msg;
                String nazivKomitenta = objMsg.getText();
                String nazivMesta = objMsg.getStringProperty("Sediste");
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findByNaziv", Komitent.class);
                List<Komitent> listaKomitenta = tq.setParameter("naziv", nazivKomitenta).getResultList();
                
                if( listaKomitenta.isEmpty()){
                    return "Ne postoji komitent";
                }
                
                Komitent komitent = listaKomitenta.get(0);
                
                TypedQuery<Mesto> tq2 = em.createNamedQuery("Mesto.findByNaziv", Mesto.class);
                List<Mesto> listaMesta = tq2.setParameter("naziv", nazivMesta).getResultList();
                
                if( listaMesta.isEmpty()){
                    return "Ne postoji dato mesto u bazi podataka dodajte ga";
                }
                Mesto mesto = listaMesta.get(0);
                
                em.getTransaction().begin();
                
                komitent.setMesto(mesto);
                
                em.getTransaction().commit();
                
                return "Promenjeno sediste komitenta";
                
                
            } catch (JMSException ex) {
                Logger.getLogger(KreiranjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
        }else{
            return "Greska u prenosu podatka";
        }
    }
}
