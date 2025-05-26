package airport.core.main;

import com.formdev.flatlaf.FlatDarkLaf;
import airport.core.json.JsonLoader;
import airport.core.models.storage.Storage;
import airport.core.view.AirportFrame;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        System.out.println("CLASE CORRECTA");
      
        FlatDarkLaf.setup();

        JsonLoader loader = new JsonLoader();
        loader.loadAll("resources/json");               
        Storage storage = Storage.getInstance();
        System.out.printf("Pasajeros cargados: %d%n", storage.getAllPassengersCopy().size());
        System.out.printf("Vuelos cargados:    %d%n", storage.getAllFlightsCopy().size());

        /* 3 ▪ Lanzar la ventana principal en el EDT */
        EventQueue.invokeLater(() -> {
            AirportFrame frame = new AirportFrame();
            frame.refreshTables();                     
            frame.setLocationRelativeTo(null);          
            frame.setVisible(true);
        });
    }
}
