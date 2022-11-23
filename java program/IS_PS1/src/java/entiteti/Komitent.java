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
@XmlRootElement( name = "KomitentInfo")
public class Komitent implements Serializable  {
    
    private int IdKom = 0;
    private String Naziv;
    private String Adresa;
    private int IdMes;
    private String Mesto;

    public int getIdKom() {
        return IdKom;
    }

    public void setIdKom(int IdKom) {
        this.IdKom = IdKom;
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

    public String getMesto() {
        return Mesto;
    }

    public void setMesto(String Mesto) {
        this.Mesto = Mesto;
    }
    
    
    
}
