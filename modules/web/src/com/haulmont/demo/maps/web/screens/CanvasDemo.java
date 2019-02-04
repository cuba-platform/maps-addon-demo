package com.haulmont.demo.maps.web.screens;

import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.CanvasLayer;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.PopupWindowOptions;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.vaadin.ui.Notification;

import javax.inject.Inject;
import java.util.Map;
import java.util.function.Function;

public class CanvasDemo extends AbstractWindow {
    @Inject
    private GeoMap map;

    private CanvasLayer canvasLayer;

    @Override
    public void init(Map<String, Object> params) {
        canvasLayer = map.getCanvas();
        map.selectCanvas();

        CanvasLayer.Point location = canvasLayer.addPoint(GeometryUtils.createPoint(50, 50));
        location.setStyle(new PointStyle(
                new FontPointIcon(CubaIcon.CAB)
                        .setIconPathFillColor("#f4d142")
                        .setIconTextFillColor("black")
                        .setIconPathStrokeColor("black")))
                .setPopupContent("Taxi location")
                .setEditable(true)
                .addRightClickListener(e -> Notification.show("Right click"));
    }

    public void drawPoint() {
        canvasLayer.drawPoint(point -> {
        });
    }

    public void drawPolyline() {
        canvasLayer.drawPolyline(polyline -> Notification.show("Drawn polyline"));
    }

    public void drawPolygon() {
        PolygonStyle style = new PolygonStyle();
        style.setFillColor("indigo");
        canvasLayer.drawPolygon(polygon -> polygon.setStyle(style));
    }

}