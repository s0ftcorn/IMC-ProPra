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

	@SuppressWarnings("unchecked")
	/**
	 * Vergleicht ein Pair mit einem Objekt
	 *
	 * @param other das zu vergleichende Objekt
	 * @return true, wenn das Objekt vergleichbar und gleich ist
	 */
	public boolean equals(Object other){
		if (this == other) return true;
		if (other == null || !(getClass().isInstance(other))) return false;
		return equals(getClass().cast(other));
	}
	
	/**
	 * Vergleicht zwei Pairs miteinander.
	 *
	 * @param other das zu vergleichende Pair
	 * @return true, wenn beide Daten der Pairs uebereinstimmen
	 */
	public boolean equals(Pair<G,H> other){
		boolean ret = (fst == null? other.fst == null: fst.equals(other.fst));
		ret = ret && (snd == null? other.snd == null: snd.equals(other.snd));
		return ret;
	}

}
