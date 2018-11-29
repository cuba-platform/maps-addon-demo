package com.company.mapsdemo.web.salesperson;

import com.company.mapsdemo.entity.Salesperson;
import com.company.mapsdemo.entity.Territory;
import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.PointLayer;
import com.haulmont.addon.maps.web.gui.components.layer.PolygonLayer;
import com.haulmont.addon.maps.web.gui.components.layer.TileLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontMarkerIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.icons.CubaIcon;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class SalespersonBrowse extends AbstractLookup {
    @Inject
    private GroupDatasource<Salesperson, UUID> salespersonsDs;

    @Inject
    private GroupTable<Salesperson> salespersonsTable;

    @Inject
    private GeoMap map;

    private PolygonLayer<Territory> territoriesLayer = new PolygonLayer<>();

    private PointLayer<Salesperson> salespersonLayer = new PointLayer<>();

    @Override
    public void init(Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);

        // Salesperson layer style
        FontMarkerIcon markerIcon = new FontMarkerIcon(CubaIcon.ARROW_CIRCLE_O_DOWN);
        markerIcon.setIconPathFillColor("#42a1f4");
        markerIcon.setIconPathStrokeColor("#025ee8");
        markerIcon.setIconTextFillColor("white");
        salespersonLayer.setStyle(new PointStyle(markerIcon));
        salespersonLayer.setPopupContentProvider(Salesperson::getName);
        map.addLayer(salespersonLayer);

        // Territories layer style
        PolygonStyle polygonStyle = new PolygonStyle();
        polygonStyle.setStrokeColor("#ff0000");
        polygonStyle.setFillColor("#ff0000");
        polygonStyle.setStrokeWeight(1);
        polygonStyle.setFillOpacity(0.1);
        territoriesLayer.setStyle(polygonStyle);

        map.setCenter(GeometryUtils.createPoint(-99.755859, 39.164141));
        map.setZoomLevel(4);
        map.addMarkerClickListener(event -> salespersonsTable.setSelected((Salesperson) event.getEntity()));
    }

    @Override
    public void ready() {
        salespersonLayer.add(salespersonsDs.getItems());

        salespersonsDs.addCollectionChangeListener(e -> {
            switch (e.getOperation()) {
                case ADD:
                    salespersonLayer.add(e.getItems());
                    break;
                case REMOVE:
                    for (Salesperson salesperson : e.getItems()) {
                        salespersonLayer.remove(salesperson);
                    }
                    break;
                case UPDATE:
                    e.getItems().forEach(salespersonLayer::remove);
                    salespersonLayer.add(e.getItems());
                    break;
            }
        });
    }
}