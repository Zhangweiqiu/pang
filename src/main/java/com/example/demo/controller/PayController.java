package com.example.demo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.example.demo.repository.KefuRepository;
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
	
	@Autowired
	KefuRepository kefuRepository;
	
	@Value("${payman.accessId}")
    private String accessId;
	
	@Value("${payman.key}")
    private String key;
	
	private String payNotifyUrl = "http://116.62.18.98/payNotify.do";
	
	private String frontBackUrl = "http://116.62.18.98/goBack.html";
	
	@RequestMapping("/passtopay")
	public Map<String,Object>  passtopay(@RequestParam(name="name",required=false)String name,
							 @RequestParam(name="money",required=false)Integer money,
							 @RequestParam(name="kefu",required=false)Integer kefu,
							 HttpServletResponse res) throws Exception {
//		System.out.println(AESUtil.decrypt("69CBC0961EAD88573955DAE7EE6B6DAE4A10D33CC9567AE23B5422A5AC3007165F7AD947348D283E37D3C2F2AE9BE9AF6E073E73D428CB745170AABF6D304F4A2CF63D68C5228C3F86FBAEEF926CE0217F454E6C50F3A9838C4E12BAFBB82AADA7E5989C05CC5A416E4D3855CABF9C8312F63F3B5C3A26FC9E82AA682571A4CF268CCF5892C4D88D1CE485A17FFF44FF4F1C356EFBAEE6A35D6325214230D7D54508A7881C66C4F6C23B3410581A449E8CAFDD49EB7C421CFC15E95A1B5149618C607FDFAA2D3846B8406794F6C3BCBEF327A6C538AD0BD45A46824E516D547B", key));
		Map<String,Object> map = new HashMap<String, Object>();
		String accessPayNo = UUID.randomUUID().toString().replace("-", "").toLowerCase();;
		PayManInfo payManInfo = new PayManInfo();
		payManInfo.setName(name);	
		payManInfo.setKefu(kefu);
		payManInfo.setKname(kefuRepository.findById(kefu).get().getKname());
//		if(tell == null) {
//			payManInfo.setRemarks("");
//		}
//		else
//			payManInfo.setRemarks(tell);
//		payManInfo.setTradeAmt(money);
//		payManInfo.setTradeType(type);
//		payManInfo.setGoodsName("程峰收款");
//		payManInfo.setRemarks(type);
		payManInfo.setAccessPayNo(accessPayNo);
		payManInfo.setTradeStatus("-99");
		if(goodsNameService.findOne(1) != null) {
			map.put("goodsName", goodsNameService.findOne(1).getGoodsName());
			payManInfo.setGoodsName(goodsNameService.findOne(1).getGoodsName());
		}else {
			map.put("goodsName", "程峰收款");
			payManInfo.setGoodsName("程峰收款");
		}
				
		map.put("merchantId", "100863200");
		map.put("withdrawType", "0");
		map.put("tradeType", "ZFBWAP");
		map.put("tradeAmt", money * 100);
		map.put("accessPayNo", accessPayNo);
		map.put("payNotifyUrl", payNotifyUrl);
		map.put("frontBackUrl", frontBackUrl);
		String url = "http://139.159.133.182:8080/pay/codePayment.do";
		String sign = Signature.getSign(map, key);
		map.put("sign", sign);
		System.out.println("--------------------");
		System.out.println(sign);
		System.out.println("--------------------");
		String data = AESUtil.encrypt(JSONObject.toJSONString(map),key);
		System.out.println(data);
		String postStr = "accessId="+accessId+"&data="+data;
		String s = PostUtil.sendPost(url,postStr);
		
		JSONObject jsonObject = JSONObject.parseObject(AESUtil.decrypt(s,key));
		System.out.println(AESUtil.decrypt(s,key));
		if(jsonObject.getInteger("code") == 0) {
			log.debug("请求成功！");
			payService.savePay(payManInfo);
			System.out.println( jsonObject.getString("htmlUrl"));
			//res.sendRedirect(jsonObject.getString("htmlUrl"));
			map = new HashMap<>();
			map.put("re",jsonObject.getString("htmlUrl"));
			map.put("accessPayNo",accessPayNo);
			return map;
		}
		else {
			log.debug("请求失败！");
		}
		map = new HashMap<>();
		map.put("re","");
		map.put("accessPayNo","");
		return map;
	}
	
	
