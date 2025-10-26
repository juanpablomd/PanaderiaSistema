package panaderiasistema;

import javax.swing.JOptionPane;

public class UtilsValidaciones {

    public static boolean esNumero(String texto) {
        return texto.matches("\\d+");
    }

    public static Double obtenerDouble(String texto, String campo, java.awt.Component parent) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            mostrarError("El campo '" + campo + "' debe ser un número decimal válido.", parent);
            return null;
        }
    }

    public static Integer obtenerEntero(String texto, String campo, java.awt.Component parent) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            mostrarError("El campo '" + campo + "' debe ser un número entero válido.", parent);
            return null;
        }
    }

    public static void mostrarError(String mensaje, java.awt.Component parent) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void limpiarCampos(javax.swing.JTextField... campos) {
        for (javax.swing.JTextField campo : campos) {
            campo.setText("");
        }
    }

    public static void limpiarComboBox(javax.swing.JComboBox<?>... combos) {
        for (javax.swing.JComboBox<?> combo : combos) {
            combo.setSelectedIndex(0);
        }
    }
}
