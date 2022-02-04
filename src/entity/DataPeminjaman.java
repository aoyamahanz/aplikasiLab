/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Herman Patria
 */
@Entity
@Table(name = "data_peminjaman")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataPeminjaman.findAll", query = "SELECT d FROM DataPeminjaman d")
    , @NamedQuery(name = "DataPeminjaman.findByKdPeminjaman", query = "SELECT d FROM DataPeminjaman d WHERE d.kdPeminjaman = :kdPeminjaman")
    , @NamedQuery(name = "DataPeminjaman.findByTglPeminjaman", query = "SELECT d FROM DataPeminjaman d WHERE d.tglPeminjaman = :tglPeminjaman")})
public class DataPeminjaman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kd_peminjaman")
    private Integer kdPeminjaman;
    @Basic(optional = false)
    @Column(name = "tgl_peminjaman")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tglPeminjaman;

    public DataPeminjaman() {
    }

    public DataPeminjaman(Integer kdPeminjaman) {
        this.kdPeminjaman = kdPeminjaman;
    }

    public DataPeminjaman(Integer kdPeminjaman, Date tglPeminjaman) {
        this.kdPeminjaman = kdPeminjaman;
        this.tglPeminjaman = tglPeminjaman;
    }

    public Integer getKdPeminjaman() {
        return kdPeminjaman;
    }

    public void setKdPeminjaman(Integer kdPeminjaman) {
        this.kdPeminjaman = kdPeminjaman;
    }

    public Date getTglPeminjaman() {
        return tglPeminjaman;
    }

    public void setTglPeminjaman(Date tglPeminjaman) {
        this.tglPeminjaman = tglPeminjaman;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdPeminjaman != null ? kdPeminjaman.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPeminjaman)) {
            return false;
        }
        DataPeminjaman other = (DataPeminjaman) object;
        if ((this.kdPeminjaman == null && other.kdPeminjaman != null) || (this.kdPeminjaman != null && !this.kdPeminjaman.equals(other.kdPeminjaman))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DataPeminjaman[ kdPeminjaman=" + kdPeminjaman + " ]";
    }
    
}
