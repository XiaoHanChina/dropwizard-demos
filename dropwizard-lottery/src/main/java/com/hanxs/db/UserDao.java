package com.hanxs.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public abstract class UserDao {

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "select id,name,balance,status,to_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at\n" +
        "\tfrom t_user order by balance desc\n" +
        ")t")
    public abstract String list();

    @SqlQuery("select COALESCE(to_json(t),'[]') from (\n" +
        "select id,name,balance,status,to_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at,to_json(t) as lottery\n" +
        "\tfrom t_user u,\n" +
        "\t(\n" +
        "\t\tselect * from\n" +
        "\t\t(select count(id) as total_count from t_user_lottery where user_id=10000000) a,\n" +
        "\t\t(select count(id) as wating_count from t_user_lottery where user_id=10000000 and status=0) b,\n" +
        "\t\t(select count(id) as win_count from t_user_lottery where user_id=10000000 and status=1 and result>0) c,\t\t\n" +
        "\t\t(select count(id) as lose_count from t_user_lottery where user_id=10000000 and status=1 and result=0) d,\n" +
        "\t\t(select sum(result) as win_prize from t_user_lottery where user_id=10000000) e\n" +
        "\t) t where u.id=10000000\n" +
        ")t")
    public abstract String getUser(@Bind("id") int userId);
}
