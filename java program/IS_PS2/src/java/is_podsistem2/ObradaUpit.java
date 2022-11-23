/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem2;

import entitetiDB.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Goran
 */
public class ObradaUpit {
    
    // 5 - upit
    public String otvaranjeRacuna( Message msg){
        
        if( msg instanceof TextMessage ){
            try {
                TextMessage txtMsg = (TextMessage)msg;
                String KomitentNaziv = txtMsg.getText();
                System.out.println(KomitentNaziv);
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findByNaziv", Komitent.class);
                List<Komitent> listaKomitenta = tq.setParameter("naziv", KomitentNaziv).getResultList();
                
                if( listaKomitenta.isEmpty()){
                    return "Ne postoji komitent";
                }
                
                Komitent komitent = listaKomitenta.get(0);
                
                Racun novRacun = new Racun();
                novRacun.setBrTransakcija(0);
                Date datum = new Date();
                novRacun.setDatumOtvaranja(datum);
                novRacun.setDozvoljenMinus(10000);
                novRacun.setIdKom(komitent);
                novRacun.setStanje(0);
                novRacun.setStatus("A"); // A - aktivan status racuna
                
                int idR = 1;
                Query qR = em.createQuery("SELECT MAX(r.idRac) FROM Racun r");
                if( qR.getSingleResult() != null){
                    idR = (int)qR.getSingleResult() + 1;
                }
                
                em.getTransaction().begin();
                em.persist(novRacun);
                em.getTransaction().commit();
                
                return "Otvoren je racun sa brojem:" + idR;
                
            } catch (JMSException ex) {
                Logger.getLogger(ObradaUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    
    }

    // 6 - upit
    public String zatvaranjeRacuna( Message msg){
         if( msg instanceof TextMessage ){
            try {
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println("------>" + txtMsg.getText());
                int idR = txtMsg.getIntProperty("IdRac");
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
                List<Racun> listaRacuna = tq.setParameter("idRac",idR).getResultList();
                
                if( listaRacuna.isEmpty()){
                    return "Ne postoji racun sa datim brojem";
                }
                
                Racun racun = listaRacuna.get(0);
                
                em.getTransaction().begin();
                racun.setStatus("Z"); // Z - zatvoren racun
                em.getTransaction().commit();
                
                return "Zatvoren je racun sa brojem:" + idR;
                
            } catch (JMSException ex) {
                Logger.getLogger(ObradaUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    }
    
    // 7 - upit
    public String transakcija( Message msg){
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.SaNa saNa = (entiteti.SaNa)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
                List<Racun> listaRacuna = tq.setParameter("idRac",saNa.getSaRac()).getResultList();
                
                if( listaRacuna.isEmpty()){
                    return "Ne postoji racun sa datim brojem SA kog se radi transakcija";
                }
                
                Racun racunSa = listaRacuna.get(0);
                listaRacuna.clear();
                listaRacuna = tq.setParameter("idRac",saNa.getNaRac()).getResultList();
                
                if( listaRacuna.isEmpty()){
                    return "Ne postoji racun sa datim brojem NA kog se radi transakcija";
                }
                
                Racun racunNa = listaRacuna.get(0);
                
                if( (racunSa.getStanje() + racunSa.getDozvoljenMinus() - saNa.getIznos() ) < 0){
                    return "Nemate dovoljno stanja za transakciju";
                }
                int brTSa = racunSa.getBrTransakcija() + 1;
                int brTNa = racunNa.getBrTransakcija() + 1;
                
                Sana novSaNa = new Sana();
                novSaNa.setBrTransakcijeSa(brTSa);
                novSaNa.setBrTransakcijeNa(brTNa);
                Date datum = new Date();
                novSaNa.setDatum(datum);
                novSaNa.setIznos(saNa.getIznos());
                novSaNa.setNaRac(racunNa);
                novSaNa.setSaRac(racunSa);
                novSaNa.setSvrha(saNa.getSvrha());
                
                em.getTransaction().begin();
                
                racunNa.setBrTransakcija(brTNa);
                racunSa.setBrTransakcija(brTSa);
                racunSa.setStanje( racunSa.getStanje() - saNa.getIznos());
                racunNa.setStanje( racunNa.getStanje() + saNa.getIznos());
                em.persist(novSaNa);
                em.getTransaction().commit();

                
                return "Odradjena transakcija";
            
            } catch (JMSException ex) {
                Logger.getLogger(ObradaUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    }
    
    // 8 - upit
    public String uplataNaRacun( Message msg){
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.Uplata uplata = (entiteti.Uplata)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                if( uplata.getIznos() < 0){
                    return "Lose unet iznos";
                }
                
                TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
                List<Racun> listaRacuna = tq.setParameter("idRac", uplata.getIdRac()).getResultList();
                
                if( listaRacuna.isEmpty()){
                    return "Ne postoji racun sa datim brojem";
                }
                
                Racun racun = listaRacuna.get(0);
                int brT = racun.getBrTransakcija() + 1;
                
                
                Uplata novaUplata = new Uplata();
                novaUplata.setBrTransakcije(brT);
                Date datum = new Date();
                novaUplata.setDatum(datum);
                novaUplata.setIdRac(racun);
                novaUplata.setIznos(uplata.getIznos());
                System.out.println(uplata.getNazivFilijale());
                novaUplata.setNazivFilijale(uplata.getNazivFilijale()); // moras sa podsistemom1 da ovo proveris
                novaUplata.setSvrha(uplata.getSvrha());
                
                em.getTransaction().begin();
                racun.setBrTransakcija(brT);
                racun.setStanje( racun.getStanje() + uplata.getIznos());
                if( racun.getStanje() + racun.getDozvoljenMinus() > 0){
                    racun.setStatus("A");
                }
                
                em.persist(novaUplata);
                em.getTransaction().commit();
                
                return "Odradjena uplata";
                
            } catch (JMSException ex) {
                Logger.getLogger(ObradaUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    }

    // 9 - upit
    public String isplataSaRacuna( Message msg){
        
        if( msg instanceof ObjectMessage ){
            try {
                ObjectMessage objMsg = (ObjectMessage)msg;
                entiteti.Isplata isplata = (entiteti.Isplata)objMsg.getObject();
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                if( isplata.getIznos() < 1){
                    return "Lose unet iznos";
                }
                
                TypedQuery<Racun> tq = em.createNamedQuery("Racun.findByIdRac", Racun.class);
                List<Racun> listaRacuna = tq.setParameter("idRac", isplata.getIdRac()).getResultList();
                
                if( listaRacuna.isEmpty()){
                    return "Ne postoji racun sa datim brojem";
                }
                
                Racun racun = listaRacuna.get(0);
                
                if( (racun.getStanje() + racun.getDozvoljenMinus()) < 0 || racun.getStatus().equals("B") ){
                    return "Ne mozete uzeti novac blokiran Vam je racun";
                }
                
                int brT = racun.getBrTransakcija() + 1;
                
                Isplata novaIsplata = new Isplata();
                novaIsplata.setBrTransakcije(brT);
                Date datum = new Date();
                novaIsplata.setDatum(datum);
                novaIsplata.setIdRac(racun);
                novaIsplata.setIznos(isplata.getIznos());
                novaIsplata.setNazivFilijale(isplata.getNazivFilijale()); // moras sa podsistemom1 da ovo proveris
                novaIsplata.setSvrha(isplata.getSvrha());
                
                em.getTransaction().begin();
                
                racun.setBrTransakcija(brT);
                racun.setStanje(racun.getStanje() - isplata.getIznos());
                if( racun.getStanje() + racun.getDozvoljenMinus() < 0){
                    racun.setStatus("B");
                }
                
                em.persist(novaIsplata);
                em.getTransaction().commit();
                
                return "Odradjena isplata";
                
            } catch (JMSException ex) {
                Logger.getLogger(ObradaUpit.class.getName()).log(Level.SEVERE, null, ex);
                return "Greska u prenosu podatka";
            }
            
        }else{
            return "Greska u prenosu podatka";
        }
    
    }
}
