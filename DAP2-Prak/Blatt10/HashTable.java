import java.util.LinkedList;

public class HashTable{
        
        private LinkedList<HashItem>[] hashList;
        private String hashfunc = "RS";
        private int hashSize
        
        public void HashTable(int size){
                hashSize = size;
                hashList = new LinkedList[size];
        }
        
        public void setHashFunc(String s){
                if(s.equals("RS")){
                        hashfunc = RS;
                }else if(s.equals("JS")){
                        hashfunc = "JS";
                }else{
                        System.out.println("Something went terribly wrong!");
                        System.out.println("Setze Hashfunktion auf RS");
                        hashfunc = "RS";
                }
        }
        
        public void put(String key){
                long tempHash = 0;
                if(hashfunc.equals("RS")){
                        tempHash = GeneralHashFunctionLibrary.RSHash(key);
                }else if(hashfunc.equals("JS")){
                        tempHash = GeneralHashFunctionLibrary.JSHash(key);
                }else{
                        System.out.println("Something went terribly wrong!");
                        System.out.println("Einfügen des Wertes schlug Fehl.")
                }
                
                int hashval = (int)(tempHash % hashSize);
                
                HashItem toPut = new HashItem(key, tempHash);
                hashList[hashval].add(toPut);
        }
}