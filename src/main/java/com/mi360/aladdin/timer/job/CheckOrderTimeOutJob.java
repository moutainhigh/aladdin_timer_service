package com.mi360.aladdin.timer.job;


import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mi360.aladdin.entity.order.Order;
import com.mi360.aladdin.mq.base.producer.MqProducer;
import com.mi360.aladdin.order.service.IOrderService;

@Component
public class CheckOrderTimeOutJob {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private MqProducer mqProducer;
	
	@Scheduled(cron="* 0/1 * * * ? ")
	public void cancelPayTimeOutOrder(){
		
		String requestId = UUID.randomUUID().toString().replace("-", "");
		
		List<Order> orderList = orderService.selectTimeOutOrder(requestId);		
		for(int i=0;i<orderList.size();i++){
			Order order = orderList.get(i);
			order.setOrderStatus("CAN");
			orderService.updateOrder(order, requestId);
			
		}
		
	}
	
	/**
	 * 	判断订单是否距离下单时间超过15天 每隔5分钟到数据库检车一次 
	 * 	1 如果已发货 则自动改为 COM 并且退款状态 不为 退款中或  申请退款  
	 */
	@Scheduled(cron="* * 0/1 * * ? ")
	public void autoCompleteOrder(){
		
		String requestId = UUID.randomUUID().toString().replace("-", "");
		
		List<Order> orderList = orderService.selectOvertimeOrder(requestId);
		for(int i=0;i<orderList.size();i++){
			orderList.get(i).setOrderStatus("COM");
			orderService.updateOrder(orderList.get(i), requestId);
			
			mqProducer.orderComplete(requestId, orderList.get(i).getOrderCode());
		}
		
	}
	
	
}
