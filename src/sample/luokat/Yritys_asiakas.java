package sample.luokat;

public class Yritys_asiakas extends Asiakas {

    private int yritys_id;
    private String nimi;
    private String y_tunnus;

    /**
     * @param asiakas_id
     * @param etunimi
     * @param sukunimi
     * @param lahiosoite
     * @param paikkakunta
     * @param postinumero
     * @param puhelin
     * @param email
     * @param nimi
     */
    public Yritys_asiakas(int asiakas_id, String etunimi, String sukunimi, String lahiosoite, String paikkakunta, String postinumero, String puhelin, String email, int yritys_id, String nimi, String y_tunnus) {
        super(asiakas_id, etunimi, sukunimi, lahiosoite, paikkakunta, postinumero, puhelin, email);
        this.yritys_id = yritys_id;
        this.nimi = nimi;
        this.y_tunnus = y_tunnus;
    }

    @Override
    public String toString() {
        return "Yritys_asiakas{" +
                "yritys_id=" + yritys_id +
                ", nimi='" + nimi + '\'' +
                ", y_tunnus='" + y_tunnus + '\'' +
                '}';
    }

    public int getYritys_id() {
        return yritys_id;
    }

    public void setYritys_id(int yritys_id) {
        this.yritys_id = yritys_id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getY_tunnus() {
        return y_tunnus;
    }

    public void setY_tunnus(String y_tunnus) {
        this.y_tunnus = y_tunnus;
    }
}