package cn.com.siemens.novel.spider.analysis.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import cn.com.siemens.novel.spider.core.IntentMap;
import net.sf.json.JSONObject;


public class GetBeanByHtml<T> {
	private final Class<T> beanClass;
	private final IntentMap config;
	private final HtmlCleaner cleaner = new HtmlCleaner();
	private final String html;
	private String realHtml;
	private String[] path;
	private Map<String, Class<?>> filedClass = new HashMap<String, Class<?>>();
	private BeanMath beanMatch;
	private TagNode root;
	private String rootpath;

	public void setBeanMatch(BeanMath beanMatch) {
		this.beanMatch = beanMatch;
	}

	public GetBeanByHtml(Class<T> cl, IntentMap con, String html) {
		this.beanClass = cl;
		this.config = con;
		this.html = html;
		init();
	}

	public GetBeanByHtml(String html) {
		this(null, null, html);
	}

	public String getValueByPath(FiledConfig fc) {
		Object value = null;
		if (root != null) {
			FiledMathch<T> math = fc.get("filedmatch");
			FiledCustom custorm = fc.get("custom");

			value = getValue(fc.getString("path"), math, root, custorm);
		}
		return (String) value;
	}

	public void init() {
		realHtml = html;
		if (config != null) {
			String pa = config.<FiledConfig> get("listpath").getString("path");
			
				path = pa.split(",");

				if (path.length > 0) {
					int po = 0;
					if (path.length > 1) {
						realHtml = getRealHtml(path[0], html);
						po = 1;
					}

					if (realHtml != null) {
						try {
							rootpath = path[po];
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			root = cleaner.clean(realHtml);
		
	}

	public List<T> getListBean() {
		List<T> list = null;

		if (path.length > 0) {

			if (realHtml != null) {
				try {

					T bean = null;
					Object[] nodes = root.evaluateXPath(rootpath);
					TagNode node = null;
					if (nodes.length > 0) {
						list = new ArrayList<T>();
						for (Object ob : nodes) {
							node = (TagNode) ob;
							bean = getBean(node);
							if (beanMatch != null) {
								if (beanMatch.match(bean)) {
									list.add(bean);
								}
							} else {
								list.add(bean);
							}
						}

					} else {
						System.out.println("无法得到节点列表，listpath:" + rootpath);
					}
				} catch (XPatherException e) {
					e.printStackTrace();
				}
			}

		}

		return list;
	}

	private String getRealHtml(String path, String html) {
		String[] jsons = path.split("。");
		String realHtml = html;
		try {

			JSONObject jo = JSONObject.fromObject(html);

			for (int i = 0; i < jsons.length - 1; i++) {
				jo = jo.getJSONObject(jsons[i]);
			}
			realHtml = jo.getString(jsons[jsons.length - 1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realHtml;
	}

	// 没有进行异常处理，无法保证异常抛出时，流程不中断

	public T getBean(TagNode node) {
		T bean = null;
		Field filed = null;
		try {
			bean = beanClass.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		Iterator<String> keys = config.keySet().iterator(); // 获取config中的键值的集合
		String key = null;
		while (keys.hasNext()) {
			if ((key = keys.next()) != "listpath") {
				FiledConfig fc = config.get(key);
				FiledMathch<T> math = fc.get("filedmatch");
				FiledCustom custorm = fc.get("custom");
				Object va = fc.get("value");
				Object value = null;
				if (va != null) {
					value = va;
				} else {
					value = getValue(fc.getString("path"), math, node, custorm);
				}
				if (value != null) {
					try {
						if (key.contains(".")) {
							String[] keyss = key.split("\\.");
							filed = beanClass.getDeclaredField(keyss[0]);
							filed.setAccessible(true);
							Object ob = filed.get(bean);
							if (ob == null) {
								ob = filedClass.get(keyss[0]).newInstance();
							}
							Field f = filedClass.get(keyss[0])
									.getDeclaredField(keyss[1]);
							f.setAccessible(true);
							f.set(ob, value);
							filed.set(bean, ob);
						} else {
							filed = beanClass.getDeclaredField(key);
							filed.setAccessible(true);
							filed.set(bean, value);
						}
					} catch (Exception e) {
						//e.printStackTrace();
						try {
							filed =beanClass.getSuperclass().getDeclaredField(key);
							filed.setAccessible(true);
							filed.set(bean, value);
						} catch (NoSuchFieldException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SecurityException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			}
		}
		return bean;
	}

	public Object getValue(String path, FiledMathch<?> math, TagNode node,
			FiledCustom custom) {


		TagNode nd = null;
		String value = null;

		Object realValue = null;
		try {

			if (custom != null) {
				nd = (TagNode) getPathValueNew(path, node);
				value = custom.handle(nd);
			} else {
				value = (String) getPathValueNew(path, node);
			}

			realValue = value;
			if (math != null) {
				realValue = math.excute(value);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("path:"+path);
			// e.printStackTrace();

		}
		return realValue;
	}

	private Object getPathValueNew(String path, TagNode node)
			throws XPatherException {
		/*
		 * String[] nodes = path.split("\\."); if(nodes.length>1){ node = root;
		 * path = nodes[1]; }
		 */
		String[] paths = path.split("\\$");
		Object[] obs = node.evaluateXPath(paths[0]);
		Object res = null;
		if (obs.length > 0) {
			if (paths.length > 1) {
				if (paths[1].equals("text")) {
					res = ((TagNode) obs[0]).getText().toString();
					/*
					 * res = ((TagNode)
					 * obs[0]).getChildren().get(0).toString().trim();
					 */
				} else {
					res = ((TagNode) obs[0]).getAttributeByName(paths[1]);
				}

			} else {
				res = obs[0];
			}
		}

		return res;
	}

	/*
	 * private Object ToRes(Object res){ String str = res.toString(); str =
	 * str.substring(0, str.indexOf("&nbsp")); return str; }
	 */

	public static TwoTuple<String, TagNode> getPathValue(String path,
			TagNode node) throws NumberFormatException, XPatherException {
		TagNode nd = node;
		String pa = path;
		int en = 0;
		String att = null;

		Matcher match = Pattern.compile("\\((\\d+)\\)").matcher(path);
		while (match.find()) {
			if (match.group() != null && !match.group().equals("")) {
				nd = (TagNode) nd.evaluateXPath(path.substring(en,
						match.start()))[Integer.valueOf(match.group()
						.substring(1, match.group().length() - 1))];
				pa = path.substring(match.end(), path.length());
				en = match.end();
			}
		}
		if (pa.length() > 1) {
			if (nd.equals(node)) {
				att = pa.substring(2, pa.length());
			} else {
				att = pa.substring(1, pa.length());
			}
		}
		TwoTuple<String, TagNode> re = new TwoTuple<String, TagNode>(att, nd);
		return re;
	}

	public String getValue(TagNode tag, int flag, String arr) {
		String result = null;
		if (tag != null) {
			// 0为text,1为属性
			if (flag == 0) {
				result = tag.getText().toString();
			} else if (flag == 1) {
				result = tag.getAttributeByName(arr);
			}
		}
		return result;
	}

	public Object getRealValue(String vl, FiledMathch<?> ma) {
		Object result = vl;
		if (ma != null) {
			result = ma.excute(vl);
		}
		return result;
	}

	public static long toLong(String rs) {

		if (rs != null && !rs.equals("")) {
			return Long.valueOf(rs);
		}
		return -1;
	}

	public static boolean isEmpty(String sr) {
		return sr == null || sr.equals("") ? true : false;
	}

	public String getValue(String path) {
		return null;
	}

	public static void main(String[] args) {
	}

	public Map<String, Class<?>> getFiledClass() {
		return filedClass;
	}

	public void setFiledClass(Map<String, Class<?>> filedClass) {
		this.filedClass = filedClass;
	}

}
