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
* The inhouse class extends the part class and provides getter and setter for the machineId.
* 
*/
public class InHouse extends Part{
   
   private int machineId;
   
   /**
    * The constructor for the inhouse class.
    * @param id the part id
    * @param name the part name
    * @param price the part price
    * @param stock the part stock
    * @param min the part min
    * @param max the part max
    * @param machineId the inhouse machine Id
    * 
    */
   public InHouse (int id, String name, double price, int stock, int min, int max, int machineId)
   {
       super( id, name, price, stock, min, max);
       this.machineId = machineId;
   }
   
   /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
    
     /**
     * @param machineId set the machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
           
           
}
