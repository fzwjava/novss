package cn.com.siemens.novel.spider.analysis.tool;

public class ForuthTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
    /**
     * 
     */
    private static final long serialVersionUID = -4929638264884313028L;
    public final D foruth;

    public ForuthTuple(A first, B two, C three, D foruth) {
        super(first, two, three);
        this.foruth = foruth;
    }

}
