package com.haulmont.demo.maps.web.territory;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Territory;

import javax.inject.Inject;

@UiController("mapsdemo$Territory.dialogbrowse")
@UiDescriptor("territory-dialog-browse.xml")
@DialogMode(forceDialog = true, width = "1200px")
@LookupComponent("table")
@LoadDataBeforeShow
public class TerritoryDialogBrowse extends MasterDetailScreen<Territory> {

    @Inject
    private GeoMap map;

    @Override
    protected void enableEditControls(boolean creating) {
        super.enableEditControls(creating);
        map.getLayer("territory").setEditable(true);
    }

    @Override
    protected void disableEditControls() {
        super.disableEditControls();
        map.getLayer("territory").setEditable(false);
    }

    @Install(to = "map.territory", subject = "styleProvider")
    private GeometryStyle territoryLayerStyleProvider(Territory territory) {
        return new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(2);
    }

}