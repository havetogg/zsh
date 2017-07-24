package org.jumutang.zsh.services;

import org.jumutang.zsh.model.DailyModel;

import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 13:52 2017/4/20
 * @Modified By:
 */
public interface DailyServiceI {

    /**
     * 保存日报
     * @param dailyModel
     * @return
     */
    int saveDaily(DailyModel dailyModel);

    /**
     * 更新日报
     * @param dailyModel
     * @return
     */
    int updateDaily(DailyModel dailyModel);

    /**
     * 查询实体
     * @param dailyModel
     * @return
     */
    List<DailyModel> listDaily(DailyModel dailyModel);
}
