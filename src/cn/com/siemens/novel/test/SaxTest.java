package cn.com.siemens.novel.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.com.siemens.novel.spider.core.HttpClientUtil;
import cn.com.siemens.novel.spider.core.IntentMap;

public class SaxTest {
    @org.junit.Test
    public void test() {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = HttpClientUtil.getDefaultMethod();
        try {
            request.setURI(new URI("http://www.dixiaoshuo.com/dl2/20/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String html = null;
        try {
            HttpResponse response = client.execute(request);
            html = HttpClientUtil.getHtml(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(html);

        // char[] chars = html.toCharArray();
        TagNode node = new HtmlCleaner().clean(html);
        List<IntentMap> list = new ArrayList<IntentMap>();
        IntentMap inMap1 = new IntentMap();
        inMap1.put("pa", "洪荒之太昊登天录");
        list.add(inMap1);
        IntentMap inMap2 = new IntentMap();
        inMap2.put("pa", "作者：东边一只猪 【完成】");
        list.add(inMap2);
        IntentMap inMap3 = new IntentMap();
        inMap3.put(
                "pa",
                "洪荒破碎，鸿钧站在紫霄宫，双眼穿透混沌，望向不知何处的地方，转过头看着太昊。“你可知道自己来洪荒的原因呢？”“知道开头，还不清楚结尾。”“那你准备怎么做？”");
        list.add(inMap3);
        IntentMap inMap4 = new IntentMap();
        inMap4.put("pa", "第二百三十四章 在混元之上看着你们");
        list.add(inMap4);
        TagNode root = node;
        t(root, list);
        System.out.println(list);
    }

    // @org.junit.Test
    public void test2() {
        System.out
                .println("    洪荒破碎，鸿钧站在紫霄宫，双眼穿透混沌，望向不知何处的地方，转过头看着太昊。    “你可知道自己来洪荒的原因呢？”    “知道开头，还不清楚结尾。”    “那你准备怎么做？”    "
                        .replaceAll("[\\n\\t( )*]", ""));
    }

    public void handle(List<IntentMap> list) {
        IntentMap inMap = null;
        int i = 0;
        TagNode node = null;
        for (IntentMap intent : list) {
            for (IntentMap intt : list) {
                if (intent != intt) {
                    if (intent.<TagNode> get("re") == intt.<TagNode> get("re")) {
                        node = intent.<TagNode> get("re");
                        i++;
                    }
                }
            }
        }
        if (i == list.size()) {

        }
    }

    public TagNode getAllParant(List<IntentMap> list, int hierarchy) {
        TagNode node = null;
        return null;
    }

    public TagNode getParant(IntentMap intent, int hierarchy) {
        TagNode node = null;
        for (int i = 0; i < hierarchy; i++) {
            if (node == null) {
                node = intent.<TagNode> get("re");
            } else {
                node = node.getParent();
            }
        }
        return node;
    }

    public void t(TagNode root, List<IntentMap> list) {

        TagNode[] tags = root.getChildTags();
        if (tags != null && tags.length > 0) {
            for (TagNode nd : tags) {
                for (IntentMap inMap : list) {
                    if (nd.getText()
                            .toString()
                            .trim()
                            .replaceAll("[\\n\\t( )*]", "")
                            .equals(inMap.getString("pa").replaceAll(
                                    "[\\n\\t( )*]", ""))) {
                        inMap.put("re", nd);
                    }
                }
                if (nd.getChildTags() != null && tags.length > 0) {
                    t(nd, list);
                } else {
                    System.out.println(nd.getChildTags());

                }
            }
        }
    }
}
