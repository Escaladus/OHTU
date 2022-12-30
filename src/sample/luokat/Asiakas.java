package sample.luokat;

/**
 *
 * @author R02
 * @version 1.0 23/04/2021
 */
public class Asiakas {
    private int asiakas_id;
    private String etunimi;
    private String sukunimi;
    private String lahiosoite;
    private String paikkakunta;
    private String postinumero;
    private String puhelin;
    private String email;

    /**
     *
     * @param asiakas_id
     * @param etunimi
     * @param sukunimi
     * @param lahiosoite
     * @param paikkakunta
     * @param postinumero
     * @param puhelin
     * @param email
     */
    public Asiakas(int asiakas_id, String etunimi, String sukunimi, String lahiosoite, String paikkakunta, String postinumero, String puhelin, String email) {
        this.asiakas_id = asiakas_id;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.lahiosoite = lahiosoite;
        this.paikkakunta = paikkakunta;
        this.postinumero = postinumero;
        this.puhelin = puhelin;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Asiakas{" +
                "asiakas_id=" + asiakas_id +
                ", etunimi='" + etunimi + '\'' +
                ", sukunimi='" + sukunimi + '\'' +
                ", lahiosoite='" + lahiosoite + '\'' +
                ", paikkakunta='" + paikkakunta + '\'' +
                ", postinumero='" + postinumero + '\'' +
                ", puhelin='" + puhelin + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     *
     * @return
     */
    public int getAsiakas_id() {
        return asiakas_id;
    }

    /**
     *
     * @param asiakas_id
     */
    public void setAsiakas_id(int asiakas_id) {
        this.asiakas_id = asiakas_id;
    }

    /**
     *
     * @return
     */
    public String getEtunimi() {
        return etunimi;
    }

    /**
     *
     * @param etunimi
     */
    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    /**
     *
     * @return
     */
    public String getSukunimi() {
        return sukunimi;
    }

    /**
     *
     * @param sukunimi
     */
    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getLahiosoite() {
        return lahiosoite;
    }

    /**
     *
     * @param lahiosoite
     */
    public void setLahiosoite(String lahiosoite) {
        this.lahiosoite = lahiosoite;
    }

    /**
     *
     * @return
     */
    public String getPaikkakunta() {
        return paikkakunta;
    }

    /**
     *
     * @param paikkakunta
     */
    public void setPaikkakunta(String paikkakunta) {
        this.paikkakunta = paikkakunta;
    }

    /**
     *
     * @return
     */
    public String getPostinumero() {
        return postinumero;
    }

    /**
     *
     * @param postinumero
     */
    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    /**
     *
     * @return
     */
    public String getPuhelin() {
        return puhelin;
    }

    /**
     *
     * @param puhelin
     */
    public void setPuhelin(String puhelin) {
        this.puhelin = puhelin;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
