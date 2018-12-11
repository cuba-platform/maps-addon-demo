package com.company.mapsdemo.web.order;

import com.company.mapsdemo.entity.Order;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.ImageMarkerIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class OrderEdit extends AbstractEditor<Order> {
    @Inject
    private GeoMap map;

    @Named("fieldGroup.latitude")
    private TextField latitudeField;

    @Named("fieldGroup.longitude")
    private TextField longitudeField;

    @Inject
    private Button removeLocationButton;

    private PointLayer<Order> locationLayer = new PointLayer<>();

    @Override
    public void init(Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);

        ImageMarkerIcon icon = new ImageMarkerIcon("classpath:/com/company/mapsdemo/web/cuba_icon.png");
        icon.setIconSize(44,44);
        locationLayer.setStyle(new PointStyle(icon));
        locationLayer.setEditable(true);
        map.addLayer(locationLayer);

        map.setCenter(GeometryUtils.createPoint(-99.755859, 39.164141));
        map.setZoomLevel(4);
        initMapClickListener();
    }

    @Override
    public void ready() {
        locationLayer.add(getItem());
        removeLocationButton.setEnabled(getItem().getLocation() != null);
        initLocationChangedListener();
    }

    private void initMapClickListener() {
        map.addMapClickListener(event -> {
            if (getItem().getLocation() == null) {
                getItem().setLocation(event.getPoint());
                removeLocationButton.setEnabled(true);
                locationLayer.refresh();
            }
        });
    }

    private void initLocationChangedListener() {
        getItem().addPropertyChangeListener(e -> {
            if ("location".equals(e.getProperty())) {
                Point newValue = (Point) e.getValue();
                if (newValue == null) {
                    longitudeField.setValue(null);
                    latitudeField.setValue(null);
                } else {
                    removeLocationButton.setEnabled(true);
                    longitudeField.setValue(newValue.getX());
                    latitudeField.setValue(newValue.getY());
                }
            }
        });
    }

    public void removeLocation() {
        getItem().setLocation(null);
        locationLayer.refresh();
        removeLocationButton.setEnabled(false);
    }

    public void applyLocation() {
        if (longitudeField.getValue() == null || latitudeField.getValue() == null) {
            showNotification("Coordinate fields should be filled");
            return;
        }
        if (getItem().getLocation() != null) {
            map.setCenter(getItem().getLocation());
        }
        locationLayer.refresh();
    }
}