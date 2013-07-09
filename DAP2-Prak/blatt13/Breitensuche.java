import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;



/**
 * Beschreiben Sie hier die Klasse Breitensuche.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Breitensuche
{
    public static void bfs (Graph g, int s) {
        // bfs initialisieren
        // queue
        Queue<Node> queue = new LinkedList<Node>();
        
        // für d[u] und pi[u] arrays initialisieren, für die Farben - arrayLists
        ArrayList<Node> nodes = g.getNodes();
        int numberOfNodes = nodes.size();
        
        double[] d = new double[numberOfNodes];
        Node[] pi = new Node[numberOfNodes];
        ArrayList<Node> whiteNodes = new ArrayList<Node>();
        ArrayList<Node> grayNodes = new ArrayList<Node>();
        
        // brauchen wir das?
        ArrayList<Node> blackNodes = new ArrayList<Node>();
        
        for (int i=0; i<numberOfNodes; i++) {
            // unendlich für die d-Werte initialisieren
            d[i] = Double.POSITIVE_INFINITY;
            
            // alle Knoten zur whiteNodes hinzufügen
            whiteNodes.add(nodes.get(i));
        }
        
        // den Startknoten initialisieren
        d[s] = 0;
        grayNodes.add( whiteNodes.remove(s) );
        
        // s-knoten in die queue hinzufügen
        queue.offer( nodes.get(s) );
        
        Node u, v;
        int uID, vID;
        while (!queue.isEmpty()) {
            u = queue.peek(); 
            uID = u.getID();
            //adjazenzliste bekommen
            ArrayList<Edge> adjazente = u.getAdjacentList();
            for (int i=0; i<adjazente.size(); i++) {
                v = adjazente.get(i).getDest();
                vID = v.getID();
                if (whiteNodes.contains( v )) {
                    if (whiteNodes.remove(v))
                        grayNodes.add(v);
                    d[vID] = d[uID]+1;
                    pi[vID] = u;
                    queue.offer(v);
                }
            }
            
            u = queue.poll(); 
            uID = u.getID();
            if (grayNodes.remove(u))
                blackNodes.add(u);
        }
        
        
        
    }
}
