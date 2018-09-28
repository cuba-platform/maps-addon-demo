package com.company.mapsdemo.entity;

import com.haulmont.addon.maps.gis.GeoObject;
import com.haulmont.addon.maps.gis.Point;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPointWKTConverter;
import com.haulmont.addon.maps.gis.datatypes.PointDatatype;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "MAPSDEMO_SHOP")
@Entity(name = "mapsdemo$Shop")
public class Shop extends StandardEntity implements GeoObject<Point> {
    private static final long serialVersionUID = -7246131885124785043L;

    @Column(name = "NAME")
    protected String name;

    @MetaProperty(datatype = PointDatatype.NAME)
    @Column(name = "LOCATION")
    @Convert(converter = CubaPointWKTConverter.class)
    protected Point location;

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public Point getGeoData() {
        return getLocation();
    }
}