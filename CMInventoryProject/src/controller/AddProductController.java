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
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Christina Murray
 */
/**
 * This controller class loads the add product controller.
 * The controller will allow user to add new product, select parts to add to associated parts, and provides logic to modify the product and associated parts. 
 * 
 */
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    Part partToAdd;
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    @FXML
    private TextField addProdAutoGenIdTxt;

    @FXML
    private TextField addProdNameTxt;

    @FXML
    private TextField addProdInvTxt;

    @FXML
    private TextField addProdPriceTxt;

    @FXML
    private TextField addProdMaxTxt;

    @FXML
    private TextField addProdMinTxt;
    
    @FXML
    private TextField searchPartToAddToProdTxt;

    @FXML
    private TableView<Part> addProdSearchTableView;

    @FXML
    private TableColumn<Part, Integer> addProdPartIdSearch;

    @FXML
    private TableColumn<Part, String> addProdPartNameSearch;

    @FXML
    private TableColumn<Part, Integer> addProdInvIdSearch;

    @FXML
    private TableColumn<Part, Double> addProdPriceIdSearch;

    @FXML
    private TableView<Part> addProdPartsInProductTableView;

    @FXML
    private TableColumn<Part, Integer> addProdPartIdsInList;

    @FXML
    private TableColumn<Part, String> addProdPartNamesInList;

    @FXML
    private TableColumn<Part, Integer> addProdInvInList;

    @FXML
    private TableColumn<Part, Double> addProdPriceCPUInList;

      /**
     * This method will add the selected part to the associated product table. 
     * 
     * @param event action when add button is selected.
     * @throws IOException 
     */
    @FXML
    void addProdAddPartToAProduct(ActionEvent event) {
        partToAdd = addProdSearchTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(partToAdd);
        addProdPartsInProductTableView.setItems(associatedParts);
    }

     /**
     * This method will remove the selected associated part. 
     * 
     * This method will remove the selected associated product, update the associated parts observable list, and prompt the user
     * with alert to confirm the removal. Logical error fixed was placing no part selected in separate block to ensure only one
     * alert pop-up instead of receiving confirmation and then error that part was not found.
     * 
     * @param event action when remove button is selected.
     */
    @FXML
    void addProdRemovePartButton(ActionEvent event) {
        Part partToRemove = addProdPartsInProductTableView.getSelectionModel().getSelectedItem();
        
        if(partToRemove !=null)
        {
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"This will remove the associated part. Do you want to proceed?");
        
            Optional<ButtonType> result = alert.showAndWait();
        
            if(result.isPresent() && result.get() == ButtonType.OK)
                associatedParts.remove(partToRemove);
        }
        else if (partToRemove == null)
        {
            Alert alert =new Alert(Alert.AlertType.WARNING,"Associated part not selected. Please select part to remove.");
            Optional<ButtonType> result = alert.showAndWait();       
        }
    }

    /**
     * This method will search for a part name or part id. 
     * 
     * The method will alert the user if the part name or id does not return a part. 
     * Original logical errors when try/catch was reversed and looked for the string first. Reversed order to 
     * locate the numeric id first and if not found in part name as string it will return an alert.
     * 
     * @param event action when enter key is pressed.
     */
    @FXML
    void addProdSearchField(ActionEvent event) {
        String searchPart = searchPartToAddToProdTxt.getText();
        ObservableList<Part> searchPartList = FXCollections.observableArrayList();
        
        if(searchPart !=null )
        {
         try
          {
            searchPartList = Inventory.lookupPart(searchPart);
            
                
          }
         catch (Exception e)
        {
            Part partId = Inventory.lookupPart(Integer.parseInt(searchPart));
            searchPartList.add(partId);
             
        }
        if ( searchPartList.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Part does not exist. Please check entry and try again");
            alert.showAndWait();
        }
        else if (searchPart.isBlank() || searchPart.isEmpty())
        {
          addProdSearchTableView.setItems(Inventory.getAllParts());
        }
        else 
         addProdSearchTableView.setItems(searchPartList);
        }
                      
    }
    
     /**
     * This method will cancel the modification of the product. 
     * Allows the user to cancel the modification of the product and will launch the main menu controller if the user selects ok on the alert.
     * 
     * @param event action when cancel button is selected.
     * @throws IOException 
     */

    @FXML
    void cancelAddProducts(ActionEvent event) throws IOException {
        
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
     * This method will add a new product..
     * 
     * The method includes input validation and will launch alerts if fields are empty or values are incorrect. If passes 
     * validation, it will add a new product and associated parts. Logical error fixed was moving the main screen after a successful part add. Originally located
     * outside of if/else statements, which caused the main screen to load before an error could be fixed.
     * 
     * @param event save button is selected.
     * @throws IOException failed to add the product.
     */  
    @FXML
    void saveAddProducts(ActionEvent event) throws IOException {
      try
      {
        int id;
        String name = addProdNameTxt.getText();
        int stock = Integer.parseInt(addProdInvTxt.getText());
        double price = Double.parseDouble(addProdPriceTxt.getText());
        int max = Integer.parseInt(addProdMaxTxt.getText());
        int min = Integer.parseInt(addProdMinTxt.getText());
        
        
        
        //incrementing size of list elements by 1 to create product id
        int index = Inventory.getAllProducts().size();
            id = ++index;
            
        if(max < min)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Maximum must be greater than minimum. Please update values.");
            alert.showAndWait();
        }
        else if (stock < min || stock > max)
        {
           Alert alert = new Alert(Alert.AlertType.ERROR,"Stock must be less than or equal to max or greater than or equal to minimum. Please update values.");
           alert.showAndWait(); 
        }
        else if (name.isBlank()|| name.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Product name must be present. Please correct value.");
            alert.showAndWait();
        }
        else if ( price < 0)
        {
           Alert alert = new Alert(Alert.AlertType.ERROR,"Price must be greater than 0. Please update values.");
           alert.showAndWait();  
        }
        else
        {
            Product newProduct = new Product(id, name, price, stock, min, max);
            
            for (Part part : associatedParts)
                newProduct.addAssociatedPart(part);
                    
            Inventory.addProduct(newProduct);
            
            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
      }
      catch(Exception e)
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
            
       }
                
    }
    
    /**
     * This method will load the part table view and load and set the column names in the associated parts table view.
     * 
     * @param url implements java fxml
     * @param rb implements locale object
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProdSearchTableView.setItems(Inventory.getAllParts());
        
        //setting the searh parts table
        
        addProdPartIdSearch.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartNameSearch.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdInvIdSearch.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceIdSearch.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //setting the associated parts table
        addProdPartIdsInList.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartNamesInList.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdInvInList.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceCPUInList.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    
    
}
