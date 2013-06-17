public class HashItem{
        
        private int info;
        private String key;
        
        public void HashItem(String k, int i){
                info = i;
                key = k;
        }
        
        public void HashItem(){
                info = 0;
                key = "";
        }
        
        public void setinfo(int i){
                info = i;
        }
        
        public int getinfo(){
                return info;
        }
        
        public void setkey(String k){
                key = k;
        }
        
        public String getkey(){
                return key;
        }
        
}