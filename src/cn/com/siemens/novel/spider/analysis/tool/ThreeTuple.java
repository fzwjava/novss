package cn.com.siemens.novel.spider.analysis.tool;

public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
    /**
     * 
     */
    private static final long serialVersionUID = 1058761795218733762L;
    public final C three;

    public ThreeTuple(A first, B two, C three) {
        super(first, two);
        this.three = three;
    }

}
