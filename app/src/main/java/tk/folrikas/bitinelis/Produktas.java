package tk.folrikas.bitinelis;

/**
 * Created by skype on 2017-04-06.
 */

public class Produktas {
    private String tipas;
    private double kiekis;
    private String uzsakymai;
    private double kaina;
    private String rusis;
    private double turis;
    private String ipakavimas;

    public Produktas(String tipas, double kiekis, String uzsakymai, double kaina, String rusis, double turis, String ipakavimas) {
        this.tipas = tipas;
        this.kiekis = kiekis;
        this.uzsakymai = uzsakymai;
        this.kaina = kaina;
        this.rusis = rusis;
        this.turis = turis;
        this.ipakavimas = ipakavimas;
    }

    public String getTipas() {
        return tipas;
    }

    public void setTipas(String tipas) {
        this.tipas = tipas;
    }

    public double getKiekis() {
        return kiekis;
    }

    public void setKiekis(double kiekis) {
        this.kiekis = kiekis;
    }

    public String getUzsakymai() {
        return uzsakymai;
    }

    public void setUzsakymai(String uzsakymai) {
        this.uzsakymai = uzsakymai;
    }

    public double getKaina() {
        return kaina;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public String getRusis() {
        return rusis;
    }

    public void setRusis(String rusis) {
        this.rusis = rusis;
    }

    public double getTuris() {
        return turis;
    }

    public void setTuris(double turis) {
        this.turis = turis;
    }

    public String getIpakavimas() {
        return ipakavimas;
    }

    public void setIpakavimas(String ipakavimas) {
        this.ipakavimas = ipakavimas;
    }
}
