package com.company.mapsdemo.web.salesperson;

import com.company.mapsdemo.entity.Salesperson;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.icons.CubaIcon;

import javax.inject.Inject;
import java.util.Map;

public class SalespersonBrowse extends AbstractLookup {
    @Inject
    private GeoMap map;

    @Override
    public void init(Map<String, Object> params) {
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