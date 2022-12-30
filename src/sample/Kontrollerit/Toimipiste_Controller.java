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
import sample.luokat.Toimipiste;
import sample.luokat.Tietokanta_kasittelija;

public class Toimipiste_Controller {

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
    Toimipiste toimipiste;

    @FXML protected TableView<Toimipiste> tbw_toimipiste;
    @FXML protected TableColumn<Toimipiste, Integer> tc_id;
    @FXML protected TableColumn<Toimipiste, String> tc_nimi;
    @FXML protected TableColumn<Toimipiste, String> tc_lahiosoite;
    @FXML protected TableColumn<Toimipiste, String> tc_paikkakunta;
    @FXML protected TableColumn<Toimipiste, String> tc_postinumero;

    @FXML
    void initialize() {

        // Haetaan toimipisteet tableviewiin
        hae_toimipisteet_tableview();

        // Muokkaa painiketta ei voi painaa ennen kuin on valinnut rivin tableviewistä
        btn_muokkaa.disableProperty().bind(Bindings.isEmpty(tbw_toimipiste.getSelectionModel().getSelectedItems()));

        btn_uusi.setOnAction(e -> {
            Stage stage;
            if (e.getSource().equals(btn_uusi)) {
                stage = new Stage();
                try {
                    FXMLLoader lisaa_toimipiste_loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/lisaa_toimipiste.fxml"));
                    Parent root = lisaa_toimipiste_loader.load();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_uusi.getScene().getWindow());
                    stage.showAndWait();
                    hae_toimipisteet_tableview();
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
                    FXMLLoader muokkaa_toimipiste_Loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/muokkaa_toimipiste.fxml"));
                    Parent root = muokkaa_toimipiste_Loader.load();

                    Muokkaa_Toimipiste_Controller muokkaa_toimipiste_controller = muokkaa_toimipiste_Loader.getController();
                    muokkaa_toimipiste_controller.hae_toimipiste_tiedot(valittu_toimipiste());

                    stage.setScene(new Scene(root));
//                    stage.initStyle(StageStyle.UNDECORATED);
//                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_muokkaa.getScene().getWindow());

                    stage.showAndWait();
                    hae_toimipisteet_tableview();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });

        tf_hae.textProperty().addListener(((observable, oldValue, newValue) -> {
            tbw_toimipiste.setItems(suodata_lista(tk_kasittelija.lataa_toimipiste_tiedot(), newValue));
        }));

    }

    /**
     * Asettaa toimipisteen tiedot tableview sarakkeisiin
     */
    public void hae_toimipisteet_tableview() {
        this.tk_kasittelija = new Tietokanta_kasittelija();

        tc_id.setCellValueFactory(new PropertyValueFactory<Toimipiste, Integer>("toimipiste_id"));
        tc_nimi.setCellValueFactory(new PropertyValueFactory<Toimipiste, String>("nimi"));
        tc_lahiosoite.setCellValueFactory(new PropertyValueFactory<Toimipiste, String>("lahiosoite"));
        tc_paikkakunta.setCellValueFactory(new PropertyValueFactory<Toimipiste, String>("paikkakunta"));
        tc_postinumero.setCellValueFactory(new PropertyValueFactory<Toimipiste, String>("postinumero"));

        tbw_toimipiste.setItems(this.tk_kasittelija.lataa_toimipiste_tiedot());
    }

    /**
     * Palauttaa tableviewissä valitun toimipiste-olion
     * @return
     */
    public Toimipiste valittu_toimipiste() {
        if (tbw_toimipiste.getSelectionModel().getSelectedItem() != null) {
            Toimipiste toimipiste = tbw_toimipiste.getSelectionModel().getSelectedItem();
            return toimipiste;
        }
        return toimipiste;
    }

    /**
     * Verrataan toimipisteen tietoja hakukentän tietoihin
     * @param toimipiste
     * @param haku_teksti
     * @return
     */
    private boolean etsi_sarakkeet(Toimipiste toimipiste, String haku_teksti){
        return  (toimipiste.getNimi().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimipiste.getLahiosoite().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimipiste.getPaikkakunta().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (toimipiste.getPostinumero().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (Integer.valueOf(toimipiste.getToimipiste_id()).toString().contains(haku_teksti.toLowerCase()));
    }

    /**
     * Vertaa hakukentän tekstiä toimipistelistaan
     * @param lista
     * @param uusi_arvo
     * @return
     */
    public ObservableList<Toimipiste>suodata_lista(List<Toimipiste> lista, String uusi_arvo) {
        List<Toimipiste>suodatettu_lista = new ArrayList<>();
        for(Toimipiste toimipiste : lista) {
            if(etsi_sarakkeet(toimipiste, uusi_arvo)) {
                suodatettu_lista.add(toimipiste);
            }
        }
        return FXCollections.observableList(suodatettu_lista);
    }
}