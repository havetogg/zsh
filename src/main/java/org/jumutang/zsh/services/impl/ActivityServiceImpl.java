package org.jumutang.zsh.services.impl;

import org.jumutang.zsh.dao.ActivityDaoI;
import org.jumutang.zsh.model.ActivityModel;
import org.jumutang.zsh.services.ActivityServiceI;
import org.jumutang.zsh.tools.DateUtil;
import org.jumutang.zsh.tools.ZshUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/31.
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityServiceI{

    @Resource
    private ActivityDaoI activityDaoI;

    @Override
    public List<ActivityModel> queryActivity(ActivityModel activityModel) {
        return activityDaoI.queryActivity(activityModel);
    }

    @Override
    public int addActivity(ActivityModel activityModel) {
        activityModel.setActivityId(ZshUtil.createUuid());
        activityModel.setCreateDate(DateUtil.getFullTime());
        return activityDaoI.addActivity(activityModel);
    }

    @Override
    public Map queryWebActivity(ActivityModel activityModel) {
        
        return null;
    }
}
