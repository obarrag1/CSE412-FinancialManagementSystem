module com.example.cse412financialmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.cse412financialmanagementsystem to javafx.fxml;
    exports com.example.cse412financialmanagementsystem;
}