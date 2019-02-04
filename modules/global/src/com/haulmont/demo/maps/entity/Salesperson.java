package com.haulmont.demo.maps.entity;

import com.haulmont.demo.maps.datatype.GeoCoordinateDatatype;
import com.haulmont.addon.maps.gis.Geometry;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPointWKTConverter;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;

@NamePattern("%s|name")
@Table(name = "MAPSDEMO_SALESPERSON")
@Entity(name = "mapsdemo$Salesperson")
public class Salesperson extends StandardEntity {
    private static final long serialVersionUID = -149427681303945265L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "PHONE")
    protected String phone;

    @MetaProperty(datatype = "GeoPoint")
    @Convert(converter = CubaPointWKTConverter.class)
    @Column(name = "LOCATION")
    @Geometry
    protected Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "TERRITORY_ID")
    protected Territory territory;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    @MetaProperty(datatype = GeoCoordinateDatatype.NAME)
    public Double getLatitude() {
        if (location == null) return null;
        return location.getY();
    }

    @MetaProperty(datatype = GeoCoordinateDatatype.NAME)
    public Double getLongitude() {
        if (location == null) return null;
        return location.getX();
    }

    @MetaProperty
    public void setLatitude(Double latitude) {
        if (latitude == null) latitude = 0D;
        if (location == null) {
            location = GeometryUtils.createPoint(0, latitude);
        } else {
            location = GeometryUtils.createPoint(location.getX(), latitude);
        }
    }

    @MetaProperty
    public void setLongitude(Double longitude) {
        if (longitude == null) longitude = 0D;
        if (location == null) {
            location = GeometryUtils.createPoint(0, longitude);
        } else {
            location = GeometryUtils.createPoint(longitude, location.getY());
        }
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Territory getTerritory() {
        return territory;
    }


}