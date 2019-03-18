package com.haulmont.demo.maps.web;

import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;
import com.haulmont.demo.maps.entity.Salesperson;
import com.haulmont.demo.maps.entity.Territory;

import javax.inject.Inject;
import java.util.List;

@UiController("mapsdemo_Map")
@UiDescriptor("map.xml")
@LoadDataBeforeShow
public class Map extends Screen {
    @Inject
    private DataManager dataManager;

    @Install(to = "map.salespersons", subject = "popupContentProvider")
    public String setSalespersonsPopupContentProvider(Salesperson salesperson) {
        List<Order> salespersonOrders = dataManager.load(Order.class)
                .query("select o from mapsdemo$Order o where o.salesperson = :salesperson")
                .parameter("salesperson", salesperson)
                .list();
        return String.format(
                "<b>Name: </b> %s " +
                        "<p>" +
                        "<b>Phone: </b> %s" +
                        "<p>" +
                        "<b>Orders count: </b> %d",
                salesperson.getName(),
                salesperson.getPhone(),
                salespersonOrders.size());
    }

    @Install(to = "map.salespersons", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return new PointStyle(new FontPointIcon(CubaIcon.USER)
                .setIconPathFillColor("#42a1f4")
                .setIconPathStrokeColor("#2c28ff")
                .setIconTextFillColor("white"));
    }

    @Install(to = "map.territories", subject = "styleProvider")
    private GeometryStyle territoryLayerStyleProvider(Territory territory) {
        return new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }

    @Install(to = "map.territories", subject = "popupContentProvider")
    private String territoryLayerPopupContentProvider(Territory territory) {
        return territory.getName();
    }

}