class Circle implements Comparable<Circle>{

    private short x;
    private short y;
    private short r;
    private short nHits;

    public Circle(int x, int y, int r){
        this.x = (short) x;
        this.y = (short) y;
        this.r = (short) r;
        this.nHits = 1;
    }

    public Circle(int x, int y, int r, int nHits){
        this.x = (short) x;
        this.y = (short) y;
        this.r = (short) r;
        this.nHits = (short) nHits;
    }

    public short getHits() {
        return nHits;
    }
    
    public short getX() {
    	return x;
    }
    
    public short getY() {
    	return y;
    }
    
    public short getR() {
    	return r;
    }

    @Override
    public int compareTo(Circle o) {
        return Integer.compare(getHits(), o.getHits());
    }
}