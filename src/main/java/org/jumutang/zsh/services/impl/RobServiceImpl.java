package org.jumutang.zsh.services.impl;

import org.apache.commons.lang.time.DateUtils;
import org.jumutang.zsh.dao.ActivityDaoI;
import org.jumutang.zsh.dao.CustomerDaoI;
import org.jumutang.zsh.dao.ReceiveAddressDaoI;
import org.jumutang.zsh.model.*;
import org.jumutang.zsh.services.RobServiceI;

import java.util.*;

import org.jumutang.zsh.dao.RobDaoI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("robServiceI")
public class RobServiceImpl implements RobServiceI {

	@Autowired
	private RobDaoI robDaoI;

	@Autowired
	private ActivityDaoI activityDaoI;

	@Autowired
	private CustomerDaoI customerDaoI;

	@Autowired
	private ReceiveAddressDaoI receiveAddressDaoI;

	/**
	 * 添加秒杀
	 * @param rob
	 * @return
	 */
	@Transactional
	public int addRob(Rob rob,String userId) {

		boolean flag1 = false;
		boolean flag2 = false;

		//秒杀积分 防止更改
		int integration = -1;
		//查询活动
		ActivityModel activityModel = new ActivityModel();
		activityModel.setActivityId(rob.getActivityId());
		List<ActivityModel> activityList = activityDaoI.queryActivity(activityModel);
		ActivityModel updateActivityModel = null;
		if(activityList.size()>0){
			ActivityModel queryActivityModel = activityList.get(0);
			integration = queryActivityModel.getIntegral();
			if(queryActivityModel.getNum()==0){
				return 1;
			}else{
				updateActivityModel = queryActivityModel;
				updateActivityModel.setNum(queryActivityModel.getNum()-1);
				flag1=true;
			}
			//时间没有比较
		}else{
			return 1;
		}

		//查询修改用户积分
		CustomerModel customerModel = new CustomerModel();
		customerModel.setCustomerOpenId(userId);
		List<CustomerModel> customerModels = customerDaoI.queryCustomer(customerModel);
		CustomerModel updateCustomerModel = null;
		if(customerModels.size()>0){
			CustomerModel queryCustomerModel = customerModels.get(0);
			if(integration==-1){
				return 1;
			}
			if(queryCustomerModel.getCustomerIntegral()>=integration){
				updateCustomerModel = queryCustomerModel;
				updateCustomerModel.setCustomerIntegral(queryCustomerModel.getCustomerIntegral()-integration);
				flag2=true;
			}else{
				return 1;
			}
		}

		if(flag1&&flag2){
			try{
				int activityInt = activityDaoI.updateActivity(updateActivityModel);
				if(activityInt <= 0 ){
					throw new Exception("更新活动出错");
				}
				int customerInt = customerDaoI.updateCustomer(updateCustomerModel);
				if(customerInt <= 0){
					throw new Exception("更新用户积分出错");
				}
				rob.setRobId(ZshUtil.createUuid());
				rob.setOpenId(userId);
				rob.setRobDate(DateUtil.getDate());
				rob.setRobStatus((short)2);
				int robInt = robDaoI.addRob(rob);
				if(robInt <= 0){
					throw new Exception("插入秒杀失败");
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			return 0;
		}else{
			return 1;
		}
	}

	/**
	 * 统计
	 * @return 
	 */
	public int countRob(Rob rob) {
		return robDaoI.countRob(rob);
	}

	@Override
	public List<Rob> queryRob(Rob rob) {
		return robDaoI.queryRob(rob);
	}

	/**
	 * 查询
	 * @return 
	 */
	public List<Map> queryRobCombination(Rob rob) {
		List<Map> returnList = new ArrayList<>();
		List<Rob> robList = robDaoI.queryRob(rob);
		for(Rob rob1:robList){
			Map returnMap = new HashMap();
			returnMap.put("robId",rob1.getRobId());
			returnMap.put("robDate",rob1.getRobDate());
			ActivityModel activityModel = new ActivityModel();
			activityModel.setActivityId(rob1.getActivityId());
			List<ActivityModel> activityModelList = activityDaoI.queryActivity(activityModel);
			if(activityModelList.size()>0){
				returnMap.put("theme",activityModelList.get(0).getTheme());
			}
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomerOpenId(rob1.getOpenId());
			List<CustomerModel> customerModelList = customerDaoI.queryCustomer(customerModel);
			if(customerModelList.size()>0){
				returnMap.put("customerName",customerModelList.get(0).getCustomerName());
				returnMap.put("customerPhone",customerModelList.get(0).getPhone());
			}
			returnList.add(returnMap);
		}
		return returnList;
	}

	@Override
	public int updateRob(Rob rob) {
		return robDaoI.updateRob(rob);
	}

	@Override
	public List<Map> queryAll(Rob queryRob) {
		List<Map> returnList = new ArrayList<>();
		List<Rob> robList = robDaoI.queryRob(queryRob);
		for(Rob rob:robList){
			Map returnMap = new HashMap();
			ActivityModel activityModel = new ActivityModel();
			activityModel.setActivityId(rob.getActivityId());
			List<ActivityModel> activityModelList = activityDaoI.queryActivity(activityModel);
			if(activityModelList.size()>0){
				returnMap.put("activityName",activityModelList.get(0).getTheme());
			}
			returnMap.put("robStatus",rob.getRobStatus());
			returnMap.put("robId",rob.getRobId());
			returnMap.put("robDate",rob.getRobDate());
			if(rob.getReceiveStatus()==(short)1){
				CustomerModel customerModel = new CustomerModel();
				customerModel.setCustomerOpenId(rob.getOpenId());
				List<CustomerModel> customerModelList = customerDaoI.queryCustomer(customerModel);
				if(customerModelList.size()>0){
					returnMap.put("consignee",customerModelList.get(0).getCustomerName());
					returnMap.put("phone",customerModelList.get(0).getPhone());
					returnMap.put("type","自提");
					returnMap.put("address",rob.getPickupAddress());
					returnList.add(returnMap);
				}
			}else if(rob.getReceiveStatus()==(short)2){
				ReceiveAddress receiveAddress = new ReceiveAddress();
				receiveAddress.setrId(rob.getAddressId());
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
