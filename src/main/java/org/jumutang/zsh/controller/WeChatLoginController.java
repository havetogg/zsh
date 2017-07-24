package org.jumutang.zsh.controller;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jumutang.zsh.model.CustomerModel;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.services.CustomerServiceI;
import org.jumutang.zsh.services.PCUserServiceI;
import org.jumutang.zsh.tools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

/**
 * 微信授权登录
 */
@Controller
@RequestMapping("/wc")
public class WeChatLoginController {
	
	Logger logger = Logger.getLogger(WeChatLoginController.class);
	@Autowired
	private CustomerServiceI customerServiceI;

	@Autowired
	private PCUserServiceI pcUserServiceI;
	
	@Value("#{propertyFactoryBean['corpid']}")
	private String corpid;
	
	@Value("#{propertyFactoryBean['secret']}")
	private String secret;
	
	//储存token信息
	private String access_token;

	private String[] customerPage = {"todayPrice.html","memberCenter.html"};

	private String[] staffPage = {"indexd.html","indexed.html"};
	
	/**
	 * 微信端获取code 微信登陆步骤1
	 * @param request 
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/oauth")
	public void weChatLogin(HttpServletRequest request,HttpServletResponse response){
		try{
			logger.debug("-----------------------------"+corpid);
			logger.debug("-----------------------------"+secret);
			String backUrl = request.getParameter("redUrl");
			request.getSession().setAttribute("redUrl", backUrl);
			String wxUrl="https://open.weixin.qq.com/connect/oauth2/authorize?";
			StringBuffer sb = new StringBuffer(wxUrl);
			String baseUrl=request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/wc/toIndex.do";
			sb.append("appid="+corpid);
			sb.append("&redirect_uri="+URLEncoder.encode(baseUrl));
			sb.append("&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
			response.setCharacterEncoding("utf-8");
			response.sendRedirect(sb.toString());
			logger.debug("-----------"+sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信获取access_token同时获取用户信息 微信登陆步骤2
	 * @param request
	 * @param response
	 * @param code
	 */
	@RequestMapping("/toIndex")
	public void intoIndex(HttpServletRequest request,HttpServletResponse response,String code){
		try{
			HttpSession session = request.getSession();
		//获取AccessToken
		String atUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?";
		StringBuffer sb = new StringBuffer();
		sb.append(atUrl);
		sb.append("corpid="+corpid);
		sb.append("&corpsecret="+secret);
		logger.debug("------------------------------"+atUrl);
		String result =HttpUtil.sendPost(sb.toString(), "utf-8", null);
		logger.debug("-----------------------"+result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String token = jsonObject.getString("access_token");
		logger.debug("token为-----------------------------"+token);
		access_token = token;
		session.setAttribute("token", token);
		String userUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?";
		sb = new StringBuffer("");
		sb.append(userUrl);
		sb.append("access_token="+token);
		sb.append("&code="+code);
		String result1 = HttpUtil.sendPost(sb.toString(), "utf-8", null);
		logger.debug("获取成员信息为---------------------------"+result1);
		JSONObject object = JSONObject.fromObject(result1);
		if(object.has("UserId")){
			String userId = object.getString("UserId");
			logger.info("企业号用户UserId为"+userId);
			response.setCharacterEncoding("utf-8");
			session.setAttribute("userId", userId);
			String result2 =HttpUtil.sendPost("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&userid="+userId, "utf-8", null);
			JSONObject jsonObject2 = JSONObject.fromObject(result2);
			logger.info("返回的JSON对象为"+jsonObject2);
			if(jsonObject2.has("department")){
				String[] sArr = ZshUtil.repalceStr(jsonObject2.getString("department")).split(",");
				for(String s:sArr) {
					if ("2".equals(s)||"6".equals(s)||"7".equals(s)||"9".equals(s)||"11".equals(s)||"12".equals(s)||"13".equals(s)||"14".equals(s)) {
						session.setAttribute("wxUserType",2);
						logger.info("员工登陆微信端");
					}else if("3".equals(s)||"5".equals(s)||"4".equals(s)||"10".equals(s)||"17".equals(s)||"18".equals(s)){
						if("3".equals(s)||"5".equals(s)){
							session.setAttribute("customerLevel",0);
						}else if("10".equals(s)){
							session.setAttribute("customerLevel",1);
						}else if("17".equals(s)){
							session.setAttribute("customerLevel",2);
						}else if("4".equals(s)){
							session.setAttribute("customerLevel",3);
						}else if("18".equals(s)){
							session.setAttribute("customerLevel",4);
						}
						session.setAttribute("wxUserType",1);
						logger.info("客户登陆微信端");
					}else if("1".equals(s)){
						logger.info("管理员登陆");
						session.setAttribute("customerLevel",5);
						session.setAttribute("wxUserType",3);
					}
				}
			}
			if(jsonObject2.has("name")){
				session.setAttribute("name",jsonObject2.get("name").toString());
			}
			CustomerModel paramCustomer = new CustomerModel();
			paramCustomer.setCustomerOpenId(userId);
			List<CustomerModel> customerModel = customerServiceI.queryCustomer(paramCustomer);
			if(customerModel.size()==0){
				CustomerModel customerModel2 = new CustomerModel();
				if(jsonObject2.has("name")){
					if(EmojiFilter.containsEmoji(jsonObject2.get("name").toString())){
						customerModel2.setCustomerName(EmojiFilter.filterEmoji(jsonObject2.get("name").toString()));
					}else{
						customerModel2.setCustomerName(jsonObject2.get("name").toString());
					}
				}
				if(jsonObject2.has("department")){
					String[] sArr = ZshUtil.repalceStr(jsonObject2.getString("department")).split(",");
					for(String s:sArr) {
						if ("2".equals(s)||"6".equals(s)||"7".equals(s)||"9".equals(s)||"11".equals(s)||"12".equals(s)||"13".equals(s)||"14".equals(s)) {
							customerModel2.setCustomerType((short)2);
						}else if("1".equals(s)||"3".equals(s)||"5".equals(s)||"4".equals(s)||"10".equals(s)){
							customerModel2.setCustomerType((short)1);
						}
					}
				}
				if(jsonObject2.has("avatar")){
					customerModel2.setCustomerHead(jsonObject2.getString("avatar"));
				}
				if(jsonObject2.has("mobile")){
					customerModel2.setPhone(jsonObject2.getString("mobile"));
				}
				if(jsonObject2.has("position")){
					customerModel2.setPosition(jsonObject2.getString("position"));
				}
				customerModel2.setCustomerOpenId(userId);
				int result3 = customerServiceI.addCustomer(customerModel2);
				logger.debug("插入customer表结果为-------------------------"+result3);
			}		
			logger.debug("----------------------------"+userId);
			 String redUrl = (String)request.getSession().getAttribute("redUrl");
			 logger.info("重定向地址---------------"+redUrl);
			if(redUrl !=null){
				if((int)session.getAttribute("wxUserType") == 1){
					for(String s:customerPage){
						if(redUrl.contains(s)){
							logger.info("重定类型为1---------------"+redUrl);
							response.sendRedirect(redUrl);
							return;
						}
					}
				}
				if((int)session.getAttribute("wxUserType") == 2||(int)session.getAttribute("wxUserType") == 3){
					for(String s:staffPage){
						if(redUrl.contains(s)){
							logger.info("重定类型为2---------------"+redUrl);
							response.sendRedirect(redUrl);
							return;
						}
					}
				}
				response.sendRedirect("/zsh/error.html");
			}
		}else{
			response.sendRedirect("/zsh/error.html");
		}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 *PC端登陆
	 */
	@RequestMapping("/PCLogin")
	public @ResponseBody ResResult PCLogin(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		if(session.getAttribute("PCUser") != null){
			return ResponseModel.successActive();
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
			logger.info("用户名或者密码为空!");
			return ResponseModel.errorActive("用户名或者密码为空!");
		}
		PCUserModel queryPCUserModel = new PCUserModel();
		queryPCUserModel.setUsername(username);
		queryPCUserModel.setPassword(Md5.EncoderByMd5(password));
		PCUserModel currentUser = pcUserServiceI.getUserModel(queryPCUserModel);
		if(currentUser == null){
			logger.info("用户名或者密码不正确!");
			return ResponseModel.errorActive("用户名或者密码不正确!");
		}
		session.setAttribute("PCUser",currentUser);
		Map returnMap = new HashMap();
		returnMap.put("role",currentUser.getRole());
		return ResponseModel.returnData(returnMap);
	}

	/**
	 * 获取access_token同时获取用户信息 PC登陆步骤2
	 * @param request
	 * @param response
	 * @param auth_code
	 */
	/*@RequestMapping("/toIndex2")
	public void intoIndex2(HttpServletRequest request,HttpServletResponse response,String auth_code){
		try{
			HttpSession session = request.getSession();
			//获取AccessToken
			String atUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?";
			StringBuffer sb = new StringBuffer();
			sb.append(atUrl);
			sb.append("corpid="+corpid);
			sb.append("&corpsecret="+secret);
			logger.debug("------------------------------"+atUrl);
			String result =HttpUtil.sendPost(sb.toString(), "utf-8", null);
			logger.debug("-----------------------"+result);
			JSONObject jsonObject = JSONObject.fromObject(result);
			String token = jsonObject.getString("access_token");
			logger.debug("-----------------------------"+token);
			access_token = token;
			session.setAttribute("token", token);
			String userUrl = "https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?";
			sb = new StringBuffer("");
			sb.append(userUrl);
			sb.append("access_token="+token);
			*//*sb.append("&auth_code="+auth_code);*//*
			JSONObject obj = new JSONObject();
			obj.put("auth_code",auth_code);
			String result1 = HttpUtil.sendPost(sb.toString(), "utf-8", obj.toString());
			logger.debug("---------------------------"+result1);
			JSONObject object = JSONObject.fromObject(result1);
			logger.info("PC端登陆结果参数"+object.toString());
			response.setCharacterEncoding("utf-8");
			if(object.has("usertype")){
				//获取用户信息
				JSONObject memberObj = object.getJSONObject("user_info");
				//将userid放入缓存中
				String userId = memberObj.getString("userid");
				session.setAttribute("userId", userId);
				logger.info("登陆userId为------------------------"+userId);
				//将name放入缓存中
				String name = memberObj.getString("name");
				session.setAttribute("name", name);
				logger.info("登陆name为------------------------"+name);
				//将头像放入session中
				String avatar = memberObj.getString("avatar");
				session.setAttribute("avatar", avatar);
				//判断用户类型如果是企业号成员就进行部门判断，如果是管理员则直接放行 将用户类型放入session
				int userType = object.getInt("usertype");
				//企业号成员
				if(userType == 5){
					String result2 =HttpUtil.sendPost("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&userid="+userId, "utf-8", null);
					JSONObject jsonObject2 = JSONObject.fromObject(result2);
					if(jsonObject2.has("department")){
						boolean flag = false;
						String[] sArr = ZshUtil.repalceStr(jsonObject2.getString("department")).split(",");
						for(String s:sArr) {
							if ("15".equals(s)) {
								flag = true;
								session.setAttribute("userType",1);
								logger.info("用户为配送类型为1");
								response.sendRedirect("/zsh/error.html");
							}else if("16".equals(s)){
								flag = true;
								session.setAttribute("userType",2);
								logger.info("用户为备用部门类型为2");
								response.sendRedirect("/zsh/error.html");
							}
						}
						if(!flag){
							response.sendRedirect("/zsh/error.html");
						}
					}else{
						response.sendRedirect("/zsh/error.html");
					}
				}else{
					session.setAttribute("userType",0);
					logger.info("用户为管理员类型为0");
					response.sendRedirect("/zsh/manager/index.html");
				}
			}else{
				response.sendRedirect("/zsh/error.html");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/


	/**
	 * 检查微信登陆状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkWxLoginInfo")
	public @ResponseBody ResResult checkWxLoginInfo(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		int wxUserType = (int)session.getAttribute("wxUserType");
		logger.info("userId为------------"+userId);
		logger.info("判断用户是否------------");
		logger.info("用户身份为------------"+wxUserType);
		if(userId==null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("该用户未登陆");
		}
		if(wxUserType == 1 || wxUserType == 2|| wxUserType == 3){
			logger.info("用户身份正常");
			return ResponseModel.returnData(wxUserType);
		}else{
			return ResponseModel.errorActive("用户身份未确定");
		}
	}

	/**
	 * 检查PC登陆状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkPCLoginInfo")
	public @ResponseBody ResResult checkPCLoginInfo(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		Object pcUserObj = session.getAttribute("PCUser");
		logger.info("判断用户是否登陆");
		if(pcUserObj==null){
			logger.info("用户未登录");
			return ResponseModel.errorActive("该用户未登陆");
		}
		PCUserModel currentUser = (PCUserModel)pcUserObj;
		short role = currentUser.getRole();
		if(role == 0 || role == 1 || role == 2){
			logger.info("用户身份正常");
			Map returnMap = new HashMap();
			returnMap.put("role",role);
			return ResponseModel.returnData(returnMap);
		}else{
			return ResponseModel.errorActive("用户身份未确定");
		}
	}


	/**
	 * PC端注销登录状态
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/logoutPC")
	public @ResponseBody ResResult logoutPC(HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession(false);//防止创建Session
		if(session == null){
			return ResponseModel.successActive();
		}else{
			Enumeration em = request.getSession().getAttributeNames();
			while(em.hasMoreElements()){
				request.getSession().removeAttribute(em.nextElement().toString());
			}
			return ResponseModel.successActive();
		}
	}
}