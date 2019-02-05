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
        VectorLayer<Salesperson> salespersonLayer = ((VectorLayer<Salesperson>) map.getLayer("salespersonLayer"));
        if (salespersonLayer != null) {
            FontPointIcon markerIcon = new FontPointIcon(CubaIcon.ARROW_CIRCLE_O_DOWN);
            markerIcon.setIconPathFillColor("#42a1f4");
            markerIcon.setIconPathStrokeColor("#025ee8");
            markerIcon.setIconTextFillColor("white");
            salespersonLayer.setStyle(new PointStyle(markerIcon));

            salespersonLayer.setPopupContentProvider(Salesperson::getName);
        }
    }


}