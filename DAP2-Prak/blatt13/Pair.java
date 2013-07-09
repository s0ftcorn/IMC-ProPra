/**
 * Klasse zur Verwaltung eines Paares
 * von Daten unterschiedlichen Typs
 */ 

public class Pair<G,H>
{
    private G fst;
    private H snd;
    
    /**
     * Erstellt ein neues Pair
     * @param g erstes Datum des neuen Pairs
     * @param h zweites Datum des neuen Pairs
     */
    public Pair(G g, H h){
        this.fst = g;
        this.snd = h;
    }
    
    /**
     * @return das erste Datum
     */
    public G getFst(){
        return fst;
    }
    
    /**
     * @return das zweite Datum
     */
    public H getSnd(){
        return snd;
    }

}
