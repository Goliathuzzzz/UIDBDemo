import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranslationsDAO {

    private final String url = "jdbc:mariadb://localhost:3306/localizations";
    private final Connection con;

    public TranslationsDAO() {
        try {
            con = DriverManager.getConnection(url, "appuser", "maailmanilmaa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> fetchTranslation(String lang) {
        String query = "SELECT key_name, translation_text FROM translations WHERE language_code= ?";
        List<String> translationsList = new ArrayList<>();
        try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, lang);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            translationsList.add(rs.getString("key_name") + ": " + rs.getString("translation_text"));
        }
        return translationsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTranslation(String title, String langCode, String translation) {
        String query = "INSERT INTO translations (key_name, language_code, translation_text) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, langCode);
            ps.setString(3, translation);
            ps.executeUpdate();
            System.out.println("Translation added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTranslation(String title, String langCode, String translation) {
        String query = "UPDATE translations SET translation_text=? WHERE language_code=? AND key_name=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, translation);
            ps.setString(2, langCode);
            ps.setString(3, title);
            ps.executeUpdate();
            System.out.println("Translation updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
