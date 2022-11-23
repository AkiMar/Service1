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
@Table(name = "sana")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sana.findAll", query = "SELECT s FROM Sana s"),
    @NamedQuery(name = "Sana.findByIdTra", query = "SELECT s FROM Sana s WHERE s.idTra = :idTra"),
    @NamedQuery(name = "Sana.findByIznos", query = "SELECT s FROM Sana s WHERE s.iznos = :iznos"),
    @NamedQuery(name = "Sana.findByDatum", query = "SELECT s FROM Sana s WHERE s.datum = :datum"),
    @NamedQuery(name = "Sana.findBySvrha", query = "SELECT s FROM Sana s WHERE s.svrha = :svrha"),
    @NamedQuery(name = "Sana.findByBrTransakcijeSa", query = "SELECT s FROM Sana s WHERE s.brTransakcijeSa = :brTransakcijeSa"),
    @NamedQuery(name = "Sana.findByBrTransakcijeNa", query = "SELECT s FROM Sana s WHERE s.brTransakcijeNa = :brTransakcijeNa")})
public class Sana implements Serializable {

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
    @Column(name = "BrTransakcijeSa")
    private int brTransakcijeSa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrTransakcijeNa")
    private int brTransakcijeNa;
    @JoinColumn(name = "NaRac", referencedColumnName = "IdRac")
    @ManyToOne(optional = false)
    private Racun naRac;
    @JoinColumn(name = "SaRac", referencedColumnName = "IdRac")
    @ManyToOne(optional = false)
    private Racun saRac;

    public Sana() {
    }

    public Sana(Integer idTra) {
        this.idTra = idTra;
    }

    public Sana(Integer idTra, int iznos, Date datum, int brTransakcijeSa, int brTransakcijeNa) {
        this.idTra = idTra;
        this.iznos = iznos;
        this.datum = datum;
        this.brTransakcijeSa = brTransakcijeSa;
        this.brTransakcijeNa = brTransakcijeNa;
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

    public int getBrTransakcijeSa() {
        return brTransakcijeSa;
    }

    public void setBrTransakcijeSa(int brTransakcijeSa) {
        this.brTransakcijeSa = brTransakcijeSa;
    }

    public int getBrTransakcijeNa() {
        return brTransakcijeNa;
    }

    public void setBrTransakcijeNa(int brTransakcijeNa) {
        this.brTransakcijeNa = brTransakcijeNa;
    }

    public Racun getNaRac() {
        return naRac;
    }

    public void setNaRac(Racun naRac) {
        this.naRac = naRac;
    }

    public Racun getSaRac() {
        return saRac;
    }

    public void setSaRac(Racun saRac) {
        this.saRac = saRac;
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
        if (!(object instanceof Sana)) {
            return false;
        }
        Sana other = (Sana) object;
        if ((this.idTra == null && other.idTra != null) || (this.idTra != null && !this.idTra.equals(other.idTra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitetiDB.Sana[ idTra=" + idTra + " ]";
    }
    
}
