package com.example.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Signature {
	public static String getSign(Map<String, Object> map, String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
		if (entry.getValue() != null&& !"".equals(entry.getValue().toString())) {
		list.add(entry.getKey() + "=" + entry.getValue() + "&");
		}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		//按字母表顺序排序，也就是忽略大小写排序
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
		sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + key;
		result = MD5Util.MD5Encode(result).toUpperCase();
		return result;
		}
}
