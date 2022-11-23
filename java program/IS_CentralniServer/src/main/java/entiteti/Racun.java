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
@XmlRootElement( name = "racunInfo")
public class Racun implements Serializable  {
    
    private int IdRac = 0;
    private int Stanje;
    private int DozvoljeniMinus;
    private String Status;
    private int IdKom;
    private Date DatumOtvaranja;
    private int BrTransakcija;

    public int getIdRac() {
        return IdRac;
    }

    public void setIdRac(int IdRac) {
        this.IdRac = IdRac;
    }

    public int getStanje() {
        return Stanje;
    }

    public void setStanje(int Stanje) {
        this.Stanje = Stanje;
    }

    public int getDozvoljeniMinus() {
        return DozvoljeniMinus;
    }

    public void setDozvoljeniMinus(int DozvoljeniMinus) {
        this.DozvoljeniMinus = DozvoljeniMinus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getIdKom() {
        return IdKom;
    }

    public void setIdKom(int IdKom) {
        this.IdKom = IdKom;
    }

    public Date getDatumOtvaranja() {
        return DatumOtvaranja;
    }

    public void setDatumOtvaranja(Date DatumOtvaranja) {
        this.DatumOtvaranja = DatumOtvaranja;
    }

    public int getBrTransakcija() {
        return BrTransakcija;
    }

    public void setBrTransakcija(int BrTransakcija) {
        this.BrTransakcija = BrTransakcija;
    }
    
    
    
}
