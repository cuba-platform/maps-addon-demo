package com.company.mapsdemo.entity;

import com.haulmont.addon.maps.gis.GeoField;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPolygonWKTConverter;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.vividsolutions.jts.geom.Polygon;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "MAPSDEMO_TERRITORY")
@Entity(name = "mapsdemo$Territory")
public class Territory extends StandardEntity {
    private static final long serialVersionUID = -1660135364445075710L;

    @Column(name = "NAME")
    protected String name;

    @Convert(converter = CubaPolygonWKTConverter.class)
    @GeoField
    @MetaProperty(datatype = "GeoPolygon")
    @Column(name = "POLYGON")
    protected Polygon polygon;

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Polygon getPolygon() {
        return polygon;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}