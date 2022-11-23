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
@XmlRootElement
public class SaNa implements Serializable  {
    
    private int IdTra = 0;
    private int SaRac;
    private int NaRac;
    private int Iznos;
    private Date Datum;
    private String svrha;
    private int BrTransakcijeSa;
    private int BrTransakcijeNa;

    public int getIdTra() {
        return IdTra;
    }

    public void setIdTra(int IdTra) {
        this.IdTra = IdTra;
    }

    public int getSaRac() {
        return SaRac;
    }

    public void setSaRac(int SaRac) {
        this.SaRac = SaRac;
    }

    public int getNaRac() {
        return NaRac;
    }

    public void setNaRac(int NaRac) {
        this.NaRac = NaRac;
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
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public int getBrTransakcijeSa() {
        return BrTransakcijeSa;
    }

    public void setBrTransakcijeSa(int BrTransakcijeSa) {
        this.BrTransakcijeSa = BrTransakcijeSa;
    }

    public int getBrTransakcijeNa() {
        return BrTransakcijeNa;
    }

    public void setBrTransakcijeNa(int BrTransakcijeNa) {
        this.BrTransakcijeNa = BrTransakcijeNa;
    }
    
    
    
}
