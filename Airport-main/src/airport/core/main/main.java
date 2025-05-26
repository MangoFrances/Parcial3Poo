package airport.core.main;

import com.formdev.flatlaf.FlatDarkLaf;
import airport.core.json.JsonLoader;
import airport.core.models.storage.Storage;
import airport.core.view.AirportFrame;
import java.awt.EventQueue;

/**
 *
 * @author becer
 */
import javax.swing.*;

public class main {

    public static void main(String[] args) {

        JsonLoader loader = new JsonLoader();
        loader.loadAll("resources/json");

        System.out.println("Pasajeros cargados: "
                + airport.core.models.storage.Storage.getInstance()
                        .getAllPassengersCopy().size());

        EventQueue.invokeLater(() -> {
            AirportFrame frame = new AirportFrame();

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        System.out.println(Storage.getInstance().getAllFlightsCopy().size());
        System.out.println("Pasajeros: " + Storage.getInstance().getAllPassengersCopy().size());
        System.out.println("Vuelos:    " + Storage.getInstance().getAllFlightsCopy().size());
    }
}
