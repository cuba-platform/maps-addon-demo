package com.haulmont.demo.maps.web;

import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.CanvasLayer;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.addon.maps.web.gui.components.layer.style.PolygonStyle;
import com.haulmont.cuba.gui.components.Button;
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

        CanvasLayer.Point location = canvasLayer.addPoint(GeometryUtils.createPoint(50, 50));
        location.setStyle(new PointStyle(
                new FontPointIcon(CubaIcon.CAB)
                        .setIconPathFillColor("#f4d142")
                        .setIconTextFillColor("black")
                        .setIconPathStrokeColor("black")))
                .setPopupContent("Taxi location")
                .setEditable(true);
    }

    @Subscribe("map.canvas")
    private void onCanvasPointClick(CanvasLayer.Point.RightClickEvent clickEvent) {
        Notification.show("Right click");
    }

    @Subscribe("drawPoint")
    private void onDrawPointClick(Button.ClickEvent event) {
        canvasLayer.drawPoint(point -> {
        });
    }

    @Subscribe("drawPolyline")
    private void onDrawPolylineClick(Button.ClickEvent event) {
        canvasLayer.drawPolyline(polyline -> Notification.show("Drawn polyline"));
    }

    @Subscribe("drawPolygon")
    private void onDrawPolygonClick(Button.ClickEvent event) {
        PolygonStyle style = new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912");
        canvasLayer.drawPolygon(polygon -> polygon.setStyle(style));
    }


}