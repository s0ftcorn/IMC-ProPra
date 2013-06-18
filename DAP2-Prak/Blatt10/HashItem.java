public class HashItem{
        
    private int info;
    private String key;
    
    public HashItem(String k, int i){
        info = i;
        key = k;
    }
    
    public void setInfo(int i){
        info = i;
    }
    
    public int getInfo(){
        return info;
    }
    
    public void setKey(String k){
        key = k;
    }
    
    public String getKey(){
        return key;
    }
}