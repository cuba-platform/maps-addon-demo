package com.haulmont.demo.maps.web.order;

import com.haulmont.addon.maps.web.gui.components.layer.VectorLayer;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;
import com.vaadin.ui.Notification;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;

@UiController("mapsdemo_OrderMapCluster")
@UiDescriptor("order-map-cluster.xml")
@LoadDataBeforeShow
public class OrderMapCluster extends Screen {

    @Inject
    private DataManager dataManager;

    @Install(to = "map.orders", subject = "styleProvider")
    private GeometryStyle orderLayerStyleProvider(Order order) {
        return new PointStyle(
                new FontPointIcon(CubaIcon.SHOPPING_BASKET)
                        .setIconPathFillColor("#0051d3"));
    }

    @Subscribe("map.orders")
    private void onOrderSelected(VectorLayer.GeoObjectSelectedEvent<Order> event) {
        Notification.show("Selected " + event.getItem());
    }

    @Subscribe("generateOrdersBtn")
    private void onGenerateClick(Button.ClickEvent event) {
        double lon = -99.755859;
        double lat = 39.1641412;
        for (int i = 0; i < 100; i++) {
            Order order = dataManager.create(Order.class);
            order.setLatitude(lat + RandomUtils.nextDouble(0, 10) - 5);
            order.setLongitude(lon + RandomUtils.nextDouble(0, 20) - 10);
            order.setAmount(RandomUtils.nextInt(0, 20));
            dataManager.commit(order);
        }
        getScreenData().loadAll();
    }

}