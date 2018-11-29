package com.company.mapsdemo.entity;

import com.haulmont.addon.maps.gis.GeoField;
import com.haulmont.addon.maps.gis.GeoObject;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPointWKTConverter;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s %s|createTs,salesperson")
@Table(name = "MAPSDEMO_ORDER")
@Entity(name = "mapsdemo$Order")
public class Order extends StandardEntity implements GeoObject {
    private static final long serialVersionUID = -749725998038408728L;

    @Column(name = "AMOUNT")
    protected Double amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALESPERSON_ID")
    protected Salesperson salesperson;

    @MetaProperty(datatype = "GeoPoint")
    @Convert(converter = CubaPointWKTConverter.class)
    @GeoField
    @Column(name = "LOCATION")
    protected Point location;

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setSalesperson(Salesperson salesperson) {
        this.salesperson = salesperson;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }


}