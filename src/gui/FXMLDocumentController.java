package gui;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import logic.models.ImportModel;
import logic.models.LoginModel;
import logic.models.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class FXMLDocumentController  implements Initializable {

    @FXML
    private Button btnLoadJSON;
    private ImportModel jsonModel;

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

    }

    @FXML
    public void handleLoadJSON(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        loadJSON(file, LoginModel.class);
    }

    public void loadJSON(File file, Class<? extends Model> modelClass) {
        if (file != null) {
            String content = null;
            try {
                content = new Scanner(file).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert content != null;

            Gson gson = new Gson();

            jsonModel = (ImportModel) gson.fromJson(content, modelClass);

        }
    }

    public ImportModel getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(ImportModel jsonModel) {
        this.jsonModel = jsonModel;
    }
}
