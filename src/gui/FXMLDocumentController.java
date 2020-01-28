package gui;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import logic.CSVCreator;
import logic.MissingAttributeException;
import logic.models.ImportModel;
import logic.models.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * FXML Controller for handeling the gui- elements of the application.
 * Uses the GSON from google https://github.com/google/gson
 *
 * @param <T> The Model for the JSONParser
 * @author Patrik Simms
 */
public class FXMLDocumentController<T extends Model> implements Initializable {

    /**
     * Loading Button
     */
    @FXML
    private Button btnLoadJSON;

    @FXML
    private Button btnSaveCSV;

    @FXML
    private ImageView loadingSpinner;

    private CSVCreator csvCreator;

    /**
     * the Model for the JSON to parse (by default usage of the application it will be ImportModel,
     * which is the default model for parsing from an bitwarden JSON).
     * <p>
     * Contains the data from the parsed JSON
     */
    private T jsonModel;

    /**
     * @return the dataModel containing the data
     */
    public T getJsonModel() {
        return jsonModel;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.csvCreator = new CSVCreator(this);
    }

    /**
     * handles the loadButton from the gui. Opens a Filechooser and calls the loadJSON for creating
     * the object
     *
     * @param actionEvent the Action performed on the Button
     */
    @FXML
    public void handleLoadJSON(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        // TODO translations
        fileChooser.setTitle("Wähle eine JSON Exportdatei von Bitwarden aus");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // TODO ImportModel has to be of Type T
            this.loadingSpinner.setVisible(true);
            loadJSON(file, (Class<T>) ImportModel.class);
        }
        this.loadingSpinner.setVisible(false);
    }

    @FXML
    public void handleCSVWrite(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        // TODO translations
        fileChooser.setTitle("Speichere CSV Export für 1Password");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                this.loadingSpinner.setVisible(true);
                csvCreator.createCSV(file.getPath());
                this.loadingSpinner.setVisible(false);
            } catch (MissingAttributeException e) {
                // TODO print stacktrace to GUI
                e.printStackTrace();
            }
        }


    }

    /**
     * loads a JSON from a file.
     * Uses GSON to read from a JSON
     *
     * @param file       the File to read from
     * @param modelClass the Model which will be used for parsing. The content will be parsed into
     *                   the Model
     */
    public void loadJSON(File file, Class<T> modelClass) {
        if (file != null) {
            String content = null;
            try {
                content = new Scanner(file).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                // TODO print stacktrace to GUI
                e.printStackTrace();
            }
            // TODO convert assertion to exception and handling
            assert content != null;

            Gson gson = new Gson();

            jsonModel = gson.fromJson(content, modelClass);
        }
    }
}
