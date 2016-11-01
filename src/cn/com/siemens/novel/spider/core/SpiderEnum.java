package cn.com.siemens.novel.spider.core;

import cn.com.siemens.novel.spider.analysis.tool.DefaultAnalysis;




public class SpiderEnum {
	public static SpiderEnum getHtml  = new SpiderEnum(new DefaultAnalysis());
	// 参数中的类需要有默认构�?�?
	public UrlHandler urlHandler;
	public AnalysisHandler anHandler;
	public ResultHandler resultHandler;

	public SpiderEnum(UrlHandler urlh, AnalysisHandler analyh,
			ResultHandler resultHandler) {
		if (analyh == null) {
			this.anHandler = new DefaultAnalysis();
		} else {
			this.anHandler = analyh;
		}
		this.resultHandler = resultHandler;
		this.urlHandler = urlh;
	}

	public SpiderEnum(AnalysisHandler analyh) {
		this.anHandler = analyh;
	}
	
	public SpiderEnum(UrlHandler urlh){
		this.urlHandler = urlh;
	}
	
	public SpiderEnum(){
		this.anHandler = new DefaultAnalysis();
	}

	public SpiderEnum(UrlHandler urlHandler2, AnalysisHandler analyh) {
		this.urlHandler = urlHandler2;
		this.anHandler = analyh;
	}

	public SpiderEnum(AnalysisHandler analyh,
			ResultHandler resultHandler) {
		this.resultHandler = resultHandler;
		this.anHandler = analyh;
	}

}
