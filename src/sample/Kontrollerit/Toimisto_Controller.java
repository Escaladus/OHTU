package sample.Kontrollerit;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.luokat.Tietokanta_kasittelija;
import sample.luokat.Toimipiste;
import sample.luokat.Toimisto;


public class Toimisto_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tf_hae;

    @FXML
    private Button btn_muokkaa;

    @FXML
    private Button btn_uusi;

    Tietokanta_kasittelija tk_kasittelija;
    Toimisto toimisto;


    @FXML protected TableView<Toimisto> tbw_toimisto;
    @FXML protected TableColumn<Toimisto, Integer> tc_id;
    @FXML protected TableColumn<Toimisto, String> tc_nimi;
    @FXML protected TableColumn<Toimisto, String> tc_paikkakunta;
    @FXML protected TableColumn<Toimisto, String> tc_lahiosoite;
    @FXML protected TableColumn<Toimisto, String> tc_postinumero;
    @FXML protected TableColumn<Toimisto, Float> tc_paiva_vuokra;
    @FXML protected TableColumn<Toimisto, String> tc_kapasiteetti;
    @FXML protected TableColumn<Toimisto, String> tc_lisatietoja;
    @FXML protected TableColumn<Toimisto, Integer> tc_toimipiste;
    @FXML protected TableColumn<Toimisto, String> tc_pinta_ala;


    @FXML
    void initialize() {
        // Haetaan toimistot tableviewiin
        hae_toimistot_tableviw();
        // Muokkaa painiketta ei voi painaa ennen kuin on valinnut rivin tableviewistä
        btn_muokkaa.disableProperty().bind(Bindings.isEmpty(tbw_toimisto.getSelectionModel().getSelectedItems()));

        btn_uusi.setOnAction(e -> {
            Stage stage;
            if (e.getSource().equals(btn_uusi)) {
                stage = new Stage();
                try {
                    FXMLLoader lisaa_toimisto_loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/lisaa_toimisto.fxml"));
                    Parent root = lisaa_toimisto_loader.load();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_uusi.getScene().getWindow());
                    stage.showAndWait();
                    hae_toimistot_tableviw();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });

        btn_muokkaa.setOnAction(e -> {
            Stage stage;
            if (e.getSource().equals(btn_muokkaa)) {
                stage = new Stage();
                try {
                    FXMLLoader muokkaa_toimisto_Loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/muokkaa_toimisto.fxml"));
                    Parent root = muokkaa_toimisto_Loader.load();

                    Muokkaa_Toimisto_Controller muokkaa_toimisto_controller = muokkaa_toimisto_Loader.getController();
                    muokkaa_toimisto_controller.hae_toimisto_tiedot(valittu_toimisto());

                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_muokkaa.getScene().getWindow());

                    stage.showAndWait();
                    hae_toimistot_tableviw();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });


        tf_hae.textProperty().addListener(((observable, oldValue, newValue) -> {
            tbw_toimisto.setItems(suodata_lista(tk_kasittelija.lataa_toimisto_tiedot(), newValue));
        }));

    }
    /**
     * Asettaa toimipisteen tiedot tableview sarakkeisiin
     */
    public void hae_toimistot_tableviw() {
        this.tk_kasittelija = new Tietokanta_kasittelija();

        tc_id.setCellValueFactory(new PropertyValueFactory<Toimisto, Integer>("toimisto_id"));
        tc_nimi.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("nimi"));
        tc_paikkakunta.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("paikkakunta"));
        tc_lahiosoite.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("lahiosoite"));
        tc_postinumero.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("postinumero"));
        tc_paiva_vuokra.setCellValueFactory(new PropertyValueFactory<Toimisto, Float>("paiva_vuokra"));
        tc_kapasiteetti.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("kapasiteetti"));
        tc_lisatietoja.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("lisatietoja"));
        tc_toimipiste.setCellValueFactory(new PropertyValueFactory<Toimisto, Integer>("toimipiste_id"));
        tc_pinta_ala.setCellValueFactory(new PropertyValueFactory<Toimisto, String>("pinta_ala"));

        tbw_toimisto.setItems(this.tk_kasittelija.lataa_toimisto_tiedot());
    }

    /**
     * Palauttaa tableviewissä valitun toimisto-olion
     * @return
     */
    public Toimisto valittu_toimisto() {
        if (tbw_toimisto.getSelectionModel().getSelectedItem() != null) {
            Toimisto toimisto = tbw_toimisto.getSelectionModel().getSelectedItem();
            return toimisto;
        }
        return toimisto;
    }
    /**
     * Verrataan toimiston tietoja hakukentän tietoihin
     * @param toimisto
     * @param haku_teksti
     * @return
     */
    private boolean etsi_sarakkeet(Toimisto toimisto, String haku_teksti){
        return  (toimisto.getNimi().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getLahiosoite().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getPaikkakunta().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getPostinumero().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getPaiva_vuokra().toString().contains(haku_teksti)) ||
                (toimisto.getKapasiteetti().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getLisatietoja().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimisto.getPinta_ala().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (Integer.valueOf(toimisto.getToimipiste_id()).toString().contains(haku_teksti.toLowerCase()));
    }
    /**
     * Vertaa hakukentän tekstiä toimipistelistaan
     * @param lista
     * @param uusi_arvo
     * @return
     */
    public ObservableList<Toimisto> suodata_lista(List<Toimisto> lista, String uusi_arvo) {
        List<Toimisto>suodatettu_lista = new ArrayList<>();
        for(Toimisto toimisto : lista) {
            if(etsi_sarakkeet(toimisto, uusi_arvo)) {
                suodatettu_lista.add(toimisto);
            }
        }
        return FXCollections.observableList(suodatettu_lista);
    }
}
