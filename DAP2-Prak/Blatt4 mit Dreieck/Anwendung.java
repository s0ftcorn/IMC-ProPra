import java.util.LinkedList;
/**
 * Write a description of class Anwendung here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Anwendung
{
    public static void main(String args[]){
        java.util.Random numberGenerator = new java.util.Random();
        if(args.length == 0){
            int x1 = numberGenerator.nextInt(2*1000+1)-1000;
            int y1 = numberGenerator.nextInt(2*1000+1)-1000;
            int x2 = numberGenerator.nextInt(2*1000+1)-1000;
            int y2 = numberGenerator.nextInt(2*1000+1)-1000;
            int x3 = numberGenerator.nextInt(2*1000+1)-1000;
            int y3 = numberGenerator.nextInt(2*1000+1)-1000;
            Dreieck temp = new Dreieck(x1,y1,x2,y2,x3,y3);
            System.out.println("Punkte:");
            System.out.println(x1 + ":"+ y1);
            System.out.println(x2 + ":"+ y2);
            System.out.println(x3 + ":"+ y3);
            System.out.println("Umfang des Dreiecks: " + temp.umfang());
            return;
        }
        
        if(args.length < 6){
            System.out.println("Zu wenig Argumente!");
            syntaxteller();
            return;
        }
        
        if(args.length > 6){
            System.out.println("Zu viele Argumente!");
            syntaxteller();
            return;
        }
        if(args.length == 6){
            int[] harr = new int[6];
            try{
                for(int i = 0; i < harr.length; i++){
                    if(Integer.parseInt(args[i]) < 0){
                        int temp = Integer.parseInt("Das hier ist kein int");
                    }else{
                        harr[i] = Integer.parseInt(args[i]);
                    }
                }
            }catch(NumberFormatException e){
                System.out.println("Bitte nur ganze Zahlen größer Null eingeben");
                syntaxteller();
            }
            
            Dreieck temp = new Dreieck(harr[0],harr[1],harr[2],harr[3],harr[4],harr[5]);
            System.out.println("Umfang: " + temp.umfang());
            return;
        }
    }
    
    public static void syntaxteller(){
        System.out.println("Korrekter Aufruf: java Anwendung x1 y1 x2 y2 x3 y3");
        System.out.println("Bei keinen Argumenten wird ein zufälliges Dreieck konstruiert");
    }
}
