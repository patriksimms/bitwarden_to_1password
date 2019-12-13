package logic;

import gui.FXMLDocumentController;
import logic.models.ImportModel;
import logic.models.ItemModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for creating export Export CSVs for 1Password.
 * <p>
 * Uses the deserialized JSON Object.
 *
 * @author Patrik Simms
 */
public class CSVCreator {

    /**
     * The Controller for the JSON Parser
     */
    private final FXMLDocumentController fxmlDocumentController;

    /**
     * The Export Attributes needed for the 1Password Export
     */
    private final Set<ExportAttribute> exportAttributes = new HashSet<>();

    /**
     * constructor
     *
     * @param fxmlDocumentController the Controller which deserializes the JSON into a JavaObject.
     */
    CSVCreator(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
        this.initDefaultExportAttributes();
    }

    /**
     * initializes the export Attributes. The attributes defined here are the default Attributes
     * needed for an export to 1password.
     * <p>
     * https://support.1password.com/create-csv-files/
     */
    private void initDefaultExportAttributes() {
        exportAttributes.add(ExportAttribute.name);
        exportAttributes.add(ExportAttribute.login);
        exportAttributes.add(ExportAttribute.loginUserName);
        exportAttributes.add(ExportAttribute.loginURI);
        exportAttributes.add(ExportAttribute.loginPassword);
    }

    /**
     * Creates a csv compatible String for the 1password Import.
     *
     * @param path the path the file should be written to
     * @return the csv String
     */
    String createCSV(String path) {

        assert this.fxmlDocumentController.getJsonModel() instanceof ImportModel;
        ImportModel currModel = (ImportModel) this.fxmlDocumentController.getJsonModel();
        StringBuilder sb = new StringBuilder();

        for (ItemModel item : currModel.items) {
            if (checkItemAttributes(item)) {
                sb.append(item.name).append(",")
                        .append(item.login.uris[0].uri).append(",")
                        .append(item.login.username).append(",")
                        .append(item.login.password)
                        .append(item.notes == null ? "" : "," + item.notes).append("\n");
            }
        }
        // deleting the last newline
        sb.deleteCharAt(sb.length() - 1);

        try {
            PrintWriter pw = new PrintWriter(path + "/bitwardenExport.csv");
            pw.println(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks for one item, if the export Attributes are existing. The export attributes are defined
     * for the whole import.
     * // TODO This implementation seems quite and static, has to be adjusted
     *
     * @param item the item to be checked if the attributes are set.
     * @return true, if all defined attributes are set.
     */
    private boolean checkItemAttributes(ItemModel item) {
        for (ExportAttribute attribute : exportAttributes) {
            try {
                switch (attribute) {
                    case id:
                        if (item.id == null) {
                            throw new MissingAttributeException("NULL", "item.id");
                        }
                        break;
                    case organizationalID:
                        if (item.organizationId == null) {
                            throw new MissingAttributeException(item.id, "item.organizationId");
                        }
                        break;
                    case name:
                        if (item.name == null) {
                            throw new MissingAttributeException(item.id, "item.name");
                        }
                        break;
                    case notes:
                        if (item.notes == null) {
                            throw new MissingAttributeException(item.id, "item.notes");
                        }
                        break;
                    case login:
                        if (item.login == null) {
                            throw new MissingAttributeException(item.id, "item.login");
                        }
                        break;
                    case loginURIs:
                        if (item.login.uris == null) {
                            throw new MissingAttributeException(item.id, "item.uris");
                        }
                        break;
                    case loginURI:
                        if (item.login.username == null) {
                            throw new MissingAttributeException(item.id, "item.username");
                        }
                        break;
                    case loginPassword:
                        if (item.login.password == null) {
                            throw new MissingAttributeException(item.id, "item.password");
                        }
                        break;
                    case totp:
                        if (item.login.totp == null) {
                            throw new MissingAttributeException(item.id, "item.login.totp");
                        }
                        break;
                    case collectionsIDs:
                        if (item.collectionIds == null) {
                            throw new MissingAttributeException(item.id, "item.collectionsIDs");
                        }
                        break;
                    case favorite:
                    case type:
                        // not nullabel
                        break;

                }
            } catch (MissingAttributeException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
