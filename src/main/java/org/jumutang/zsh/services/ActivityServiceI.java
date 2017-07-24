package org.jumutang.zsh.services;

import org.jumutang.zsh.model.ActivityModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/31.
 */
public interface ActivityServiceI {
    public List<ActivityModel> queryActivity(ActivityModel activityModel);

    public int addActivity(ActivityModel activityModel);

    public Map queryWebActivity(ActivityModel activityModel);
}
