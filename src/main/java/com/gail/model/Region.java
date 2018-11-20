package com.gail.model;


import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity 
@Table(name = "region")
public class Region {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name = "region_id", nullable = false)
    private BigInteger regionId;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @Column(name = "region_code", nullable = false)
    private String regionCode;

    @Column (name = "status", nullable = false)
    private int status;

    @Column (name = "created_by", nullable = false)
    private BigInteger createdBy;

    @Column (name = "created_date", nullable = false)
    private Date createDate;

    @Column (name = "updated_date", nullable = false)
    private Date updatedDate;

    @Column (name = "updated_by", nullable = false)
    private BigInteger updatedBy;



    public BigInteger getRegionId() {
        return regionId;
    }

    public void setRegionId(BigInteger regionId) {
        this.regionId = regionId;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

  

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigInteger getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigInteger updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "RegionModel [regionId=" + regionId + ", regionName="
                + regionName + ", status=" + status + ", createdBy="
                + createdBy + ", createDate=" + createDate + ", updatedDate="
                + updatedDate + ", updatedBy=" + updatedBy + "]";
    }




}
