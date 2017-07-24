package org.jumutang.zsh.dao;

import org.jumutang.zsh.model.PCUserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 10:01 2017/6/26
 * @Modified By:
 */
@Repository
public interface PCUserDaoI {

    PCUserModel getUserModel(PCUserModel pcUserModel);
}
