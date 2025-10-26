package panaderiasistema;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author juanp
 */
public class Proveedores {
    private int IdProveedor;
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Direccion;
    
    /*public Proveedores(int idProveedor, String Nombre, String Telefono, String Email, String Direccion){
        this.idProveedor = idProveedor;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Email = Email;
        this.Direccion = Direccion;
    }*/
    
    public Proveedores(int IdProveedor, String Nombre){
    this.IdProveedor = IdProveedor;
    this.Nombre = Nombre;
    this.Telefono = "";
    this.Email = "";
    this.Direccion = "";
    }
    
    public int getIdProveedor() {
        return IdProveedor;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    @Override
    public String toString(){
        return Nombre;
    }
}
