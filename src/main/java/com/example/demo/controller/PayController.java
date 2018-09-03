package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.PayManInfo;
import com.example.demo.service.GoodsNameService;
import com.example.demo.service.PayService;
import com.example.demo.util.AESUtil;
import com.example.demo.util.PostUtil;
import com.example.demo.util.Signature;


@RestController
public class PayController {
	
	private static Logger log = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	PayService payService;
	
	@Autowired
	GoodsNameService goodsNameService;
	
	@Value("${payman.accessId}")
    private String accessId;
	
	@Value("${payman.key}")
    private String key;
	
	@RequestMapping("/passtopay")
	public void passtopay(@RequestParam(name="name",required=false)String name,
							 @RequestParam(name="money",required=false)double money,
							 @RequestParam(name="type",required=false)String type,
							 @RequestParam(name="tell",required=false)String tell,
							 HttpServletResponse res) throws Exception {
		Map<String,Object> map = new TreeMap<String, Object>();
		String accessPayNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		PayManInfo payManInfo = new PayManInfo();
		payManInfo.setName(name);
		payManInfo.setRemarks(tell);
		payManInfo.setTradeAmt(money);
//		payManInfo.setTradeType(type);
//		payManInfo.setGoodsName("程峰收款");
//		payManInfo.setRemarks(type);
//		payManInfo.setAccessPayNo(accessPayNo);
		if(goodsNameService.findOne(1L) != null) {
			map.put("goodsName", goodsNameService.findOne(1L).getGoodsName());
		}else
			map.put("goodsName", "程峰收款");
		map.put("merchantId", "100863200");
		map.put("withdrawType", "0");
		
		map.put("tradeType", type);
		map.put("tradeAmt", money);
		map.put("accessPayNo", accessPayNo);
//		map.put("payNotifyUrl", "http://139.159.133.182:8080/index.php?s=/Home/MCNotify/index");
//		map.put("frontBackUrl", "frontBackUrl");
		String url = "http://139.159.133.182:8080/pay/codePayment.do";
		String sign = Signature.getSign(map, key);
		String data = AESUtil.encrypt(JSONObject.toJSONString(sign),key);
		String postStr = "accessId="+accessId+"&data="+data;
		JSONObject jsonObject = PostUtil.httpRequest(url,"POST",postStr);
		if(jsonObject.getInteger("code") == 0) {
			log.debug("请求成功！");
			payManInfo.setAccessPayNo(jsonObject.getString("payNo"));
			payManInfo.setActualAmt(jsonObject.getDouble("actualAmt"));
			payManInfo.setTradeType(jsonObject.getString("tradeType"));
			payService.savePay(payManInfo);
			res.setCharacterEncoding("utf-8");
			res.sendRedirect(jsonObject.getString("htmlUrl"));
		}
		else {
			log.debug("请求失败！");
		}		
	}
	
	
	@RequestMapping("/showPayList")
	public List<PayManInfo> showPayList() {
		return payService.showPayList();
	}
}
