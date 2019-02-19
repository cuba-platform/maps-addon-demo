package com.haulmont.demo.maps.web.order;

import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
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

    @Subscribe("showHeatMapBtn")
    private void onShowHeatMapBtnClick(Button.ClickEvent event) {
        screens.create(OrderHeatMap.class, OpenMode.DIALOG).show();
    }

}