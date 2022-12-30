package sample.Kontrollerit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.luokat.Toimipiste;
import sample.luokat.Tietokanta_kasittelija;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Muokkaa_Toimipiste_Controller {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;


    @FXML
    private Button btn_poista;

    @FXML
    private TextField tf_paikkakunta;

    @FXML
    private TextField tf_nimi;

    @FXML
    private TextField tf_lahiosoite;

    @FXML
    private TextField tf_postinumero;

    @FXML
    private Button btn_tallenna;

    @FXML
    private Button btn_peruuta;

    Tietokanta_kasittelija tk_kasittelija;
    Toimipiste toimipiste;

    @FXML
    void initialize() {

        btn_tallenna.setOnAction(e -> {
            if(tarkista_syote()) {
                muokkaa_toimipiste();
                Stage stage = (Stage) btn_tallenna.getScene().getWindow();
                stage.close();
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
                    System.out.println(toimipiste);
                    tk_kasittelija.delete_toimipiste(this.toimipiste);
                    Stage stage = (Stage) btn_poista.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Haetaan toimipisteen tiedot ja asetetaan tiedot muokkaa toimipiste lomakkeelle
     * @param toimipiste
     */
    public void hae_toimipiste_tiedot(Toimipiste toimipiste) {

        if (toimipiste != null) {
            this.toimipiste = toimipiste;
            tf_nimi.setText(this.toimipiste.getNimi());
            tf_lahiosoite.setText(this.toimipiste.getLahiosoite());
            tf_paikkakunta.setText(this.toimipiste.getPaikkakunta());
            tf_postinumero.setText(this.toimipiste.getPostinumero());
        } else {

            tf_nimi.setText("");
            tf_lahiosoite.setText("");
            tf_paikkakunta.setText("");
            tf_postinumero.setText("");
        }
    }
    public void muokkaa_toimipiste() {
        tk_kasittelija = new Tietokanta_kasittelija();

        try {
            this.toimipiste = new Toimipiste(
                    toimipiste.getToimipiste_id(),
                    tf_postinumero.getText(),
                    tf_paikkakunta.getText(),
                    tf_lahiosoite.getText(),
                    tf_nimi.getText()

            );
            tk_kasittelija.update_toimipiste(this.toimipiste);

        } catch (Exception e) {
            e.printStackTrace();
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
