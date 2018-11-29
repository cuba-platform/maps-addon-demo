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

import javax.inject.Inject;
import java.util.Map;

public class OrderEdit extends AbstractEditor<Order> {
    @Inject
    private GeoMap map;

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

    public void removeLocation() {
        getItem().setLocation(null);
        locationLayer.refresh();
        removeLocationButton.setEnabled(false);
    }
}