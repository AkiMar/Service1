package is_podsistem1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entitetiDB.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Goran
 */

public class DohvatanjeUpit {
    
    // 10 upit
    public List<entiteti.Mesto> dohvatanjeMesta(Message msg){
        System.out.println("Dohvatanje mesta");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
        EntityManager em = emf.createEntityManager();
        
        if( msg instanceof TextMessage ){
            TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findAll", Mesto.class);
            List<Mesto> listaMesta = tq.getResultList();
            List<entiteti.Mesto> listaMestaSlanje = new ArrayList<>();
            for (Mesto mestoDB : listaMesta) {
                entiteti.Mesto mesto = new entiteti.Mesto();
                mesto.setIdMes( mestoDB.getIdMes());
                mesto.setNaziv( mestoDB.getNaziv());
                mesto.setPostanskiBroj( mestoDB.getPostanskiBroj());
                
                listaMestaSlanje.add(mesto);
            }
            
            for (entiteti.Mesto mesto : listaMestaSlanje) {
                System.out.println(mesto.getNaziv());
            }
            
            return listaMestaSlanje;
            
        }else{
            return new ArrayList<>();
        }
    
    }
    
    // 11 upit
    public List<entiteti.Filijala> dohvatanjeFilijala(Message msg){
        
        System.out.println("Dohvatanje Filijala");
        
        if( msg instanceof TextMessage ){
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
            EntityManager em = emf.createEntityManager();
            
            TypedQuery<Filijala> tq = em.createNamedQuery("Filijala.findAll", Filijala.class);
            List<Filijala> listaFilijala = tq.getResultList();
            List<entiteti.Filijala> listaFilijalaSlanje = new ArrayList<>();
            
            for (Filijala filijalaDB : listaFilijala) {
                
                entiteti.Filijala filijala = new entiteti.Filijala();
                filijala.setAdresa( filijalaDB.getAdresa());
                filijala.setIdFil( filijalaDB.getIdFil());
                filijala.setIdMes( filijalaDB.getMesto().getIdMes());
                filijala.setMestoF( filijalaDB.getMesto().getNaziv());
                filijala.setNaziv( filijalaDB.getNaziv());
                
                listaFilijalaSlanje.add(filijala);
                
            }
            
            for (entiteti.Filijala fil : listaFilijalaSlanje) {
                System.out.println(fil.getNaziv());
            }
            
            return listaFilijalaSlanje;
            
        }else{
            return new ArrayList<>();
        }
    
    }
    
    // 12 upit
    public List<entiteti.Komitent> dohvatanjeKomitenta(Message msg){
        
        System.out.println("Dohvatenje Komitenta");
        
        if( msg instanceof TextMessage ){
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS_PodSistem1PU");
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
            
        }else{
            return new ArrayList<>();
        }
    
    }
    
    
}
