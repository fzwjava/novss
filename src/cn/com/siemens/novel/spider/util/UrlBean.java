package cn.com.siemens.novel.spider.util;

import java.util.Map;



public class UrlBean implements Cloneable {
	private String waitKey;
	private String url;
	private String furl;
	private String key;
	private String cityEn;
	private boolean sw;
	private String stayDate;
	private Integer outDate;
	
	

	public String getWaitKey() {
		return waitKey;
	}

	public void setWaitKey(String waitKey) {
		this.waitKey = waitKey;
	}

	public void setStayDate(String stayDate) {
		this.stayDate = stayDate;
	}

	public String getStayDate() {
		return stayDate;
	}

	public Map<String, String> mp;

	public String getCityEn() {
		return cityEn;
	}

	public void setCityEn(String cityEn) {
		this.cityEn = cityEn;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	private String city;

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public String toString() {
		return BeanUtil.BeantoString(this);
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String cityId;

	public UrlBean clone() {
		UrlBean u = null;
		try {
			u = (UrlBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return u;
	}

	public boolean getSw() {
		return sw;
	}

	public void setSw(boolean sw) {
		this.sw = sw;
	}

	public Integer getOutDate() {
		return outDate;
	}

	public void setOutDate(Integer outDate) {
		this.outDate = outDate;
	}

	


}
