/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Christina Murray
 */


/**
 * This controller class loads the selected product from the main menu, associated parts, and provides logic to modify the product and associated parts. 
 * 
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    private int index;
    Part partToAdd;
    
    
            
    @FXML
    private TextField modifyProductAutoGenIdTxt;

    @FXML
    private TextField modProductNameTxt;

    @FXML
    private TextField modProductInvTxt;

    @FXML
    private TextField modProductPriceTxt;

    @FXML
    private TextField modProductMaxTxt;

    @FXML
    private TextField modProductMinTxt;
    
    @FXML
    private TextField modProdPartSearch;

    @FXML
    private TableView<Part> modProdSearchProd;

    @FXML
    private TableColumn<Part, Integer> modProductPartIdSearch;

    @FXML
    private TableColumn<Part, String> modProductPartNameSearch;

    @FXML
    private TableColumn<Part, Integer> modProductInvIdSearch;

    @FXML
    private TableColumn<Part, Double> modProductPriceIdSearch;

    @FXML
    private TableView<Part> modProdTableView;

    @FXML
    private TableColumn<Part, Integer> modProductPartIdsInList;

    @FXML
    private TableColumn<Part, String> modProductPartNamesInList;

    @FXML
    private TableColumn<Part, Integer> modProductsInvInList;

    @FXML
    private TableColumn<Part, Double> modProductsPriceCPUInList;
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    /**
     * This method will cancel the modification of the product. 
     * Allows the user to cancel the modification of the product and will launch the main menu controller if the user selects ok on the alert.
     * @param event action when cancel button is selected.
     * @throws IOException 
     */

    @FXML
    void cancelModifyProduct(ActionEvent event) throws IOException {
        
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"This will clear all text fields and bring you back to main menu. Do you want to proceed?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method will add the selected part to the associated product table. 
     * 
     * @param event action when add button is selected.
     * @throws IOException 
     */
    @FXML
    void modProductAddPartToAProduct(ActionEvent event) {
        partToAdd = modProdSearchProd.getSelectionModel().getSelectedItem();
        associatedParts.add(partToAdd);
        modProdTableView.setItems(associatedParts);
    }

    /**
     * This method will remove the selected associated part. 
     * 
     * If an associated part is selected, a confirmation alert will pop-up. A warning will pop-up if no part selected. Added 
     * else if statement if part is not found.
     * @param event action when remove button is selected.
     */
    @FXML
    void modProductRemovePartButton(ActionEvent event) {
        Part partToRemove = modProdTableView.getSelectionModel().getSelectedItem();
        
        if(partToRemove!=null)
        {
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"This will remove the associated part. Do you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
        
            if(result.isPresent() && result.get() == ButtonType.OK)
                associatedParts.remove(partToRemove);
        }
        else if (partToRemove ==null)
        {
           Alert alert =new Alert(Alert.AlertType.WARNING,"Associated part not selected. Please select part to remove.");
           Optional<ButtonType> result = alert.showAndWait(); 
        }
        
    }

     /**
     * This method will search for a part name or part id. 
     * 
     * Original logical errors when try/catch was reversed and looked for the string first. Reversed order to 
     * locate the numeric id first and if not found in part name as string it will return an alert.
     * 
     * @param event action when enter key is pressed.
     */
    @FXML
    void modifyProductSearchField(ActionEvent event) {
        
        String searchPart = modProdPartSearch.getText();
        ObservableList<Part> searchPartList = FXCollections.observableArrayList();
        
        if(searchPart !=null )
        {
            try
             {

               Part partId = Inventory.lookupPart(Integer.parseInt(searchPart));
               searchPartList.add(partId);

             }
            catch (Exception e)
            {
               searchPartList = Inventory.lookupPart(searchPart);

            }
        }
        
        if (searchPartList.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Part does not exist. Please check entry and try again");
            alert.showAndWait();
        }
         
        else if(searchPart.isBlank() || searchPart.isEmpty())
        {
            modProdSearchProd.setItems(Inventory.getAllParts());
        }
        else
            modProdSearchProd.setItems(searchPartList);
        }
        
     /**
     * This method save the modified part.
     * 
     * The method includes input validation and will launch alerts if fields are empty or values are incorrect. If passes 
     * validation, it will save modifications to the Product.
     * 
     * @param event save button is selected.
     * @throws IOException failed to add the product.
     */  

    @FXML
    void saveModifyProducts(ActionEvent event) throws IOException {
        try
        {
            int id = Integer.parseInt(modifyProductAutoGenIdTxt.getText());
            String name = modProductNameTxt.getText();
            int stock = Integer.parseInt(modProductInvTxt.getText());
            double price = Double.parseDouble(modProductPriceTxt.getText());
            int max = Integer.parseInt(modProductMaxTxt.getText());
            int min = Integer.parseInt(modProductMinTxt.getText());



                if(max < min )
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Maximum must be > than minimum. Please correct values.");
                    alert.showAndWait();
                }   

                else if (stock > max || stock < min)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Stock must be <= maximum and >= minimum. Please correct values.");
                    alert.showAndWait();
                }
                else if (name.isBlank()|| name.isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Product name must be present. Please correct value.");
                    alert.showAndWait();
                }
                else if (price <0 )
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Price must be > 0. Please correct value.");
                    alert.showAndWait();
                }
                else 
                {

                    Product updatedProduct = new Product(id, name, price, stock, min, max);

                    for (Part part : associatedParts)
                        updatedProduct.addAssociatedPart(part);

                    Inventory.updateProduct(index,updatedProduct);

                    stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
            }
        }
        catch(NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
            
        }
    }
    
    /**
     * This method receives the product selection from the Main Menu Controller when the modify product button is selected.
     * 
     * This method sets all the product fields from the product selected and will load the associated parts in the 
     * associated parts table. 
     * 
     * @param product gets the product and associated parts from the Main Menu Controller.
     */
    public void sendProduct(Product product)
    {
        
        index =getAllProducts().indexOf(product);
        modifyProductAutoGenIdTxt.setText(String.valueOf(product.getId()));
        modProductNameTxt.setText(product.getName());
        modProductInvTxt.setText(String.valueOf(product.getStock()));
        modProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modProductMaxTxt.setText(String.valueOf(product.getMax()));
        modProductMinTxt.setText(String.valueOf(product.getMin()));
        modProductNameTxt.requestFocus();
        
        associatedParts = product.getAllAssociatedParts();
        modProdTableView.setItems(associatedParts);
        
        
    }
    /**
     * This method initializes the modify product controller, sets the parts, and associated parts table views.
     * @param url implements java fxml
     * @param rb implements locale object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
     
        modProdSearchProd.setItems(Inventory.getAllParts());
        modProductPartIdSearch.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductPartNameSearch.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductInvIdSearch.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductPriceIdSearch.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modProdTableView.setItems(associatedParts);
        modProductPartIdsInList.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductPartNamesInList.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProductsInvInList.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProductsPriceCPUInList.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
