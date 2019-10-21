package logic;

import gui.FXMLDocumentController;
import gui.FXMLDocumentControllerTest;
import logic.models.ImportModel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CSVCreatorTest {

    private static final String testClasspath = FXMLDocumentControllerTest.class
            .getProtectionDomain().getCodeSource().getLocation().getPath();


    @Test
    void createCSV() {
        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator test = new CSVCreator(fxmlDocumentController);
        test.createCSV();
    }
}