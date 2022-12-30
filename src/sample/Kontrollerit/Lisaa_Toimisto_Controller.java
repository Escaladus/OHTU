package sample.Kontrollerit;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.luokat.Tietokanta_kasittelija;
import sample.luokat.Toimipiste;
import sample.luokat.Toimisto;

public class Lisaa_Toimisto_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tf_paikkakunta;

    @FXML
    private TextField tf_lahiosoite;

    @FXML
    private TextField tf_kapasiteetti;

    @FXML
    private TextField tf_lisatietoja;

    @FXML
    private TextField tf_nimi;

    @FXML
    private TextField tf_postinumero;

    @FXML
    private TextField tf_vuokra;

    @FXML
    private TextField tf_toimipiste;

    @FXML
    private TextField tf_pinta_ala;

    @FXML
    private Button btn_tallenna;

    @FXML
    private Button btn_peruuta;

    Tietokanta_kasittelija tk_kasittelija;
    Toimisto toimisto;

    @FXML
    void initialize() {
        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote()) {
                lisaa_toimisto();
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
    private void lisaa_toimisto() {

        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.toimisto = new Toimisto(
                    tk_kasittelija.seuraava_toimisto_id(),
                    Float.valueOf(tf_vuokra.getText()),
                    tf_lahiosoite.getText(),
                    tf_paikkakunta.getText(),
                    tf_postinumero.getText(),
                    tf_nimi.getText(),
                    tf_kapasiteetti.getText(),
                    tf_lisatietoja.getText(),
                    Integer.parseInt(tf_toimipiste.getText()),
                    tf_pinta_ala.getText()
            );

            tk_kasittelija.insert_toimisto(this.toimisto);
            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (SQLException se) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Toimisto");
            alert.setHeaderText("Toimisto on olemassa");
            alert.showAndWait();
        }
    }
    private boolean tarkista_syote() {
        boolean nimi=false, lahiosoite=false, paikkakunta=false, postinumero=false, vuokra = false, pita_ala = false;
        boolean lisaa_toimipiste = false, kapasiteetti = false, toimipiste = false;

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
        if(tf_kapasiteetti.getText().isEmpty()) {
            tf_kapasiteetti.setStyle("-fx-border-color: red");
        } else {
            tf_kapasiteetti.setStyle("-fx-border-color: grey");
            kapasiteetti = true;
        }
        if(tf_toimipiste.getText().isEmpty()) {
            tf_toimipiste.setStyle("-fx-border-color: red");
        } else {
            tf_toimipiste.setStyle("-fx-border-color: grey");
            toimipiste = true;
        }
        if(tf_vuokra.getText().isEmpty()) {
            tf_vuokra.setStyle("-fx-border-color: red");
        } else {
            tf_vuokra.setStyle("-fx-border-color: grey");
            vuokra = true;
        }
        if(tf_pinta_ala.getText().isEmpty()) {
            tf_pinta_ala.setStyle("-fx-border-color: red");
        } else {
            tf_pinta_ala.setStyle("-fx-border-color: grey");
            pita_ala = true;
        }
        if(nimi && lahiosoite && paikkakunta && postinumero && kapasiteetti && toimipiste && vuokra && pita_ala) {
            lisaa_toimipiste = true;
        }
        return lisaa_toimipiste;
    }

}
