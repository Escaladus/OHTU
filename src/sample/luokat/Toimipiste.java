package sample.luokat;

/**
 *
 * @author R02
 * @version 1.0 03/05/2021
 */
public class Toimipiste {
    private int toimipiste_id;
    private String postinumero;
    private String paikkakunta;
    private String lahiosoite;
    private String nimi;

    /**
     * @param toimipiste_id
     * @param postinumero
     * @param paikkakunta
     * @param lahiosoite
     * @param nimi
     */

    public Toimipiste(int toimipiste_id, String postinumero, String paikkakunta, String lahiosoite, String nimi) {
        this.toimipiste_id = toimipiste_id;
        this.postinumero = postinumero;
        this.paikkakunta = paikkakunta;
        this.lahiosoite = lahiosoite;
        this.nimi = nimi;
    }

    @Override
    public String toString() {
        return "Toimipiste{" +
                "toimipiste_id=" + toimipiste_id +
                ", postinumero='" + postinumero + '\'' +
                ", paikkakunta='" + paikkakunta + '\'' +
                ", lahiosoite='" + lahiosoite + '\'' +
                ", nimi='" + nimi + '\'' +
                '}';
    }

    /**
     *
     * @return
     */
    public int getToimipiste_id() {
        return toimipiste_id;
    }

    /**
     *
     * @param toimipiste_id
     */
    public void setToimipiste_id(int toimipiste_id) {
        this.toimipiste_id = toimipiste_id;
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