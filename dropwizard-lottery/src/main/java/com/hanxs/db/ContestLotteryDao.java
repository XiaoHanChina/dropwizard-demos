package com.hanxs.db;

import com.hanxs.utils.LogSqlFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

@LogSqlFactory
public abstract class ContestLotteryDao {

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "select id,contest_id,type,win,offer_goal,offer_win,\n" +
        "\ttotal_goal,host_goal,visitor_goal,odds,result,status,\n" +
        "\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at\n" +
        "from t_contest_lottery where contest_id=:id\n" +
        ")t")
    public abstract String getLotteries(@Bind("id") int contestId);
}
