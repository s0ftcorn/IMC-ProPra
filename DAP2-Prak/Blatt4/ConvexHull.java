import java.util.LinkedList;
public class ConvexHull
{    
    public static LinkedList<Punkt> simpleConvex(Punkt[] P) {
        // eine Hilfsliste, die folgendermaßen strukturiert ist:
        // Die Punkte werden immer paarweise hinzugefügt (ein Vektor aus der Hülle)
        // da wir in unserem Algorithmus immer gucken, ob es auf der linken Seite keine Punkte gibt, sind die Vektoren in der Uhrzeigedrehrichtung
        // daher - alle gerade Elementen der Liste - Vektorsanfänge, und danach folgende Elemente - Vektorsende
        // das Hilft uns bei der Erstellung endgültiger Punktliste der Hülle
        LinkedList<Punkt> tempList = new LinkedList<Punkt>();
        
        for (int i=0; i<P.length; i++) {
            // fix für einen Array mit leeren Stellen
            if (P[i] != null) {
                // berechnen der Linie-kante von jedem Punkt i aus -->
                
                // Stützvektor
                Punkt s = P[i];
                
                for (int j=0; j<P.length; j++) {
                    // wir bilden jeweils eine Linie von i nach j
                    // falls es keine Linie gibt (die Punkte sind gleich), überspringen
                    // && fix für einen Array mit leeren StellenConvexHullTest.java
                    if (!gleichePunkte(P[j], P[i]) && P[j] != null) {
                        // Richtungsvektor berechnen
                        Punkt r = new Punkt(P[j].getX()-P[i].getX(), P[j].getY()-P[i].getY() );
                        
                        boolean leftIsEmpty = true;
                        //wir gucken, ob alle andere Punkte rechts oder auf der Linie liegen
                        for (int n=0; n<P.length && leftIsEmpty; n++) {
                            // && fix für einen Array mit leeren Stellen
                            if (n!=i && n!=j && P[n] != null) {
                                // ausrechnen, auf welcher Seite bezüglich dem Richtungsvektor liegt der Punkt n 
                                // falls das Ergebnis der Formel > 0 ist => Punkt n liegt auf der rechten Seite
                                // falls das Ergebnis der Formel = 0 ist => Punkt n liegt auf dem Richtungsvektor
                                // falls das Ergebnis der Formel < 0 ist => Punkt n liegt auf der linken Seite
                                if (r.getY()*(P[n].getX() - s.getX() ) - r.getX()*(P[n].getY() - s.getY() ) < 0) {
                                    leftIsEmpty = false;
                                }
                            }
                        }
                        
                        // falls es keine Punkte links von der Linie i->j liegen, diese ins tempList hinzufügen
                        if (leftIsEmpty) {
                            tempList.add(P[i]);
                            tempList.add(P[j]);
                        }
                    }
                }
            }
        }
        
        /*
        System.out.println("------------------------------------- Gefundene Kanten -------------------------------------");
        for (int i = 0; i<tempList.size(); i+=2) {
            System.out.println("("+tempList.get(i).getX()+","+tempList.get(i).getY()+") -> ("+tempList.get(i+1).getX()+","+tempList.get(i+1).getY()+")");
        }
        System.out.println("--------------------------------------------------------------------------------------------");
        */
     
        // eine Liste nacheinander kommenden Punkten der Hülle erzeugen, anhand der Liste der gefundenen Kanten
        LinkedList<Punkt> hull = new LinkedList<Punkt>();
        LinkedList<Punkt> temp = new LinkedList<Punkt>();
        
        // wir nehmen die erste Kante als Anfang der Hülle
        Punkt firstPunkt = tempList.get(0);
        Punkt findFrom = tempList.get(1);
        
        hull.add( firstPunkt );
        hull.add( findFrom );
        
        // wir gucken immer die Kanten an, und vergleichen den Anfang der Kante mit dem Ende der bisher generierten Hülle
        boolean endIt = false;
        while (!endIt) {
            // alle Kanten durchgehen, und die, die aus dem Punkt findFrom gehen, in temp Liste hinzufügen
            for (int i = 0; i<tempList.size(); i += 2) {
                if (tempList.get(i).equals( findFrom )) {
                    temp.add( tempList.get(i) );
                    temp.add( tempList.get(i+1) );
                }
            }
            
            // wir gucken, wie viele Kanten wir von dem Punkt findFrom aus haben
            if (temp.size() == 2) {
                // falls es nur eine kante gibt -> findFrom redefinieren
                findFrom = temp.get(1);
            } else {
                // falls es mehrere Kanten gibt, die kleinste auswählen und findFrom redefinieren
                // erst mal die Länge der ersten Kante ausrechnen und davon ausgehen, dass es die kleinste kante sein kann, deswegen schon hier findFrom redefinieren 
                double smallestLength = Math.sqrt(
                ((temp.get(0).getX()-temp.get(1).getX())*(temp.get(0).getX()-temp.get(1).getX()))
                +
                ((temp.get(0).getY()-temp.get(1).getY())*(temp.get(0).getY()-temp.get(1).getY()))
                );
                findFrom = temp.get(1);
                for (int i = 2; i<temp.size(); i += 2) {
                    double nextLength = Math.sqrt(
                    ((temp.get(i).getX()-temp.get(i+1).getX())*(temp.get(i).getX()-temp.get(i+1).getX()))
                    +
                    ((temp.get(i).getY()-temp.get(i+1).getY())*(temp.get(i).getY()-temp.get(i+1).getY()))
                    );
                    if (smallestLength > nextLength) {
                        // falls wir eine kleinere Kante gefunden haben, redefinieren wir diese als findFrom
                        smallestLength = nextLength;
                        findFrom = temp.get(i+1);
                    }
                }
            }
            
            // falls findFrom auf den Anfang der Hülle zeigt -> die Suche unterbrechen, sonst zur Hülle hinzufügen
            if (!findFrom.equals( firstPunkt )) {
                hull.add( findFrom );
            } else {
                endIt = true;
            }
            // temp Liste für weitere suche leeren
            temp.clear();
        }
        
        return hull;
    }
    
    private static boolean gleichePunkte(Punkt p1, Punkt p2) {
        if (p1.getX() == p2.getX() && p1.getY() == p2.getY())
            return true;
        else
            return false;
    }
}
