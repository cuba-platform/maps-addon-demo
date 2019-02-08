package com.haulmont.demo.maps.web;

import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.CanvasLayer;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.vaadin.ui.Notification;

import javax.inject.Inject;


@UiController("mapsdemo_Canvas")
@UiDescriptor("canvas.xml")
public class Canvas extends Screen {
    @Inject
    private GeoMap map;

    private CanvasLayer canvasLayer;

    @Subscribe
    protected void onInit(InitEvent event) {
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
        style.setFillColor("#08a343")
                .setStrokeColor("#004912");
        canvasLayer.drawPolygon(polygon -> polygon.setStyle(style));
    }

}