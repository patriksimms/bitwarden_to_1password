package logic;

import com.google.gson.JsonSyntaxException;
import gui.FXMLDocumentController;
import gui.FXMLDocumentControllerTest;
import junitx.framework.FileAssert;
import logic.models.ImportModel;
import org.junit.jupiter.api.Assertions;
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
    public void createCSV() {
        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator test = new CSVCreator(fxmlDocumentController);
        try {
            test.createCSV("bitwarden_to_1password.csv");
        } catch (MissingAttributeException e) {
            e.printStackTrace();
        }

        File generated = new File("bitwarden_to_1password.csv");

        assert generated.delete();
    }

    @Test
    void create_bitwarden_export_textCSV() {
        File testFile = new File(testClasspath + "/ressources/bitwarden_export_test.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator csvCreator = new CSVCreator(fxmlDocumentController);
        try {
            csvCreator.createCSV("bitwarden_to_1password.csv");
        } catch (MissingAttributeException e) {
            e.printStackTrace();
        }

        File generated = new File("bitwarden_to_1password.csv");

        FileAssert.assertEquals(new File(testClasspath + "/ressources" +
                "/exp_bitwarden_export_test.csv"), generated);

        assert generated.delete();
    }

    @Test
    void create_csv_too_many_attributes() {
        File testFile = new File(testClasspath + "/ressources/too_many_attributes_in_json.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator csvCreator = new CSVCreator(fxmlDocumentController);
        try {
            csvCreator.createCSV("bitwarden_to_1password.csv");
        } catch (MissingAttributeException e) {
            e.printStackTrace();
        }

        File generated = new File("bitwarden_to_1password.csv");

        FileAssert.assertEquals(new File(testClasspath + "/ressources" +
                "/exp_bitwarden_export_test.csv"), generated);

        assert generated.delete();
    }

    @Test
    void create_csv_too_few_attributes() {
        File testFile = new File(testClasspath + "/ressources/too_few_attributes_in_json.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        fxmlDocumentController.loadJSON(testFile, ImportModel.class);

        CSVCreator csvCreator = new CSVCreator(fxmlDocumentController);

        Assertions.assertThrows(MissingAttributeException.class, () -> csvCreator.createCSV("bitwarden_to_1_password.csv"));
    }

    @Test
    void test_invalid_json() {
        File testFile = new File(testClasspath + "/ressources/invalid_json.json");

        FXMLDocumentController<ImportModel> fxmlDocumentController = new FXMLDocumentController<>();

        Assertions.assertThrows(JsonSyntaxException.class, () -> fxmlDocumentController.loadJSON(testFile, ImportModel.class));

    }
}