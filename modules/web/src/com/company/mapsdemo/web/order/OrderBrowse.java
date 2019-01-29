package com.company.mapsdemo.web.order;

import com.company.mapsdemo.entity.Order;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.HeatMapOptions;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderBrowse extends AbstractLookup {
    @Inject
    private GeoMap map;

    @Inject
    private GroupDatasource<Order, UUID> ordersDs;

    @Override
    public void ready() {
        HeatMapOptions options = new HeatMapOptions();
        options.setMinOpacity(0.3);
        options.setMaxZoom(7D);
        options.setRadius(35);
        options.setMaximumIntensity(500D);

        Collection<Order> items = ordersDs.getItems();
        Map<Point, Double> intensityMap = items.stream().collect(
                Collectors.toMap(Order::getLocation,
                        order -> order.getAmount() != null ? order.getAmount() : 0));
        map.addHeatMap(intensityMap, options);
    }
}