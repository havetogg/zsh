package org.jumutang.zsh.services.impl;

import org.jumutang.zsh.dao.CustomerDaoI;
import org.jumutang.zsh.dao.GoodDaoI;
import org.jumutang.zsh.dao.ReceiveAddressDaoI;
import org.jumutang.zsh.model.*;
import org.jumutang.zsh.services.ExchangeServiceI;
import org.jumutang.zsh.tools.BeanUtil;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ResponseModel;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.jumutang.zsh.dao.ExchangeDaoI;
import org.springframework.transaction.annotation.Transactional;

@Service("exchangeServiceI")
public class ExchangeServiceImpl implements ExchangeServiceI {
	
	@Autowired
	private ExchangeDaoI exchangeDaoI;

	@Autowired
	private GoodDaoI goodDaoI;

	@Autowired
	private CustomerDaoI customerDaoI;

	@Autowired
	private ReceiveAddressDaoI receiveAddressDaoI;
	
	/**
	 * 兑换状态
	 */
	//private final static Short EXCHANGE_STATUS=1;
	
	//默认每页10条
	private final static Integer PAGE_SIZE = 500000;
	
	/**
	 * 添加兑换状态
	 */
	public int addExchange(Exchange exchange,String userId,String userName) {

		//设置两个标识符控制商品库存表和用户积分表
		boolean flag1=false;
		boolean flag2=false;
		//商品积分 防止更改
		int integration = -1;

		//查询修改商品库存
		GoodModel goodModel = new GoodModel();
		goodModel.setGoodId(exchange.getGoodId());
		goodModel.setGoodState(1);
		List<GoodModel> goodModels= goodDaoI.queryGoodInfo(goodModel);
		GoodModel updateGoodModel = null;
		if(goodModels.size()>0){
			GoodModel queryGoodModel = goodModels.get(0);
			exchange.setGoodName(queryGoodModel.getGoodName());
			integration = queryGoodModel.getIntegration();
			if(queryGoodModel.getInventory()-exchange.getExchangeCount()>=0){
				updateGoodModel = queryGoodModel;
				updateGoodModel.setInventory(queryGoodModel.getInventory()-exchange.getExchangeCount());
				updateGoodModel.setReceive(queryGoodModel.getReceive()+exchange.getExchangeCount());
				flag1=true;
			}else{
				return 0;
			}
		}else{
			return 0;
		}

		//查询修改用户积分
		CustomerModel customerModel = new CustomerModel();
		customerModel.setCustomerOpenId(userId);
		List<CustomerModel> customerModels = customerDaoI.queryCustomer(customerModel);
		CustomerModel updateCustomerModel = null;
		if(customerModels.size()>0){
			CustomerModel queryCustomerModel = customerModels.get(0);
			if(integration==-1){
				return 0;
			}
			if(queryCustomerModel.getCustomerIntegral()>=integration*exchange.getExchangeCount()){
				updateCustomerModel = queryCustomerModel;
				updateCustomerModel.setCustomerIntegral(queryCustomerModel.getCustomerIntegral()-integration*exchange.getExchangeCount());
				flag2=true;
			}else{
				return 0;
			}
		}

		exchange.setIntegration(integration*exchange.getExchangeCount());
		exchange.setExchangeId(ZshUtil.createUuid());
		exchange.setExchangeDate(DateUtil.getFullTime());

		if(flag1&&flag2){
			goodDaoI.updateGood(updateGoodModel);
			customerDaoI.updateCustomer(updateCustomerModel);
			exchange.setExchangeStatus((short)2);
			exchangeDaoI.addExchange(exchange);
			return 1;
		}else{
			exchange.setExchangeStatus((short)1);
			exchangeDaoI.addExchange(exchange);
			return 0;
		}
	}
	
