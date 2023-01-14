package comps311.mesapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.function.Consumer;

public class DBUtil {

    public static Connection getConnection()
            throws SQLException, IOException {
        var props = new Properties();
        try (var in = new FileInputStream("database.properties")) {
            props.load(in);
        }
        var url = props.getProperty("jdbc.url");
        var username = props.getProperty("jdbc.username");
        var password = props.getProperty("jdbc.password");
        var conn = DriverManager.getConnection(url, username, password);
        if (!tableExists(conn, "Messages")) {
            createTable(conn);
        }
        return conn;
    }

    private static boolean tableExists(Connection conn, String tableName)
            throws SQLException {
        var meta = conn.getMetaData();
        try (var rs = meta.getTables(null, null, null,
                new String[] {"TABLE"})) {
            while (rs.next()) {
                var name = rs.getString(3);
                if (tableName.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void createTable(Connection conn) throws SQLException {
        var sql = "CREATE TABLE Messages ( "
                + "Author VARCHAR(25), "
                + "MsgTime VARCHAR(20),"
                + "Text VARCHAR(1000), "
                + "Image CLOB(100M),"
                + "Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public static void insertMessage(Connection conn, Message message)
            throws SQLException {
        var sql = "INSERT INTO Messages (Author, Text, Image, MsgTime) "
                + "VALUES (?, ?, ?, ?)";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message.getAuthor());
            if (message.getImage() != null) {
                var clob = conn.createClob();
                clob.setString(1, message.getImage());
                stmt.setClob(3, clob);
                stmt.setNull(2, Types.VARCHAR);
                stmt.setString(4, message.getTime());
            } else {
                stmt.setString(2, message.getText());
                stmt.setNull(3, Types.CLOB);
                stmt.setString(4, message.getTime());
            }
            stmt.execute();
        }
    }

    public static void consumeMessages(Connection conn,
            Consumer<Message> consumer) throws SQLException {
        var sql = "SELECT Author, Text, Image, MsgTime FROM Messages "
                + "ORDER BY Created";
        try (var stmt = conn.createStatement();
                var rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                var message = new Message();
                var author = rs.getString(1);
                if (author != null) {
                    message.setAuthor(author.trim());
                }
                var text = rs.getString(2);
                if (text != null) {
                    text = text.trim();
                    message.setText(text.isEmpty() ? null : text);
                }
                var clob = rs.getClob(3);
                if (clob != null) {
                    int length = (int) clob.length();
                    var image = clob.getSubString(1, length).trim();
                    message.setImage(image.isEmpty() ? null : image);
                }
                
                var time = rs.getString(4);
                if (time != null) {
                    message.setTime(time.trim());
                }
                consumer.accept(message);
            }
        }
    }
}
