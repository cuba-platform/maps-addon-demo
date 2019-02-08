package com.haulmont.demo.maps.web.order;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.HeatMapOptions;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;


@UiController("mapsdemo$Order.heatmap")
@UiDescriptor("order-heatmap.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderHeatMap extends StandardLookup<Order> {
    @Inject
    private CollectionContainer<Order> ordersDc;

    @Inject
    private GeoMap map;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        HeatMapOptions options = new HeatMapOptions()
                .setMinOpacity(0.3)
                .setMaxZoom(7D)
                .setRadius(35)
                .setMaximumIntensity(500D);

        Map<Point, Double> pointIntensityMap = ordersDc.getItems().stream().collect(
                Collectors.toMap(Order::getLocation,
                        order -> order.getAmount() != null ? order.getAmount() : 0));

        map.addHeatMap(pointIntensityMap, options);
    }
    
    


}