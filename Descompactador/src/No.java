class No extends Arvore {
    public final Arvore left, right;
 
    public No(Arvore l, Arvore r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}