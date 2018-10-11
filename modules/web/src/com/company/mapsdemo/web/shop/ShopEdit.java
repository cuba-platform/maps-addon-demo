package com.company.mapsdemo.web.shop;

import com.company.mapsdemo.entity.Shop;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.WebMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.vaadin.server.FontAwesome;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;

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
        item.setLocation(GeometryUtils.getGeometryFactory().createPoint(new Coordinate(x.getValue(), y.getValue())));
    }

    @Override
    protected void postInit() {
        map.setCenter(getItem().getLocation());
        map.setZoomLevel(15.1);

        x.setValue(getItem().getLocation().getCoordinate().x);
        y.setValue(getItem().getLocation().getCoordinate().y);

//        WMSTileLayer wmsTileLayer = new WMSTileLayer();
//        wmsTileLayer.setUrl("http://129.206.228.72/cached/osm?");
//        wmsTileLayer.setLayers("osm_auto:all");
//        wmsTileLayer.setFormat("image/png");
//        wmsTileLayer.setAttributionString("Provided by http://www.osm-wms.de");
//        map.addLayer(wmsTileLayer);

        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl("https://maps.omniscale.net/v2/maps-demo-60e580b0/style.default/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("Provided by omniscale.com");
        map.addLayer(tileLayer);

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
        Point newLocation = GeometryUtils.getGeometryFactory().createPoint(new Coordinate(x.getValue(), y.getValue()));
        map.setCenter(newLocation);
        getItem().setLocation(newLocation);
        pointLayer.refresh();
    }
}