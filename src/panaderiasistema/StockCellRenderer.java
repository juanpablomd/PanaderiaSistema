package panaderiasistema;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class StockCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setOpaque(true); // Necesario para que el fondo se aplique

        int stockCol = 3;
        Object stockValue = table.getValueAt(row, stockCol);

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
            return label;
        }

        if (stockValue != null) {
            try {
                int stock = Integer.parseInt(stockValue.toString());

                if (stock < 10) {
                    label.setBackground(new Color(255, 102, 102)); // Rojo claro
                    label.setForeground(Color.WHITE);              // Texto blanco
                    
                } else {
                    label.setBackground(new Color(204, 255, 204)); // Verde claro
                    label.setForeground(Color.BLACK);              // Texto negro
                }

            } catch (NumberFormatException e) {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        } else {
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }

        return label;
    }
}


