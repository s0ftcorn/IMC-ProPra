import java.lang.Math;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.io.IOException;
import java.util.StringTokenizer;

public class CountWords{
  
    // Festgelegte Menge der interessierenden Wörter
    static String[] words = {
    "a",
    "about",
    "all",
    "an",
    "and",
    "are",
    "around",
    "as",
    "at",
    "away",
    "back",
    "be",
    "beach",
    "beat",
    "black",
    "body",
    "brown",
    "but",
    "by",
    "can",
    "close",
    "come",
    "cut",
    "day",
    "did",
    "do",
    "door",
    "down",
    "eyes",
    "face",
    "find",
    "for",
    "from",
    "get",
    "gets",
    "go",
    "going",
    "gonna",
    "got",
    "has",
    "have",
    "he",
    "her",
    "here",
    "him",
    "his",
    "how",
    "i",
    "if",
    "in",
    "inside",
    "into",
    "is",
    "it",
    "jungle",
    "just",
    "know",
    "library",
    "like",
    "look",
    "looks",
    "lost",
    "main",
    "man",
    "matrix",
    "me",
    "mean",
    "my",
    "now",
    "of",
    "off",
    "oh",
    "on",
    "one",
    "open",
    "out",
    "over",
    "phone",
    "right",
    "room",
    "see",
    "sees",
    "she",
    "something",
    "sun",
    "tank",
    "that",
    "the",
    "their",
    "them",
    "then",
    "there",
    "they",
    "think",
    "this",
    "through",
    "to",
    "turns",
    "two",
    "up",
    "us",
    "walks",
    "wanna",
    "want",
    "was",
    "we",
    "well",
    "what",
    "when",
    "where",
    "who",
    "why",
    "with",
    "would",
    "yeah",
    "you",
    "your"
    };
  
