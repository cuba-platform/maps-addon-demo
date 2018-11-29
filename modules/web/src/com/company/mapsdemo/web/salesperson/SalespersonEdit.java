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
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class SalespersonEdit extends AbstractEditor<Salesperson> {
    @Inject
    private GeoMap map;

    @Named("fieldGroup.territory")
    private PickerField territoryField;

    @Inject
    private TextField xCoordinate;

    @Inject
    private TextField yCoordinate;

    @Inject
    private Button removeLocationButton;

    private PolygonLayer<Territory> territoryLayer = new PolygonLayer<>();

    private PointLayer<Salesperson> locationLayer = new PointLayer<>();

    @Override
    protected void initNewItem(Salesperson item) {
        map.setCenter(GeometryUtils.createPoint(-99.755859, 39.164141));
        map.setZoomLevel(4);
    }

    @Override
    public void init(Map<String, Object> params) {
        TileLayer tileLayer = new TileLayer();
        tileLayer.setUrl(AppContext.getProperty("maps.tileServerUrl"));
        tileLayer.setAttributionString(AppContext.getProperty("maps.attribution"));
        map.addLayer(tileLayer);

        PolygonStyle polygonStyle = new PolygonStyle();
        polygonStyle.setFillColor("#08a343");
        polygonStyle.setStrokeColor("#004912");
        polygonStyle.setFillOpacity(0.3);
        polygonStyle.setStrokeWeight(1);
        territoryLayer.setStyle(polygonStyle);
        map.addLayer(territoryLayer);

        FontMarkerIcon markerIcon = new FontMarkerIcon(CubaIcon.ARROW_CIRCLE_O_DOWN);
        markerIcon.setIconPathFillColor("#42a1f4");
        markerIcon.setIconPathStrokeColor("#2c28ff");
        markerIcon.setIconTextFillColor("white");

        locationLayer.setStyle(new PointStyle(markerIcon));
        locationLayer.setEditable(true);
        map.addLayer(locationLayer);

        addTerritoryFieldChangeListener();
        initMapClickListener();
    }

    @Override
    protected void postInit() {
        if (getItem().getLocation() != null) {
            map.setCenter(getItem().getLocation());
        } else {
            map.setCenter(GeometryUtils.createPoint(-99.755859, 39.164141));
        }
        map.setZoomLevel(4);

        locationLayer.add(getItem());
        removeLocationButton.setEnabled(getItem().getLocation() != null);
        initLocationChangedListener();
    }

    @Override
    protected boolean preCommit() {
        Territory territory = getItem().getTerritory();
        if (territory != null
                && territory.getPolygon() != null
                && getItem().getLocation() != null
                && !getItem().getLocation().within(territory.getPolygon())) {
            showNotification("Location should be within the specified territory.");
            return false;
        }
        return true;
    }

    private void addTerritoryFieldChangeListener() {
        territoryField.addValueChangeListener(e -> {
            Object oldValue = e.getPrevValue();
            if (oldValue != null) territoryLayer.remove(((Territory) oldValue));
            Object newValue = e.getValue();
            if (newValue != null) territoryLayer.add(((Territory) newValue));
            territoryLayer.refresh();
        });
    }

    private void initMapClickListener() {
        map.addMapClickListener(event -> {
            if (getItem().getLocation() == null) {
                getItem().setLocation(event.getPoint());
                locationLayer.refresh();
                removeLocationButton.setEnabled(true);
            }
        });
    }

    private void initLocationChangedListener() {
        getItem().addPropertyChangeListener(e -> {
            if ("location".equals(e.getProperty())) {
                Point newValue = (Point) e.getValue();
                if (newValue == null) {
                    xCoordinate.setValue(null);
                    yCoordinate.setValue(null);
                } else {
                    removeLocationButton.setEnabled(true);
                    xCoordinate.setValue(newValue.getX());
                    yCoordinate.setValue(newValue.getY());
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
        if (xCoordinate.getValue() == null || yCoordinate.getValue() == null) {
            showNotification("Coordinate fields shouldn't be empty");
            return;
        }
        Point newLocation = GeometryUtils.createPoint(xCoordinate.getValue(), yCoordinate.getValue());
        map.setCenter(newLocation);
        getItem().setLocation(newLocation);
        locationLayer.refresh();
    }
}