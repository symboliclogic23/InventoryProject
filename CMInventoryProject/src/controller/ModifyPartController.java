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
import static model.Inventory.getAllParts;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Christina Murray
 */

/**
 * This controller class provides the logic to modify the selected part from the main menu controller. 
 * 
 */
public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;
    private int index;
    
    
    
    @FXML
    private GridPane modPartGridPane;
    
    @FXML
    private Label modPartMachineIDLbl;
       
    @FXML
    private TextField modifyPartIDfield;

    @FXML
    private TextField modifyNameTxt;

    @FXML
    private TextField modifyInvTxt;

    @FXML
    private TextField modifyPriceTxt;

    @FXML
    private TextField modifyMaxTxt;

    @FXML
    private TextField modifyMinTxt;

    @FXML
    private TextField modifyMachineIdTxt;
    
    @FXML
    private RadioButton inhouseRBModPart;
    
    @FXML
    private RadioButton outsourceModPartRB;

    @FXML
    private ToggleGroup modPartMadeToggleGrp;
   
    /**
     * This method will cancel the modify part. 
     * Allows the user to cancel the modification of a part and will launch the main menu controller if the user selects ok on the alert.
     * @param event action when cancel button is selected.
     * @throws IOException 
     */
        
    @FXML
    void cancelModifyPartButton(ActionEvent event) throws IOException {
            
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
    void inHouseRBModifyPart(ActionEvent event) {
        //updating label based upon radio button selection
        if(inhouseRBModPart.isSelected())
            modPartMachineIDLbl.setText("Machine ID");
        
    }
    /**
     * The method will update the label to Company Name.
     * @param event out sourced radio button is selected.
     */
    @FXML
    void outsourcedModifyPartRB(ActionEvent event) {
        //updating label based upon radio button selection
        if(outsourceModPartRB.isSelected())
            modPartMachineIDLbl.setText("Company Name");
            
    }
    
    /**
     * This method receive the part selection from the Main Menu Controller. It sets all the part fields from the part selected.
     * 
     * @param part gets the part from the Main Menu Controller.
     */
    
    public void sendPart(Part part)
    {
        index =getAllParts().indexOf(part);
         
        modifyPartIDfield.setText(String.valueOf(part.getId()));
        modifyNameTxt.setText(part.getName());
        modifyInvTxt.setText(String.valueOf(part.getStock()));
        modifyPriceTxt.setText(String.valueOf(part.getPrice()));
        modifyMaxTxt.setText(String.valueOf(part.getMax()));
        modifyMinTxt.setText(String.valueOf(part.getMin()));
        
        
        if (part instanceof InHouse)
        {
           modPartMachineIDLbl.setText("Machine ID");
           modifyMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
           inhouseRBModPart.setSelected(true);
           inhouseRBModPart.requestFocus();
        }
        else if (part instanceof Outsourced)
        {
           modPartMachineIDLbl.setText("Company Name");
           modifyMachineIdTxt.setText(((Outsourced) part).getCompanyName());
           outsourceModPartRB.setSelected(true);  
           outsourceModPartRB.requestFocus();
        }
    }        
    
    /**
     * This method will save the modifications to the part.
     * 
     * The method includes input validation and will launch alerts if fields are empty or values are incorrect. If passes 
     * validation, it will add a part. Moved the main menu controller stage after the part modification was successful. Originally
     * was after the if/else statement blocks and would flash alert and go to main screen instead of allowing user to make the modification.
     * 
     * @param event save button is selected.
     * @throws IOException failed to save modifications to the part.
     */
    @FXML
    void saveModifyPartButton(ActionEvent event) throws IOException {
      try
      {
        int id = Integer.parseInt(modifyPartIDfield.getText());
        String name = modifyNameTxt.getText();
        int stock = Integer.parseInt(modifyInvTxt.getText());
        double price = Double.parseDouble(modifyPriceTxt.getText());
        int max = Integer.parseInt(modifyMaxTxt.getText());
        int min = Integer.parseInt(modifyMinTxt.getText());

        int machineId = 0;
        String companyName = null;
        

        Part updatedPart;
        
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
        
        else if(price <0 )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Price must be > 0. Please correct value.");
            alert.showAndWait();
        }
              

        else if(inhouseRBModPart.isSelected())
        {
            
           machineId = Integer.parseInt(modifyMachineIdTxt.getText());
           updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
           Inventory.updatePart(index, updatedPart);
           
           stage =(Stage)((Button)event.getSource()).getScene().getWindow();
           scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
           stage.setScene(new Scene(scene));
           stage.show();
        
         }
        
        else if (outsourceModPartRB.isSelected() )
        {
                     
            companyName = modifyMachineIdTxt.getText();
                
                if(companyName.isBlank()|| companyName.isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Outsourced company cannot be empty. Please correct value.");
                    alert.showAndWait();
                }    
                else
                {
                    updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index,updatedPart);


                    stage =(Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
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
     * 
     * @param url implements java fxml
     * @param rb implements locale object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
