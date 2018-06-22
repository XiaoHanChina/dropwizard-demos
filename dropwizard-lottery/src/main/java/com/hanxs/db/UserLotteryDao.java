package com.hanxs.db;

import com.hanxs.utils.LogSqlFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

@LogSqlFactory
public abstract class UserLotteryDao {

    @SqlUpdate("insert into t_user_lottery(user_id,contest_lottery_ids,times)\n" +
        "\tvalues(:userId,to_json(:ids::json),:times)")
    @GetGeneratedKeys
    public abstract int insertUserLottery(@Bind("userId") int id,
                                          @Bind("ids") String contestLotteryIds,
                                          @Bind("times") int times);

    /**
     * 用户购买竞猜
     */
    @SqlUpdate("update t_user set balance = balance - :times * 2 where id=:id")
    public abstract void buyLottery(@Bind("id") int userId, @Bind("times") int times);

    @SqlQuery("select COALESCE(to_json(t),'[]') from (\n" +
        "select id,user_id,contest_lottery_ids,times,result,status,\n" +
        "\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at\n" +
        "\tfrom t_user_lottery where id=:id\n" +
        ")t")
    public abstract String getUserLottery(@Bind("id") int userLotteryId);

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "select id,user_id,contest_lottery_ids,times,result,status,\n" +
        "\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at\n" +
        "\tfrom t_user_lottery where user_id=:id\n" +
        ")t")
    public abstract String getUserLotteries(@Bind("id") int userId);


}
