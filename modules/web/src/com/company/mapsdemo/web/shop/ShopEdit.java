package com.company.mapsdemo.web.shop;

import com.company.mapsdemo.entity.Shop;
import com.haulmont.addon.maps.gis.CRS;
import com.haulmont.addon.maps.gis.Point;
import com.haulmont.addon.maps.web.gui.components.WebMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.WMSTileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;

import javax.inject.Inject;
import java.io.File;
import java.util.Objects;

public class ShopEdit extends AbstractEditor<Shop> {
    @Inject
    private WebMap map;

    @Inject
    private TextField x;

    @Inject
    private TextField y;

    @Inject
    private Button button;

    private PointLayer<Shop> pointLayer;

    @Override
    protected void initNewItem(Shop item) {
        super.initNewItem(item);
        x.setValue(46.005163D);
        y.setValue(51.532268D);
        item.setLocation(new Point( x.getValue(), y.getValue()));
    }

    @Override
    protected void postInit() {
        map.setCenter(getItem().getGeoData());
        map.setZoomLevel(15.1);

        x.setValue(getItem().getLocation().getGeometry().getCoordinate().x);
        y.setValue(getItem().getLocation().getGeometry().getCoordinate().y);

        WMSTileLayer wmsTileLayer = new WMSTileLayer();
        wmsTileLayer.setUrl("http://129.206.228.72/cached/osm?");
        wmsTileLayer.setLayers("osm_auto:all");
        wmsTileLayer.setFormat("image/png");
        wmsTileLayer.setAttributionString("Provided by http://www.osm-wms.de");
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

    public void applyLocation() {
        Point newLocation = new Point(x.getValue(), y.getValue());
        map.setCenter(newLocation);
        getItem().setLocation(newLocation);
        pointLayer.refresh();
    }
}