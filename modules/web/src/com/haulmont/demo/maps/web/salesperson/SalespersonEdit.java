package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Salesperson;
import com.haulmont.demo.maps.entity.Territory;

import javax.inject.Inject;

@UiController("mapsdemo$Salesperson.edit")
@UiDescriptor("salesperson-edit.xml")
@EditedEntityContainer("salespersonDc")
@LoadDataBeforeShow
public class SalespersonEdit extends StandardEditor<Salesperson> {

    @Inject
    private Notifications notifications;

    @Inject
    private GeoMap map;

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
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

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        Territory territory = getEditedEntity().getTerritory();
        if (territory != null
                && territory.getPolygon() != null
                && getEditedEntity().getLocation() != null
                && !getEditedEntity().getLocation().within(territory.getPolygon())) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Location should be within the specified territory")
                    .show();
            event.preventCommit();
        }
    }

}