package cn.com.siemens.novel.spider.analysis.tool;

public class FifthTuple<A, B, C, D, E> extends ForuthTuple<A, B, C, D> {
    /**
     * 
     */
    private static final long serialVersionUID = -9136114099238408266L;
    public final E fifth;

    public FifthTuple(A first, B two, C three, D foruth, E fifth) {
        super(first, two, three, foruth);
        this.fifth = fifth;
    }

}
