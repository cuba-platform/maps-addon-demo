package com.haulmont.demo.maps.web.order;

import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.GeometryStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;
import com.haulmont.demo.maps.entity.Salesperson;
import com.vividsolutions.jts.geom.Point;

import javax.inject.Inject;


@UiController("mapsdemo$Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {

    @Inject
    private TextField<Double> latitudeField;

    @Inject
    private TextField<Double> longitudeField;

    @Install(to = "map.orderLayer", subject = "styleProvider")
    private GeometryStyle orderLayerStyleProvider(Order order) {
        return new PointStyle(
                new FontPointIcon(CubaIcon.SHOPPING_BASKET)
                        .setIconPathFillColor("#0051d3"));

        // Example of using fully custom image as an icon.
//        return new PointStyle(
//                new ImagePointIcon("classpath:/com/haulmont/demo/maps/web/cuba_icon.png")
//                        .setIconSize(33, 33));
    }

    @Install(to = "map.salespersonLayer", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return new PointStyle(new FontPointIcon(CubaIcon.USER));
    }

    @Install(to = "map.salespersonLayer", subject = "popupContentProvider")
    private String salespersonLayerPopupContentProvider(Salesperson salesperson) {
        return String.format("<b>Salesperson:</b> %s", salesperson.getName());
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