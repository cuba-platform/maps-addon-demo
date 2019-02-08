package com.haulmont.demo.maps.web.order;

import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Order;

import javax.inject.Inject;


@UiController("mapsdemo$Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {
    @Inject
    private Screens screens;

    @Inject
    private GroupTable<Order> ordersTable;

    public void showHeatMap() {
        screens.create(OrderHeatMap.class, OpenMode.DIALOG).show();
    }

    @Subscribe("ordersTable")
    protected void onOrdersTableSelection(Table.SelectionEvent<Order> event) {

    }
    
    
}