package sample.Kontrollerit;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.luokat.Toimipiste;
import sample.luokat.Tietokanta_kasittelija;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Lisaa_Toimipiste_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField tf_nimi;
    @FXML
    private TextField tf_paikkakunta;
    @FXML
    private TextField tf_lahiosoite;
    @FXML
    private TextField tf_postinumero;
    @FXML
    private Button btn_tallenna;
    @FXML
    private Button btn_peruuta;
    @FXML



    Tietokanta_kasittelija tk_kasittelija;
    Toimipiste toimipiste;

    @FXML
    void initialize() {

        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote()) {
                lisaa_toimipiste();
            }
        });

        btn_peruuta.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Peruuta");
            alert.setHeaderText("Valitsemalla OK, kaikki tehdyt muutokset perutaan");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Peruuta");
            Optional<ButtonType> vastaus = alert.showAndWait();
            if (vastaus.get().equals(ButtonType.OK)) {
                Stage stage = (Stage) btn_peruuta.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * Lisää toimipisteen tietokantaan tekstikenttään syötetyistä tiedoista
     */
    private void lisaa_toimipiste() {

        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.toimipiste = new Toimipiste(
                    tk_kasittelija.seuraava_toimipiste_id(),
                    tf_postinumero.getText(),
                    tf_paikkakunta.getText(),
                    tf_lahiosoite.getText(),
                    tf_nimi.getText()


            );

            tk_kasittelija.insert_toimipiste(this.toimipiste);
            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (SQLException se) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Toimipiste");
            alert.setHeaderText("Toimipiste on olemassa");
            alert.showAndWait();
        }
    }

    private boolean tarkista_syote() {
        boolean nimi=false, lahiosoite=false, paikkakunta=false, postinumero=false;
        boolean lisaa_toimipiste = false;

        if(tf_nimi.getText().isEmpty()) {
            tf_nimi.setStyle("-fx-border-color: red ; ");
        } else {
            tf_nimi.setStyle("-fx-border-color: grey");
            nimi = true;
        }
        if(tf_lahiosoite.getText().isEmpty()) {
            tf_lahiosoite.setStyle("-fx-border-color: red");
        } else {
            tf_lahiosoite.setStyle("-fx-border-color: grey");
            lahiosoite = true;
        }
        if(tf_paikkakunta.getText().isEmpty()) {
            tf_paikkakunta.setStyle("-fx-border-color: red");
        } else {
            tf_paikkakunta.setStyle("-fx-border-color: grey");
            paikkakunta = true;
        }
        if(tf_postinumero.getText().isEmpty()) {
            tf_postinumero.setStyle("-fx-border-color: red");
        } else {
            tf_postinumero.setStyle("-fx-border-color: grey");
            postinumero = true;
        }
        if(nimi && lahiosoite && paikkakunta && postinumero) {
            lisaa_toimipiste = true;
        }
        return lisaa_toimipiste;
    }
}
