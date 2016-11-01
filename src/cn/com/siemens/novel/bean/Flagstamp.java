package cn.com.siemens.novel.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Flagstamp {
	
	@Id
	private String id;
	private Integer catalogueFlag;
	private Integer pageFlag;
	private String type;//biquge,
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCatalogueFlag() {
		return catalogueFlag;
	}
	public void setCatalogueFlag(Integer catalogueFlag) {
		this.catalogueFlag = catalogueFlag;
	}
	public Integer getPageFlag() {
		return pageFlag;
	}
	public void setPageFlag(Integer pageFlag) {
		this.pageFlag = pageFlag;
	}
	@Override
	public String toString() {
		return "Flagstamp [id=" + id + ", catalogueFlag=" + catalogueFlag + ", pageFlag=" + pageFlag + "]";
	}
	
	
	
	

}
