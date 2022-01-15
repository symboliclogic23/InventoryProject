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
* The inventory class that provides data to the application.
*/
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    
    /**
    * This method will add a part to the allParts Observable list.
    * @param newPart the part that will be added.
    */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }
    
    /**
    * This method will add a new product to the allProducts Observable list.
    * @param newProduct the product that will be added.
    */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    /**
    * This method will search the allParts list for partId.
    * 
    * @param partId the id for the part lookup.
    * @return part if the part id is in the list. return null if part id not found.
    */
    public static Part lookupPart(int partId)
    {
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId()== partId)
                return part;
            
        }
        return null;
        
    }
    
    /**
    * This method will search the allProducts list for productId.
    * 
    * @param productId the id for the product lookup.
    * @return product if the product id is in the list. return null if the product Id not found.
    */
    public static Product lookupProduct(int productId)
    {
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId()== productId)
                return product;
            
        }
        
        return null;
        
    }
    
    /**
    * This method will search the allParts list for part name.
    * 
    * @param partName the partName for the part lookup.
    * @return filtered observable list if any of the parts contain the search string.
    * 
    */
    public static ObservableList<Part> lookupPart(String partName) 
    {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        
        for(Part part : allParts)
        {
            if(part.getName().contains(partName))
            {
                filteredParts.add(part);
            }
            
        }
        
        return filteredParts;   
     }
    
    /**
    * This method will search the allProducts list for product name.
    * 
    * @param productName the productName for the product lookup.
    * @return filtered observable list if any of the products contain the search string.
    * 
    */
    public static ObservableList<Product> lookupProduct(String productName) 
    {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        
        for(Product product : allProducts)
        {
            if(product.getName().contains(productName))
            {
                filteredProducts.add(product);
            }
            
        }
                
        return filteredProducts;
                   
     }
    
    /**
    * This method will update the Part in the observable all parts list.
    * 
    * @param index observable list index of the part being updated
    * @param selectedPart part being updated
    *  
    */
    public static void updatePart(int index,Part selectedPart)
    {
       
        allParts.set(index, selectedPart);
        
    }    

    /**
    * This method will update the Product in the observable all products list.
    * 
    * @param index observable list index of the product being updated
    * @param selectedProduct product being updated
    *  
    */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }   
    
    
    /**
    * This method will delete the selected Part.
    * 
    * The allParts observable list will be searched for the part and delete the part if found.
    * 
    * @param selectedPart part being deleted
    * @return true if the part is found in the part list and deleted. return false if not found.
    */
    public static boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart)) 
        {
            allParts.remove(selectedPart);
            return true;
        }
        else 
        {
            return false;
        }
        
    }
    
    /**
    * This method will delete the selected Product.
    * 
    * The allProducts observable list will be searched for the product and delete the product if found.
    * 
    * @param selectedProduct product being deleted
    * @return true if the product is found in the product list and deleted. return false if not found.
    */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct)) 
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        else 
        {
            return false;
        }
        
    }
    
    /**
    * This method will get all the parts in the observable list.
    * 
    * @return allParts in the observable list.
    */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    /**
    * This method will get all the products in the observable list.
    * 
    * @return allProducts in the observable list.
    */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
      
    
    
}
