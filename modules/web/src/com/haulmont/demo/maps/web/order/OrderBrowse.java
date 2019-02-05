package com.haulmont.demo.maps.web.order;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;


@UiController("mapsdemo$Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {
}