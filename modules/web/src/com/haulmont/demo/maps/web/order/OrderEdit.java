package com.haulmont.demo.maps.web.order;

import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.style.ImagePointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;


@UiController("mapsdemo$Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {

    @Inject
    private GeoMap map;

    @Inject
    private TextField<Double> latitudeField;

    @Inject
    private TextField<Double> longitudeField;

    @Subscribe
    protected void onInit(InitEvent event) {
        Layer orderLayer = map.getLayer("orderLayer");
        if (orderLayer != null) {
            ImagePointIcon icon = new ImagePointIcon("classpath:/com/haulmont/demo/maps/web/cuba_icon.png");
            icon.setIconSize(44, 44);
            orderLayer.setStyle(new PointStyle(icon));
        }
    }

    @Subscribe(id = "orderDc", target = Target.DATA_CONTAINER)
    protected void onOrderDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Order> event) {
        if ("location".equals(event.getProperty())) {
            Point newValue = (Point) event.getValue();
            if (newValue == null) {
                longitudeField.setValue(null);
                latitudeField.setValue(null);
            } else {
                longitudeField.setValue(newValue.getX());
                latitudeField.setValue(newValue.getY());
            }
        }
    }





}