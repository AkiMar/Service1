/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitetiDB;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ma180130
 */
@Entity
@Table(name = "uplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uplata.findAll", query = "SELECT u FROM Uplata u"),
    @NamedQuery(name = "Uplata.findByIdTra", query = "SELECT u FROM Uplata u WHERE u.idTra = :idTra"),
    @NamedQuery(name = "Uplata.findByIznos", query = "SELECT u FROM Uplata u WHERE u.iznos = :iznos"),
    @NamedQuery(name = "Uplata.findByDatum", query = "SELECT u FROM Uplata u WHERE u.datum = :datum"),
    @NamedQuery(name = "Uplata.findBySvrha", query = "SELECT u FROM Uplata u WHERE u.svrha = :svrha"),
    @NamedQuery(name = "Uplata.findByNazivFilijale", query = "SELECT u FROM Uplata u WHERE u.nazivFilijale = :nazivFilijale"),
    @NamedQuery(name = "Uplata.findByBrTransakcije", query = "SELECT u FROM Uplata u WHERE u.brTransakcije = :brTransakcije")})
public class Uplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdTra")
    private Integer idTra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Iznos")
    private int iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Size(max = 45)
    @Column(name = "Svrha")
    private String svrha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NazivFilijale")
    private String nazivFilijale;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrTransakcije")
    private int brTransakcije;
    @JoinColumn(name = "IdRac", referencedColumnName = "IdRac")
    @ManyToOne(optional = false)
    private Racun idRac;

    public Uplata() {
    }

    public Uplata(Integer idTra) {
        this.idTra = idTra;
    }

    public Uplata(Integer idTra, int iznos, Date datum, String nazivFilijale, int brTransakcije) {
        this.idTra = idTra;
        this.iznos = iznos;
        this.datum = datum;
        this.nazivFilijale = nazivFilijale;
        this.brTransakcije = brTransakcije;
    }

    public Integer getIdTra() {
        return idTra;
    }

    public void setIdTra(Integer idTra) {
        this.idTra = idTra;
    }

    public int getIznos() {
        return iznos;
    }

    public void setIznos(int iznos) {
        this.iznos = iznos;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public String getNazivFilijale() {
        return nazivFilijale;
    }

    public void setNazivFilijale(String nazivFilijale) {
        this.nazivFilijale = nazivFilijale;
    }

    public int getBrTransakcije() {
        return brTransakcije;
    }

    public void setBrTransakcije(int brTransakcije) {
        this.brTransakcije = brTransakcije;
    }

    public Racun getIdRac() {
        return idRac;
    }

    public void setIdRac(Racun idRac) {
        this.idRac = idRac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTra != null ? idTra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uplata)) {
            return false;
        }
        Uplata other = (Uplata) object;
        if ((this.idTra == null && other.idTra != null) || (this.idTra != null && !this.idTra.equals(other.idTra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitetiDB.Uplata[ idTra=" + idTra + " ]";
    }
    
}
