package cn.com.siemens.novel.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Catalogue {

    private String name;// 
    private String content;
    private String url;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Catalogue [name=" + name + ", content=" + content + ", url=" + url + "]";
	}
    
    

}
