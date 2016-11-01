package cn.com.siemens.novel.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Novel {
	
    private String name;// 小说名字
    private String url;
    private String author;
    private String info;
    private String imageUrl;
    private String status;
    private String nvUpdateTime;
    private String novleType;
    private String novelTypeFlag;
    private List<KeyValue<String, String, String>> details;// 目录

    private List<Catalogue> catalogues = new ArrayList<Catalogue>();// 目录
    private Date createTime;
    private Date updateTime;
    private String pageFlag;
    private Boolean isSpider;
    private String spiderCatalogue;
    
    
    public String getSpiderCatalogue() {
		return spiderCatalogue;
	}

	public void setSpiderCatalogue(String spiderCatalogue) {
		this.spiderCatalogue = spiderCatalogue;
	}

	public Boolean getIsSpider() {
		return isSpider;
	}

	public void setIsSpider(Boolean isSpider) {
		this.isSpider = isSpider;
	}

	public List<Catalogue> getCatalogues() {
		return catalogues;
	}

	public void setCatalogues(List<Catalogue> catalogues) {
		this.catalogues = catalogues;
	}
    @Id
	private String id;
    
	public String getNovelTypeFlag() {
		return novelTypeFlag;
	}

	public void setNovelTypeFlag(String novelTypeFlag) {
		this.novelTypeFlag = novelTypeFlag;
	}

	public String getNovleType() {
		return novleType;
	}

	public void setNovleType(String novleType) {
		this.novleType = novleType;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KeyValue<String, String, String>> getDetails() {
        return details;
    }

    public void setDetails(List<KeyValue<String, String, String>> details) {
        this.details = details;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNvUpdateTime() {
		return nvUpdateTime;
	}

	public void setNvUpdateTime(String nvUpdateTime) {
		this.nvUpdateTime = nvUpdateTime;
	}

	@Override
	public String toString() {
		return "Novel [name=" + name + ", url=" + url + ", author=" + author
				+ ", info=" + info + ", imageUrl=" + imageUrl + ", status="
				+ status + ", nvUpdateTime=" + nvUpdateTime + ", details="
				+ details + ", id=" + id + "]";
	}
	

}
