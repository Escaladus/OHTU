package sample.Kontrollerit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.luokat.Asiakas;
import sample.luokat.Tietokanta_kasittelija;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Muokkaa_Asiakas_Controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TextField tf_sukunimi;

    @FXML
    private Button btn_poista;

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


    Tietokanta_kasittelija tk_kasittelija;
    Asiakas asiakas;

    @FXML
    void initialize() {

        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote()) {
                muokkaa_asiakas();
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

        btn_poista.setOnAction(e -> {
            tk_kasittelija = new Tietokanta_kasittelija();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tietojen poisto");
            alert.setHeaderText("Haluatko varmasti poistaa tiedot");
            alert.setContentText("Vastaamalla OK tiedot poistetaan");
            Optional<ButtonType> vastaus = alert.showAndWait();
            if (vastaus.get().equals(ButtonType.OK)) {
                try {
                    System.out.println(asiakas);
                    tk_kasittelija.delete_asiakas(this.asiakas);
                    Stage stage = (Stage) btn_poista.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Haetaan asiakkaan tiedot ja asetetaan tiedot muokkaa asiakas lomakkeelle
     * @param asiakas
     */
    public void hae_asiakas_tiedot(Asiakas asiakas) {

        if (asiakas != null) {
            this.asiakas = asiakas;
            tf_etunimi.setText(this.asiakas.getEtunimi());
            tf_sukunimi.setText(this.asiakas.getSukunimi());
            tf_lahiosoite.setText(this.asiakas.getLahiosoite());
            tf_paikkakunta.setText(this.asiakas.getPaikkakunta());
            tf_postinumero.setText(this.asiakas.getPostinumero());
            tf_puhelin.setText(this.asiakas.getPuhelin());
            tf_email.setText(this.asiakas.getEmail());
        } else {

            tf_etunimi.setText("");
            tf_sukunimi.setText("");
            tf_lahiosoite.setText("");
            tf_paikkakunta.setText("");
            tf_postinumero.setText("");
            tf_puhelin.setText("");
            tf_email.setText("");
        }
    }
    public void muokkaa_asiakas() {
        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.asiakas = new Asiakas(
                    asiakas.getAsiakas_id(),
                    tf_etunimi.getText(),
                    tf_sukunimi.getText(),
                    tf_lahiosoite.getText(),
                    tf_paikkakunta.getText(),
                    tf_postinumero.getText(),
                    tf_puhelin.getText(),
                    tf_email.getText()
            );
            tk_kasittelija.update_asiakas(this.asiakas);
            Stage stage = (Stage) btn_tallenna.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
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
            tf_etunimi.setStyle("-fx-border-color: #ff0000 ; ");
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
