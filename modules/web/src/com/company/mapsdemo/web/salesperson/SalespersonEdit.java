package com.company.mapsdemo.web.salesperson;

import com.company.mapsdemo.entity.Salesperson;
import com.company.mapsdemo.entity.Territory;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.vaadin.ui.Notification;

import javax.inject.Inject;
import javax.inject.Named;

public class SalespersonEdit extends AbstractEditor<Salesperson> {

    @Inject
    private Notifications notifications;

    @Inject
    private GeoMap map;

    @Named("map.salespersonLayer")
    private VectorLayer<Salesperson> salespersonLayer;

    @Override
    protected void postInit() {
        Notification.show(salespersonLayer + "");

        Layer territoryLayer = map.getLayer("territoryLayer");
        if (territoryLayer != null) {
            PolygonStyle polygonStyle = new PolygonStyle();
            polygonStyle.setFillColor("#08a343");
            polygonStyle.setStrokeColor("#004912");
            polygonStyle.setFillOpacity(0.3);
            polygonStyle.setStrokeWeight(1);
            territoryLayer.setStyle(polygonStyle);
        }

        Layer locationLayer = map.getLayer("salespersonLayer");
        if (locationLayer != null) {
            FontPointIcon markerIcon = new FontPointIcon(CubaIcon.ARROW_CIRCLE_O_DOWN);
            markerIcon.setIconPathFillColor("#42a1f4");
            markerIcon.setIconPathStrokeColor("#2c28ff");
            markerIcon.setIconTextFillColor("white");
            locationLayer.setStyle(new PointStyle(markerIcon));
        }
    }

    @Override
    protected boolean preCommit() {
        Territory territory = getItem().getTerritory();
        if (territory != null
                && territory.getPolygon() != null
                && getItem().getLocation() != null
                && !getItem().getLocation().within(territory.getPolygon())) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Location should be within the specified territory")
                    .show();
            return false;
        }
        return true;
    }

    public void removeLocation() {
        getItem().setLocation(null);
    }
}