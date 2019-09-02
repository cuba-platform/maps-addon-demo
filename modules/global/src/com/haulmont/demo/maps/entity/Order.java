package com.haulmont.demo.maps.entity;

import com.haulmont.addon.maps.gis.Geometry;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPointWKTConverter;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s %s|createTs,salesperson")
@Table(name = "MAPSDEMO_ORDER")
@Entity(name = "mapsdemo$Order")
public class Order extends StandardEntity {
    private static final long serialVersionUID = -749725998038408728L;

    @Column(name = "AMOUNT")
    protected Integer amount;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALESPERSON_ID")
    protected Salesperson salesperson;

    @MetaProperty(datatype = "GeoPoint")
    @Convert(converter = CubaPointWKTConverter.class)
    @Geometry
    @Column(name = "LOCATION")
    protected Point location;

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
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

    @MetaProperty(datatype = "geocoordinate", related = "location")
    public Double getLatitude() {
        if (location == null) return null;
        return location.getY();
    }

    @MetaProperty(datatype = "geocoordinate", related = "location")
    public Double getLongitude() {
        if (location == null) return null;
        return location.getX();
    }

    @MetaProperty
    public void setLatitude(Double latitude) {
        if (latitude == null) latitude = 0D;
        if (location == null) {
            location = GeometryUtils.createPoint(0, latitude);
            propertyChanged("location", null, location);
        } else {
            Point prevValue = location;
            location = GeometryUtils.createPoint(location.getX(), latitude);
            propertyChanged("location", prevValue, location);
        }
    }

    @MetaProperty
    public void setLongitude(Double longitude) {
        if (longitude == null) longitude = 0D;
        if (location == null) {
            location = GeometryUtils.createPoint(longitude, 0);
            propertyChanged("location", null, location);
        } else {
            Point prevValue = location;
            location = GeometryUtils.createPoint(longitude, location.getY());
            propertyChanged("location", prevValue, location);
        }
    }
}