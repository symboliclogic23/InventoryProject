package model;


import model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Christina Murray
 */

/**
 * The product class provides methods to get and set products and parts associated with products.
 * 
 */
public class Product {

   
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts =FXCollections.observableArrayList();
    
    /**
    * The constructor for the product class.
    * 
    * @param id the product id
    * @param name the product name
    * @param price the product price
    * @param stock the product stock
    * @param min the product min
    * @param max the product max
    */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max; 
              
    }

     /**
     * Product Id getter.
     * @return the product id
     */
    public int getId() {
        return id;
    }

     /**
     * The product Id setter.
     * @param id set the product id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * The product name getter.
     * @return the product name
     */
    public String getName() {
        return name;
    }

     /**
     * The product name setter.
     * @param name set the product name
     */
    public void setName(String name) {
        this.name = name;
    }

     /**
     * The product price getter.
     * @return the product price
     */
    public double getPrice() {
        return price;
    }
     /**
     * The product price setter.
     * @param price set the product price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * The product stock getter.
     * @return the product stock
     */
    public int getStock() {
        return stock;
    }
    
     /**
     * The product stock setter.
     * @param stock set the product stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * The product min getter.
     * @return the product min
     */
    public int getMin() {
        return min;
    }
    
    /**
     * The product min setter.
     * @param min set the product min
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * The product max getter.
     * @return the product max
     */
    public int getMax() {
        return max;
    }
    
     /**
     * The product max setter.
     * @param max set the product max
     */
    public void setMax(int max) {
        this.max = max;
    }
    
     /**
     * The method will add a part to the parts associated to a product.
     * @param part add part to associated parts list.
     */
     public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }
    
    /**
     * The method will delete associated part to a product.
     * 
     * The deletion is set up in the add and modify controllers. 
     * 
     * @param selectedAssociatedPart associated part to be deleted.
     * @return boolean if part deleted.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return true;
        
    }
    /**
    * This method will return the list of associated parts.
    * 
    * @return all associatedParts in the observable list
    */        
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
    
    
}
