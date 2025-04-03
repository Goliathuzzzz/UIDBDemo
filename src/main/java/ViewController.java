import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewController {

    private final TranslationsDAO dao = new TranslationsDAO();
    private final HashMap<String, String> codes = new HashMap<>();
    private final HashMap<String, String> countries = new HashMap<>();
    private ResourceBundle rb;
    private Locale locale;
    private String langCode = "en";

    @FXML
    private VBox root;

    @FXML
    private ComboBox<String> comboBoxLanguages;

    @FXML
    private ListView<String> textAreaMessages;

    @FXML
    private Label lblTitle, titleInputLabel, translationInputLabel;

    @FXML
    private Button btnUpdate, btnAdd;

    @FXML
    private TextField titleInput, translationInput;

    @FXML
    private void initialize() {
        locale = new Locale("en", "US");
        rb = ResourceBundle.getBundle("messages", locale);
        comboBoxLanguages.getItems().addAll("English", "French", "Chinese");
        comboBoxLanguages.setValue("English");
        comboBoxLanguages.setOnAction(actionEvent -> changeLanguage());
        btnAdd.setOnAction(actionEvent -> addTranslation());
        btnUpdate.setOnAction(actionEvent -> updateTranslation());
        codes.put("English", "en");
        codes.put("French", "fr");
        codes.put("Chinese", "zh");
        countries.put("en", "US");
        countries.put("fr", "FR");
        countries.put("zh", "CN");
        updateFields();
    }

    @FXML
    private void changeLanguage() {
        String language = comboBoxLanguages.getValue();
        langCode = codes.get(language);
        locale = new Locale(langCode, countries.get(langCode));
        rb = ResourceBundle.getBundle("messages", locale);
        updateFields();
    }

    private void updateFields() {
        List<String> translations = dao.fetchTranslation(langCode);
        textAreaMessages.getItems().clear();
        textAreaMessages.getItems().addAll(translations);
        lblTitle.setText(rb.getString("title"));
        btnUpdate.setText(rb.getString("update"));
        btnAdd.setText(rb.getString("add"));
        titleInputLabel.setText(rb.getString("title_input"));
        translationInputLabel.setText(rb.getString("translation_input"));
    }

    private void addTranslation() {
        dao.addTranslation(titleInput.getText(), langCode, translationInput.getText());
        updateFields();
    }

    private void updateTranslation() {
        dao.updateTranslation(titleInput.getText(), langCode, translationInput.getText());
        updateFields();
    }
}
