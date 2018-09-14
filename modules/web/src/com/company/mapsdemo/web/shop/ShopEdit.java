package com.company.mapsdemo.web.shop;

import com.haulmont.addon.maps.gis.CRS;
import com.haulmont.addon.maps.gis.Point;
import com.haulmont.addon.maps.web.gui.components.WebMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.WMSTileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.mapsdemo.entity.Shop;
import com.vaadin.server.FontAwesome;

import javax.inject.Inject;

public class ShopEdit extends AbstractEditor<Shop> {
    @Inject
    private WebMap map;

    private PointLayer<Shop> pointLayer;

    @Override
    protected void initNewItem(Shop item) {
        super.initNewItem(item);
        item.setLocation(new Point( 46.005163D, 51.532268D));
    }

    @Override
    protected void postInit() {
        map.setCenter(getItem().getGeoData());
        map.setZoomLevel(15.0);

        WMSTileLayer wmsTileLayer = new WMSTileLayer();
        wmsTileLayer.setUrl("http://129.206.228.72/cached/osm?");
        wmsTileLayer.setLayers("osm_auto:all");
        wmsTileLayer.setFormat("image/png");
        wmsTileLayer.setCrs(CRS.EPSG4326);
        wmsTileLayer.setVersion("1.3.0");
        map.addLayer(wmsTileLayer);

        pointLayer = new PointLayer<>(getItem());

        PointStyle style = new PointStyle();
        style.setIcon(FontAwesome.SHOPPING_BAG);
        style.setIconPathFillColor("#ff0000");
        style.setIconPathStrokeColor("#000000");
        style.setIconTextFillColor("#ffffff");
        pointLayer.setStyle(style);

        map.addLayer(pointLayer);
    }

    public void moveMarker() {
        pointLayer.removeEntity(getItem());
        getItem().setGeoData(new Point(46.011300D, 51.534404D));
        pointLayer.addEntity(getItem());
    }
}