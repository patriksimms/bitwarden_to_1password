package logic;

import gui.FXMLDocumentController;
import logic.models.ImportModel;
import logic.models.ItemModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVCreator {

    private final FXMLDocumentController fxmlDocumentController;

    CSVCreator(FXMLDocumentController fxmlDocumentController) {
        this.fxmlDocumentController = fxmlDocumentController;
    }

    public String createCSV() {

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

        try {
            PrintWriter pw = new PrintWriter("test.csv");
            pw.println(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean checkItemAttributes(ItemModel item) {
        if (item.name != null) {
            if (item.login != null) {
                if (item.login.username != null && item.login.uris != null) {
                    if (item.login.password != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
