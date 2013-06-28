import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Klasse zur Verwaltung von Graphen
 * Speichert die Knoten in einer Liste und verwaltet zusaetzlich eine
 * Kostenmatrix; ein Eintrag (i,j) = 0 bedeutet, dass es keine Kante von
 * dem Knoten mit ID i nach Knoten mit ID j gibt.
 * Kann gerichtet oder ungerichtet sein
 */
public class Graph
{
	private ArrayList<Node> nodes;
	private double[][] costs;
	private int cap = 4;
	private boolean directed;

	/**
	 * Erstellt einen leeren, (un)gerichteten Graphen mit einer Grundkapazitaet
	 * @param cap Grundkapazitaet fuer die Anzahl der Knoten
	 * @param directed entschiedet, ob der Graph gerichtet (true) oder ungerichtet (false) ist
	 */
	public Graph(int cap, boolean directed){
		if (cap > 0) this.cap = cap;
		nodes = new ArrayList<Node>(this.cap);
		costs = new double[this.cap][this.cap];
		this.directed = directed;
	}

	/**
	 * Erstellt einen leeren, ungerichteten Graphen mit einer Grundkapazitaet
	 * @param cap Grundkapazitaet fuer die Anzahl der Knoten
	 */
	public Graph(int cap){
		this(cap,false);
	}

	/**
	 * Erstellt einen leeren, (un)gerichteten Graphen
	 * @param directed entschiedet, ob der Graph gerichtet (true) oder ungerichtet (false) ist
	 */
	public Graph(boolean directed){
		this(0,directed);
	}

	/**
	 * Erstellt einen leeren, ungerichteten Graphen
	 */
	public Graph(){
		this(0,false);
	}

	/**
	 * @return true, wenn der Graph gerichtet ist
	 */
	public boolean isDirected(){
		return directed;
	}

	/**
	 * Fuegt einen neuen Knoten in den Graphen ein
	 * Erzeugt keinen neuen Knoten, wenn dieser schon exisitiert
	 * @return Die ID des neuen Knotens, -1 falls der Knoten nicht eingefuegt wurde
	 */
	public int addNode(){
		return addNode(nodes.size());
	}

	/**
	 * Fuegt einen neuen Knoten in den Graphen ein
	 * Erzeugt keinen neuen Knoten, wenn dieser schon exisitiert
	 * @param id Die ID, die der neue Knoten erhalten soll, muss groesser oder gleich 0 sein
	 * @return Die ID des neuen Knotens, -1 falls der Knoten schon vorhanden war
	 */
	public int addNode(int id){
		// Eine ID kleiner 0 ist nicht zulaessig, eine zu grosse ID sprengt die Kostenmatrix
		if (id < 0 || id > cap*1000) return -1;
		Node v = new Node(id);
		// Knoten exisiteirt bereits
		if (nodes.contains(v)) return id;
		// Knoten kann nicht eingefuegt werden
		if (!nodes.add(v)) return -1;
		// Knoten sortiert halten
		Collections.sort(nodes);
		// Kostenmatrix anpassen, falls ID zu gross
		if (id >= cap){
			while (id >= cap){
				cap *= 2;
			}
			double[][] newCosts = new double[cap][cap];
			int oldCap = costs.length;
			for (int i = 0; i < oldCap; i++){
				System.arraycopy(costs[i], 0, newCosts[i], 0, oldCap);
			}
			costs = newCosts;
		}
		return id;
	}

	/**
	 * Gibt den Knoten mit der angeforderten ID
	 * Benutzt dabei binaere Suche
	 * @param id ID des gesuchten Knotens
	 * @return der Knoten mit ID id oder null, falls er nicht exisitert
	 */
	public Node getNode(int id){
		int bottom = 0, top = nodes.size()-1, middle;
		Node v;
		// Binaere Suche anhand der ID
		while(bottom<=top){
			middle = (bottom+top)/2;
			v = nodes.get(middle);
			int compare = v.compareTo(id);
			if (compare < 0){
				bottom = middle+1;
			} else if (compare > 0){
				top = middle-1;
			} else return v;
		}
		return null;
	}

	/**
	 * Gibt eine Kopie der Liste aller Knoten zurueck
	 * @return Kopie der Liste aller Knoten
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> tmp = new ArrayList<Node>();
		tmp.addAll(nodes);
		return tmp;
	}

    /**
     * Gibt eine Kopie der Liste aller Kanten erstellt aus den Adjazenzlisten 
     * der Knoten zurück.
     * @return Kopie der Liste aller Kanten
     */
    public ArrayList<Edge> getEdges(){
    	ArrayList<Edge> tmp = new ArrayList<Edge>();
        for (Node n : nodes) {
        	tmp.addAll(n.getAdjacentList());
        }
        return tmp;
    }
	
