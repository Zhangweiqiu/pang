package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.GoodsName;
import com.example.demo.pojo.PayManInfo;


public interface PayRepository extends CrudRepository<PayManInfo,Long>{
	@Modifying
	@Query(nativeQuery = true,value = "SELECT   *   FROM   pay_man_info order by  payTime")  
	List<PayManInfo> findMyAll();

	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "UPDATE pay_man_info SET  payNo =?1 AND tradeAmt =?2 AND actualAmt=?3 AND tradeStatus=?4 AND payTime=?5 AND tradeType=?6 WHERE access_pay_no=?7")
	boolean saveadd(String PayNo,double TradeAmt,double ActualAmt,String TradeStatus, Date PayTime, String TradeType,String access_pay_no);
}
