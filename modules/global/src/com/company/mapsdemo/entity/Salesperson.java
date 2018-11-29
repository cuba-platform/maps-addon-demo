package com.company.mapsdemo.entity;

import com.haulmont.addon.maps.gis.GeoField;
import com.haulmont.addon.maps.gis.GeoObject;
import com.haulmont.addon.maps.gis.converters.wkt.CubaPointWKTConverter;
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
public class Salesperson extends StandardEntity implements GeoObject {
    private static final long serialVersionUID = -149427681303945265L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "PHONE")
    protected String phone;

    @MetaProperty(datatype = "GeoPoint")
    @Convert(converter = CubaPointWKTConverter.class)
    @Column(name = "LOCATION")
    @GeoField
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

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Territory getTerritory() {
        return territory;
    }


}