package sample.luokat;

/**
 * Class Toimiso
 * @author R2
 */
public class Toimisto {
    // luokan attribuutit
    private int toimisto_id;
    private Float paiva_vuokra;
    private String lahiosoite;
    private String paikkakunta;
    private String postinumero;
    private String nimi;
    private String kapasiteetti;
    private String lisatietoja;
    private int toimipiste_id;
    private String pinta_ala;

    /**
     * Luokan konstruktori
     * @param toimisto_id
     * @param paiva_vuokra
     * @param lahiosoite
     * @param paikkakunta
     * @param postinumero
     * @param nimi
     * @param kapasiteetti
     * @param lisatietoja
     * @param toimipiste_id
     * @param pinta_ala
     */
    public Toimisto(int toimisto_id, Float paiva_vuokra, String lahiosoite, String paikkakunta, String postinumero, String nimi, String kapasiteetti, String lisatietoja, int toimipiste_id,String pinta_ala) {
        this.toimisto_id = toimisto_id;
        this.paiva_vuokra = paiva_vuokra;
        this.lahiosoite = lahiosoite;
        this.paikkakunta = paikkakunta;
        this.postinumero = postinumero;
        this.nimi = nimi;
        this.kapasiteetti = kapasiteetti;
        this.lisatietoja = lisatietoja;
        this.toimipiste_id = toimipiste_id;
        this.pinta_ala = pinta_ala;
    }
    //Luokan geterit

    /**
     *
     * @return int arvo
     */
    public int getToimisto_id() {
        return toimisto_id;
    }

    /**
     *
     * @return Float arvo
     */
    public Float getPaiva_vuokra() {
        return paiva_vuokra;
    }

    /**
     *
     * @return String arvo
     */
    public String getLahiosoite() {
        return lahiosoite;
    }

    /**
     *
     * @return Sting arvo
     */
    public String getPaikkakunta() {
        return paikkakunta;
    }

    /**
     *
     * @return String arvo
     */
    public String getPostinumero() {
        return postinumero;
    }

    /**
     *
     * @return String arvo
     */
    public String getNimi() {
        return nimi;
    }

    /**
     *
     * @return String arvo
     */
    public String getKapasiteetti() {
        return kapasiteetti;
    }

    /**
     *
     * @return String arvo
     */
    public String getLisatietoja() {
        return lisatietoja;
    }

    /**
     *
     * @return int arvo
     */
    public int getToimipiste_id() {
        return toimipiste_id;
    }

    /**
     *
     * @return String arvo
     */
    public String getPinta_ala() {
        return pinta_ala;
    }

    // Luodaat seterit

    /**
     *
     * @param toimisto_id int arvo
     */
    public void setToimisto_id(int toimisto_id) {
        this.toimisto_id = toimisto_id;
    }

    /**
     *
     * @param paiva_vuokra Float arvo
     */
    public void setPaiva_vuokra(Float paiva_vuokra) {
        this.paiva_vuokra = paiva_vuokra;
    }

    /**
     *
     * @param lahiosoite String arvo
     */
    public void setLahiosoite(String lahiosoite) {
        this.lahiosoite = lahiosoite;
    }

    /**
     *
     * @param paikkakunta String arvo
     */
    public void setPaikkakunta(String paikkakunta) {
        this.paikkakunta = paikkakunta;
    }

    /**
     *
     * @param postinumero String arvo
     */
    public void setPostinumero(String postinumero) {
        this.postinumero = postinumero;
    }

    /**
     *
     * @param nimi String arvo
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     *
     * @param kapasiteetti String arvo
     */
    public void setKapasiteetti(String kapasiteetti) {
        this.kapasiteetti = kapasiteetti;
    }

    /**
     *
     * @param lisatietoja String arvo
     */
    public void setLisatietoja(String lisatietoja) {
        this.lisatietoja = lisatietoja;
    }

    /**
     *
     * @param toimipiste_id String arvo
     */
    public void setToimipiste_id(int toimipiste_id) {
        this.toimipiste_id = toimipiste_id;
    }

    /**
     *
     * @param pinta_ala String
     */
    public void setPinta_ala(String pinta_ala) {
        this.pinta_ala = pinta_ala;
    }
}
