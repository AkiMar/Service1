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
public class PodaciDB_A implements Serializable{
    
    List<entiteti.Mesto> listaMesta;
    
    List<entiteti.Filijala> listaFilijala;
    
    List<entiteti.Komitent> listaKomitenta;

    public PodaciDB_A() {
    }

    public List<Mesto> getListaMesta() {
        return listaMesta;
    }

    public void setListaMesta(List<Mesto> listaMesta) {
        this.listaMesta = listaMesta;
    }

    public List<Filijala> getListaFilijala() {
        return listaFilijala;
    }

    public void setListaFilijala(List<Filijala> listaFilijala) {
        this.listaFilijala = listaFilijala;
    }

    public List<Komitent> getListaKomitenta() {
        return listaKomitenta;
    }

    public void setListaKomitenta(List<Komitent> listaKomitenta) {
        this.listaKomitenta = listaKomitenta;
    }
    
    
    
}