	/**
	 * 查询兑换礼物\
	 * @param param
	 * @return 
	 */
	public PageMode queryByPage(Map<String,Object> param,PageMode pageMode){
		param.put("pageIndex", (pageMode.getPageIndex()-1)*PAGE_SIZE);
		param.put("pageSize", PAGE_SIZE);
		List<Exchange> list = exchangeDaoI.queryByPage(param);
		List<Map> returnList = new ArrayList();
		for(Exchange exchange:list){
			Map returnMap = new HashMap();
			returnMap.put("exchangeId",exchange.getExchangeId());
			returnMap.put("exchangeDate",exchange.getExchangeDate());
			returnMap.put("orderNum",exchange.getExchangeId());
			returnMap.put("exchangeStatus",exchange.getExchangeStatus());
			GoodModel goodModel = new GoodModel();
			goodModel.setGoodId(exchange.getGoodId());
			List<GoodModel> goodModelList = goodDaoI.queryGoodInfo(goodModel);
			if(goodModelList.size()>0){
				returnMap.put("goodName",goodModelList.get(0).getGoodName());
				returnMap.put("integration",goodModelList.get(0).getIntegration());
			}
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomerOpenId(exchange.getOpenId());
			List<CustomerModel> customerModelList = customerDaoI.queryCustomer(customerModel);
			if(customerModelList.size()>0){
				returnMap.put("customerName",customerModelList.get(0).getCustomerName());
			}
			returnList.add(returnMap);
		}
		pageMode.setList(returnList);
		pageMode.setPageNow(pageMode.getPageIndex());
		return pageMode;
	}

	/**
	 * 统计信息条数
	 * @param param
	 * @return
	 */
	public int countExchange(Map<String,Object> param){
		return exchangeDaoI.countExchange(param);
	}

	public List<Map> queryByUser(String userId) {
		List<Exchange> exchangeList = exchangeDaoI.queryByUser(userId);
		List<Map> returnList = new ArrayList();
		for(Exchange exchange:exchangeList){
			Map returnMap = BeanUtil.transBean2Map(exchange);
			GoodModel goodModel = new GoodModel();
			goodModel.setGoodId(exchange.getGoodId());
			List<GoodModel> goodModelList = goodDaoI.queryGoodInfo(goodModel);
			if(goodModelList.size()>0){
				returnMap.put("picUrl",goodModelList.get(0).getPicUrl());
			}
			returnList.add(returnMap);
		}
		return returnList;
	}

	public Exchange queryByExchangeId(String exchangeId) {
		return exchangeDaoI.queryByExchangeId(exchangeId);
	}

	public int updateExchange(Exchange exchange){
		return exchangeDaoI.updateExchange(exchange);
	}

	@Override
	public List<Map> queryAll() {
		List<Map> returnList = new ArrayList<>();
		List<Exchange> exchangeList = exchangeDaoI.queryAll();
		for(Exchange exchange:exchangeList){
			Map returnMap = new HashMap();
			returnMap.put("goodName",exchange.getGoodName());
			returnMap.put("exchangeStatus",exchange.getExchangeStatus());
			returnMap.put("exchangeId",exchange.getExchangeId());
			if(exchange.getReceiveStatus()==(short)1){
				CustomerModel customerModel = new CustomerModel();
				customerModel.setCustomerOpenId(exchange.getOpenId());
				List<CustomerModel> customerModelList = customerDaoI.queryCustomer(customerModel);
				if(customerModelList.size()>0){
					returnMap.put("consignee",customerModelList.get(0).getCustomerName());
					returnMap.put("phone",customerModelList.get(0).getPhone());
					returnMap.put("type","自提");
					returnMap.put("address",exchange.getPickupAddress());
					returnList.add(returnMap);
				}
			}else if(exchange.getReceiveStatus()==(short)2){
				ReceiveAddress receiveAddress = new ReceiveAddress();
				receiveAddress.setrId(exchange.getAddressId());
				List<ReceiveAddress> receiveAddressList = receiveAddressDaoI.queryById(receiveAddress);
				if(receiveAddressList.size()>0){
					returnMap.put("consignee",receiveAddressList.get(0).getrName());
					returnMap.put("phone",receiveAddressList.get(0).getrPhone());
					returnMap.put("type","送货");
					returnMap.put("address",receiveAddressList.get(0).getrAddress());
					returnList.add(returnMap);
				}
			}
		}
		return returnList;
	}
}
