package com.company.mapsdemo.web.screens;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.AbstractWindow;

import javax.inject.Inject;

public class Map extends AbstractWindow {

    @Inject
    private GeoMap map;

    @Override
    public void init(java.util.Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);

        map.setZoomLevel(3);
    }
}