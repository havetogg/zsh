package org.jumutang.zsh.services.impl;

import org.jumutang.zsh.dao.PCUserDaoI;
import org.jumutang.zsh.model.PCUserModel;
import org.jumutang.zsh.services.PCUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 10:57 2017/6/26
 * @Modified By:
 */
@Service
public class PCUserServiceImpl implements PCUserServiceI{

    @Autowired
    private PCUserDaoI pcUserDaoI;

    @Override
    public PCUserModel getUserModel(PCUserModel pcUserModel) {
        return pcUserDaoI.getUserModel(pcUserModel);
    }
}
