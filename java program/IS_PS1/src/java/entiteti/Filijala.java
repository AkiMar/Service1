/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Goran
 */
@XmlRootElement( name = "filijalaInfo")
public class Filijala implements Serializable {
    
    private int IdFil = 0;
    private String Naziv;
    private String Adresa;
    private int IdMes;
    private String MestoF;
    
    public int getIdFil() {
        return IdFil;
    }

    public void setIdFil(int IdFil) {
        this.IdFil = IdFil;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String Naziv) {
        this.Naziv = Naziv;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String Adresa) {
        this.Adresa = Adresa;
    }

    public int getIdMes() {
        return IdMes;
    }

    public void setIdMes(int IdMes) {
        this.IdMes = IdMes;
    }

    public String getMestoF() {
        return MestoF;
    }

    public void setMestoF(String MestoF) {
        this.MestoF = MestoF;
    }
    
    
    
}
