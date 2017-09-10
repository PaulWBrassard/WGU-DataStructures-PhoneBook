package phonebook;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * PhoneBook is a JavaFX application that uses a BinaryTree and a HashTable of
 * Contacts to show implementation of each data structure.
 *
 * @author Paul Brassard
 */
public class PhoneBook extends Application {

    private final HashTable<String, Contact> table = new HashTable<>();
    private final BinaryTree<Contact> tree = new BinaryTree<>();
    private final StringBuilder leftPrompt = new StringBuilder();
    private final StringBuilder rightPrompt = new StringBuilder();
    private VBox main, right;
    private HBox sides, exitBox, adtLabels, displays, radios;
    private Button exit, execute;
    private Label tableLabel, treeLabel, leftDisplay, rightDisplay,
            fName, lName, phone, email;
    private StackPane root;
    private TextField fNameTF, lNameTF, phoneTF, emailTF;
    private GridPane left;
    private ToggleGroup radioGroup;
    private RadioButton add, find, delete;
    private ScrollPane scrollPane;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    //PB - Javafx method run automatically with application start.
    @Override
    public void start(Stage primaryStage) {
        instantiateVariables();
        interconnectGUIComponents();
        addActionListeners();

        //PB - PrimaryStage configuration
        primaryStage.setTitle("Phone Book");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setWidth(600);
        primaryStage.setHeight(300);
    }

    //PB - Instatiates and configures all the variables needed in one place for ease.
    private void instantiateVariables() {
        root = new StackPane();
        main = new VBox(10);
        main.setPadding(new Insets(10));
        sides = new HBox(10);
        exit = new Button("Exit");
        exitBox = new HBox();
        exitBox.setAlignment(Pos.BASELINE_RIGHT);
        right = new VBox(10);
        tableLabel = new Label("Table");
        treeLabel = new Label("Tree");
        adtLabels = new HBox(150);
        scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setPrefViewportWidth(400);
        leftDisplay = new Label(leftPrompt.toString());
        leftDisplay.setMinWidth(170);
        rightDisplay = new Label(rightPrompt.toString());
        rightDisplay.setMinWidth(170);
        displays = new HBox(5);
        left = new GridPane();
        left.setHgap(10);
        left.setVgap(10);
        left.setMinWidth(200);
        fName = new Label("First Name: ");
        lName = new Label("Last Name: ");
        phone = new Label("Phone number: ");
        email = new Label("Email: ");
        phone.setMinWidth(100);
        fNameTF = new TextField();
        lNameTF = new TextField();
        phoneTF = new TextField();
        emailTF = new TextField();
        radioGroup = new ToggleGroup();
        add = new RadioButton("Add");
        add.setToggleGroup(radioGroup);
        add.setSelected(true);
        find = new RadioButton("Find");
        find.setToggleGroup(radioGroup);
        delete = new RadioButton("Delete");
        delete.setToggleGroup(radioGroup);
        radios = new HBox(10);
        execute = new Button("Execute");
        scene = new Scene(root, 300, 250);
        //PB - Use preferred styling
        scene.getStylesheets().add(PhoneBook.class.getResource("darkTheme.css").toExternalForm());
    }

    //PB - Interconnects the GUI components in the appropriate order and places.
    private void interconnectGUIComponents() {
        exitBox.getChildren().add(exit);
        radios.getChildren().addAll(add, find, delete);
        left.add(fName, 0, 0);
        left.add(fNameTF, 1, 0);
        left.add(lName, 0, 1);
        left.add(lNameTF, 1, 1);
        left.add(phone, 0, 2);
        left.add(phoneTF, 1, 2);
        left.add(email, 0, 3);
        left.add(emailTF, 1, 3);
        left.add(radios, 0, 4, 3, 1);
        left.add(execute, 1, 5);
        adtLabels.getChildren().addAll(tableLabel, treeLabel);
        displays.getChildren().addAll(leftDisplay, rightDisplay);
        scrollPane.setContent(displays);
        right.getChildren().addAll(adtLabels, scrollPane);
        sides.getChildren().addAll(left, right);
        main.getChildren().addAll(sides, exitBox);
        root.getChildren().add(main);
    }

