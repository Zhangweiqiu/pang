package com.example.demo.util;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import sun.net.www.protocol.file.Handler;

public class PostUtil {
	private static Logger log = LoggerFactory.getLogger(PostUtil.class);
	/**
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
 
//            URL url = new URL(requestUrl);
//            URL url = new URL(null,requestUrl,new Handler());
            URL url = new URL(null,requestUrl, new sun.net.www.protocol.http.Handler());
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
 
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
 
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();
 
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
 
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
 
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    public static String sendPost(String url, String param) {
	    PrintWriter out = null;
	    BufferedReader in = null;
	    String result = "";
	    try { URL realUrl = new URL(url); // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection(); // 设置通用的请求属性
             conn.setRequestProperty("accept", "*/*");
             conn.setRequestProperty("connection", "Keep-Alive");
             conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); // 发送POST请求必须设置如下两行
             conn.setDoOutput(true); conn.setDoInput(true); // 获取URLConnection对象对应的输出流
             out = new PrintWriter(conn.getOutputStream()); // 发送请求参数
             out.print(param); // flush输出流的缓冲
             out.flush(); // 定义BufferedReader输入流来读取URL的响应
             in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
             String line; while ((line = in.readLine()) != null) { result += line;
             } }
             catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	    } //使用finally块来关闭输出流、输入流
         finally{
	        try{
	            if(out!=null){
	                out.close();
	            }
	            if(in!=null){
	                in.close();
	            }
	        } catch(IOException ex){
	            ex.printStackTrace();
	        }
	    }
	    return result;
	}
}
