package airport.core.main;

import com.formdev.flatlaf.FlatDarkLaf;
import airport.core.json.JsonLoader;
import airport.core.view.AirportFrame;

/**
 *
 * @author becer
 */
import javax.swing.*;

public class main {

    public static void main(String[] args) {

        System.setProperty("flatlaf.useNativeLibrary", "false");
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        try {
            JsonLoader.loadAll();
        } catch (Exception e) {
            System.err.println("Error loading JSON data: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new AirportFrame().setVisible(true);
        });
    }
}
