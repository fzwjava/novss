package cn.com.siemens.novel.spider.analysis.tool;

import java.io.Serializable;

public class TwoTuple<A, B> implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  A first;
    public  B two;

    public TwoTuple(A first, B two) {
        this.first = first;
        this.two = two;
    }
    public TwoTuple() {}
    @Override
    public String toString() {
    	return first.toString()+"\t"+two.toString();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object obj) {
    	TwoTuple<A, B> cobj = (TwoTuple<A,B>)obj;
    	if((this.first.toString()+this.two.toString()).equals(cobj.first.toString()+cobj.two.toString())){
    		return true;
    	}
    	return false;
    }
    @Override
    public int hashCode() {
    	return (this.first.toString()+this.two.toString()).hashCode();
    }

}
