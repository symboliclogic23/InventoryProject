/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 *
 * @author Christina Murray
 */

/**
 * This class creates an Inventory application for inventory management.
 * 
 * Some useful updates for a future version would be to include a count in the associated products, instead of adding a part 
 * multiple times in the table list. Another addition that would be nice to include would be an indicator in the main controller
 * that would alert that there are associated parts for each product. This would help alert an end user that they will need to modify
 * the product to remove the associated parts before the alert when they try to delete the product.
 */

public class CMInventoryProject extends Application {
    /**
     * This is the main method.
     * 
     * 
     @param args the command line arguments
    */
   
    public static void main(String[] args) {
        
        // adding test data 
        InHouse inhouse1 = new InHouse(1, "wires", 14.00, 5, 5, 2, 4522);
        InHouse inhouse2 = new InHouse(2, "capacitors", 16.99, 5, 5, 2, 4521);
        Outsourced outsourced1 = new Outsourced(3, "capacitors", 16.00, 5, 5, 2, "Panic");
        Outsourced outsourced2 = new Outsourced(4, "wires", 13.05, 4, 10, 3, "Panic");
        
        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        
        Product newProduct1 = new Product(1,"harness",1999.99, 2,2,5);
        Product newProduct2 = new Product(2,"starter",1500.00, 1,2,4);
        
        Inventory.addProduct(newProduct1);
        Inventory.addProduct(newProduct2);
        
        newProduct1.addAssociatedPart(inhouse1);
        newProduct2.addAssociatedPart(outsourced1);
        newProduct2.addAssociatedPart(outsourced1);
        
        
        
        launch(args);
    }
     /**
     * This method starts the stage.
     * @param stage sets the stage. 
     */
   
        @Override
        public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Christina Murray - Inventory Project");
        stage.show();
        
    }
}
