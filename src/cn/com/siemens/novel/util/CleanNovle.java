package cn.com.siemens.novel.util;

import cn.com.siemens.novel.spider.analysis.tool.TwoTuple;

public class CleanNovle {

	
	public static String eliminateStEd(String content,TwoTuple<String, String> com){
		String re = null;
		if(content.length()>300){
			String end = content.substring(content.length() - 150, content.length());
			if(com.two!=null)
				end = end.replace(com.two, "");
			String start = content.substring(0, 150);
			if(com.first!=null)
				start = start.replace(com.first, "");
			re =start+ content.substring(150,content.length() - 150)+end;
		}
		return re;
		
	}
	
	public static TwoTuple<String, String> commonStr(String s1,String s2){
		String end = null;
		String start = null;
		if(s1.length()>150&&s2.length()>150){
			String end1 = s1.substring(s1.length() - 150, s1.length());
			String end2 = s2.substring(s2.length() - 150, s2.length());
			end = findStr(end1+end2);
			String start1 = s1.substring(0, 150);
			String start2 = s2.substring(0, 150);
			start = findStr(start1+start2);
			return new TwoTuple<String, String>(start, end);

		}
		return null;
	}

	private static String findStr(String s) {
		if (s == null) {
			return null;
		}
		// 最长重复子串的长度
		int max = 0;
		// 最长重复子串的第一个字符在s中的下标
		int first = 0;
		String res = null;
		// i为每次循环设定的字符串比较间隔：1,2，...，s.length()-1
		for (int i = 1; i < s.length(); i++) {
			for (int k = 0, j = 0; j < s.length() - i; j++) {
				if (s.charAt(j) == s.charAt(j + i))
					k++;
				else
					k = 0;
				if (k > max) {
					max = k;
					first = j - max + 1;
				}
			}
			if (max > 0) {
				res = s.substring(first, first + max);
			}
		}
		return res;
	}

}
