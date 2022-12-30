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
import sample.luokat.Asiakas;
import sample.luokat.Tietokanta_kasittelija;
import sample.luokat.Yritys_asiakas;

public class Asiakashallinta_Controller {

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
    Asiakas asiakas;
    Yritys_asiakas yritys_asiakas;


    @FXML protected TableView<Asiakas> tbw_asiakas;
    @FXML protected TableColumn<Asiakas, Integer> tc_id;
    @FXML protected TableColumn<Asiakas, String> tc_etunimi;
    @FXML protected TableColumn<Asiakas, String> tc_sukunimi;
    @FXML protected TableColumn<Asiakas, String> tc_lahiosoite;
    @FXML protected TableColumn<Asiakas, String> tc_paikkakunta;
    @FXML protected TableColumn<Asiakas, String> tc_postinumero;
    @FXML protected TableColumn<Asiakas, String> tc_puhelin;
    @FXML protected TableColumn<Asiakas, String> tc_email;

    @FXML
    void initialize() {

        // Haetaan asiakkaat tableviewiin
        hae_asiakkaat_tableview();

        // Muokkaa painiketta ei voi painaa ennen kuin on valinnut rivin tableviewistä
        btn_muokkaa.disableProperty().bind(Bindings.isEmpty(tbw_asiakas.getSelectionModel().getSelectedItems()));

        btn_uusi.setOnAction(e -> {
            Stage stage;
            if (e.getSource().equals(btn_uusi)) {
                stage = new Stage();
                try {
                    FXMLLoader lisaa_asiakas_loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/lisaa_asiakas.fxml"));
                    Parent root = lisaa_asiakas_loader.load();
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_uusi.getScene().getWindow());
                    stage.showAndWait();
                    hae_asiakkaat_tableview();
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
                    FXMLLoader muokkaa_asiakas_Loader = new FXMLLoader(getClass().getResource("/sample/fxml_tiedostot/muokkaa_asiakas.fxml"));
                    Parent root = muokkaa_asiakas_Loader.load();

                    Muokkaa_Asiakas_Controller muokkaa_asiakas_controller = muokkaa_asiakas_Loader.getController();
                    muokkaa_asiakas_controller.hae_asiakas_tiedot(valittu_asiakas());
//                    System.out.println(valittu_asiakas());
                    stage.setScene(new Scene(root));
//                    stage.initStyle(StageStyle.UNDECORATED);
//                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn_muokkaa.getScene().getWindow());

                    stage.showAndWait();
                    hae_asiakkaat_tableview();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });

        tf_hae.textProperty().addListener(((observable, oldValue, newValue) -> {
            tbw_asiakas.setItems(suodata_lista(tk_kasittelija.lataa_asiakas_tiedot(), newValue));
        }));
    }

    /**
     * Asettaa asiakkaan tiedot tableview sarakkeisiin
     */
    public void hae_asiakkaat_tableview() {
        this.tk_kasittelija = new Tietokanta_kasittelija();

        tc_id.setCellValueFactory(new PropertyValueFactory<Asiakas, Integer>("asiakas_id"));
        tc_etunimi.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("etunimi"));
        tc_sukunimi.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("sukunimi"));
        tc_lahiosoite.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("lahiosoite"));
        tc_paikkakunta.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("paikkakunta"));
        tc_postinumero.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("postinumero"));
        tc_puhelin.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("puhelin"));
        tc_email.setCellValueFactory(new PropertyValueFactory<Asiakas, String>("email"));

        tbw_asiakas.setItems(this.tk_kasittelija.lataa_asiakas_tiedot());
    }

    /**
     * Palauttaa tableviewissä valitun asiakasolion
     * @return
     */
    public Asiakas valittu_asiakas() {
        if (tbw_asiakas.getSelectionModel().getSelectedItem() != null) {
            Asiakas asiakas = tbw_asiakas.getSelectionModel().getSelectedItem();
            return asiakas;
        }
        return asiakas;
    }

    /**
     * Verrataan asiakkaan tietoja hakukentän tietoihin
     * @param asiakas
     * @param haku_teksti
     * @return
     */
    private boolean etsi_sarakkeet(Asiakas asiakas, String haku_teksti){
        return  (asiakas.getEtunimi().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getSukunimi().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getLahiosoite().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getPaikkakunta().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getPostinumero().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getPuhelin().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (asiakas.getEmail().toLowerCase().contains(haku_teksti.toLowerCase())) ||
                (Integer.valueOf(asiakas.getAsiakas_id()).toString().contains(haku_teksti.toLowerCase()));
    }

    /**
     * Vertaa hakukentän tekstiä asiakaslistaan
     * @param lista
     * @param uusi_arvo
     * @return
     */
    public ObservableList<Asiakas>suodata_lista(List<Asiakas> lista, String uusi_arvo) {
        List<Asiakas>suodatettu_lista = new ArrayList<>();
        for(Asiakas asiakas : lista) {
            if(etsi_sarakkeet(asiakas, uusi_arvo)) {
                suodatettu_lista.add(asiakas);
            }
        }
        return FXCollections.observableList(suodatettu_lista);
    }
}