import java.util.LinkedList;
public class ConvexHullTest
{
    public static void main(String[] args) {
        
        //generieren 1000 zufällige Punkte und berechne die konvexe Hülle
        Punkt[] P = new Punkt[1000];
        for (int i=0; i<P.length; i++) {
            P[i] = randomPunkt();
        }
        
        LinkedList<Punkt> hull = ConvexHull.simpleConvex( P );
        
        System.out.println("------------------------------------- Konvexe Hülle für 1000 random Punkte -------------------------------------");
        for (int i = 0; i<hull.size(); i++) {
            if (i!=0)
                System.out.print(" -> ");
            System.out.print("("+hull.get(i).getX()+","+hull.get(i).getY()+")");
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
 
        
        
        java.util.Random numberGenerator = new java.util.Random();
        int x1 = numberGenerator.nextInt(2*1000+1)-1000;
        int y1 = numberGenerator.nextInt(2*1000+1)-1000;
        int x2 = numberGenerator.nextInt(2*1000+1)-1000;
        int y2 = numberGenerator.nextInt(2*1000+1)-1000;
        int x3 = numberGenerator.nextInt(2*1000+1)-1000;
        int y3 = numberGenerator.nextInt(2*1000+1)-1000;
        Dreieck triangle = new Dreieck(x1,y1,x2,y2,x3,y3);
        System.out.println("Punkte des Dreiecks:");
        System.out.println("("+x1 + ","+ y1+") -> ("+x2 + ","+ y2+") -> ("+x3 + ","+ y3+")");
        
        Punkt[] PInTriangle = new Punkt[20];
        Punkt genPunkt;
        System.out.println("Generierte Punkte, die im Dreieck liegen:");
        for (int i=0; i<PInTriangle.length;) {
            genPunkt = new Punkt(numberGenerator.nextInt(2*100+1)-100, numberGenerator.nextInt(2*100+1)-100);
            if (triangle.contains(genPunkt)) {
                PInTriangle[i] = genPunkt;
                System.out.print("("+genPunkt.getX()+","+genPunkt.getY()+") ");
                i++;
            }
        }
        System.out.println();
        
        Punkt[] Phull = new Punkt[PInTriangle.length + 3];
        Phull[0] = new Punkt(x1,y1);
        Phull[1] = new Punkt(x2,y2);
        Phull[2] = new Punkt(x3,y3);
        for (int i=0; i<PInTriangle.length; i++) {
            Phull[i+3] = PInTriangle[i];
        }
        
        LinkedList<Punkt> hullTriangle = ConvexHull.simpleConvex( Phull );
        
        System.out.println("------------------------------------- Konvexe Hülle für Dreieck und Punkte drine -------------------------------------");
        for (int i = 0; i<hullTriangle.size(); i++) {
            if (i!=0)
                System.out.print(" -> ");
            System.out.print("("+hullTriangle.get(i).getX()+","+hullTriangle.get(i).getY()+")");
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------------------");
 
    }
    
    private static Punkt randomPunkt() {
        int grenze = 1000;
        java.util.Random numberGenerator = new java.util.Random();
        return new Punkt(numberGenerator.nextInt(2*grenze+1)-grenze, numberGenerator.nextInt(2*grenze+1)-grenze);
    }
}