//	@RequestMapping("/showPayList")
//	public JSONObject showPayList(Integer limit, Integer offset) {
//		JSONObject jsonObject = new JSONObject();
//		List<PayManInfo> payManInfoList = payService.showPayList();
//		int sie = limit * offset;
//		List<PayManInfo> payManInfoList1 = new ArrayList<>();
//		if (payManInfoList.size() > sie){
//            System.out.println(limit+"========================="+offset);
//            int k = payManInfoList.size()-sie;
//            if (k > limit)
//                for (int i = 0; i < limit;i++)
//                	payManInfoList1.add(payManInfoList.get(i+sie));
//            else
//                for (int i = 0 ; i < k; i++)
//                	payManInfoList1.add(payManInfoList.get(i+sie));
//        }else{
//            System.out.println(limit+"========================="+offset);
//            int j = limit*(offset);
//            int k = payManInfoList.size() - j;
//            for (int i = 0 ; i < k ; i++)
//            	payManInfoList1.add(payManInfoList.get(i+j));
//        }
//        jsonObject.put("total",payManInfoList.size());
//        jsonObject.put("rows",payManInfoList1);
//        
//        return jsonObject;
//	}	
	
//	@RequestMapping("/showPayList") 
//	public JSONObject showPayListByKefu(Integer limit, Integer offset,String kefu) {
//		JSONObject jsonObject = new JSONObject();
//		List<PayManInfo> payManInfoList = payService.showPayListByKefu(kefu);
//		int sie = limit * offset;
//		List<PayManInfo> payManInfoList1 = new ArrayList<>();
//		if (payManInfoList.size() > sie){
//            System.out.println(limit+"========================="+offset);
//            int k = payManInfoList.size()-sie;
//            if (k > limit)
//                for (int i = 0; i < limit;i++)
//                	payManInfoList1.add(payManInfoList.get(i+sie));
//            else
//                for (int i = 0 ; i < k; i++)
//                	payManInfoList1.add(payManInfoList.get(i+sie));
//        }else{
//            System.out.println(limit+"========================="+offset);
//            int j = limit*(offset);
//            int k = payManInfoList.size() - j;
//            for (int i = 0 ; i < k ; i++)
//            	payManInfoList1.add(payManInfoList.get(i+j));
//        }
//        jsonObject.put("total",payManInfoList.size());
//        jsonObject.put("rows",payManInfoList1);
//        
//        return jsonObject;
//	}
	
	@RequestMapping("/payNotify.do")
	public void payNotify(@RequestParam(name="accessId",required=false)String accessId,
						  @RequestParam(name="data",required=false)String data,
						  HttpServletResponse res) throws IOException {
		System.out.println("11111111");
		System.out.println(accessId);
		System.out.println(data);
		PayManInfo payManInfo = new PayManInfo();
		String payNotifyStr = AESUtil.decrypt(data, key);
		JSONObject jsonObject = JSONObject.parseObject(payNotifyStr);
		System.out.println("22222222222");
		System.out.println(jsonObject.getString("accessPayNo"));
		payManInfo.setAccessPayNo(jsonObject.getString("accessPayNo"));
		payManInfo.setPayNo(jsonObject.getString("payNo"));
		payManInfo.setTradeAmt(jsonObject.getInteger("tradeAmt"));	
		payManInfo.setActualAmt(jsonObject.getInteger("actualAmt"));
		payManInfo.setTradeStatus(jsonObject.getString("tradeStatus"));
		payManInfo.setPayTime(jsonObject.getDate("payTime"));
		payManInfo.setTradeType(jsonObject.getString("tradeType"));
		System.out.println(payManInfo.getTradeAmt()+"---------");
		boolean ifsucce = payService.savePay1(payManInfo.getPayNo(),
						   payManInfo.getTradeAmt(),
						   payManInfo.getActualAmt(),
						   payManInfo.getTradeStatus(),
						   payManInfo.getPayTime(),
						   payManInfo.getTradeType(),
						   payManInfo.getAccessPayNo());
		
		if(ifsucce) {
			log.debug("数据更新成功！");
		}
		else {
			log.debug("数据更新失败！");
		}
//		if(jsonObject.getString("tradeStatus").equals("1")) {
//			res.setCharacterEncoding("utf-8");
//			res.sendRedirect("/goBack.html");
//		}
		
	}
	
	@RequestMapping("/showMyPayList")
	public JSONObject showMyPayList(Integer limit, Integer offset,Integer kefu,String time1,String time2) {
		List<PayManInfo> payManInfoList = new ArrayList<>();
		String maxdate = "";
		String mindate = "";
		int res=time1.compareTo(time2);
        if(res>0) {
        	maxdate = time1;
        	mindate = time2;
        }
        else if(res==0)
        	maxdate = mindate = time1;
        else  {
        	maxdate = time2;
        	mindate = time1;
        }
        if(time1.equals("0") && time2.equals("0")) {
        	if(kefu == 0) {
        		payManInfoList = payService.showPayList();
        	}else
        		payManInfoList = payService.showPayListByKefu(kefu);
        }else {
        	if(kefu == 0) {
        		payManInfoList = payService.showPayListBytime(mindate,maxdate);
        	}else
        		payManInfoList = payService.showPayListByKefuAndTime(kefu,mindate,maxdate);
        }
        
		
//		if(kefu == 0 && days.equals("0")) {
//			payManInfoList = payService.showPayList();
//		}
//		if(kefu == 0 && !days.equals("0")) {
//			payManInfoList = payService.showPayListByDays(days);
//		}
//		if(kefu != 0 && days.equals("0")) { 
//			payManInfoList = payService.showPayListByKefu(kefu);
//		}
//		if(kefu != 0 && !days.equals("0")) {
//			payManInfoList = payService.showmyPayList(kefu,days);
//		}
		
		JSONObject jsonObject = new JSONObject();
		int sie = limit * offset;
		List<PayManInfo> payManInfoList1 = new ArrayList<>();
		if (payManInfoList.size() > sie){
            System.out.println(limit+"========================="+offset);
            int k = payManInfoList.size()-sie;
            if (k > limit)
                for (int i = 0; i < limit;i++)
                	payManInfoList1.add(payManInfoList.get(i+sie));
            else
                for (int i = 0 ; i < k; i++)
                	payManInfoList1.add(payManInfoList.get(i+sie));
        }else{
            System.out.println(limit+"========================="+offset);
            int j = limit*(offset);
            int k = payManInfoList.size() - j;
            for (int i = 0 ; i < k ; i++)
            	payManInfoList1.add(payManInfoList.get(i+j));
        }
        jsonObject.put("total",payManInfoList.size());
        jsonObject.put("rows",payManInfoList1);
        return jsonObject;
	}

	@RequestMapping("/Ispay")
	public Map<String ,Object> isPay(String accessPayNo){
		System.out.println("----------------------------1");
		return payService.isPay(accessPayNo);
	}
	
	@RequestMapping("/showMoneny")
	public Integer showMoneny(Integer kefu,String time1,String time2) {
		Integer total = 0;
		List<PayManInfo> payManInfoList = new ArrayList<>();
		String maxdate = "";
		String mindate = "";
		int res=time1.compareTo(time2);
        if(res>0) {
        	maxdate = time1;
        	mindate = time2;
        }
        else if(res==0)
        	maxdate = mindate = time1;
        else  {
        	maxdate = time2;
        	mindate = time1;
        }
        if(time1.equals("0") && time2.equals("0")) {
        	if(kefu == 0) {
        		payManInfoList = payService.showPayList();
        	}else
        		payManInfoList = payService.showPayListByKefu(kefu);
        }else {
        	if(kefu == 0) {
        		payManInfoList = payService.showPayListBytime(mindate,maxdate);
        	}else
        		payManInfoList = payService.showPayListByKefuAndTime(kefu,mindate,maxdate);
        }
//		if(kefu == 0 && days.equals("0")) {
//			payManInfoList = payService.showPayList();
//		}
//		if(kefu == 0 && !days.equals("0")) {
//			payManInfoList = payService.showPayListByDays(days);
//		}
//		if(kefu != 0 && days.equals("0")) { 
//			payManInfoList = payService.showPayListByKefu(kefu);
//		}
//		if(kefu != 0 && !days.equals("0")) {
//			payManInfoList = payService.showmyPayList(kefu,days);
//		}
		
		for(PayManInfo payManInfo : payManInfoList) {
			if(payManInfo.getTradeAmt() != null)
			total += payManInfo.getTradeAmt();
		}
		return total;
	}
	
}
