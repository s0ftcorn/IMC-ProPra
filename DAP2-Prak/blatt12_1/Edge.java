/**
 * Klasse zur Verwaltung von Kanten
 * Hat natuerliche Ordnung bzgl. Gewichten
 * Achtung: compareTo und equals haengen nicht zusammen!
 */

public class Edge extends Pair<Pair<Node, Node>, Double> implements Comparable<Edge>
{

	/**
	 * Kantenkonstruktor
	 *
	 * @param v Startknoten der Kante
	 * @param u Endknoten der Kante
	 * @param c Kosten der Kante
	 * @throws NullPointerException Falls einer der uebergebenen Knoten null ist
	 */
	public Edge(Node v, Node u, double c){
		super(new Pair<Node,Node>(v,u),c);
		if (v == null || u == null){
			// Kanten mit null-Knoten sind unerwuenscht
			throw new NullPointerException();
		}
	}

	/**
	 * @return Startknoten
	 */
	public Node getSource(){
		return getFst().getFst();
	}

	/**
	 * @return Endknoten
	 */
	public Node getDest(){
		return getFst().getSnd();
	}

	/**
	 * @return Kosten
	 */
	public double getCost(){
		return getSnd();
	}

	/**
	 * Vergleicht zwei Kanten anhand ihrer Gewichte
	 * @param other die zu vergleichende Kante
	 * @return -1, 0 bzw. 1 falls diese Kante ein kleineres, gleiches oder groesseres Gewicht hat
	 */
	public int compareTo(Edge other){
		return (getCost() < other.getCost() ? -1: getCost() > other.getCost() ? 1: 0);
	}

	/**
	 * Vergleicht eine Kante mit einem Objekt
	 *
	 * @param other das zu vergleichende Objekt
	 * @return true, wenn das Objekt vergleichbar und gleich ist
	 */
	public boolean equals(Object other){
		if (this == other) return true;
		if (other == null) return false;
		if (other instanceof Edge) return equals((Edge) other);
		if (other instanceof Node) return equals((Node) other);
		return false;
	}

	/**
	 * Vergleicht eine Kante mit einem Knoten
	 *
	 * @param other der zu vergleichende (End)Knoten
	 * @return true, wenn der Endknoten der uebergebene Knoten ist
	 */
	public boolean equals(Node other){
		return getDest().equals(other);
	}

	/**
	 * Vergleicht zwei Kanten anhand der Knoten
	 *
	 * @param other die zu vergleichende Kante
	 * @return true, wenn Start- und Endknoten der Kanten uebereinstimmen, false sonst
	 */
	public boolean equals(Edge other){
		return getSource().equals(other.getSource()) && getDest().equals(other.getDest());
	}

}
