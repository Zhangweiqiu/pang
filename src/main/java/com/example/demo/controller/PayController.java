package com.example.demo.controller;

import java.util.HashMap;
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
							 @RequestParam(name="money",required=false)Integer money,
							 @RequestParam(name="type",required=false)String type,
							 @RequestParam(name="tell",required=false)String tell,
							 HttpServletResponse res) throws Exception {
//		System.out.println(AESUtil.decrypt("69CBC0961EAD88573955DAE7EE6B6DAE4A10D33CC9567AE23B5422A5AC3007165F7AD947348D283E37D3C2F2AE9BE9AF6E073E73D428CB745170AABF6D304F4A2CF63D68C5228C3F86FBAEEF926CE0217F454E6C50F3A9838C4E12BAFBB82AADA7E5989C05CC5A416E4D3855CABF9C8312F63F3B5C3A26FC9E82AA682571A4CF268CCF5892C4D88D1CE485A17FFF44FF4F1C356EFBAEE6A35D6325214230D7D54508A7881C66C4F6C23B3410581A449E8CAFDD49EB7C421CFC15E95A1B5149618C607FDFAA2D3846B8406794F6C3BCBEF327A6C538AD0BD45A46824E516D547B", key));
		Map<String,Object> map = new HashMap<String, Object>();
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
		map.put("sign", sign);
		String data = AESUtil.encrypt(JSONObject.toJSONString(map),key);
		System.out.println(data);
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
