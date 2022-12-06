import java.util.*;

public class Main {

	public static void main(String[] args) {	
        String test = "Eernie eyes seen near lake"; // Tamanho do texto = 112 bits 
        
        // Neste exemplo ser� considerado que teremos no m�ximo 256 caracteres diferentes
        // Passo 1 - Percorre o texto contando os s�mbolos e montando um vetor de frequ�ncias.
        int[] charFreqs = new int[256];
        for (char c : test.toCharArray())
            charFreqs[c]++;
        
        // Criar a �rvore dos c�digos para a Compacta��o
        Arvore tree = buildTree(charFreqs);
        
       

        String decode = "110001001010111100110111100000100011110011010011111011101101010111101001101000110";
        decode = decode(tree, decode);
        System.out.println(decode);
         


    }
    public static Arvore buildTree(int[] charFreqs) {
    	// Cria uma Fila de Prioridade 
    	// A Fila ser� criado pela ordem de frequ�ncia da letra no texto
        PriorityQueue<Arvore> trees = new PriorityQueue<Arvore>();
        // Criar as Folhas da �rvore para cada letra 
        for (int i = 0; i < charFreqs.length; i++){
            if (charFreqs[i] > 0)
                trees.offer(new Folha(charFreqs[i], (char)i)); // Inser os elementos, n� folha, na fila de prioridade
        }
 
        while (trees.size() > 1) {
            // Pega os dois n�s com menor frequ�ncia
            Arvore a = trees.poll(); // poll - Retorna o pr�ximo n� da Fila ou NULL se n�o tem mais
            Arvore b = trees.poll(); // poll - Retorna o pr�ximo n� da Fila ou NULL se n�o tem mais
 
            // Criar os n�s da �rvore bin�ria
            trees.offer(new No(a, b)); 
        }
        // Retorna o primeiro n� da �rvore
        return trees.poll();
    }
 
    /* COMPACTAR a string 
     *     Par�metros de Entrada: tree - Raiz da �rvore de compacta��o
     *     						  encode - Texto original 
     *     Par�metros de Sa�da: encodeText- Texto Compactado
    */ 
    public static String encode(Arvore tree, String encode){
    	assert tree != null;
    	
    	String encodeText = "";
        for (char c : encode.toCharArray()){
        	encodeText+=(getCodes(tree, new StringBuffer(),c));
        }
    	return encodeText; // Retorna o texto Compactado
    }
    
    

    public static String decode(Arvore tree, String encode) {
    	assert tree != null;
    	
    	String decodeText="";
    	No node = (No)tree;
    	for (char code : encode.toCharArray()){
    		if (code == '0'){ // Quando for igual a 0 � o Lado Esquerdo
    		    if (node.left instanceof Folha) { 
    		    	decodeText += ((Folha)node.left).value; // Retorna o valor do n� folha, pelo lado Esquerdo  
	                node = (No)tree; // Retorna para a Ra�z da �rvore
	    		}else{
	    			node = (No) node.left; // Continua percorrendo a �rvore pelo lado Esquerdo 
	    		}
    		}else if (code == '1'){ // Quando for igual a 1 � o Lado Direito
    		    if (node.right instanceof Folha) {
    		    	decodeText += ((Folha)node.right).value; //Retorna o valor do n� folha, pelo lado Direito
	                node = (No)tree; // Retorna para a Ra�z da �rvore
	    		}else{
	    			node = (No) node.right; // Continua percorrendo a �rvore pelo lado Direito
	    		}
    		}
    	} // End for
    	return decodeText; // Retorna o texto Decodificado
    }    
    

    public static void printCodes(Arvore tree, StringBuffer prefix) {
        assert tree != null;
        
        if (tree instanceof Folha) {
            Folha leaf = (Folha)tree;
            
            // Imprime na tela a Lista
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t\t" + prefix);
 
        } else if (tree instanceof No) {
            No node = (No)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    
 
    public static String getCodes(Arvore tree, StringBuffer prefix, char w) {
        assert tree != null;
        
        if (tree instanceof Folha) {
            Folha leaf = (Folha)tree;
            
            // Retorna o texto compactado da letra
            if (leaf.value == w ){
            	return prefix.toString();
            }
            
        } else if (tree instanceof No) {
            No node = (No)tree;
 
            // Percorre a esquerda
            prefix.append('0');
            String left = getCodes(node.left, prefix, w);
            prefix.deleteCharAt(prefix.length()-1);
 
            // Percorre a direita
            prefix.append('1');
            String right = getCodes(node.right, prefix,w);
            prefix.deleteCharAt(prefix.length()-1);
            
            if (left==null) return right; else return left;
        }
		return null;
    }

}
