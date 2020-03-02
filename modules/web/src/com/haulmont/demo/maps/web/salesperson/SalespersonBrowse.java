package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.TooltipOptions;
import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
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

    private TooltipOptions tooltipOptions;

    private VectorLayer<Salesperson> salespersonLayer;

    @Subscribe
    public void onInit(InitEvent event) {
        salespersonLayer = map.getLayer("salespersonLayer");
        tooltipOptions = new TooltipOptions();
        tooltipOptions.setDirection("auto");
        tooltipOptions.setPermanent(true);
        tooltipOptions.setInteractive(true);
        salespersonLayer.setTooltipOptions(tooltipOptions);
    }

    @Install(to = "map.salespersonLayer", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return new PointStyle(
                new FontPointIcon(CubaIcon.USER)
                        .setIconPathFillColor("#42a1f4")
                        .setIconPathStrokeColor("#025ee8")
                        .setIconTextFillColor("white"));
    }

    @Install(to = "map.salespersonLayer", subject = "popupContentProvider")
    private String salespersonLayerPopupContentProvider(Salesperson salesperson) {
        return salesperson.getName();
    }


    @Install(to = "map.salespersonLayer", subject = "tooltipContentProvider")
    private String salespersonLayerTooltipContentProvider(Salesperson salesperson) {
        return salesperson.getName();
    }

}