/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_podsistem2;

import entitetiDB.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Goran
 */
public class DohvatanjeUpit {
    
    // 13 upit
    public List<entiteti.Racun> dohvatanjeRacunaKomitenta(Message msg){
        
        if( msg instanceof TextMessage ){
            try {
                System.out.println("Dohvatanje Racuna komitenta:");
                TextMessage txtMsg = (TextMessage)msg;
                String nazivKomitenta = txtMsg.getText();
                System.out.println(nazivKomitenta);
                
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findByNaziv", Komitent.class);
                List<Komitent> listaKom = tq.setParameter("naziv", nazivKomitenta).getResultList();
                
                if( listaKom.isEmpty() || nazivKomitenta == null){
                    return new ArrayList<>();
                }
                
                Komitent kom = listaKom.get(0);
                
                List<entiteti.Racun> listaRacunaSlanje = new ArrayList<>();
                for (Racun racunDB : kom.getRacunList()) {
                    entiteti.Racun racun = new entiteti.Racun();
                    racun.setBrTransakcija( racunDB.getBrTransakcija());
                    racun.setDatumOtvaranja( racunDB.getDatumOtvaranja());
                    racun.setDozvoljeniMinus( racunDB.getDozvoljenMinus());
                    racun.setIdKom( kom.getIdKom());
                    racun.setStanje(racunDB.getStanje());
                    racun.setStatus(racunDB.getStatus());
                    
                    listaRacunaSlanje.add(racun);
                }
                System.out.println(listaRacunaSlanje);
                return listaRacunaSlanje;
            } catch (JMSException ex) {
                Logger.getLogger(DohvatanjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return new ArrayList<>();
            }
            
        }else{
            return new ArrayList<>();
        }
    
    }
    
    // 14 upit
    public List<Object> dohvatanjeSvihTransakcija(Message msg){
        
         if( msg instanceof TextMessage ){
            try {
                TextMessage txtMsg = (TextMessage)msg;
                int IdRac = txtMsg.getIntProperty("IdRac");
                System.out.println(" Dohvatanje transakcija IdRac:" + IdRac);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
                EntityManager em = emf.createEntityManager();
                
                Racun racun = em.find(Racun.class, IdRac);
                
                if( racun == null ){
                    return new ArrayList<>();
                }
                
                List<Object> listaTransakcija = new ArrayList<>();
                for (Sana sanaDB : racun.getSanaList()) {
                    entiteti.SaNa sana = new entiteti.SaNa();
                    sana.setDatum( sanaDB.getDatum());
                    sana.setIdTra( sanaDB.getIdTra());
                    sana.setIznos( sanaDB.getIznos());
                    sana.setNaRac( sanaDB.getNaRac().getIdRac());
                    sana.setSaRac( sanaDB.getSaRac().getIdRac());
                    sana.setSvrha( sanaDB.getSvrha());
                    sana.setBrTransakcijeNa( sanaDB.getBrTransakcijeNa());
                    sana.setBrTransakcijeSa( sanaDB.getBrTransakcijeSa());
                    
                    listaTransakcija.add(sana);
                }
                
                for (Sana sanaDB : racun.getSanaList1()) {
                    entiteti.SaNa sana = new entiteti.SaNa();
                    sana.setDatum( sanaDB.getDatum());
                    sana.setIdTra( sanaDB.getIdTra());
                    sana.setIznos( sanaDB.getIznos());
                    sana.setNaRac( sanaDB.getNaRac().getIdRac());
                    sana.setSaRac( sanaDB.getSaRac().getIdRac());
                    sana.setSvrha( sanaDB.getSvrha());
                    sana.setBrTransakcijeNa( sanaDB.getBrTransakcijeNa());
                    sana.setBrTransakcijeSa( sanaDB.getBrTransakcijeSa());
                    
                    listaTransakcija.add(sana);
                }
                
                for (Uplata uplataDB : racun.getUplataList()) {
                    entiteti.Uplata uplata = new entiteti.Uplata();
                    uplata.setDatum( uplataDB.getDatum());
                    uplata.setIdFil(-1); // moras resiti ovo posle
                    uplata.setIdRac( racun.getIdRac());
                    uplata.setIdTra( uplataDB.getIdTra());
                    uplata.setIznos( uplataDB.getIznos());
                    uplata.setSvrha( uplataDB.getSvrha());
                    
                    listaTransakcija.add(uplata);
                }
                
                for (Isplata isplataDB : racun.getIsplataList()) {
                    entiteti.Isplata isplata = new entiteti.Isplata();
                    isplata.setDatum( isplataDB.getDatum());
                    isplata.setIdFil(-1); // moras resiti ovo posle
                    isplata.setIdRac( racun.getIdRac());
                    isplata.setIdTra( isplataDB.getIdTra());
                    isplata.setIznos( isplataDB.getIznos());
                    isplata.setSvrha( isplataDB.getSvrha());
                    
                    listaTransakcija.add(isplata);
                }
                
                return listaTransakcija;
            } catch (JMSException ex) {
                Logger.getLogger(DohvatanjeUpit.class.getName()).log(Level.SEVERE, null, ex);
                return new ArrayList<>();
            }
            
        }else{
            return new ArrayList<>();
        }
        
    }

    public List<entiteti.Isplata> dohvatanjeIsplata(){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Isplata> tq = em.createNamedQuery("Isplata.findAll", Isplata.class);
        List<Isplata> listaIsplata = tq.getResultList();
        List<entiteti.Isplata> listaIsplataRes = new ArrayList<>();
        for (Isplata isplataDB : listaIsplata) {
            entiteti.Isplata isplata = new entiteti.Isplata();
            isplata.setDatum( isplataDB.getDatum());
            isplata.setIdRac( isplataDB.getIdRac().getIdRac());
            isplata.setIdTra( isplataDB.getIdTra());
            isplata.setIznos( isplataDB.getIznos());
            isplata.setSvrha( isplataDB.getSvrha());
            isplata.setNazivFilijale( isplataDB.getNazivFilijale());
                
            listaIsplataRes.add(isplata);
        }
        
        return listaIsplataRes;
    }

    public List<entiteti.Komitent> dohvatanjeKomitenta(){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Komitent> tq = em.createNamedQuery("Komitent.findAll", Komitent.class);
            List<Komitent> listaKomitenta = tq.getResultList();
            List<entiteti.Komitent> listaKomitantaSlanje = new ArrayList<>();
            
            for (Komitent komitentDB : listaKomitenta) {
                
                entiteti.Komitent komitent = new entiteti.Komitent();
                komitent.setAdresa( komitentDB.getAdresa());
                komitent.setIdKom( komitentDB.getIdKom());
                komitent.setIdMes( komitentDB.getMesto().getIdMes());
                komitent.setMesto( komitentDB.getMesto().getNaziv());
                komitent.setNaziv( komitentDB.getNaziv());
                
                listaKomitantaSlanje.add(komitent);
            }
            
            return listaKomitantaSlanje;
    
    }
    
    public List<entiteti.Racun> dohvatanjeRacuna(){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
        EntityManager em = emf.createEntityManager();
            
        TypedQuery<Racun> tq = em.createNamedQuery("Racun.findAll", Racun.class);
        List<Racun> listaRacuna = tq.getResultList();
        List<entiteti.Racun> listaRacunaRes = new ArrayList<>();
            
        for (Racun racunDB : listaRacuna) {
                
            entiteti.Racun racun = new entiteti.Racun();
            racun.setBrTransakcija( racunDB.getBrTransakcija());
            racun.setDatumOtvaranja( racunDB.getDatumOtvaranja());
            racun.setDozvoljeniMinus( racunDB.getDozvoljenMinus());
            racun.setIdKom( racunDB.getIdKom().getIdKom());
            racun.setIdRac( racunDB.getIdRac());
            racun.setStanje(racunDB.getStanje());
            racun.setStatus( racunDB.getStatus());
                
            listaRacunaRes.add(racun);
        }
            
        return listaRacunaRes;
    
    }
    
    public List<entiteti.Uplata> dohvatanjeUplata(){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Uplata> tq = em.createNamedQuery("Uplata.findAll", Uplata.class);
        List<Uplata> listaUplata = tq.getResultList();
        List<entiteti.Uplata> listaUplataRes = new ArrayList<>();
        for (Uplata uplataDB : listaUplata) {
            entiteti.Uplata uplata = new entiteti.Uplata();
            uplata.setDatum(uplataDB.getDatum());
            uplata.setIdRac(uplataDB.getIdRac().getIdRac());
            uplata.setIdTra(uplataDB.getIdTra());
            uplata.setIznos(uplataDB.getIznos());
            uplata.setSvrha(uplataDB.getSvrha());
            uplata.setNazivFilijale(uplataDB.getNazivFilijale());
                
            listaUplataRes.add(uplata);
        }
        
        return listaUplataRes;
    }
    
    public List<entiteti.SaNa> dohvatanjeSaNa(){
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PS2PU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Sana> tq = em.createNamedQuery("Sana.findAll", Sana.class);
        List<Sana> listaSana = tq.getResultList();
        List<entiteti.SaNa> listaSanaRes = new ArrayList<>();
        for (Sana sanaDB : listaSana) {
            entiteti.SaNa sana = new entiteti.SaNa();
            sana.setDatum(sanaDB.getDatum());
            sana.setBrTransakcijeNa( sanaDB.getBrTransakcijeNa());
            sana.setBrTransakcijeSa( sanaDB.getBrTransakcijeSa());
            sana.setNaRac( sanaDB.getNaRac().getIdRac());
            sana.setSaRac( sanaDB.getSaRac().getIdRac());
            sana.setIdTra(sanaDB.getIdTra());
            sana.setIznos(sanaDB.getIznos());
            sana.setSvrha(sanaDB.getSvrha());
                
            listaSanaRes.add(sana);
        }
        
        return listaSanaRes;
    }
    
}
