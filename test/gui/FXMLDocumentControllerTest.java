package gui;

import logic.CSVCreator;
import logic.models.*;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Testclass for imports from bitwarden
 *
 * @author Patrik Simms
 */
public class FXMLDocumentControllerTest {

    private static final String testClasspath = FXMLDocumentControllerTest.class
            .getProtectionDomain().getCodeSource().getLocation().getPath();

    /**
     * Complete Test Import
     */
    @Test
    void testLoadJSONImportModel() {

        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

    }


    // Single Model Tests

    /**
     * FoldersModelTest
     */
    @Test
    void testLoadJSONFoldersModel() {
        File testFile = new File(testClasspath + "/ressources/folderModelTest.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, FoldersModel.class);
    }


    /**
     * LoginsModelTest
     */
    @Test
    void testLoadJSONLoginsModel() {
        File testFile = new File(testClasspath + "/ressources/loginModelTest.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, LoginsModel.class);
    }

    /**
     * ItemsModelTest
     */
    @Test
    void testLoadJSONItemModel() {
        File testFile = new File(testClasspath + "/ressources/itemModelTest.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, ItemsModel.class);
    }

    /**
     * URISModelTest
     */
    @Test
    void testLoadJSONURIModel() {
        File testFile = new File(testClasspath + "/ressources/uriModelTest.json");

        FXMLDocumentController fxmlDocumentController = new FXMLDocumentController();

        fxmlDocumentController.loadJSON(testFile, URISModel.class);
    }
}