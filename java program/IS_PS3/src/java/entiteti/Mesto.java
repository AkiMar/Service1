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
@XmlRootElement( name = "mestoInfo")
public class Mesto implements Serializable  {
    
    private int IdMes = 0;
    private String Naziv;
    private int PostanskiBroj;

    public int getIdMes() {
        return IdMes;
    }

    public void setIdMes(int IdMes) {
        this.IdMes = IdMes;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String Naziv) {
        this.Naziv = Naziv;
    }

    public int getPostanskiBroj() {
        return PostanskiBroj;
    }

    public void setPostanskiBroj(int PostanskiBroj) {
        this.PostanskiBroj = PostanskiBroj;
    }
    
    
    
}
