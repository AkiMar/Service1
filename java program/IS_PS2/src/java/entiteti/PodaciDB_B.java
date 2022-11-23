/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import entiteti.Filijala;
import entiteti.Komitent;
import entiteti.Mesto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 *
 * @author ma180130
 */
public class PodaciDB_B implements Serializable{
    
    List<entiteti.Isplata> listaIsplata;
    
    List<entiteti.Komitent> listaKomitenta;
    
    List<entiteti.Racun> listaRacuna;
    
    List<entiteti.SaNa> listaSaNa;
    
    List<entiteti.Uplata> listaUplata;

    public PodaciDB_B() {
    }

    public List<Isplata> getListaIsplata() {
        return listaIsplata;
    }

    public void setListaIsplata(List<Isplata> listaIsplata) {
        this.listaIsplata = listaIsplata;
    }

    public List<Komitent> getListaKomitenta() {
        return listaKomitenta;
    }

    public void setListaKomitenta(List<Komitent> listaKomitenta) {
        this.listaKomitenta = listaKomitenta;
    }

    public List<Racun> getListaRacuna() {
        return listaRacuna;
    }

    public void setListaRacuna(List<Racun> listaRacuna) {
        this.listaRacuna = listaRacuna;
    }

    public List<SaNa> getListaSaNa() {
        return listaSaNa;
    }

    public void setListaSaNa(List<SaNa> listaSaNa) {
        this.listaSaNa = listaSaNa;
    }

    public List<Uplata> getListaUplata() {
        return listaUplata;
    }

    public void setListaUplata(List<Uplata> listaUplata) {
        this.listaUplata = listaUplata;
    }

   
    
    
    
}
