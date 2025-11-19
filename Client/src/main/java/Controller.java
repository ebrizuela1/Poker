import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private TextArea myTextArea;
    @FXML private Button NewOrder;
    @FXML private Button DeleteOrder;
    @FXML private Button ExtraShot;
    @FXML private Button Cream;
    @FXML private Button Sugar;
    @FXML private Button ColdFoam;
    @FXML private Button WhippedCream;

    ArrayList<String> currentAddOns;

    public void initialize(URL location, ResourceBundle resources){
        currentAddOns = new ArrayList<>();
    }

    public void addOn(String addOn){
        currentAddOns.add(addOn);
        updateDisplay();
    }

    public void removeAddOn(String addOn){
        currentAddOns.remove(addOn);
        updateDisplay();
    }

    public void handleNewOrder(){
        currentAddOns.clear();
        myTextArea.clear();
        updateDisplay();
        Cream.setText("Cream");
        Sugar.setText("Sugar");
        ExtraShot.setText("Extra Shot");
    }
    public void handleDeleteOrder(){
        currentAddOns.clear();
        myTextArea.clear();
        Cream.setText("Cream");
        Sugar.setText("Sugar");
        ExtraShot.setText("Extra Shot");
    }
    public void handleExtraShot(){
        // If it is NOT in the list, ADD it
        if(!currentAddOns.contains("ExtraShot")){
            addOn("ExtraShot");
            ExtraShot.setText("Remove extra shot");
        } else {
            // If it IS in the list, REMOVE it
            removeAddOn("ExtraShot");
            ExtraShot.setText("Add extra shot");
        }
    }

    public void handleCream(){
        if(!currentAddOns.contains("Cream")){
            addOn("Cream");
            Cream.setText("Remove cream");
        } else {
            removeAddOn("Cream");
            Cream.setText("Add cream");
        }
    }

    public void handleSugar(){
        if(!currentAddOns.contains("Sugar")){
            addOn("Sugar");
            Sugar.setText("Remove sugar");
        } else {
            removeAddOn("Sugar");
            Sugar.setText("Add sugar");
        }
    }

    public void handleColdFoam(){
        if(!currentAddOns.contains("ColdFoam")){
            addOn("ColdFoam");
            ColdFoam.setText("Remove cold foam");
        } else {
            removeAddOn("ColdFoam");
            ColdFoam.setText("Add cold foam");
        }
    }

    public void handleWhippedCream(){
        if(!currentAddOns.contains("WhippedCream")){
            addOn("WhippedCream");
            WhippedCream.setText("Remove whipped cream");
        } else {
            removeAddOn("WhippedCream");
            WhippedCream.setText("Add whipped cream");
        }
    }

    public void updateDisplay(){
        myTextArea.clear();

        Coffee order = new BasicCoffee();

        for (String s : currentAddOns) {
            switch (s) {
                case "Cream":
                    order = new Cream(order);
                    break;
                case "Sugar":
                    order = new Sugar(order);
                    break;
                case "ExtraShot":
                    order = new ExtraShot(order);
                    break;
                case "ColdFoam":
                    order = new ColdFoam(order);
                    break;
                case "WhippedCream":
                    order = new WhippedCream(order);
                    break;
            }
        }

        myTextArea.setText(order.getReceipt());
        myTextArea.appendText("\nTotal: $" + String.format("%.2f", order.makeCoffee()));
    }



}
