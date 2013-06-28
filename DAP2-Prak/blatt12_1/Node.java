import java.util.ArrayList;


/**
 * Klasse zur Verwaltung von Knoten
 * Hat natuerliche Ordnung bzgl. ID
 * Verwaltet Kanten auf andere Knoten
 */
public class Node implements Comparable<Node>
{
	private int id;
	private ArrayList<Edge> adj = new ArrayList<Edge>();


	/**
	 * Node Konstruktor
	 *
	 * @param id ID des Knotens
	 */
	public Node(int id){
		this.id = id;
	}

	/**
	 * @return Die ID des Knotens
	 */
	public int getID(){
		return id;
	}

	/**
	 * Fuegt eine neue Kante in die Adjazenzliste des Knotens ein
	 * @param v Endknoten der Kante
	 * @param c Kosten der Kante
	 * @return true, wenn noch keine Kante zu diesem Knoten besteht
	 */
	public boolean addEdge(Node v, double c){
		Edge e = new Edge(this,v,c);
		if (adj.contains(e)) return false;
		return adj.add(e);
	}

	/**
	 * Aendert eine Kante in der Adjazenzliste des Knotens
	 * @param v Endknoten der Kante
	 * @param c neue Kosten der Kante
	 * @return true, wenn die Kante bereits exisitiert und geaendert wurde
	 */
	public boolean changeEdge(Node v, double c){
		Edge e = new Edge(this,v,c);
		if (!adj.remove(e)) return false;
		return adj.add(e);
	}

	/**
	 * Loescht eine Kante aus der Adjazenzliste
	 * @param ezu loeschende Kante
	 * @return true, wenn die Kante vorhanden war
	 */
	public boolean deleteEdge(Edge e){
		return adj.remove(e);
	}

	/**
	 * Loescht eine Kante aus der Adjazenzliste
	 * @param v Endknoten der zu loeschenden Kante
	 * @return true, wenn die Kante vorhanden war
	 */
	public boolean deleteEdge(Node v){
		return adj.remove(v);
	}

	/**
	 * Gibt eine Kopie der Adjazenzliste zurueck
	 * @return Kopie der Adjazenzliste
	 */
	public ArrayList<Edge> getAdjacentList(){
		ArrayList<Edge> tmp = new ArrayList<Edge>();
		tmp.addAll(adj);
		return tmp;
	}

	/**
	 * Vergleicht zwei Knoten anhand ihrer ID
	 * @param other Knoten, mit dem verglichen wird
	 * @return eine Zahl kleiner/gleich/groesser 0, wenn die ID
	 * dieses Knotens kleiner/gleich/groesser der des anderen Knotens ist
	 */
	public int compareTo(Node other){
		return id-other.id;
	}

	/**
	 * Vergleicht die ID dieses Knotens mit einer anderen ID
	 * @param other ID, mit der verglichen wird
	 * @return eine Zahl kleiner/gleich/groesser 0, wenn die ID
	 * dieses Knotens kleiner/gleich/groesser der anderen ID ist
	 */
	public int compareTo(Integer other){
		return id-other;
	}

	/**
	 * Vergleicht einen Knoten mit einem Objekt
	 * @param other Objekt, mit dem Verglichen wird
	 * @return true, wenn das Objekt vergleichbar und gleich ist
	 */
	public boolean equals(Object other){
		if (this == other) return true;
		if (other == null) return false;
		if (other instanceof Node) return equals((Node) other);
		if (other instanceof Integer) return equals ((Integer) other);
		if (other instanceof Edge) return ((Edge) other).equals(this);
		return false;
	}

	/**
	 * Vergleicht zwei Knoten anhand ihrer ID
	 * @param other Knoten, mit dem verglichen wird
	 * @return true, falls die IDs gleich sind
	 */
	public boolean equals(Node other){
		return id == other.id;
	}

	/**
	 * Vergleicht die ID dieses Knotens mit einer anderen ID
	 * @param other ID, mit der verglichen wird
	 * @return true, wenn die ID dieses Knotens gleich der anderen ID ist
	 */
	public boolean equals(Integer other){
		return id == other;
	}
}
