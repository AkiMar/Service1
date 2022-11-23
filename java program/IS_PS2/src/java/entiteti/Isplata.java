/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Goran
 */
@XmlRootElement( name = "isplataInfo")
public class Isplata implements Serializable  {

    private int IdTra = 0;
    private int Iznos;
    private Date Datum;
    private String Svrha;
    private int IdRac;
    private int IdFil;
    private String NazivFilijale;
    private int brTransakcije;

    public int getBrTransakcije() {
        return brTransakcije;
    }

    public void setBrTransakcije(int brTransakcije) {
        this.brTransakcije = brTransakcije;
    }
    
    

    public int getIdTra() {
        return IdTra;
    }

    public void setIdTra(int IdTra) {
        this.IdTra = IdTra;
    }

    public int getIznos() {
        return Iznos;
    }

    public void setIznos(int Iznos) {
        this.Iznos = Iznos;
    }

    public Date getDatum() {
        return Datum;
    }

    public void setDatum(Date Datum) {
        this.Datum = Datum;
    }

    public String getSvrha() {
        return Svrha;
    }

    public void setSvrha(String Svrha) {
        this.Svrha = Svrha;
    }

    public int getIdRac() {
        return IdRac;
    }

    public void setIdRac(int IdRac) {
        this.IdRac = IdRac;
    }

    public int getIdFil() {
        return IdFil;
    }

    public void setIdFil(int IdFil) {
        this.IdFil = IdFil;
    }

    public String getNazivFilijale() {
        return NazivFilijale;
    }

    public void setNazivFilijale(String NazivFilijale) {
        this.NazivFilijale = NazivFilijale;
    }
    
    
    
}
