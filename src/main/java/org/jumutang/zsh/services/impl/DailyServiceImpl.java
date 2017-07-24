package org.jumutang.zsh.services.impl;

import org.jumutang.zsh.dao.DailyDaoI;
import org.jumutang.zsh.model.DailyModel;
import org.jumutang.zsh.services.DailyServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 13:55 2017/4/20
 * @Modified By:
 */
@Service("dailyService")
public class DailyServiceImpl implements DailyServiceI{

    @Autowired
    private DailyDaoI dailyDaoI;

    @Override
    public int saveDaily(DailyModel dailyModel) {
        dailyModel.setDailyId(ZshUtil.createUuid());
        dailyModel.setCreateDate(DateUtil.formatDate());
        return dailyDaoI.saveDaily(dailyModel);
    }

    @Override
    public int updateDaily(DailyModel dailyModel) {
        return dailyDaoI.updateDaily(dailyModel);
    }

    @Override
    public List<DailyModel> listDaily(DailyModel dailyModel) {
        return dailyDaoI.listDaily(dailyModel);
    }
}
