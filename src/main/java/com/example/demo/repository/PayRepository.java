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
	@Query(nativeQuery = true,value = "SELECT   *   FROM   pay_man_info where trade_status='1' order by  pay_time")  
	List<PayManInfo> findMyAll();

	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "UPDATE pay_man_info SET  pay_no =?1,trade_amt =?2,actual_amt=?3 , trade_status=?4 , pay_time=?5 , trade_type=?6 WHERE access_pay_no=?7")
	Integer saveadd(String PayNo,Integer TradeAmt,Integer ActualAmt,String TradeStatus, Date PayTime, String TradeType,String access_pay_no);

	PayManInfo findByAccessPayNo(String accessPayNo);
}
