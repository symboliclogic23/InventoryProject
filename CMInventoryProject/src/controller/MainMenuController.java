/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Part;
import model.Inventory;
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
import model.Product;

/**
 * FXML Controller class
 *
 * @author Christina Murray
 */

/**
 * This is the main menu controller class.
 * 
 * The main menu controller class which will load the part and product tables. The user can search, add, modify, delete, or exit
 * the application.
 * 
 */
public class MainMenuController implements Initializable {
        
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField searchPartTxt;
    
    @FXML
    private TextField searchProductTxt;
    
    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvLevelCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    
    
   /**
   * This method will exit the application and close the application.
   * 
   * @param event exit button will exit application.
   */
    @FXML
    void ExitApplication(ActionEvent event) {
        System.exit(0);
    }
    
   /**
   * This method will launch the product modification controller.
   * 
   * The method will launch the modify product menu for the selected product. If no product is selected, the user will be alerted.
   * 
   * @param event product modify button.
   * @throws IOException
   */

    @FXML
    void ProductModifyButton(ActionEvent event) throws IOException {
                       
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();
            
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();   
                
            if(selectedProduct !=null)
            {
                ModifyProductController MProController = loader.getController();
                MProController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
                stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
               Alert alert = new Alert(Alert.AlertType.ERROR,"No product selected. Please select product to modify.");
               Optional<ButtonType> result = alert.showAndWait(); 
            }
                
            
    }
    
   /**
   * This method will launch the add part controller.
   * 
   * The method will launch the add part controller for the user to add a new part.
   * 
   * @param event add part button.
   * @throws IOException
   */
    @FXML
    void addPartButton(ActionEvent event) throws IOException {
            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }
    
  /**
   * This method will launch the part modification controller.
   * 
   * The method will launch the modify part menu for the selected product. If no part is selected, the user will be alerted.
   * 
   * @param event part modify button.
   * @throws IOException
   */
    @FXML
    void modifyPartButton(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            
            if(selectedPart !=null)
            {
                ModifyPartController MPController = loader.getController();
                MPController.sendPart(partsTableView.getSelectionModel().getSelectedItem());
                stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
               Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected. Please select part to modify.");
               Optional<ButtonType> result = alert.showAndWait(); 
            }
            
            
            
    }
   /**
   * This method will delete the selected part.
   * 
   * The method will delete a selected part and launch a confirmation alert. If no part is selected, the user will be alerted.
   * 
   * @param event delete part button.
   * @throws IOException
   */
    @FXML
    void partDeleteButton(ActionEvent event) {
        
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
           
        
           if(selectedPart !=null)
            {
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"This will delete the part selected. Do you want to proceed?");
                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.isPresent() && result.get() == ButtonType.OK)
                    {
                        
                        Inventory.deletePart(selectedPart);
                    }
            }
        
            else
            {
               
               Alert alert = new Alert(Alert.AlertType.ERROR,"No part selected. Please select part to delete.");
               Optional<ButtonType> result = alert.showAndWait(); 
               
            }
        
    }
    
    /**
   * This method will launch the add product controller.
   * 
   * 
   * @param event add product button.
   * @throws IOException
   */
    @FXML
    void productAddButton(ActionEvent event) throws IOException {
            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }
    
   /**
   * This method will delete the selected product.
   * 
   * The method will delete the product if there are no associated parts. If associated parts are present, the user
   * will be alerted to remove those first. The user will be alerted if no product is selected.
   * 
   * @param event delete product button.
   * @throws IOException
   */
    @FXML
    void productDeleteButton(ActionEvent event) {
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();    
        
        if(selectedProduct !=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will delete the product selected. Do you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
        
            
            if(result.isPresent() && result.get() == ButtonType.OK)
            {

                associatedParts = selectedProduct.getAllAssociatedParts();

                if(associatedParts.size()>0)
                {
                    alert = new Alert(Alert.AlertType.ERROR,"Parts associated with this product. Unable to delete until removed.");
                    result = alert.showAndWait();
                }
                else
                {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
        else 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No product selected. Please select product to delete.");
            Optional<ButtonType> result = alert.showAndWait(); 
        }
    }
    
    /**
     * This method will search for a part name or part id in the parts table. 
     * 
     * The method will alert the user if the part name or id does not return a part.
     * The method will alert the user if the part name or id does not return a part. 
     * Original logical errors when try/catch was reversed and looked for the string first. Reversed order to 
     * locate the numeric id first and if not found in part name as string it will return an alert.
     * @param event action when enter key is pressed.
     */
    
    @FXML
    void searchPartsTable(ActionEvent event) {
        String searchPart = searchPartTxt.getText();
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
        }
        
        if( searchPartList.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Part does not exist. Please check entry and try again");
            alert.showAndWait();
        }
         
        else if(searchPart.isBlank() || searchPart.isEmpty())
        {
          partsTableView.setItems(Inventory.getAllParts());
        }        
             
        else 
        {
            partsTableView.setItems(searchPartList);
        }
        
      
    }
    
     /**
     * This method will search for product or product id in the product table. 
     * 
     * The method will alert the user if the product name or id does not return a product.
     * Original logical errors when try/catch was reversed and looked for the string first. Reversed order to 
     * locate the numeric id first and if not found in part name as string it will return an alert.
     * 
     * @param event action when enter key is pressed.
     */   
    @FXML
    void searchProductTable(ActionEvent event) {
        String searchProduct = searchProductTxt.getText();
        ObservableList<Product> searchProductList = FXCollections.observableArrayList();
        
        if(searchProduct !=null )
        {
         try
          {
             
            searchProductList = Inventory.lookupProduct(searchProduct);
                
          }
         catch (Exception e)
          {
            
            Product productId = Inventory.lookupProduct(Integer.parseInt(searchProduct));
            searchProductList.add(productId);
          }
        }
       
        if ( searchProductList.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Product does not exist. Please check entry and try again");
            alert.showAndWait();
        }
        
        else if ( searchProduct.isBlank() || searchProduct.isEmpty())
        {
          productTableView.setItems(Inventory.getAllProducts());
        }
        
        else
          productTableView.setItems(searchProductList);
        
        }
        
             
    /**
     * This method will load the part and product table views and load and set the column names for both tables.
     * 
     * @param url implements java fxml
     * @param rb implements locale object
     */    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());
       
        
        //setting Parts columns
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //setting Product columns
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }    
    
}
