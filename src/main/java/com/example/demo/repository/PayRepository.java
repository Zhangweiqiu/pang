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
	@Query(nativeQuery = true,value = "SELECT   *   FROM   pay_man_info where trade_status='1' and kefu=?1 order by  pay_time")  
	List<PayManInfo> findMyAllByKefu(Integer kefu);

	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "UPDATE pay_man_info SET  pay_no =?1,trade_amt =?2,actual_amt=?3 , trade_status=?4 , pay_time=?5 , trade_type=?6 WHERE access_pay_no=?7")
	Integer saveadd(String PayNo,Integer TradeAmt,Integer ActualAmt,String TradeStatus, Date PayTime, String TradeType,String access_pay_no);

	PayManInfo findByAccessPayNo(String accessPayNo);

	@Modifying
	@Query(nativeQuery = true,value = "SELECT * FROM pay_man_info WHERE trade_status='1' and kefu=?1 AND DATE_SUB(CURDATE(), INTERVAL ?2 DAY) <= DATE(pay_time) order by  pay_time")  
	List<PayManInfo> findMyNewAll(Integer kefu,String days);

	@Modifying
	@Query(nativeQuery = true,value = "SELECT   *   FROM   pay_man_info where trade_status='1' and DATE_SUB(CURDATE(), INTERVAL ?1 DAY) <= DATE(pay_time) order by  pay_time")  
	List<PayManInfo> showPayListByDays(String days);

	@Modifying
	@Query(nativeQuery = true,value = "SELECT SUM(trade_amt) FROM pay_man_info  WHERE trade_status='1'")  
	Integer showAllMoney();

	@Modifying
	@Query(nativeQuery = true,value = "SELECT SUM(trade_amt) FROM pay_man_info  WHERE trade_status='1' and kefu=?1")  
	Integer showMyMoney(Integer kid);
}
