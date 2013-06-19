import java.util.LinkedList;
import java.util.ListIterator;

public class HashTable{
    private LinkedList<HashItem>[] hashArray;
    private String hashFunc;
    
    public HashTable(int hashSize){
        this(hashSize, "RSHash");
    }
    
    public HashTable(int hashSize, String hashFunc){
        // standarde Werte einsezen
        this.hashFunc = hashFunc;
        
        // array erzeugen
        hashArray = new LinkedList[hashSize];
        // array mit leeren Listen für Kollisionen befüllen
        for (int i=0; i< hashSize; i++) {
            hashArray[i] = new LinkedList<HashItem>();
        }
    }
    
    public void put(String key){
        // den Element-index aus dem hashArray bestimmen, wo der Wert key hinzugefügt werden soll
        int listNumber = getHashOfString(key) % hashArray.length;
        
        // HashItem generieren
        HashItem toPut = new HashItem(key, 1);
        
        // die richtige Liste durchgehen, und gucken, ob der Wert key schon drin ist
        for (int i=0; i<hashArray[listNumber].size(); i++) {
            // falls der Wert schon in der Liste vorhanden ist, die Ausführung abbrechen
            if (hashArray[listNumber].get(i).getKey().equals( key ) )
                return;
        }
        
        // falls wir immer noch in der Methode sind -->
        // einfügen des HashItems mit dem key in die Liste 
        hashArray[listNumber].add(toPut);
    }
    
    public HashItem get(String key) {
        // den Element-index aus dem hashArray bestimmen, wo der Wert key hinzugefügt werden soll
        int listNumber = getHashOfString(key) % hashArray.length;
        
        // die richtige Liste durchgehen, und gucken, ob der Wert key drin ist
        HashItem tempItem;
        for (int i=0; i<hashArray[listNumber].size(); i++) {
            // falls der Wert schon in der Liste vorhanden ist, die Ausführung abbrechen
            tempItem = hashArray[listNumber].get(i);
            if (tempItem.getKey().equals( key ) )
                return tempItem;
        }
        
        // falls bisher nix zurückgegeben wurde, heißt es, dass key nicht in der Tabelle ist, wir geben null zurück
        return null;
    }
    
    public void clear() {
        // array durchgehen und die Listen leeren
        for (int i=0; i< hashArray.length; i++) {
            hashArray[i].clear();
        }
    }
    
    public int numberOfCollisions() {
        int collisions = 0;
        // array durchgehen
        for (int i = 0; i<hashArray.length; i++) {
            int tempInt = hashArray[i].size();
            if (tempInt > 1)
                collisions += tempInt-1;
        }
        return collisions;
    }
    
    public void printHashTable() {
        System.out.println("***************************************");
        System.out.println();
        System.out.println("---------------------------------------");
        
        // array durchgehen
        for (int i=0; i<hashArray.length; i++) {
            System.out.println("Hash items with hash value "+i+":");
            
            // liste mit Hilfe von Iterator durchgehen
            ListIterator<HashItem> iterator = hashArray[i].listIterator();
            while(iterator.hasNext()) {
                HashItem currItem = iterator.next();
                System.out.println("        key: "+currItem.getKey()+"  -- info: "+currItem.getInfo());
            }
            
            System.out.println("---------------------------------------");
        }
        
        System.out.println();
        System.out.println("The number of collisions is "+numberOfCollisions());
        System.out.println();
        System.out.println("***************************************");
    }
    
    
    
    private int getHashOfString( String s) {
        if(hashFunc.equals("JSHash")){
            return Math.abs((int)GeneralHashFunctionLibrary.JSHash(s));
        } else {
            // falls hashFunc ungleich JS ist, dann benutzen wir nach dem vorgegebenen Standard RSHash
            return Math.abs((int)GeneralHashFunctionLibrary.RSHash(s));
        }
    }
    
}