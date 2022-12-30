package sample.luokat;

public class Yhteiso_asiakas extends Asiakas {

    private int yhteiso_id;
    private String nimi;

    /**
     * Alustetaan yhteisöasiakas
     *  @param asiakas_id
     * @param etunimi
     * @param sukunimi
     * @param lahiosoite
     * @param paikkakunta
     * @param postinumero
     * @param puhelin
     * @param email
     * @param yhteiso_id
     * @param nimi
     */
    public Yhteiso_asiakas(int asiakas_id, String etunimi, String sukunimi, String lahiosoite, String paikkakunta, String postinumero, String puhelin, String email, int yhteiso_id, String nimi) {
        super(asiakas_id, etunimi, sukunimi, lahiosoite, paikkakunta, postinumero, puhelin, email);
        this.yhteiso_id = yhteiso_id;
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return "Yhteiso_asiakas{" +
                "yhteiso_id=" + yhteiso_id +
                ", nimi='" + nimi + '\'' +
                '}';
    }

    /**
     *
     * @return
     */
    public int getYhteiso_id() {
        return yhteiso_id;
    }

    /**
     *
     * @param yhteiso_id
     */
    public void setYhteiso_id(int yhteiso_id) {
        this.yhteiso_id = yhteiso_id;
    }

    /**
     *
     * @return
     */
    public String getNimi() {
        return nimi;
    }

    /**
     *
     * @param nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}
