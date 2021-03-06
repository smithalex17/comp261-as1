package comp261.assig1;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.*;

public class GraphController {

    // names from the items defined in the FXML file
    @FXML
    private TextField searchText;
    @FXML
    private Button load;
    @FXML
    private Button quit;
    @FXML
    private Button up;
    @FXML
    private Button down;
    @FXML
    private Button left;
    @FXML
    private Button right;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private Label nodeDisplay;
    @FXML
    private TextArea tripText;

    // These are use to map the nodes to the location on screen
    private Double scale = 5000.0; // 5000 gives 1 pixel ~ 2 meter
    private static final double ratioLatLon = 0.73; // in Wellington ratio of latitude to longitude
    private GisPoint mapOrigin = new GisPoint(174.77, -41.3); // Lon Lat for Wellington

    private static int stopSize = 5; // drawing size of stops
    private static int moveDistance = 100; // 100 pixels
    private static double zoomFactor = 1.1; // zoom in/out factor

    private ArrayList<Stop> highlightNodes = new ArrayList<Stop>();

    // map model to screen using scale and origin
    private Point2D model2Screen(GisPoint modelPoint) {
        return new Point2D(model2ScreenX(modelPoint.lon), model2ScreenY(modelPoint.lat));
    }

    private double model2ScreenX(double modelLon) {
        return (modelLon - mapOrigin.lon) * (scale * ratioLatLon) + mapCanvas.getWidth() / 2;
    }

    // the getHeight at the start is to flip the Y axis for drawing as JavaFX draws
    // from the top left with Y down.
    private double model2ScreenY(double modelLat) {
        return mapCanvas.getHeight() - ((modelLat - mapOrigin.lat) * scale + mapCanvas.getHeight() / 2);
    }

    // map screen to model using scale and origin
    private double getScreen2ModelX(Point2D screenPoint) {
        return (((screenPoint.getX() - mapCanvas.getWidth() / 2) / (scale * ratioLatLon)) + mapOrigin.lon);
    }

    private double getScreen2ModelY(Point2D screenPoint) {
        return ((((mapCanvas.getHeight() - screenPoint.getY()) - mapCanvas.getHeight() / 2) / scale) + mapOrigin.lat);
    }

    private GisPoint getScreen2Model(Point2D screenPoint) {
        return new GisPoint(getScreen2ModelX(screenPoint), getScreen2ModelY(screenPoint));
    }

    public void initialize() {
        // currently blank
    }

    /* handle the load button being pressed connected using FXML */
    public void handleLoad(ActionEvent event) {
        Stage stage = (Stage) mapCanvas.getScene().getWindow();
        System.out.println("Handling event " + event.getEventType());
        FileChooser fileChooser = new FileChooser();
        // Set to user directory or go to default if cannot access

        File defaultNodePath = new File("data");
        if (!defaultNodePath.canRead()) {
            defaultNodePath = new File("C:/");
        }
        fileChooser.setInitialDirectory(defaultNodePath);
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extentionFilter);

        fileChooser.setTitle("Open Stop File");
        File stopFile = fileChooser.showOpenDialog(stage);

        fileChooser.setTitle("Open Stop Pattern File");
        File tripFile = fileChooser.showOpenDialog(stage);