	/**
	 * Fuegt eine neue Kante in den Graphen ein
	 * @param src ID des Startknotens
	 * @param dest ID des Endknotens
	 * @param cost Kosten der Kante (muss groesser 0 sein)
	 * @return true, wenn die Kante eingefuegt wurde
	 */
	public boolean addEdge(int src, int dest, double cost){
		// ungueltige Kosten abfangen
		if (cost <= 0) return false;
		Node v, u;
		v = getNode(src);
		u = getNode(dest);
		// eigentliches Einfuegen
		return addEdge(v,u,cost);
	}

	/**
	 * Fuegt eine neue Kante in den Graphen ein
	 * @param v Startknoten
	 * @param u Endknoten
	 * @param cost Kosten der Kante (muss groesser 0 sein)
	 * @return true, wenn die Kante eingefuegt wurde
	 */
	public boolean addEdge(Node v, Node u, double cost){
		// ungueltige Knoten und Kosten abfangen
		if (v == null || u == null || cost <= 0) return false;
		if (!nodes.contains(v) || !nodes.contains(u)) return false;
		// Kante in Adjazenzliste des Knotens eintragen
		if (v.addEdge(u, cost)){
			if (!directed){
				// im ungerichteten Fall GegenKante einfuegen
				if (!u.addEdge(v, cost)){
					v.deleteEdge(u);
					return false;
				}
				costs[u.getID()][v.getID()] = cost;

			}
			costs[v.getID()][u.getID()] = cost;
			return true;
		}
		return false;
	}

	/**
	 * Aendert eine Kante im Graphen
	 * @param src ID des Startknotens
	 * @param dest ID des Endknotens
	 * @param cost neue Kosten der Kante (muss groesser 0 sein)
	 * @return true, wenn die Kante geaendert wurde
	 */
	public boolean changeEdge(int src, int dest, double cost){
		// ungueltige Kosten abfangen, ungueltige Kntoen einschraenken
		if (src > cap || dest > cap || cost <= 0) return false;
		// ungueltige Kante abfangen
		if (costs[src][dest] == 0) return false;
		Node v, u;
		v = getNode(src);
		u = getNode(dest);
		return changeEdge(v,u,cost);
	}

	/**
	 * Aendert eine Kante im Graphen
	 * @param v Startknoten
	 * @param u Endknoten
	 * @param cost neue Kosten der Kante (muss groesser 0 sein)
	 * @return true, wenn die Kante geaendert wurde
	 */
	public boolean changeEdge(Node v, Node u, double cost){
		// ungueltige Knoten und Kosten abfangen
		if (v == null || u == null || cost <= 0) return false;
		if (!nodes.contains(v) || !nodes.contains(u)) return false;
		// ungueltige Kanten abfangen
		if (costs[v.getID()][u.getID()] == 0) return false;
		// Kante aus Adjazenzliste loeschen...
		if (!deleteEdge(v,u)) return false;
		// ... und neu einfuegen
		if (!addEdge(v, u, cost)){
			if (!directed){
				costs[u.getID()][v.getID()] = 0;
			}
			costs[v.getID()][u.getID()] = 0;
			return false;
		}
		return true;
	}

	/**
	 * Aendert eine Kante im Graphen
	 * @param e zu aendernde Kante
	 * @param cost neue Kosten der Kante (muss groesser 0 sein)
	 * @return true, wenn die Kante geaendert wurde
	 */
	public boolean changeEdge(Edge e, double cost){
		if (e == null) return false;
		return changeEdge(e.getSource(), e.getDest(), cost);
	}

	/**
	 * Loescht eine Kante aus dem Graphen
	 * @param src ID des Startknotens
	 * @param dest ID des Endknotens
	 * @return true, wenn die Kante geloescht wurde
	 */
	public boolean deleteEdge(int src, int dest){
		// ungueltige Knoten einschraenken
		if (src > cap || dest > cap) return false;
		// ungueltige Kanten abfangen
		if (costs[src][dest] == 0) return false;
		Node v, u;
		v = getNode(src);
		u = getNode(dest);
		return deleteEdge(v,u);
	}

