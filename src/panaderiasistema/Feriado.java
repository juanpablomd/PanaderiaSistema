/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panaderiasistema;

/**
 *
 * @author juanp
 */
public class Feriado {
      private String date;
    private String localName;

    public String getDate() {
        return date;
    }

    public String getLocalName() {
        return localName;
    }

    @Override
    public String toString() {
        return date + " - " + localName;
    }
}
