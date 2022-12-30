package sample.Kontrollerit;

import java.net.URL;
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

public class Muokkaa_Toimisto_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_poista;

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
    private Button btn_tallenna;

    @FXML
    private Button btn_peruuta;

    @FXML
    private TextField tf_vuokra;

    @FXML
    private TextField tf_toimipiste;

    @FXML
    private TextField tf_pinta_ala;

    Tietokanta_kasittelija tk_kasittelija;
    Toimisto toimisto;

    @FXML
    void initialize() {
        /**
         *
         */
        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote()) {
                muokkaa_toimisto();
                Stage stage = (Stage) btn_tallenna.getScene().getWindow();
                stage.close();
            }
        });
        /**
         *
         */
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
        /**
         *
         */
        btn_poista.setOnAction(e -> {
            tk_kasittelija = new Tietokanta_kasittelija();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tietojen poisto");
            alert.setHeaderText("Haluatko varmasti poistaa tiedot");
            alert.setContentText("Vastaamalla OK tiedot poistetaan");
            Optional<ButtonType> vastaus = alert.showAndWait();
            if (vastaus.get().equals(ButtonType.OK)) {
                try {
                    System.out.println(toimisto);
                    tk_kasittelija.delete_toimisto(this.toimisto);
                    Stage stage = (Stage) btn_poista.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }
    /**
     * Haetaan toimiston tiedot ja asetetaan tiedot muokkaa toimistot lomakkeelle
     * @param toimisto
     */
    public void hae_toimisto_tiedot(Toimisto toimisto) {
 // (Float.valueOf(toimisto.getPaiva_vuokra()).toString().contains(haku_teksti))
        if (toimisto != null) {
            this.toimisto = toimisto;
            tf_nimi.setText(this.toimisto.getNimi());
            tf_lahiosoite.setText(this.toimisto.getLahiosoite());
            tf_paikkakunta.setText(this.toimisto.getPaikkakunta());
            tf_postinumero.setText(this.toimisto.getPostinumero());
            tf_vuokra.setText(String.valueOf(this.toimisto.getPaiva_vuokra().toString()));
            tf_kapasiteetti.setText(this.toimisto.getKapasiteetti());
            tf_lisatietoja.setText(this.toimisto.getLisatietoja());
            tf_toimipiste.setText(String.valueOf(this.toimisto.getToimipiste_id()));
            tf_pinta_ala.setText(this.toimisto.getPinta_ala());
        } else {

            tf_nimi.setText("");
            tf_lahiosoite.setText("");
            tf_paikkakunta.setText("");
            tf_postinumero.setText("");
            tf_vuokra.setText("");
            tf_kapasiteetti.setText("");
            tf_lisatietoja.setText("");
            tf_toimipiste.setText("");
            tf_pinta_ala.setText("");
        }
    }
    public void muokkaa_toimisto() {
        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.toimisto = new Toimisto(
                    toimisto.getToimisto_id(),
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
            tk_kasittelija.update_toimisto(this.toimisto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean tarkista_syote() {
        boolean nimi=false, lahiosoite=false, paikkakunta=false, postinumero=false, vuokra = false, pinta_ala = false;
        boolean lisaa_toimipiste = false, kapasiteetti=false, toimipiste = false;

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
            pinta_ala = true;
        }

        if(nimi && lahiosoite && paikkakunta && postinumero && kapasiteetti && toimipiste && vuokra && pinta_ala) {
            lisaa_toimipiste = true;
        }
        return lisaa_toimipiste;
    }
}
