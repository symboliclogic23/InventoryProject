package model;

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
* This subclass extends the Part class.
* 
* The outsource class extends the part class and provides getter and setter for the company name.
* 
*/
public class Outsourced extends Part{
    
    private String companyName;
   
    /**
    * This constructor for the outsourced sub-class.
    * 
    * @param id the part id
    * @param name the part name
    * @param price the part price
    * @param stock the part stock
    * @param min the part min
    * @param max the part max
    * @param companyName the outsourced company name
    */
    public Outsourced (int id, String name, double price, int stock, int min, int max, String companyName)
   {
       super( id, name, price, stock, min, max);
       this.companyName = companyName;
   }
    
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * @param companyName set the companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
