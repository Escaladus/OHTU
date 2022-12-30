package sample.Kontrollerit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Varaus_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> tbw_asiakas;

    @FXML
    private TableColumn<?, ?> tc_toimisto_id;

    @FXML
    private TableColumn<?, ?> tc_lasku_id;

    @FXML
    private TextField tf_hae;

    @FXML
    private Button btn_muokkaa;

    @FXML
    private TableColumn<?, ?> tc_vuokraus_pvm;

    @FXML
    private Button btn_uusi;

    @FXML
    private TableColumn<?, ?> tc_aloitus_pvm;

    @FXML
    private TableColumn<?, ?> tc_id;

    @FXML
    private TableColumn<?, ?> tc_asiakas_id;

    @FXML
    private TableColumn<?, ?> tc_lopetus_pvm;

    @FXML
    void initialize() {

    }
}