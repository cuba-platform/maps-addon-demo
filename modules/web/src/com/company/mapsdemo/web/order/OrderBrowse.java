package com.company.mapsdemo.web.order;

import com.company.mapsdemo.entity.Order;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.HeatMapLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.HeatMapLayerStyle;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class OrderBrowse extends AbstractLookup {
    @Inject
    private GeoMap map;

    @Inject
    private GroupDatasource<Order, UUID> ordersDs;

    @Override
    public void init(Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);

        map.setCenter(GeometryUtils.createPoint(-99.755859, 39.164141));
        map.setZoomLevel(4);
    }

    @Override
    public void ready() {
        HeatMapLayer<Order> heatMapLayer = new HeatMapLayer<>();
        HeatMapLayerStyle heatMapLayerStyle = new HeatMapLayerStyle();
        heatMapLayerStyle.setMinOpacity(0.3);
        heatMapLayerStyle.setMaxZoom(7D);
        heatMapLayerStyle.setRadius(35);
        heatMapLayer.setStyle(heatMapLayerStyle);
        heatMapLayer.add(ordersDs.getItems());
        map.addLayer(heatMapLayer);
    }
}