        Main.graph = new Graph(stopFile, tripFile);
        drawGraph();
        event.consume(); // this prevents other handlers from being called
    }

    public void handleQuit(ActionEvent event) {
        System.out.println("Quitting with event " + event.getEventType());
        event.consume();
        System.exit(0);
    }

    public void handleZoomin(ActionEvent event) {
        System.out.println("Zoom in event " + event.getEventType());
        // Todo: zoom in
        scale *= zoomFactor;
        drawGraph();
        event.consume();
    }

    public void handleZoomout(ActionEvent event) {
        System.out.println("Zoom out event " + event.getEventType());
        // Todo: zoom out
        scale *= 1.0 / zoomFactor;
        drawGraph();
        event.consume();
    }

    public void handleUp(ActionEvent event) {
        System.out.println("Move up event " + event.getEventType());
        // Todo: move up
        mapOrigin.add(new GisPoint(0, moveDistance / ratioLatLon));

        drawGraph();
        event.consume();
    }

    public void handleDown(ActionEvent event) {
        System.out.println("Move Down event " + event.getEventType());
        // Todo: move down
        mapOrigin.subtract(new GisPoint(0, moveDistance / ratioLatLon));
        drawGraph();
        event.consume();
    }

    public void handleLeft(ActionEvent event) {
        System.out.println("Move Left event " + event.getEventType());
        // Todo: move left
        mapOrigin.add(new GisPoint(moveDistance / ratioLatLon, 0));
        drawGraph();
        event.consume();
    }

    public void handleRight(ActionEvent event) {
        System.out.println("Move Right event " + event.getEventType());
        // Todo: move right
        mapOrigin.subtract(new GisPoint(moveDistance / ratioLatLon, 0));
        drawGraph();
        event.consume();
    }

    public void handleSearch(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + searchText.getText());
        String search = searchText.getText();
        // Todo: figure out how to add searching and by text
        // This is were a Trie would be used potentially
        highlightNodes.clear();
        for (Stop stop : Main.graph.getStopList()) {
            if (search.length() > 0 && stop.getName().contains(search)) {
                highlightNodes.add(stop);
                nodeDisplay.setText(stop.toString());
            }
        }
        drawGraph();
        event.consume();
    }

    public void handleSearchKey(KeyEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + searchText.getText());
        String search = searchText.getText();
        // Todo: figure out how to add searching and by text after each keypress
        // This is were a Trie would be used potentially

        highlightNodes.clear();
        for (Stop stop : Main.graph.getStopList()) {
            if (search.length() > 0 && stop.getName().contains(search)) {
                highlightNodes.add(stop);
                nodeDisplay.setText(stop.getName());
            }
        }
        event.consume();
    }

    /*
     * handle mouse clicks on the canvas
     * select the node closest to the click
     */
    public void handleMouseClick(MouseEvent event) {
        System.out.println("Mouse click event " + event.getEventType());
        // Todo: find node closed to mouse click
        // event.getX(), event.getY() are the mouse coordinates

        Point2D screenPoint = new Point2D(event.getX(), event.getY());
        double x = getScreen2ModelX(screenPoint);
        double y = getScreen2ModelY(screenPoint);
        highlightClosestStop(x, y);
        event.consume();
    }

    // find the Closest stop to the lon,lat postion
    public void highlightClosestStop(double lon, double lat) {
        double minDist = Double.MAX_VALUE;
        Stop closestStop = null;
        // Todo: find closest stop and work out how to highlight it
        // Todo: Work out highlighting the trips through this node
        for (Stop s : Main.graph.getStopList()) {
            double dist = s.getPoint().distance(lat, lon);
            if (dist < minDist) {
                minDist = dist;
                closestStop = s;
            }
            ;
        }
        if (closestStop != null) {
            highlightNodes.clear();
            highlightNodes.add(closestStop);
            nodeDisplay.setText(closestStop.toString());
            drawGraph();
        }
    }

    /*
     * Drawing the graph on the canvas
     */
    public void drawCircle(double x, double y, int radius) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        mapCanvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
    }

    // This will draw the graph in the graphics context of the mapcanvas
    public void drawGraph() {
        Graph graph = Main.graph;
        if(graph == null) {
            return;
        }
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

        // Todo:  store node list so we can use nodes to find edge end points.
        ArrayList<Stop> stopList = graph.getStopList();

        // Todo: use the nodes form the data in graph to draw the graph
        // probably use something like this
        
        stopList.forEach(stop -> {
            int size = stopSize;
            if (highlightNodes.contains(stop)) {
                gc.setFill(Color.YELLOW);
                size = stopSize * 2;
            } else {
                gc.setFill(Color.BLUE);
            }
            Point2D screenPoint = model2Screen(stop.getPoint());
            drawCircle(screenPoint.getX(), screenPoint.getY(), size);
        });
        
     
        //draw edges
        
        graph.getTripList().forEach(trip -> {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            //Todo: step through the edges and draw them with something like

            if(trip.getEdges() != null){
                for(Edge e : trip.getEdges()){
                    Stop to = e.getTo();
                    Stop from = e.getFrom();
    
                   // Point2D startPoint = model2Screen(from.getPoint());
                   // Point2D endPoint = model2Screen(to.getPoint());
                   // drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
                }
            }           
        });
        
            
            
            
                
    }

    private void drawTrip(Trip trip, GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);
        // Todo: step through a trip to highlight it in a different colour
        // Point2D startPoint = model2Screen(fromStop.getPoint());
        // Point2D endPoint = model2Screen(toStop.getPoint());
        // drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(),
        // endPoint.getY());

    }
}