package sample.Kontrollerit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.luokat.Tietokanta_kasittelija;

public class Kirjautuminen_Controller {

    Tietokanta_kasittelija tk_kasittelija = new Tietokanta_kasittelija();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tf_Kayttajatunnus;

    @FXML
    private PasswordField pf_Salasana;

    @FXML
    private Button btn_sisaan;


    @FXML
    void initialize() {

        // Luodaan tietokanta
        tk_kasittelija.luo_tietokanta();
//        tk_kasittelija.kayta_tietokantaa();
        // Luodaan tietokannan taulut
        tk_kasittelija.luo_asiakas_taulu();
        tk_kasittelija.luo_yritys_taulu();
        tk_kasittelija.luo_yhteiso_taulu();
        tk_kasittelija.luo_toimipiste_taulu();
        tk_kasittelija.luo_laite_taulu();
        tk_kasittelija.luo_palvelu_taulu();
        tk_kasittelija.luo_toimisto_taulu();
        tk_kasittelija.luo_lasku_taulu();
        tk_kasittelija.luo_varaus_taulu();
        tk_kasittelija.luo_ostoskori_taulu();

        /**
         * Sisään pääsy toiminto, kun käyttäjä painaa näppäintä sissän
         * jos käyttäjätunnus täsmää ja salasana
         * käyttäjä pääsee aloitusikkunaan
         *
         */
        btn_sisaan.setOnAction(event -> {
            if(tarkistus(tf_Kayttajatunnus.getText(),pf_Salasana.getText())) {
                // Jos ehto toteutuu niin piloitetaan ikkunan
                btn_sisaan.getScene().getWindow().hide();
                // Luodaan loader
                FXMLLoader loader = new FXMLLoader();
                // annetaan loaderille polun
                loader.setLocation(getClass().getResource("/sample/fxml_tiedostot/valikko.fxml"));
                // load aiheuttaa poikeuksen, otetaan sen kiinni
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Luodaan Parent, Stage, Scene uutta ikkuna varten
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root,1200,800));
                stage.showAndWait();
            } else {
                // tarkistetaan täsmäkö käyttäjätunnus ja salasana
                eiTasma();
            }
        });
    }

    /**
     * Metodi tarkista syötettyen tietojen oikellisuuden
     * jos salasana ja käyttäjätunnus täsmää
     * käyttäjä pääsee pääikkunnaan (aloitusikkuna)
     *
     * @param k_tunnnus Käyttäjätunnus (syötetään text field:iin)
     * @param salasana Salasana (syötetöön pasword field:iin)
     * @return boolean arvo
     */
    private boolean tarkistus(String k_tunnnus, String salasana) {
        return (k_tunnnus.equals("root") && salasana.equals("toor"));
    }

    /**
     * Jos käyttäjätunnus tai salasana eivät täsmää
     * ilmoitetaan siitä käyttäjälle.
     */
    private void eiTasma() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Väärä salasana");
        alert.setContentText("Syötit väärän salasanan, yritä uudelleen.");
        alert.setHeaderText("Salasana tai käyttäjätunnus ei täsmää");
        alert.showAndWait();
    }
}
