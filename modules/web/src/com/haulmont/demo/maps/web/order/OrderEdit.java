package com.haulmont.demo.maps.web.order;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.style.ImagePointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;

import javax.inject.Inject;


@UiController("mapsdemo$Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {

    @Inject
    private GeoMap map;

    @Subscribe
    protected void onInit(InitEvent event) {
        Layer orderLayer = map.getLayer("orderLayer");
        if (orderLayer != null) {
            ImagePointIcon icon = new ImagePointIcon("classpath:/com/haulmont/demo/maps/web/cuba_icon.png");
            icon.setIconSize(44, 44);
            orderLayer.setStyle(new PointStyle(icon));
        }
    }

}