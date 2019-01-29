package com.company.mapsdemo.web.order;

import com.company.mapsdemo.entity.Order;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.Layer;
import com.haulmont.addon.maps.web.gui.components.layer.style.ImagePointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractEditor;

import javax.inject.Inject;
import java.util.Map;

public class OrderEdit extends AbstractEditor<Order> {
    @Inject
    private GeoMap map;

    @Override
    public void init(Map<String, Object> params) {
        Layer orderLayer = map.getLayer("orderLayer");
        if (orderLayer != null) {
            ImagePointIcon icon = new ImagePointIcon("classpath:/com/company/mapsdemo/web/cuba_icon.png");
            icon.setIconSize(44, 44);
            orderLayer.setStyle(new PointStyle(icon));
        }
    }
}