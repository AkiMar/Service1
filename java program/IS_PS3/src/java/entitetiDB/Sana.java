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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
    @NamedQuery(name = "Sana.findByBrTransakcijeNa", query = "SELECT s FROM Sana s WHERE s.brTransakcijeNa = :brTransakcijeNa"),
    @NamedQuery(name = "Sana.findByBrTransakcijeSa", query = "SELECT s FROM Sana s WHERE s.brTransakcijeSa = :brTransakcijeSa")})
public class Sana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @Column(name = "BrTransakcijeNa")
    private Integer brTransakcijeNa;
    @Column(name = "BrTransakcijeSa")
    private Integer brTransakcijeSa;
    @JoinColumns({
        @JoinColumn(name = "NaRac", referencedColumnName = "IdRac"),
        @JoinColumn(name = "NaRac", referencedColumnName = "IdRac")})
    @ManyToOne(optional = false)
    private Racun racun;
    @JoinColumns({
        @JoinColumn(name = "SaRac", referencedColumnName = "IdRac"),
        @JoinColumn(name = "SaRac", referencedColumnName = "IdRac")})
    @ManyToOne(optional = false)
    private Racun racun1;

    public Sana() {
    }

    public Sana(Integer idTra) {
        this.idTra = idTra;
    }

    public Sana(Integer idTra, int iznos, Date datum) {
        this.idTra = idTra;
        this.iznos = iznos;
        this.datum = datum;
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

    public Integer getBrTransakcijeNa() {
        return brTransakcijeNa;
    }

    public void setBrTransakcijeNa(Integer brTransakcijeNa) {
        this.brTransakcijeNa = brTransakcijeNa;
    }

    public Integer getBrTransakcijeSa() {
        return brTransakcijeSa;
    }

    public void setBrTransakcijeSa(Integer brTransakcijeSa) {
        this.brTransakcijeSa = brTransakcijeSa;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Racun getRacun1() {
        return racun1;
    }

    public void setRacun1(Racun racun1) {
        this.racun1 = racun1;
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
