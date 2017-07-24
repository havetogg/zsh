package org.jumutang.zsh.tools;


import net.sf.json.JSONObject;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 10:50 2017/6/27
 * @Modified By:
 */
public class AccessTokenUtil {

    private static String tokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx39c1f88b39e5ccf4&corpsecret=b9DEzVqTYAf6CtG9lh9bqWRD3sa088DaY1up9Cg0kDQ4JVFtmKwAJFWupOTYvu0B";

    private static String access_token = null;

    private static long accesstocken_timestamp = 0;

    private static long accesstocken_expiresIn = 0;

    /**
     * 获取access_tocken
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        if (null == access_token || System.currentTimeMillis() >= accesstocken_timestamp + accesstocken_expiresIn) {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.sendPost(tokenUrl,"utf-8",null));
            access_token = jsonObject.getString("access_token");
            accesstocken_timestamp = System.currentTimeMillis();
            accesstocken_expiresIn = jsonObject.getInt("expires_in") * 1000;
        }
        return access_token;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(AccessTokenUtil.getAccessToken());
    }
}
