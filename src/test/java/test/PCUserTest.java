package test;

import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.services.PCUserServiceI;
import org.jumutang.zsh.tools.AccessTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 11:02 2017/6/26
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-core.xml" })
public class PCUserTest {


    @Test
    @Rollback(true)
    public void test1() throws Exception{
        AccessTokenUtil.getAccessToken();
        AccessTokenUtil.getAccessToken();
    }
}
