package com.company.mapsdemo.web.territory;

import com.company.mapsdemo.entity.Territory;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.PolygonLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;

import javax.inject.Inject;
import java.util.Map;

public class TerritoryEdit extends AbstractEditor<Territory> {
    @Inject
    private GeoMap map;

    @Inject
    private Button removePolygonButton;

    private PolygonLayer<Territory> polygonLayer;

    @Override
    public void init(Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);
    }

    @Override
    protected void postInit() {
        polygonLayer = new PolygonLayer<>(getItem());
        polygonLayer.setEditable(true);

        map.addLayer(polygonLayer);
        if (getItem().getPolygon() == null) {
            initDrawingMode();
        } else {
            removePolygonButton.setEnabled(true);
            map.setCenter(getItem().getPolygon().getCentroid());
            map.setZoomLevel(6);
        }
    }

    private void initDrawingMode() {
        map.setCenter(GeometryUtils.createPoint(-99.755859,39.164141));
        map.setZoomLevel(4);
        map.addPolygonDrawnListener(event -> {
            getItem().setPolygon(event.getPolygon());
            polygonLayer.refresh();
            removePolygonButton.setEnabled(true);
        });
        map.startPolygon();
    }

    public void removePolygon() {
        getItem().setPolygon(null);
        initDrawingMode();
        polygonLayer.refresh();
        removePolygonButton.setEnabled(false);
    }

}