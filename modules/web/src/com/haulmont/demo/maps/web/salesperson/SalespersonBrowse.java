package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Salesperson;

import javax.inject.Inject;

@UiController("mapsdemo$Salesperson.browse")
@UiDescriptor("salesperson-browse.xml")
@LookupComponent("salespersonsTable")
@LoadDataBeforeShow
public class SalespersonBrowse extends StandardLookup<Salesperson> {
    @Inject
    private GeoMap map;

    @Subscribe
    protected void onInit(InitEvent event) {
        VectorLayer<Salesperson> salespersonLayer = map.getLayer("salespersonLayer");

        PointStyle locationStyle = new PointStyle(
                new FontPointIcon(CubaIcon.ARROW_CIRCLE_O_DOWN)
                        .setIconPathFillColor("#42a1f4")
                        .setIconPathStrokeColor("#025ee8")
                        .setIconTextFillColor("white"));
        salespersonLayer.setStyleProvider(salesperson -> locationStyle);

        salespersonLayer.setPopupContentProvider(Salesperson::getName);
    }


}