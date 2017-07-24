package org.jumutang.zsh.dao;

import org.jumutang.zsh.model.DailyModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: Tinny.liang
 * @Description:
 * @Date: Create in 11:34 2017/4/20
 * @Modified By:
 */
@Repository
public interface DailyDaoI {
    int saveDaily(DailyModel dailyModel);
    int updateDaily(DailyModel dailyModel);
    List<DailyModel> listDaily(DailyModel dailyModel);
}
