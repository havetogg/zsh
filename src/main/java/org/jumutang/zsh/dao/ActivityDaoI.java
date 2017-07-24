package org.jumutang.zsh.dao;

import org.jumutang.zsh.model.ActivityModel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
@Repository
public interface ActivityDaoI {

    public List<ActivityModel> queryActivity(ActivityModel activityModel);

    public int addActivity(ActivityModel activityModel);

    public int updateActivity(ActivityModel activityModel);
}
