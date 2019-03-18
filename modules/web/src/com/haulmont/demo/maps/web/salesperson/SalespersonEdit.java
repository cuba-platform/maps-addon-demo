package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Salesperson;
import com.haulmont.demo.maps.entity.Territory;
import com.haulmont.demo.maps.web.territory.TerritoryDialogBrowse;

import javax.inject.Inject;

@UiController("mapsdemo$Salesperson.edit")
@UiDescriptor("salesperson-edit.xml")
@EditedEntityContainer("salespersonDc")
@LoadDataBeforeShow
public class SalespersonEdit extends StandardEditor<Salesperson> {

    @Inject
    private Notifications notifications;

    @Inject
    private ScreenBuilders screenBuilder;

    @Inject
    private PickerField<Territory> territoryField;

    @Install(to = "map.territoryLayer", subject = "styleProvider")
    private GeometryStyle territoryLayerStyleProvider(Territory territory) {
        return new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }

    @Install(to = "map.salespersonLayer", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return new PointStyle(new FontPointIcon(CubaIcon.USER)
                .setIconPathFillColor("#42a1f4")
                .setIconPathStrokeColor("#2c28ff")
                .setIconTextFillColor("white"));
    }

    @Subscribe("territoryField.lookup")
    private void onTerritoryFieldLookup(Action.ActionPerformedEvent event) {
        screenBuilder.lookup(territoryField)
                .withScreenClass(TerritoryDialogBrowse.class)
                .withLaunchMode(OpenMode.DIALOG)
                .build()
                .show();
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