package sample.Kontrollerit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Valikko_Controller {



    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_varaus;

    @FXML
    private Button btn_laskutus;

    @FXML
    private Button btn_kirjaudu_ulos;

    @FXML
    private Button btn_toimisto;

    @FXML
    private Button btn_asiakashallinta;

    @FXML
    private Button btn_aloitus;

    @FXML
    private ImageView iv_keskinakyma;

    @FXML
    private Button btn_toimipiste;

    @FXML
    private Button btn_raportointi;


    @FXML
    private BorderPane bp_paanakyma;


    @FXML
    public void initialize() {

    }

    @FXML
    public void lataa_nakyma(String nakyma) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nakyma));
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        bp_paanakyma.setCenter(root);
    }


    @FXML
    private void aloitus_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/aloitus.fxml");
    }
    @FXML
    private void varaus_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/varaus.fxml");
    }
    @FXML
    private void asiakashallinta_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/asiakashallinta.fxml");
    }
    @FXML
    private void toimisto_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/toimisto.fxml");
    }
    @FXML
    private void toimipiste_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/toimipiste.fxml");
    }
    @FXML
    private void laskutus_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/laskutus.fxml");
    }
    @FXML
    private void raportointi_nakyma(MouseEvent event) {
        lataa_nakyma("/sample/fxml_tiedostot/raportointi.fxml");
    }
    @FXML private void kirjautuminen_nakyma(MouseEvent event) {
        btn_kirjaudu_ulos.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/fxml_tiedostot/kirjautuminen.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root,500,300));
        stage.setResizable(false);
        stage.show();
    }
    @FXML
    public void sulje_ohjelma(MouseEvent event) {
        Stage stage = (Stage) bp_paanakyma.getScene().getWindow();
        stage.close();
    }
}
