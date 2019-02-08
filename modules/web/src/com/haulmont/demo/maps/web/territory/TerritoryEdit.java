package com.haulmont.demo.maps.web.territory;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Territory;

@UiController("mapsdemo$Territory.edit")
@UiDescriptor("territory-edit.xml")
@EditedEntityContainer("territoryDc")
@LoadDataBeforeShow
public class TerritoryEdit extends StandardEditor<Territory> {
}