    public static void main(String[] args){
        System.out.println();
        System.out.println();
        if (args.length == 0 || args.length > 3) {
            // falsche Anzahl an Parametern, richtiger Syntaxaufruf zurückgeben
            syntaxteller();
        } else {
            // die Eingaben bearbeiten
            
            // der Erster Parameter ist immer die Dateiname, deswegen definieren wir fileName sofort
            String fileName = args[0];
            int hashSize = 10;
            String hashType = "RSHash";
            
            if (args.length == 2) {
                // wir haben zwei Parametern bekommen. Fallunterscheidung -->
                if (args[1].equals("RSHash") || args[1].equals("JSHash")) {
                    // als Parametern wurden die Dateiname und die Hash-Methode übergegeben
                    hashType = args[1];
                } else {
                    // als Parametern wurden die Dateiname und die Größe der Hash-Tabelle übergegeben
                    try {
                        hashSize = Integer.parseInt(args[1]);
                        if (hashSize < 1) 
                            Integer.parseInt("throw exception!");
                    } catch (NumberFormatException e) {
                        System.out.println(args[1]+" ist keine natürliche Zahl!");
                        syntaxteller();
                        return;
                    }
                }
            } else if (args.length == 3) {
                // wir haben aller drei Parametern bekommen: Dateiname, Größe der Hash-Tabelle, Hash-Methode
                // hashSize bearbeiten
                // -------------------
                // COPY-PASTE VON OBEN
                // -------------------
                try {
                    hashSize = Integer.parseInt(args[1]);
                    if (hashSize < 1) 
                        Integer.parseInt("throw exception!");
                } catch (NumberFormatException e) {
                    System.out.println(args[1]+" ist keine natürliche Zahl!");
                    syntaxteller();
                    return;
                }
                
                // hash-Methode bearbeiten
                // -------------------
                // COPY-PASTE VON OBEN (mit index-wechsel)
                // -------------------
                if (args[2].equals("RSHash") || args[2].equals("JSHash")) {
                    hashType = args[2];
                } else {
                    System.out.println("Dritter Parameter muss entweder RSHash oder JSHash sein!");
                    syntaxteller();
                    return;
                }
            }
            
            // wir sind ENDLICH MAL mit der Aufnahme der Parametern fertig
            // HashTable Object anlegen
            HashTable hTable = new HashTable(hashSize, hashType);
            for (int i=0;i<words.length;i++) {
                hTable.put( words[i] );
            }
            
            // Datei einlesen
            RandomAccessFile file;
            try {
                file = new RandomAccessFile(fileName, "r");
            } catch (FileNotFoundException e) {
                System.out.println("Fehler beim Einlesen der Datei: Datei nicht gefunden, Pfad korrigieren");
                return;
            } catch (SecurityException e) {
                System.out.println("Fehler beim Einlesen der Datei: Keine Berechtigung");
                return;
            }
            
            boolean endOfFileReached = false;
            String zeile;
            String wort;
            StringTokenizer st;
            HashItem hItem;
            while(!endOfFileReached){
                try{
                    zeile = file.readLine();
                }
                catch(IOException e){
                    System.out.println("Fehler beim Einlesen der Datei");
                    System.out.println(e);
                    return;
                }
                
                if(zeile == null)
                    endOfFileReached = true;
                else {
                    // bisher läuft alles gut, wir haben nun in "zeile" eine Zeile aus der Datei
                    // die NullPointerException müssen wir nicht behandeln,
                    //    weil sie NUR dann geworfen wird, falls zeile == null,
                    //    was wir aber gerade abgefragt haben und sind uns sicher, dass z != null ist.
                    // wir rufen toLowerCase auf, damit wir richtig Wörter zählen können
                    zeile = zeile.toLowerCase();
                    st = new StringTokenizer(zeile, " ,.;:!?@*()_+=-[]\\'\"/|{}<>`~£$%^&±§1234567890\t");
                    while (st.hasMoreTokens()) {
                        // hier ebenso, die NoSuchElementException müssen wir nicht abfangen
                        //     wegen der while-Bedingung
                        
                        wort = st.nextToken();
                        // überprüfen, ob das Wort in der Tabelle vorhanden ist
                        hItem = hTable.get(wort);
                        if (hItem != null) {
                            // das Wort ist vorhanden, info erhöhen
                            hItem.setInfo( hItem.getInfo() + 1 );
                        }
                        // falls das Wort ist nicht vorhanden -> überspringen
                    }
                }
            }

            // Wir haben endlich die Datei bearbeitet und die Hash-tabelle
            //     mit den Wörtern und dessen Wiederholungsfrequenz befüllt
            // Nun geben wir die Statistik für die Vorgegebene Wörter zurück
            
            int wiederholung;
            int t1;
            System.out.println("  Wörter ---------- Anzahl");
            System.out.println("----------------------------");
            for (int i=0; i<words.length;i++) {
                // get HashItem von HashTabelle und zeigen in die Console
                //     wir benutzen schon vorher definierte hItem variable
                hItem = hTable.get(words[i]);
                if (hItem == null)
                    wiederholung = 0;
                else
                    wiederholung = hItem.getInfo();
                    
                // schöne Ausgabe generieren
                t1 = 14-words[i].length();
                System.out.print("  "+words[i]+" ");
                for (int t=0; t<t1; t++) {
                    System.out.print("-");
                }
                System.out.println("-- "+wiederholung);
            }
            System.out.println("---------------------------------------");
            System.out.println();
            System.out.println("Die Hash-Tabelle enthält "+hTable.numberOfCollisions()+" Kollisionen");
            System.out.println();
            System.out.println("---------------------------------------");
            
            //hTable.printHashTable();
        }
        System.out.println();
    }
  
    
    public static void syntaxteller() {
        System.out.println();
        System.out.println("Richtiger aufruf: java CountWords textFileName [hashSize] [RSHash|JSHash]");
        System.out.println("Bei [hashSize] werden nur natürliche Zahlen akzeptiert");
        System.out.println();
    }
}