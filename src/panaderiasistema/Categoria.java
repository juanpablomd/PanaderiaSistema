package panaderiasistema;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author juanp
 */
public class Categoria {
private int IdCategoria;
private String Nombre;

// Constructor
public Categoria(int IdCategoria, String Nombre) {
    this.IdCategoria = IdCategoria;
    this.Nombre = Nombre;
}

// Getters
public int getIdCategoria() {
    return IdCategoria;
}

public String getNombre() {
    return Nombre;
}

// Esto es lo que se mostrará en el JComboBox
@Override
public String toString() {
    return Nombre;
}
}
