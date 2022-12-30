package sample.luokat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Tietokanta_kasittelija {
    protected String osoite = "jdbc:mariadb://localhost:3306/";
    protected String kayttaja_tunnus = "root";
    protected String salasana = Configs.tietokannan_salasana;
    protected String tietokanta = "R02_varausjarjestelma";
    protected String valitse_tk = "USE R02_varausjarjestelma";

    protected Connection connection;

    /**
     * Tyhjä alustaja
     */
    public Tietokanta_kasittelija() {
    }

    /*
    #################### MUODOSTETAAN YHTEYS TIETOKANTAAN, LUODAAN TIETOKANTA JA TAULUT  ###############################
     */
    /**
     * Rakentaa url osoitteen tietokanta yhteyttä varten
     * @return url osoitteen
     */
    public String get_tietokanta_osoite() {
        String tk_osoite = osoite;
        String tk_kayttaja = "?user=" + kayttaja_tunnus;
        String tk_salasana = "&password=" + salasana;
        String tk_URL = tk_osoite + tk_kayttaja + tk_salasana;
        return tk_URL;
    }

    /**
     * Hakee yhteyden tietokantaan
     * @return
     */
    public Connection avaa_yhteys() {
        try {
            Connection conn = DriverManager.getConnection(get_tietokanta_osoite());
            this.connection = conn;
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Suljetaan yhteys tietokantaan
     */
    public void sulje_yhteys() {
        try {
            if (this.connection != null) {
//                System.out.println("sulje_yhteys metodi");
                this.connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Luodaan tietokanta
     */
    public void luo_tietokanta() {
        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery("CREATE DATABASE IF NOT EXISTS " + tietokanta);
            stmt.executeQuery(valitse_tk);
        } catch(SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();
    }

//    /**
//     * Käytetään tietokantaa
//     */
//    public void kayta_tietokantaa() {
//        avaa_yhteys();
//        try {
//            Statement stmt = this.connection.createStatement();
//            stmt.execute("USE " + tietokanta);
//            System.out.println("\t >> " + tietokanta + " valmis");
//        } catch(SQLException e){
//            System.out.println("\t >> yhteys epäonnistui " + tietokanta + "// kayta_tietokantaa metodi");
//        }
//        sulje_yhteys();
//    }

    /**
     * Luodaan asiakas taulu tietokantaan
     */
    public void luo_asiakas_taulu() {

        String sql_asiakas = "CREATE TABLE IF NOT EXISTS asiakas\n" +
                "(\n" +
                "  asiakas_id INT NOT NULL,\n" +
                "  etunimi VARCHAR(45) NOT NULL,\n" +
                "  sukunimi VARCHAR(45) NOT NULL,\n" +
                "  lahiosoite VARCHAR(45) NOT NULL,\n" +
                "  paikkakunta VARCHAR(45) NOT NULL,\n" +
                "  postinumero VARCHAR(45) NOT NULL,\n" +
                "  puhelin VARCHAR(45) NOT NULL,\n" +
                "  email VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (asiakas_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_asiakas);
            System.out.println("Luotu asiakas taulu");

        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Asiakas taulua ei pystytty luomaan");
        }
        sulje_yhteys();
    }

    /**
     * Luodaan laskutaulu tietokantaan
     */
    public void luo_lasku_taulu() {

        String sql_lasku = "CREATE TABLE IF NOT EXISTS lasku\n" +
                "(\n" +
                "  lasku_id INT NOT NULL,\n" +
                "  summa_alv_0 FLOAT NOT NULL,\n" +
                "  summa_alv FLOAT NOT NULL,\n" +
                "  viitenumero VARCHAR(45) NOT NULL,\n" +
                "  saajan_tilinumero VARCHAR(45) NOT NULL,\n" +
                "  saajan_nimi VARCHAR(45) NOT NULL,\n" +
                "  erapaiva DATE NOT NULL,\n" +
                "  PRIMARY KEY (lasku_id),\n" +
                "  UNIQUE (viitenumero)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_lasku);
            System.out.println("Luotu laskutaulu");

        } catch (SQLException es) {
            System.out.println("Laskutaulua ei pystytty luomaan");
            es.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan toimipistetaulu tietokantaan
     */
    public void luo_toimipiste_taulu() {

        String sql_toimipiste = "CREATE TABLE IF NOT EXISTS toimipiste\n" +
                "(\n" +
                "  toimipiste_id INT NOT NULL,\n" +
                "  postinumero VARCHAR(45) NOT NULL,\n" +
                "  paikkakunta VARCHAR(45) NOT NULL,\n" +
                "  lahiosoite VARCHAR(45) NOT NULL,\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  PRIMARY KEY (toimipiste_id)\n" +
                ");";


        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_toimipiste);
            System.out.println("Luotu toimipistetaulu");

        } catch (SQLException se) {
            System.out.println("Toimipistetaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan palvelutaulu tietokantaan
     */
    public void luo_palvelu_taulu() {

        String sql_palvelu = "CREATE TABLE IF NOT EXISTS palvelu\n" +
                "(\n" +
                "  palvelu_id INT NOT NULL,\n" +
                "  paiva_hinta FLOAT NOT NULL,\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  toimipiste_id INT NOT NULL,\n" +
                "  PRIMARY KEY (palvelu_id),\n" +
                "  FOREIGN KEY (toimipiste_id) REFERENCES toimipiste(toimipiste_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_palvelu);
            System.out.println("Luotu palvelutaulu");

        } catch (SQLException se) {
            System.out.println("Palvelutaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }
    public void luo_laite_taulu() {

        String sql_laite = "CREATE TABLE IF NOT EXISTS laite\n" +
                "(\n" +
                "  laite_id INT NOT NULL,\n" +
                "  paiva_hinta FLOAT NOT NULL,\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  toimipiste_id INT NOT NULL,\n" +
                "  PRIMARY KEY (laite_id),\n" +
                "  FOREIGN KEY (toimipiste_id) REFERENCES toimipiste(toimipiste_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_laite);
            System.out.println("Luotu laitetaulu");

        } catch (SQLException se) {
            System.out.println("Laitetaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan yritys taulu tietokantaan
     */
    public void luo_yritys_taulu() {

        String sql_yritys = "CREATE TABLE IF NOT EXISTS yritys\n" +
                "(\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  y_tunnus VARCHAR(45) NOT NULL,\n" +
                "  yritys_id INT NOT NULL,\n" +
                "  asiakas_id INT NOT NULL,\n" +
                "  PRIMARY KEY (yritys_id),\n" +
                "  FOREIGN KEY (asiakas_id) REFERENCES asiakas(asiakas_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_yritys);
            System.out.println("Luotu yritys taulu");

        } catch (SQLException se) {
            System.out.println("Yritys taulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan yhteisötaulu tietokantaan
     */
    public void luo_yhteiso_taulu() {

        String sql_yhteiso = "CREATE TABLE IF NOT EXISTS yhteiso\n" +
                "(\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  yhteiso_id INT NOT NULL,\n" +
                "  asiakas_id INT NOT NULL,\n" +
                "  PRIMARY KEY (yhteiso_id),\n" +
                "  FOREIGN KEY (asiakas_id) REFERENCES asiakas(asiakas_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_yhteiso);
            System.out.println("Luotu yhteisötaulu");
        } catch (SQLException se) {
            System.out.println("Yhteisötaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan toimistotaulu tietokantaan
     */
    public void luo_toimisto_taulu() {

        String sql_toimisto = "CREATE TABLE IF NOT EXISTS toimisto\n" +
                "(\n" +
                "  toimisto_id INT NOT NULL,\n" +
                "  paiva_vuokra FLOAT NOT NULL,\n" +
                "  pinta_ala VARCHAR(45) NOT NULL,\n" +
                "  lahiosoite VARCHAR(45) NOT NULL,\n" +
                "  paikkakunta VARCHAR(45) NOT NULL,\n" +
                "  postinumero VARCHAR(45) NOT NULL,\n" +
                "  nimi VARCHAR(45) NOT NULL,\n" +
                "  kapasiteetti VARCHAR(45) NOT NULL,\n" +
                "  lisatietoja VARCHAR(45),\n" +
                "  toimipiste_id INT NOT NULL,\n" +
                "  PRIMARY KEY (toimisto_id),\n" +
                "  FOREIGN KEY (toimipiste_id) REFERENCES toimipiste(toimipiste_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_toimisto);
            System.out.println("Luotu toimistotaulu");
        } catch (SQLException se) {
            System.out.println("Toimistotaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaa varaus taulu tietokantaan
     */
    public void luo_varaus_taulu() {

        String sql_varaus = "CREATE TABLE IF NOT EXISTS varaus\n" +
                "(\n" +
                "  varaus_id INT NOT NULL,\n" +
                "  aloitus_pvm DATE NOT NULL,\n" +
                "  lopetus_pvm DATE NOT NULL,\n" +
                "  vuokraus_paiva DATE NOT NULL,\n" +
                "  asiakas_id INT NOT NULL,\n" +
                "  toimisto_id INT NOT NULL,\n" +
                "  lasku_id INT NOT NULL,\n" +
                "  PRIMARY KEY (varaus_id),\n" +
                "  FOREIGN KEY (asiakas_id) REFERENCES asiakas(asiakas_id),\n" +
                "  FOREIGN KEY (toimisto_id) REFERENCES toimisto(toimisto_id),\n" +
                "  FOREIGN KEY (lasku_id) REFERENCES lasku(lasku_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_varaus);
            System.out.println("Luotu varaustaulu");

        } catch (SQLException se) {
            System.out.println("Varaustaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Luodaan ostoskoritaulu tietokantaan
     */
    public void luo_ostoskori_taulu() {

        String sql_ostoskori = "CREATE TABLE IF NOT EXISTS ostoskori\n" +
                "(\n" +
                "  ostoskori_id INT NOT NULL,\n" +
                "  palvelu_id INT NOT NULL,\n" +
                "  varaus_id INT NOT NULL,\n" +
                "  laite_id INT NOT NULL,\n" +
                "  PRIMARY KEY (ostoskori_id),\n" +
                "  FOREIGN KEY (palvelu_id) REFERENCES palvelu(palvelu_id),\n" +
                "  FOREIGN KEY (varaus_id) REFERENCES varaus(varaus_id),\n" +
                "  FOREIGN KEY (laite_id) REFERENCES laite(laite_id)\n" +
                ");";

        avaa_yhteys();
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt.executeQuery(sql_ostoskori);
            System.out.println("Luotu ostoskoritaulu");
        } catch (SQLException se) {
            System.out.println("Ostoskoritaulua ei pystytty luomaan");
            se.printStackTrace();
        }
        sulje_yhteys();
    }




    /*
    ####################################### ALOITUS NÄKYMÄN KYSELYT ####################################################
     */

    /*
    ###################################### ASIAKASHALLINTA NÄKYMÄN KYSELYT #############################################
     */

    /**
     * Etsitään seuraava asiakas id
     * @return
     */
    public int seuraava_asiakas_id() {

        int seuraava_id = 0;
        String sql ="SELECT asiakas_id \r\n"
                + "FROM asiakas\r\n"
                + "ORDER BY asiakas_id DESC\r\n"
                + "LIMIT 1;";

        avaa_yhteys();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery(sql);

            while ( resultset.next() ) {
                seuraava_id = resultset.getInt("asiakas_id");
            }
            stmt.close();
            resultset.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();

        return seuraava_id + 1;
    }

    /**
     * Lisätään uusi asiakas tietokantaan
     * @param asiakas
     */
    public void insert_asiakas(Asiakas asiakas) throws SQLException {

        String sql_select_asiakas = "SELECT * FROM asiakas WHERE etunimi=? AND sukunimi=? AND lahiosoite=?";

        avaa_yhteys();

        ResultSet resultset = null;

        PreparedStatement pstmt = null;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql_select_asiakas);
            pstmt.setString(1, asiakas.getEtunimi());
            pstmt.setString(2, asiakas.getSukunimi());
            pstmt.setString(3, asiakas.getLahiosoite());
            resultset = pstmt.executeQuery();

            if (resultset.next()) {
                throw new SQLException("Asiakas on olemassa");
            }
            else {

                String sql_insert_asiakas = "INSERT INTO asiakas(asiakas_id, etunimi,sukunimi,lahiosoite,paikkakunta,postinumero,puhelin,email)" +
                        "VALUES(?,?,?,?,?,?,?,?)";

                try {
                    stmt = this.connection.createStatement();
                    pstmt = this.connection.prepareStatement(sql_insert_asiakas);

                    pstmt.setInt(1, asiakas.getAsiakas_id());
                    pstmt.setString(2, asiakas.getEtunimi());
                    pstmt.setString(3, asiakas.getSukunimi());
                    pstmt.setString(4, asiakas.getLahiosoite());
                    pstmt.setString(5, asiakas.getPaikkakunta());
                    pstmt.setString(6, asiakas.getPostinumero());
                    pstmt.setString(7, asiakas.getPuhelin());
                    pstmt.setString(8, asiakas.getEmail());

                    pstmt.execute();
                    pstmt.close();
                    stmt.close();

                } catch (SQLException se) {
                    throw se;
                }
            }
        }catch (SQLException se) {
            throw se;
        }
        sulje_yhteys();
    }

        /**
     * Päivitetään olemassa olevan asiakkaan tiedot uusilla tiedoilla
     * @param asiakas
     * @return
     */
    public Asiakas update_asiakas(Asiakas asiakas) throws SQLException {

        avaa_yhteys();

        String sql = "SELECT asiakas_id"
                + " FROM asiakas WHERE asiakas_id = ?";

        ResultSet resultset = null;
        PreparedStatement pstmt = null;
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt( 1, asiakas.getAsiakas_id());
            resultset = pstmt.executeQuery();

            if (!resultset.next()) {
                throw new Exception("Asiakasta ei löydy");
            }
            stmt.close();
        } catch (Exception se) {
            se.printStackTrace();
        }

        String sql_select_asiakas = "SELECT * FROM asiakas WHERE etunimi=? AND sukunimi=? AND lahiosoite=?";

        try {

            pstmt = connection.prepareStatement(sql_select_asiakas);
            pstmt.setString(1, asiakas.getEtunimi());
            pstmt.setString(2, asiakas.getSukunimi());
            pstmt.setString(3, asiakas.getLahiosoite());
            resultset = pstmt.executeQuery();

            if (resultset.next()) {
                throw new SQLException("Asiakas on olemassa");
            } else {
                String sql_update = "UPDATE asiakas SET etunimi=?, sukunimi=?, lahiosoite=?,paikkakunta=?, postinumero=?, puhelin=?, email=? " +
                        "WHERE asiakas_id=?";

                try {
                    pstmt = this.connection.prepareStatement(sql_update);

                    pstmt.setString(1, asiakas.getEtunimi());
                    pstmt.setString(2, asiakas.getSukunimi());
                    pstmt.setString(3, asiakas.getLahiosoite());
                    pstmt.setString(4, asiakas.getPaikkakunta());
                    pstmt.setString(5, asiakas.getPostinumero());
                    pstmt.setString(6, asiakas.getPuhelin());
                    pstmt.setString(7, asiakas.getEmail());
                    pstmt.setInt(8, asiakas.getAsiakas_id());

                    int result = pstmt.executeUpdate();

                    if (result == 0) {
                        throw new SQLException("Virhe asiakastietojen päivityksessä");
                    }
                    pstmt.executeUpdate();
                    resultset.close();
                    pstmt.close();

                } catch (SQLException se) {
                    System.out.println("Update asiakas => Epäonnistui");
                }
            }
        }catch (SQLException se) {
            throw se;
        }
        sulje_yhteys();
        return asiakas;
    }

    /**
     * Poistaa asiakkaan tiedot tietokannasta
     * @param asiakas
     * @throws Exception
     */
    public void delete_asiakas (Asiakas asiakas) throws Exception {

        avaa_yhteys();

        String sqlDelete = "DELETE FROM asiakas WHERE asiakas_id = ?";

        PreparedStatement pstmt;
        Statement stmt = this.connection.createStatement();
        stmt.executeQuery(valitse_tk);
        pstmt = connection.prepareStatement(sqlDelete);
        pstmt.setInt( 1, asiakas.getAsiakas_id());
        int result = pstmt.executeUpdate();
        if (result == 0) {
            throw new Exception("Asiakasta ei pystytty poistamaan");
        }
        pstmt.close();
        sulje_yhteys();
    }

    /**
     * Lataa kaikki asiakastaulussa olevat asiakkaat Observablelistaan
     * @return
     */
    public ObservableList<Asiakas> lataa_asiakas_tiedot() {

        avaa_yhteys();

        ObservableList<Asiakas> lista= FXCollections.observableArrayList();
        lista.removeAll();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery("SELECT * FROM asiakas");

            while ( resultset.next() ) {
                lista.add(new Asiakas(
                        resultset.getInt("asiakas_id"),
                        resultset.getString("etunimi"),
                        resultset.getString("sukunimi"),
                        resultset.getString("lahiosoite"),
                        resultset.getString("paikkakunta"),
                        resultset.getString("postinumero"),
                        resultset.getString("puhelin"),
                        resultset.getString("email")
                ));
            }
            stmt.close();
            resultset.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        sulje_yhteys();
        return lista;
    }

    /**
     * Etsitään seuraava yritys id
     * @return seuraava yritys id
     */
    public int seuraava_yritys_id() {

        int seuraava_id = 0;
        String sql ="SELECT yritys_id \r\n"
                + "FROM yritys\r\n"
                + "ORDER BY yritys_id DESC\r\n"
                + "LIMIT 1;";

        avaa_yhteys();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery(sql);

            while ( resultset.next() ) {
                seuraava_id = resultset.getInt("yritys_id");
            }
            stmt.close();
            resultset.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();

        return seuraava_id + 1;
    }

    /**
     * Lisätään yritysasiakas tietokantaan
     * @param yritys_asiakas
     */
    public void insert_yritys_asiakas(Yritys_asiakas yritys_asiakas) {

        String sql_insert_yritys = "INSERT INTO yritys(yritys_id, nimi,y_tunnus,asiakas_id)" +
                "VALUES(?,?,?,?)";

        avaa_yhteys();

        PreparedStatement pstmt = null;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt = this.connection.createStatement();
            pstmt = this.connection.prepareStatement(sql_insert_yritys);

            pstmt.setInt(1, yritys_asiakas.getYritys_id());
            pstmt.setString(2, yritys_asiakas.getNimi());
            pstmt.setString(3, yritys_asiakas.getY_tunnus());
            pstmt.setInt(4, yritys_asiakas.getAsiakas_id());

            pstmt.execute();
            pstmt.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /**
     * Etsitään seuraava yhteisö id
     * @return seuraava yhteisö id
     */
    public int seuraava_yhteiso_id() {

        int seuraava_id = 0;

        String sql ="SELECT yhteiso_id \r\n"
                + "FROM yhteiso\r\n"
                + "ORDER BY yhteiso_id DESC\r\n"
                + "LIMIT 1;";

        avaa_yhteys();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery(sql);

            while ( resultset.next() ) {
                seuraava_id = resultset.getInt("yhteiso_id");
            }
            stmt.close();
            resultset.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();

        return seuraava_id + 1;
    }

    /**
     * Lisätään yhteisöasiakas tietokantaan
     * @param yhteiso_asiakas
     */
    public void insert_yhteiso_asiakas(Yhteiso_asiakas yhteiso_asiakas) {

        String sql_insert_yhteiso = "INSERT INTO yhteiso(yhteiso_id, nimi, asiakas_id)" +
                "VALUES(?,?,?)";

        avaa_yhteys();

        PreparedStatement pstmt = null;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            stmt = this.connection.createStatement();
            pstmt = this.connection.prepareStatement(sql_insert_yhteiso);

            pstmt.setInt(1, yhteiso_asiakas.getYhteiso_id());
            pstmt.setString(2, yhteiso_asiakas.getNimi());
            pstmt.setInt(3, yhteiso_asiakas.getAsiakas_id());

            pstmt.execute();
            pstmt.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();
    }

    /*
    ###################################### KIRJAUTUMIS NÄKYMÄN KYSELYT #################################################
     */

    /*
    ###################################### LASKUTUS NÄKYMÄN KYSELYT ####################################################
     */

    /*
    ###################################### RAPORTOINIT NÄKYMÄN KYSELYT #################################################
     */

    /*
    ###################################### TOIMIPISTE NÄKYMÄN KYSELYT  #################################################
     */

    /**
     * Etsitään seuraava toimipiste id
     * @return
     */
    public int seuraava_toimipiste_id() {

        int seuraava_id = 0;
        String sql ="SELECT toimipiste_id \r\n"
                + "FROM toimipiste\r\n"
                + "ORDER BY toimipiste_id DESC\r\n"
                + "LIMIT 1;";

        avaa_yhteys();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery(sql);

            while ( resultset.next() ) {
                seuraava_id = resultset.getInt("toimipiste_id");
            }
            stmt.close();
            resultset.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();

        return seuraava_id + 1;
    }

    /**
     * Lisätään uusi toimipiste tietokantaan
     * @param toimipiste
     */
    public void insert_toimipiste(Toimipiste toimipiste) throws SQLException {
        String sql_select_toimipiste = "SELECT * FROM toimipiste WHERE nimi=? AND lahiosoite=? AND paikkakunta=?";

        avaa_yhteys();

        ResultSet resultset = null;

        PreparedStatement pstmt = null;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql_select_toimipiste);
            pstmt.setString(1, toimipiste.getNimi());
            pstmt.setString(2, toimipiste.getLahiosoite());
            pstmt.setString(3, toimipiste.getPaikkakunta());
            resultset = pstmt.executeQuery();

            if (resultset.next()) {
                throw new SQLException("Toimipiste on olemassa");
            }
            else {

                String sql_insert_toimipiste = "INSERT INTO toimipiste(toimipiste_id,postinumero,paikkakunta,lahiosoite, nimi)" +
                        "VALUES(?,?,?,?,?)";

                try {
                    stmt = this.connection.createStatement();
                    pstmt = this.connection.prepareStatement(sql_insert_toimipiste);

                    pstmt.setInt(1, toimipiste.getToimipiste_id());
                    pstmt.setString(2, toimipiste.getPostinumero());
                    pstmt.setString(3, toimipiste.getPaikkakunta());
                    pstmt.setString(4, toimipiste.getLahiosoite());
                    pstmt.setString(5, toimipiste.getNimi());

                    pstmt.execute();
                    pstmt.close();
                    stmt.close();

                } catch (SQLException se) {
                    throw se;
                }
            }
        }catch (SQLException se) {
            throw se;
        }
        sulje_yhteys();
    }

    /**
     * Päivitetään olemassa olevan toimipisteen tiedot uusilla tiedoilla
     * @param toimipiste
     * @return
     */
    public Toimipiste update_toimipiste(Toimipiste toimipiste) throws SQLException {

        avaa_yhteys();

        String sql = "SELECT toimipiste_id"
                + " FROM toimipiste WHERE toimipiste_id = ?";

        ResultSet resultset = null;
        PreparedStatement pstmt = null;
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt( 1, toimipiste.getToimipiste_id());
            resultset = pstmt.executeQuery();

            if (!resultset.next()) {
                throw new Exception("Toimipistettä ei löydy");
            }
            stmt.close();
        } catch (Exception se) {
            se.printStackTrace();
        }

        String sql_select_toimipiste = "SELECT * FROM toimipiste WHERE nimi=? AND lahiosoite=?";

        try {

            pstmt = connection.prepareStatement(sql_select_toimipiste);
            pstmt.setString(1, toimipiste.getNimi());
            pstmt.setString(2, toimipiste.getLahiosoite());
            resultset = pstmt.executeQuery();

            if (resultset.next()) {
                throw new SQLException("Toimipiste on olemassa");
            } else {
                String sql_update = "UPDATE toimipiste SET nimi=?, lahiosoite=?, paikkakunta=?, postinumero=? " +
                        "WHERE toimipiste_id=?";

                try {
                    pstmt = this.connection.prepareStatement(sql_update);

                    pstmt.setString(1, toimipiste.getNimi());
                    pstmt.setString(2, toimipiste.getLahiosoite());
                    pstmt.setString(3, toimipiste.getPaikkakunta());
                    pstmt.setString(4, toimipiste.getPostinumero());
                    pstmt.setInt(5, toimipiste.getToimipiste_id());

                    int result = pstmt.executeUpdate();

                    if (result == 0) {
                        throw new SQLException("Virhe toimipistetietojen päivityksessä");
                    }
                    pstmt.executeUpdate();
                    resultset.close();
                    pstmt.close();

                } catch (SQLException se) {
                    System.out.println("Update toimipiste => Epäonnistui");
                }
            }
        }catch (SQLException se) {
            throw se;
        }
        sulje_yhteys();
        return toimipiste;
    }

    /**
     * Poistaa toimipisteeb tiedot tietokannasta
     * @param toimipiste
     * @throws Exception
     */
    public void delete_toimipiste (Toimipiste toimipiste) throws Exception {

        avaa_yhteys();

        String sqlDelete = "DELETE FROM toimipiste WHERE toimipiste_id=?";

        PreparedStatement pstmt;
        Statement stmt = this.connection.createStatement();
        stmt.executeQuery(valitse_tk);
        pstmt = connection.prepareStatement(sqlDelete);
        pstmt.setInt( 1, toimipiste.getToimipiste_id());
        int result = pstmt.executeUpdate();
        if (result == 0) {
            throw new Exception("Toimipistettä ei pystytty poistamaan");
        }
        pstmt.close();
        sulje_yhteys();
    }

    /**
     * Lataa kaikki toimipistetaulussa olevat asiakkaat Observablelistaan
     * @return
     */
    public ObservableList<Toimipiste> lataa_toimipiste_tiedot() {

        avaa_yhteys();

        ObservableList<Toimipiste> lista= FXCollections.observableArrayList();
        lista.removeAll();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery("SELECT * FROM toimipiste");

            while ( resultset.next() ) {
                lista.add(new Toimipiste(
                        resultset.getInt("toimipiste_id"),
                        resultset.getString("postinumero"),
                        resultset.getString("paikkakunta"),
                        resultset.getString("lahiosoite"),
                        resultset.getString("nimi")
                ));
            }
            stmt.close();
            resultset.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        sulje_yhteys();
        return lista;
    }

    /*
    ###################################### TOIMISTO NÄKYMÄN KYSELYT  ###################################################
     */
    /**
     * Etsitään seuraava toimipiste id
     * @return
     */
    public int seuraava_toimisto_id() {

        int seuraava_id = 0;
        String sql ="SELECT toimisto_id \r\n"
                + "FROM toimisto\r\n"
                + "ORDER BY toimisto_id DESC\r\n"
                + "LIMIT 1;";

        avaa_yhteys();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery(sql);

            while ( resultset.next() ) {
                seuraava_id = resultset.getInt("toimisto_id");
            }
            stmt.close();
            resultset.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }
        sulje_yhteys();

        return seuraava_id + 1;
    }

    /**
     * Lisätään uusi toimisto tietokantaan
     * @param toimisto
     */
    public void insert_toimisto(Toimisto toimisto) throws SQLException {
        String sql_select_toimisto = "SELECT * FROM toimisto WHERE nimi=? AND lahiosoite=? AND paikkakunta=? AND toimipiste_id=?";

        avaa_yhteys();

        ResultSet resultset = null;

        PreparedStatement pstmt = null;

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql_select_toimisto);
            pstmt.setString(1, toimisto.getNimi());
            pstmt.setString(2, toimisto.getLahiosoite());
            pstmt.setString(3, toimisto.getPaikkakunta());
            pstmt.setString(4, String.valueOf(toimisto.getToimisto_id()));
            resultset = pstmt.executeQuery();

            if (resultset.next()) {
                throw new SQLException("Toimisto on olemassa");
            }
            else {

                String sql_insert_toimipiste = "INSERT INTO toimisto (toimisto_id,paiva_vuokra,pinta_ala,lahiosoite,paikkakunta,postinumero,nimi,kapasiteetti,lisatietoja,toimipiste_id)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?)";

                try {
                    stmt = this.connection.createStatement();
                    pstmt = this.connection.prepareStatement(sql_insert_toimipiste);

                    pstmt.setInt(1, toimisto.getToimisto_id());
                    pstmt.setFloat(2, toimisto.getPaiva_vuokra());
                    pstmt.setString(3, toimisto.getPinta_ala());
                    pstmt.setString(4, toimisto.getLahiosoite());
                    pstmt.setString(5, toimisto.getPaikkakunta());
                    pstmt.setString(6, toimisto.getPostinumero());
                    pstmt.setString(7, toimisto.getNimi());
                    pstmt.setString(8, toimisto.getKapasiteetti());
                    pstmt.setString(9, toimisto.getLisatietoja());
                    pstmt.setInt(10, toimisto.getToimipiste_id());

                    pstmt.execute();
                    pstmt.close();
                    stmt.close();

                } catch (SQLException se) {
                    throw se;
                }
            }
        }catch (SQLException se) {
            throw se;
        }
        sulje_yhteys();
    }

    /**
     * Päivitetään olemassa olevan toimiston tiedot uusilla tiedoilla
     * @param toimisto
     * @return
     */
    public Toimisto update_toimisto(Toimisto toimisto) {

        avaa_yhteys();

        String sql = "SELECT toimisto_id"
                + " FROM toimisto WHERE toimisto_id = ?";

        ResultSet resultset = null;
        PreparedStatement pstmt = null;
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt( 1, toimisto.getToimipiste_id());
            resultset = pstmt.executeQuery();

            if (!resultset.next()) {
                throw new Exception("Toimistoa ei löydy");
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        String sql_update = "UPDATE toimisto SET paiva_vuokra=?, pinta_ala=?, lahiosoite=?, paikkakunta=?, postinumero=?, nimi=?, kapasiteetti=?, lisatietoja=?, toimipiste_id=? " +
                "WHERE toimisto_id=?";

        try {
            pstmt = this.connection.prepareStatement(sql_update);

            pstmt.setFloat(1, toimisto.getPaiva_vuokra());
            pstmt.setString(2, toimisto.getPinta_ala());
            pstmt.setString(3, toimisto.getLahiosoite());
            pstmt.setString(4, toimisto.getPaikkakunta());
            pstmt.setString(5, toimisto.getPostinumero());
            pstmt.setString(6, toimisto.getNimi());
            pstmt.setString(7, toimisto.getKapasiteetti());
            pstmt.setString(8, toimisto.getLisatietoja());
            pstmt.setInt(9, toimisto.getToimipiste_id());
            pstmt.setInt(10, toimisto.getToimisto_id());

            int result = pstmt.executeUpdate();
            if (result == 0) {
                throw new Exception("Virhe toimistotietojen päivityksessä");
            }
            pstmt.executeUpdate();
            pstmt.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Update toimisto => Epäonnistui");
        }
        sulje_yhteys();
        return toimisto;
    }
    /**
     * Poistaa toimisto-tiedot tietokannasta
     * @param toimisto
     * @throws Exception
     */
    public void delete_toimisto (Toimisto toimisto) throws Exception {

        avaa_yhteys();

        String sqlDelete = "DELETE FROM toimisto WHERE toimisto_id=?";

        PreparedStatement pstmt;
        Statement stmt = this.connection.createStatement();
        stmt.executeQuery(valitse_tk);
        pstmt = connection.prepareStatement(sqlDelete);
        pstmt.setInt( 1, toimisto.getToimipiste_id());
        int result = pstmt.executeUpdate();
        if (result == 0) {
            throw new Exception("Toimistoa ei pystytty poistamaan");
        }
        pstmt.close();
        sulje_yhteys();
    }

    /**
     * Lataa kaikki toimistotaulussa olevat tidot Observablelistaan
     * @return
     */
    public ObservableList<Toimisto> lataa_toimisto_tiedot() {

        avaa_yhteys();

        ObservableList<Toimisto> lista= FXCollections.observableArrayList();
        lista.removeAll();

        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeQuery(valitse_tk);
            ResultSet resultset = this.connection.createStatement().executeQuery("SELECT * FROM toimisto");

            while ( resultset.next() ) {
                lista.add(new Toimisto(
                        resultset.getInt("toimisto_id"),
                        resultset.getFloat("paiva_vuokra"),
                        resultset.getString("lahiosoite"),
                        resultset.getString("paikkakunta"),
                        resultset.getString("postinumero"),
                        resultset.getString("nimi"),
                        resultset.getString("kapasiteetti"),
                        resultset.getString("lisatietoja"),
                        resultset.getInt("toimipiste_id"),
                        resultset.getString("pinta_ala")
                ));
            }
            stmt.close();
            resultset.close();
        } catch (Exception se) {
            se.printStackTrace();
        }
        sulje_yhteys();
        return lista;
    }

    /*
    ###################################### VARAUS NÄKYMÄN KYSELYT  #####################################################
     */
}



