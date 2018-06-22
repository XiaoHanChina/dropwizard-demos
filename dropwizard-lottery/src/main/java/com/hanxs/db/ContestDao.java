package com.hanxs.db;

import com.hanxs.utils.LogSqlFactory;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

@LogSqlFactory
public abstract class ContestDao {

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "\tselect id,host_team,visiting_team,host_goal,visitor_goal," +
        "to_char(start_at, 'YYYY-MM-DD HH24:MI:SS') start_at\n" +
        "\tfrom t_contest\n" +
        ")t")
    public abstract String getContests();
}
