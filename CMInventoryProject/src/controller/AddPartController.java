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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author Christina Murray
 */

/**
 * This method implements the Add Part Controller. 
 * 
 */
public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML
    private GridPane addPartGrid;
    
    @FXML
    private Label addPartMachineIDLbl;
    
    @FXML
    private TextField addPartAutoGenIdTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;
    
    @FXML
    private TextField addPartMachineIdTxt;

    @FXML
    private RadioButton inHouseAddPartRB;

    @FXML
    private RadioButton outsourceAddPartRB;

    @FXML
    private ToggleGroup partMadeToggleGrp;

    /**
     * This method will cancel add part. 
     * Allows the user to cancel adding a part and will launch the main menu controller if the user selects ok on the alert.
     * @param event action when cancel button is selected.
     * @throws IOException 
     */
    @FXML
    void cancelAddPartButton(ActionEvent event) throws IOException {
        
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
     * The method will update the label to Machine Id.
     * @param event in-house radio button is selected.
     */
    @FXML
    void inHouseAddPartRadioButton(ActionEvent event) {
                        
            if(inHouseAddPartRB.isSelected())
                addPartMachineIDLbl.setText("Machine ID");
            else
               addPartMachineIDLbl=null; 
    }
    
    /**
     * The method will update the label to Company Name.
     * @param event out sourced radio button is selected.
     */
    @FXML
    void outsourcedAddPartRadioButton(ActionEvent event) {
           
        
            if(outsourceAddPartRB.isSelected())
                addPartMachineIDLbl.setText("Company Name");
            else
               addPartMachineIDLbl=null; 
          
    }

    /**
     * This method will add a new part.
     * 
     * The method includes input validation and will launch alerts if fields are empty or values are incorrect. If passes 
     * validation, it will add a part. Logical error fixed was moving the main screen after a successful part add. Originally located
     * outside of if/else statements, which caused the main screen to load before an error could be fixed.
     * 
     * @param event save button is selected.
     * @throws IOException failed to add the part.
     * 
     */
    @FXML
    void saveAddPartButton(ActionEvent event) throws IOException {
     try
     {
            int id = 0;
            String name = addPartNameTxt.getText();
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int stock = Integer.parseInt(addPartInvTxt.getText()); 
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText()); 
            int machineId = 0;
            String companyName = null;


             int index = Inventory.getAllParts().size();
             id = ++index;

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
                    if(inHouseAddPartRB.isSelected())
                    {
                        machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                        
                        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }

                    if(outsourceAddPartRB.isSelected())
                    {
                        companyName = addPartMachineIdTxt.getText();       
                        if(companyName.isBlank() || companyName.isEmpty())
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Company Name is blank. Please correct value.");
                            alert.showAndWait();
                        }
                        else
                        {
                            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                        }
                    }

                   
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
     * 
     * @param url implements java fxml
     * @param rb implements locale object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
