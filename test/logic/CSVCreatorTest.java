package logic;

import gui.FXMLDocumentController;
import gui.FXMLDocumentControllerTest;
import junitx.framework.FileAssert;
import logic.models.ImportModel;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Test class for the CSV creator
 *
 * @author Patrik Simms
 */
class CSVCreatorTest {

    private static final String testClasspath = FXMLDocumentControllerTest.class
            .getProtectionDomain().getCodeSource().getLocation().getPath();


    @Test
    void createCSV() {
        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator test = new CSVCreator(fxmlDocumentController);
        test.createCSV("");
    }

    @Test
    void create_bitwarden_export_textCSV() {
        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        String homeDir = System.getProperty("user.home");

        CSVCreator csvCreator = new CSVCreator(fxmlDocumentController);
        csvCreator.createCSV( homeDir + "/projects/bitwarden_to_1password");

        File generated = new File(homeDir + "/projects/bitwarden_to_1password" +
                "/bitwardenExport.csv");

        FileAssert.assertEquals(new File(testClasspath + "/ressources" +
                "/exp_bitwarden_export_test.csv"), generated);
    }
}