	/**
	 * Loescht eine Kante aus dem Graphen
	 * @param v Startknoten
	 * @param u Endknoten
	 * @return true, wenn die Kante geloescht wurde
	 */
	public boolean deleteEdge(Node v, Node u){
		// ungueltige Knoten einschraenken
		if (v == null || u == null) return false;
		if (!nodes.contains(v) || !nodes.contains(u)) return false;
		// ungueltige Kanten abfangen
		if (costs[v.getID()][u.getID()] == 0) return false;
		// Kante loeschen
		if (v.deleteEdge(u)){
			if (!directed){
				// im gerichteten Fall Gegenkante loeschen
				if (!u.deleteEdge(v)){
					v.addEdge(u,costs[v.getID()][u.getID()]);
					return false;
				}
				costs[u.getID()][v.getID()] = 0;
			}
			costs[v.getID()][u.getID()] = 0;
			return true;
		}
		return false;
	}

	/**
	 * Loescht eine Kante aus dem Graphen
	 * @param e zu loeschende Kante
	 * @return true, wenn die Kante geloescht wurde
	 */
	public boolean deleteEdge(Edge e){
		if (e == null) return false;
		return deleteEdge(e.getSource(), e.getDest());
	}

	/**
	 * Gibt die Kosten einer Kante zurueck
	 * @param src ID des Startknotens
	 * @param dest ID des Endknotens
	 * @return die Kosten der Kante oder -1, falls die Knoten oder die Kante nicht exisitieren
	 */
	public double getCost(int src, int dest){
		try {
			return (costs[src][dest] > 0? costs[src][dest]: -1);
		}
		catch (ArrayIndexOutOfBoundsException e){
			return -1;
		}
	}

	/**
	 * Gibt die Kosten einer Kante zurueck
	 * @param v Startknoten
	 * @param u Endknoten
	 * @return die Kosten der Kante oder -1, falls die Knoten oder die Kante nicht exisitieren
	 */
	public double getCost(Node v, Node u){
		return getCost(v.getID(),u.getID());
	}

	/**
	 * Gibt eine Kopie der Kostenmatrix zurueck
	 * @return Kopie der Kostenmatrix
	 */
	public double[][] getCosts(){
		double[][] ret = new double[cap][cap];
		for(int i = 0; i < cap;i++){
			System.arraycopy(costs[i], 0, ret[i], 0, cap);
		}
		return ret;
	}

	/**
	 * Liest eine Datei ein und gibt den daraus resultierenden Grahen zurueck
	 * @param filename Pfad der zu lesenden Datei
	 * @return den aufgebauten Graphen, oder null, wenn die Datei keinen Grpah enthaelt oder ein Fehler auftritt
	 */
	public static Graph fromFile(String filename){
		try{
			// Datei zum Lesen oeffnen
			RandomAccessFile file = null;
			file = new RandomAccessFile(filename,"r");
			if (file == null) return null;
			// Erste Zeile beinhaltet Groesse und Art
			String line = file.readLine();
			StringTokenizer st = new StringTokenizer(line,",");
			int n = 0;
			boolean d = false;
			n = Integer.parseInt(st.nextToken());
			d = Boolean.parseBoolean(st.nextToken());
			Graph g = new Graph(n,d);
			// Kanten einfuegen
			while ((line = file.readLine())!=null){
				st = new StringTokenizer(line,",");
				int src = Integer.parseInt(st.nextToken());
				int dest = Integer.parseInt(st.nextToken());
				double cost = Double.parseDouble(st.nextToken());
				g.addNode(src);
				g.addNode(dest);
				g.addEdge(src,dest,cost);    
			}
			file.close();
			return g;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Schreibt diesen Graph in eine Datei
	 * @param filename Pfad der Datei, in der gespeichert werden soll
	 * @return true, falls das Speichern erfolgreich war
	 */
	public boolean toFile(String filename){
		try{
			// Datei zum Schreiben oeffnen
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			if (file == null) return false;
			// Datei leeren
			file.setLength(0);
			int n = nodes.size();
			// Erste Zeile mit Groesse und Art erstellen
			String graph = n + ","+directed+"\n";
			// Kanten erstellen, bei ungerichteten reicht eine Richtung
			for (int i = 0; i < n; i++){
				for (int j = (!directed ? i+1: 0); j < n; j++){
					if (costs[i][j] > 0) graph += i+","+j+","+costs[i][j] +"\n";
				}
			}
			// String in Datei schreiben
			file.writeBytes(graph);
			file.close();
			return true;
		} catch (Exception e){
			return false;
		}
	}

}
