package com.company.mapsdemo.web.screens;

import com.haulmont.addon.maps.gis.utils.GeometryUtils;
import com.haulmont.addon.maps.web.gui.components.CanvasLayer;
import com.haulmont.addon.maps.web.gui.components.GeoMap;
import com.haulmont.addon.maps.web.gui.components.layer.style.FontPointIcon;
import com.haulmont.addon.maps.web.gui.components.layer.style.PointStyle;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.vaadin.ui.Notification;

import javax.inject.Inject;
import java.util.Map;

public class CanvasDemo extends AbstractWindow {
    @Inject
    private GeoMap map;

    private CanvasLayer canvasLayer;

    @Inject
    private Button drawPoint;
    @Inject
    private Button drawPolygon;
    @Inject
    private Button drawPolyline;

    @Override
    public void init(Map<String, Object> params) {
        canvasLayer = map.getCanvas();
        map.selectCanvas();

        CanvasLayer.Point location = canvasLayer.addPoint(GeometryUtils.createPoint(50, 50));
        FontPointIcon icon = new FontPointIcon(CubaIcon.CAB)
                .setIconPathFillColor("#f4d142")
                .setIconTextFillColor("black")
                .setIconPathStrokeColor("black");

        location.setStyle(new PointStyle(icon))
                .setPopupContent("Taxi location")
                .setEditable(true)
                .addRightClickListener(e -> Notification.show("Right click"));

        canvasLayer.addAfterPointDrawnListener(drawnEvent -> canvasLayer.addPoint(drawnEvent.getPoint()));
        canvasLayer.addAfterPolylineDrawnListener(drawnEvent -> canvasLayer.addPolyline(drawnEvent.getPolyline()));
        canvasLayer.addAfterPolygonDrawnListener(drawnEvent -> canvasLayer.addPolygon(drawnEvent.getPolygon()));
    }

    public void drawPoint() {
        canvasLayer.drawPoint();
    }

    public void drawPolyline() {
        canvasLayer.drawPolyline();
    }

    public void drawPolygon() {
        canvasLayer.drawPolygon();
    }

}