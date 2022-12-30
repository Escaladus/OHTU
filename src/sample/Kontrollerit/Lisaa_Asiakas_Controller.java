package sample.Kontrollerit;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.luokat.Asiakas;
import sample.luokat.Tietokanta_kasittelija;
import sample.luokat.Yhteiso_asiakas;
import sample.luokat.Yritys_asiakas;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Lisaa_Asiakas_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField tf_sukunimi;
    @FXML
    private TextField tf_paikkakunta;
    @FXML
    private TextField tf_etunimi;
    @FXML
    private TextField tf_puhelin;
    @FXML
    private TextField tf_lahiosoite;
    @FXML
    private TextField tf_postinumero;
    @FXML
    private Button btn_tallenna;
    @FXML
    private Button btn_peruuta;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_yritys;
    @FXML
    private TextField tf_yhteiso;
    @FXML
    private TextField tf_y_tunnus;

    @FXML private RadioButton rb_henkilo;
    @FXML private RadioButton rb_yritys;
    @FXML private RadioButton rb_yhteiso;

    Tietokanta_kasittelija tk_kasittelija;
    Asiakas asiakas;
    Yritys_asiakas yritys_asiakas;
    Yhteiso_asiakas yhteiso_asiakas;

    final ToggleGroup tg_radiobutton = new ToggleGroup();

    @FXML
    void initialize() {

        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote() && rb_henkilo.isSelected()) {
                lisaa_asiakas();
            } else if(tarkista_syote() && rb_yritys.isSelected()) {
                lisaa_yritys_asiakas();
            } else if(tarkista_syote() && rb_yhteiso.isSelected()) {
                lisaa_yhteiso_asiakas();
            }
        });

        btn_peruuta.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Peruuta");
            alert.setHeaderText("Valitsemalla OK, kaikki tehdyt muutokset perutaan");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Peruuta");
            Optional<ButtonType> vastaus = alert.showAndWait();
            if(vastaus.get().equals(ButtonType.OK)) {
                Stage stage = (Stage) btn_peruuta.getScene().getWindow();
                stage.close();
            }
        });

        // Vain yksi valinta mahdollista radiobuttoneilla
        rb_henkilo.setToggleGroup(tg_radiobutton);
        rb_yritys.setToggleGroup(tg_radiobutton);
        rb_yhteiso.setToggleGroup(tg_radiobutton);
        // Alkuasetus valitaan radiobutton ja piilotetaan tekstikentät
        rb_henkilo.setSelected(true);
        tf_yritys.setVisible(false);
        tf_y_tunnus.setVisible(false);
        tf_yhteiso.setVisible(false);
        // Kuunnellaan yritys radiobuttonia, onko valittu vai ei
        rb_yritys.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                rb_yritys.setSelected(true);
                tf_yritys.setVisible(true);
                tf_y_tunnus.setVisible(true);
            } else {
                rb_yritys.setSelected(false);
                tf_yritys.setVisible(false);
                tf_y_tunnus.setVisible(false);
            }
        });
        // Kuunnellaan yhteisö radiobuttonia, onko valittu vai ei
        rb_yhteiso.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                rb_yhteiso.setSelected(true);
                tf_yhteiso.setVisible(true);

            } else {
                rb_yhteiso.setSelected(false);
                tf_yhteiso.setVisible(false);
            }
        });
    }

    /**
     * Lisää asiakkaan tietokantaan tekstikenttään syötetyistä tiedoista
     */
    private void lisaa_asiakas() {

        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.asiakas = new Asiakas(
                    tk_kasittelija.seuraava_asiakas_id(),
                    tf_etunimi.getText(),
                    tf_sukunimi.getText(),
                    tf_lahiosoite.getText(),
                    tf_paikkakunta.getText(),
                    tf_postinumero.getText(),
                    tf_puhelin.getText(),
                    tf_email.getText()
            );

            tk_kasittelija.insert_asiakas(this.asiakas);
            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (SQLException se) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Asiakas");
            alert.setHeaderText("Asiakas on olemassa");
            alert.showAndWait();
        }
    }

    /**
     * Lisää Yritys asiakkaan tietokantaan tekstikenttään syötetyistä tiedoista
     */
    private void lisaa_yritys_asiakas() {

        tk_kasittelija = new Tietokanta_kasittelija();

        try {

            this.yritys_asiakas = new Yritys_asiakas(
                    tk_kasittelija.seuraava_asiakas_id(),
                    tf_etunimi.getText(),
                    tf_sukunimi.getText(),
                    tf_lahiosoite.getText(),
                    tf_paikkakunta.getText(),
                    tf_postinumero.getText(),
                    tf_puhelin.getText(),
                    tf_email.getText(),
                    tk_kasittelija.seuraava_yritys_id(),
                    tf_yritys.getText(),
                    tf_y_tunnus.getText()
            );

            tk_kasittelija.insert_asiakas(this.yritys_asiakas);
            tk_kasittelija.insert_yritys_asiakas(this.yritys_asiakas);

            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (SQLException se) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Asiakas");
            alert.setHeaderText("Asiakas on olemassa");
            alert.showAndWait();
        }
    }

    private void lisaa_yhteiso_asiakas() {

        tk_kasittelija = new Tietokanta_kasittelija();

        try {

            this.yhteiso_asiakas
                    = new Yhteiso_asiakas(
                    tk_kasittelija.seuraava_asiakas_id(),
                    tf_etunimi.getText(),
                    tf_sukunimi.getText(),
                    tf_lahiosoite.getText(),
                    tf_paikkakunta.getText(),
                    tf_postinumero.getText(),
                    tf_puhelin.getText(),
                    tf_email.getText(),
                    tk_kasittelija.seuraava_yhteiso_id(),
                    tf_yhteiso.getText()
            );

            tk_kasittelija.insert_asiakas(this.yhteiso_asiakas);
            tk_kasittelija.insert_yhteiso_asiakas(this.yhteiso_asiakas);

            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (SQLException se) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Asiakas");
            alert.setHeaderText("Asiakas on olemassa");
            alert.showAndWait();
        }
    }
    private boolean tarkista_syote() {
        boolean etunimi=false, sukunimi=false, lahiosoite=false, paikkakunta=false, postinumero=false, puhelin=false, email=false;
        boolean lisaa_asiakas = false;

        if(tf_etunimi.getText().isEmpty()) {
            tf_etunimi.setStyle("-fx-border-color: red ; ");
        } else {
            tf_etunimi.setStyle("-fx-border-color: grey");
            etunimi = true;
        }
        if(tf_sukunimi.getText().isEmpty()) {
            tf_sukunimi.setStyle("-fx-border-color: red");
        } else {
            tf_sukunimi.setStyle("-fx-border-color: grey");
            sukunimi = true;
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
        if(tf_puhelin.getText().isEmpty()) {
            tf_puhelin.setStyle("-fx-border-color: red");
        } else {
            tf_puhelin.setStyle("-fx-border-color: grey");
            puhelin = true;
        }
        if(tf_email.getText().isEmpty()) {
            tf_email.setStyle("-fx-border-color: red");
        } else {
            tf_email.setStyle("-fx-border-color: grey");
            email = true;
        }
        if(etunimi && sukunimi && lahiosoite && paikkakunta && postinumero && puhelin && email) {
            lisaa_asiakas = true;
        }
        return lisaa_asiakas;
    }
}
