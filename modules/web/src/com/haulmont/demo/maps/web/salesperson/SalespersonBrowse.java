package com.haulmont.demo.maps.web.salesperson;

import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Salesperson;

@UiController("mapsdemo$Salesperson.browse")
@UiDescriptor("salesperson-browse.xml")
@LookupComponent("salespersonsTable")
@LoadDataBeforeShow
public class SalespersonBrowse extends StandardLookup<Salesperson> {

    @Install(to = "map.salespersonLayer", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return new PointStyle(
                new FontPointIcon(CubaIcon.ARROW_CIRCLE_O_DOWN)
                        .setIconPathFillColor("#42a1f4")
                        .setIconPathStrokeColor("#025ee8")
                        .setIconTextFillColor("white"));
    }

    @Install(to = "map.salespersonLayer", subject = "popupContentProvider")
    private String salespersonLayerPopupContentProvider(Salesperson salesperson) {
        return salesperson.getName();
    }

}