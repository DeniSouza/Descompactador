abstract class Arvore implements Comparable<Arvore> {
    public final int frequency;
    public Arvore(int freq) { 
    	frequency = freq; 
    }
    
   
    public int compareTo(Arvore tree) {
        return frequency - tree.frequency;
    }
}