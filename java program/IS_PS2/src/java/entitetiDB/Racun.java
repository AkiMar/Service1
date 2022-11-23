/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitetiDB;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ma180130
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdRac", query = "SELECT r FROM Racun r WHERE r.idRac = :idRac"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvoljenMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljenMinus = :dozvoljenMinus"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDatumOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumOtvaranja = :datumOtvaranja"),
    @NamedQuery(name = "Racun.findByBrTransakcija", query = "SELECT r FROM Racun r WHERE r.brTransakcija = :brTransakcija")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdRac")
    private Integer idRac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private int stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvoljenMinus")
    private int dozvoljenMinus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrTransakcija")
    private int brTransakcija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRac")
    private List<Isplata> isplataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "naRac")
    private List<Sana> sanaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saRac")
    private List<Sana> sanaList1;
    @JoinColumn(name = "IdKom", referencedColumnName = "IdKom")
    @ManyToOne(optional = false)
    private Komitent idKom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRac")
    private List<Uplata> uplataList;

    public Racun() {
    }

    public Racun(Integer idRac) {
        this.idRac = idRac;
    }

    public Racun(Integer idRac, int stanje, int dozvoljenMinus, String status, Date datumOtvaranja, int brTransakcija) {
        this.idRac = idRac;
        this.stanje = stanje;
        this.dozvoljenMinus = dozvoljenMinus;
        this.status = status;
        this.datumOtvaranja = datumOtvaranja;
        this.brTransakcija = brTransakcija;
    }

    public Integer getIdRac() {
        return idRac;
    }

    public void setIdRac(Integer idRac) {
        this.idRac = idRac;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    public int getDozvoljenMinus() {
        return dozvoljenMinus;
    }

    public void setDozvoljenMinus(int dozvoljenMinus) {
        this.dozvoljenMinus = dozvoljenMinus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(Date datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public int getBrTransakcija() {
        return brTransakcija;
    }

    public void setBrTransakcija(int brTransakcija) {
        this.brTransakcija = brTransakcija;
    }

    @XmlTransient
    public List<Isplata> getIsplataList() {
        return isplataList;
    }

    public void setIsplataList(List<Isplata> isplataList) {
        this.isplataList = isplataList;
    }

    @XmlTransient
    public List<Sana> getSanaList() {
        return sanaList;
    }

    public void setSanaList(List<Sana> sanaList) {
        this.sanaList = sanaList;
    }

    @XmlTransient
    public List<Sana> getSanaList1() {
        return sanaList1;
    }

    public void setSanaList1(List<Sana> sanaList1) {
        this.sanaList1 = sanaList1;
    }

    public Komitent getIdKom() {
        return idKom;
    }

    public void setIdKom(Komitent idKom) {
        this.idKom = idKom;
    }

    @XmlTransient
    public List<Uplata> getUplataList() {
        return uplataList;
    }

    public void setUplataList(List<Uplata> uplataList) {
        this.uplataList = uplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRac != null ? idRac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idRac == null && other.idRac != null) || (this.idRac != null && !this.idRac.equals(other.idRac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitetiDB.Racun[ idRac=" + idRac + " ]";
    }
    
}
