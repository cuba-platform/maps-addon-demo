# Maps demo

This is an example of CUBA application working with the [CUBA Maps add-on](https://www.cuba-platform.com/marketplace/maps/).

The application has three entities with corresponding screens:

* `Territory` - defines a territory as a polygon on the map.
* `Salesperson` - a salesperson assigned to a territory.
* `Order` - contains date, amount and the reference to a salesperson.

The editor screens of these entities have a map, where you can modify corresponding geometry.

The *Map* screen displays the map with two vector layers: `territories` and `salespersons`. If you click to a salesperson, a popup window with the salesperson's details will be opened.

The *Canvas* screen demonstrates examples of working with the map's canvas.

The *Orders* screen contains a list of orders. If you click to the *Show map* button, a screen with a map will be opened in a dialog. The map contains a layer of orders with clustering enabled. You can click to the *Generate 100 orders* button in order to automatically generate 100 new orders. The *Orders* screen also has *Show heatmap* button. If you click this button, you will see a heatmap of orders opened in a new screen.

Based on CUBA Platform 7.0.5

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support, and reporting problems coressponding to this sample.