package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
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
        VectorLayer<Territory> territoryLayer = map.getLayer("territoryLayer");
        territoryLayer.setStyle(new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(1));

        VectorLayer<Salesperson> locationLayer = map.getLayer("salespersonLayer");
        locationLayer.setStyle(new PointStyle(new FontPointIcon(CubaIcon.ARROW_CIRCLE_O_DOWN)
                .setIconPathFillColor("#42a1f4")
                .setIconPathStrokeColor("#2c28ff")
                .setIconTextFillColor("white")));
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    protected void onPreCommit(DataContext.PreCommitEvent event) {
        Territory territory = getEditedEntity().getTerritory();
        if (territory != null
                && territory.getPolygon() != null
                && getEditedEntity().getLocation() != null
                && !getEditedEntity().getLocation().within(territory.getPolygon())) {
            event.preventCommit();
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Location should be within the specified territory")
                    .show();
        }
    }


}