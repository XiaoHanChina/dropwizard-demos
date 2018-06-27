package com.hanxs.db;

import com.hanxs.api.UserLotteryBean;
import com.hanxs.utils.Common;
import com.hanxs.utils.JsonUtils;
import com.hanxs.utils.LogSqlFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@LogSqlFactory
@UseStringTemplate3StatementLocator
public abstract class UserLotteryDao {

    @Transaction
    public UserLotteryBean insertUserLottery(UserLotteryBean bean, ArrayList<Integer> contestLotteryIds) throws
        IOException {
        // Check if all the contest lotteries can be bought

        // Calculate the total prize.
        List<Double> contestLotteryOdds = getContestLotteryOdds(contestLotteryIds);
        double totalPrize = Common.LOTTERY_UNIT_PRIZE * bean.getTimes();
        for (double odds : contestLotteryOdds) {
            totalPrize = totalPrize * odds;
        }

        // Insert user lottery
        int userLotteryId = insertUserLottery(bean.getUserId(),
            bean.getTimes(), (int) totalPrize);
        System.out.println("Convert " + totalPrize + " to " + (int) totalPrize);

        // Insert user lottery item
        for (int cId : contestLotteryIds) {
            insertUserLotteryItem(userLotteryId, cId);
        }

        // Deduct the cost from balance.
        buyLottery(bean.getUserId(), bean.getTimes() * Common.LOTTERY_UNIT_PRIZE);
        return JsonUtils.snakeJsonToJavaBean(getUserLottery(userLotteryId),
            UserLotteryBean.class);
    }

    @SqlQuery("select odds from t_contest_lottery where id in (<ids>)")
    abstract List<Double> getContestLotteryOdds(@BindIn("ids") Collection<Integer> contestLotteryIds);

    @SqlUpdate("insert into t_user_lottery(user_id,times,prize)\n" +
        "\tvalues(:userId,:times,:prize)")
    @GetGeneratedKeys
    abstract int insertUserLottery(@Bind("userId") int id,
                                   @Bind("times") int times,
                                   @Bind("prize") int prize);

    @SqlUpdate("insert into t_user_lottery_item(user_lottery_id,contest_lottery_id) \n" +
        "\tvalues(:uId,:cId)")
    abstract void insertUserLotteryItem(@Bind("uId") int userLotteryId,
                                        @Bind("cId") int contestLotteryId);

    /**
     * 用户购买竞猜
     */
    @SqlUpdate("update t_user set balance = balance - :cost where id=:id")
    abstract void buyLottery(@Bind("id") int userId, @Bind("cost") int cost);

    @SqlQuery("select COALESCE(to_json(t),'[]') from (\n" +
        "select id,user_id,times,result,prize,status,\n" +
        "\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at,\n" +
        "\t(select json_agg(t) from (\n" +
        "\t\tselect id,contest_id,type,win,offer_goal,offer_win,\n" +
        "\t\t\ttotal_goal,host_goal,visitor_goal,odds,result,status,\n" +
        "\t\t\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at\n" +
        "\t\tfrom t_user_lottery_item u,t_contest_lottery c\n" +
        "\t\twhere  u.user_lottery_id=:id and u.contest_lottery_id=c.id )t)contest_lotteries\n" +
        "\tfrom t_user_lottery where id=:id" +
        ")t")
    public abstract String getUserLottery(@Bind("id") int userLotteryId);

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "\tselect id,user_id,times,prize,result,status,\n" +
        "\t\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at,c.*\t\t\n" +
        "\t\tfrom t_user_lottery u,\n" +
        "\t\t(select user_lottery_id, json_agg(t)contest_lotteries from (\n" +
        "\t\t\t\tselect id,contest_id,type,win,offer_goal,offer_win,\n" +
        "\t\t\t\t\ttotal_goal,host_goal,visitor_goal,odds,result,status,\n" +
        "\t\t\t\t\tto_char(created_at, 'YYYY-MM-DD HH24:MI:SS') created_at,\n" +
        "\t\t\t\t\tu.user_lottery_id\n" +
        "\t\t\t\tfrom t_user_lottery_item u,t_contest_lottery c\n" +
        "\t\t\t\twhere u.contest_lottery_id=c.id \n" +
        "\t\t\t)t group by user_lottery_id)c\n" +
        "\t\twhere u.user_id=:id and u.id=c.user_lottery_id" +
        ")t")
    public abstract String getUserLotteries(@Bind("id") int userId);


}