    //PB - Creates all of the event handling in the GUI.
    private void addActionListeners() {
        //PB - Exit Button to end the program in the standard javafx method.
        exit.setOnAction((ActionEvent e) -> {
            Platform.exit();
        });
        //PB - RadioButton add makes all TextFields available.
        add.setOnAction((ActionEvent e) -> {
            emailTF.setEditable(true);
            emailTF.setText("");
            phoneTF.setEditable(true);
            phoneTF.setText("");
        });
        //PB - RadioButton find disables email and phone TextFields.
        find.setOnAction((ActionEvent e) -> {
            emailTF.setEditable(false);
            emailTF.setText("~disabled");
            phoneTF.setEditable(false);
            phoneTF.setText("~disabled");
        });
        //PB - RadioButton delete disables email and phone TextFields.
        delete.setOnAction((ActionEvent e) -> {
            emailTF.setEditable(false);
            emailTF.setText("~disabled");
            phoneTF.setEditable(false);
            phoneTF.setText("~disabled");
        });
        /* PB - Execute Button to act upon the data structures based on radio selection.
        * Left and right displays will give details of results for the table and tree
        * respectively.
         */
        execute.setOnAction((ActionEvent e) -> {
            //PB - First and last names are concatonated and capitalized 
            String name = fNameTF.getText() + " " + lNameTF.getText();
            name = name.toUpperCase();
            //PB - When add is selected, the entered contact is added to the data structures.
            if (add.isSelected()) {
                Contact record = new Contact(name);
                record.setPhone(phoneTF.getText());
                record.setEmail(emailTF.getText());
                table.add(name, record);
                tree.add(record);
                leftDisplay.setText(leftPrompt.append("~Record added to table: \n")
                        .append(record).append("\n").toString());
                rightDisplay.setText(rightPrompt.append("~Record added to tree: \n")
                        .append(record).append("\n").toString());
            }
            //PB - When find is selected, the data structures return the corresponding contact.
            if (find.isSelected()) {
                Contact tableValue = table.getValue(name);
                Contact treeValue = tree.getValue(new Contact(name));
                if (tableValue == null) {
                    leftDisplay.setText(leftPrompt.append("~Table record not found:\n")
                            .append(name).append("\n\n").toString());
                } else {
                    leftDisplay.setText(leftPrompt.append("~Table record found:\n")
                            .append(tableValue).append("\n").toString());
                }
                if (treeValue == null) {
                    rightDisplay.setText(rightPrompt.append("~Tree record not found:\n")
                            .append(name).append("\n\n").toString());
                } else {
                    rightDisplay.setText(rightPrompt.append("~Tree record found:\n")
                            .append(treeValue).append("\n").toString());
                }
            }
            //PB - When delete is selected, the data structures remove and return the corresponding contact.
            if (delete.isSelected()) {
                Contact tableValue = table.remove(name);
                Contact treeValue = tree.remove(new Contact(name));
                if (tableValue == null) {
                    leftDisplay.setText(leftPrompt.append("~Table record not found:\n")
                            .append(name).append("\n\n").toString());
                } else {
                    leftDisplay.setText(leftPrompt.append("~Table record deleted:\n")
                            .append(tableValue).append("\n").toString());
                }
                if (treeValue == null) {
                    rightDisplay.setText(rightPrompt.append("~Tree record not found:\n")
                            .append(name).append("\n\n").toString());
                } else {
                    rightDisplay.setText(rightPrompt.append("~Tree record deleted:\n")
                            .append(treeValue).append("\n").toString());
                }
            }
            //PB - Automatic scrolling based on leftDisplay height.
            scrollPane.vvalueProperty().bind(leftDisplay.heightProperty());
        });
    }
}
