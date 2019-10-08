package lukas.java_classes;

import java.util.List;

public class Film {
    private int filmID,fsk,dauer,bewertung;
    private String titel,beschreibung;
    private List<Genre> genres;
    private List<Darsteller>darsteller;
    private List<Regisseur>regie;

    public Film(int filmID, String titel, List<Regisseur> regie, String beschreibung, int dauer, int fsk, int bewertung, List<Genre> genres, List<Darsteller>darsteller){
      this.filmID = filmID;
      this.titel = titel;
      this.regie = regie;
      this.beschreibung = beschreibung;
      this.dauer = dauer;
      this.fsk = fsk;
      this.bewertung = bewertung;
      this.genres = genres;
      this.darsteller = darsteller;
    }//Konstruktor

  public Film(){}

  public int getFilmID() {
    return filmID;
  }

  public int getFsk() {
    return fsk;
  }

  public int getDauer() {
    return dauer;
  }

  public int getBewertung() {
    return bewertung;
  }

  public String getTitel() {
    return titel;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public List<Regisseur> getRegie() {
    return regie;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public List<Darsteller> getDarsteller() {
    return darsteller;
  }

  public boolean equals (Film film){
      if (titel.equals(film.getTitel())) return true;
      else return false;
  }//equals


}//class
