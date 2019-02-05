package com.haulmont.demo.maps.web.territory;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.demo.maps.entity.Territory;


@UiController("mapsdemo$Territory.browse")
@UiDescriptor("territory-browse.xml")
@LookupComponent("territoriesTable")
@LoadDataBeforeShow
public class TerritoryBrowse extends StandardLookup<Territory> {
}