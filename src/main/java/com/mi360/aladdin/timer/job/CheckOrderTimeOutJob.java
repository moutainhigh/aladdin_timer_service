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
	
	@Scheduled(cron="0/15 * * * * ? ")
	public void share(){
		
		String requestId = UUID.randomUUID().toString().replace("-", "");
		
		List<Order> orderList = orderService.selectTimeOutOrder(requestId);		
		for(int i=0;i<orderList.size();i++){
			Order order = orderList.get(i);
			order.setOrderStatus("CAN");
			orderService.updateOrder(order, requestId);
			logger.info("发送消息队列");
			System.out.println("自动取消订单");
			//TODO 发送消息队列
			
			
			
		}
		
	}
	
	/**
	 * 	判断订单是否距离下单时间超过15天 每隔5分钟到数据库检车一次 
	 * 	1 如果已发货 则自动改为 COM
	 *  2 如果未发货 
	 *  3 如果申请退款
	 *  4 如果申请退货 	
	 */
	@Scheduled(cron="0/5 * * * * ? ")
	public void printTime(){
		
		String requestId = UUID.randomUUID().toString().replace("-", "");
		
		List<Order> orderList = orderService.selectOvertimeOrder(requestId);
		for(int i=0;i<orderList.size();i++){
			orderList.get(i).setOrderStatus("COM");
			orderService.updateOrder(orderList.get(i), requestId);
			logger.info("发送消息队列");
			System.out.println("自动完成订单");
			//TODO 发送消息队列
		}
		
	}
	
	
}
