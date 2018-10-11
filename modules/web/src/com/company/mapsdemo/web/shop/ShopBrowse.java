package com.company.mapsdemo.web.shop;

import com.company.mapsdemo.entity.Shop;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.Map;
import com.haulmont.addon.maps.web.gui.components.WebMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.vaadin.server.FontAwesome;
import com.vividsolutions.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;


public class ShopBrowse extends AbstractLookup {

    private Logger log = LoggerFactory.getLogger(WebMap.class);

    @Inject
    private Map map;

    @Inject
    private GroupDatasource<Shop, UUID> shopsDs;

    @Override
    public void init(java.util.Map<String, Object> params) {
        map.setCenter(GeometryUtils.getGeometryFactory().createPoint(new Coordinate(46.005163D, 51.532268D)));
        map.setZoomLevel(13);

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

        shopsDs.refresh();
        PointLayer<Shop> pointLayer = new PointLayer<>(shopsDs.getItems());

        PointStyle style = new PointStyle();
        style.setIcon(FontAwesome.SHOPPING_BAG);
        style.setIconPathFillColor("#ff0000");
        style.setIconPathStrokeColor("#000000");
        style.setIconTextFillColor("#ffffff");
        pointLayer.setStyle(style);

        map.addLayer(pointLayer);

        shopsDs.addCollectionChangeListener(e -> {
            switch (e.getOperation()) {
                case ADD:
                    pointLayer.addEntities(e.getItems());
                    break;
                case REMOVE:
                    for (Shop shop : e.getItems()) {
                        pointLayer.removeEntity(shop);
                    }
                    break;
                case UPDATE:
                    e.getItems().forEach(pointLayer::removeEntity);
                    pointLayer.addEntities(e.getItems());
                    break;
            }
        });
    }
}