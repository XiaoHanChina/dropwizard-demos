package com.hanxs.db;

import com.hanxs.api.ContestBean;
import com.hanxs.utils.Common;
import com.hanxs.utils.LogSqlFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.Collection;
import java.util.List;

@LogSqlFactory
@UseStringTemplate3StatementLocator
public abstract class ContestDao {

    public void cleanContest(ContestBean bean) {
        // Step 1: update contest result
        updateContestResult(bean);

        // Step 2: update contest lotteries to win or lose
        // update win or lose
        int temp = bean.getHostGoal() - bean.getVisitorGoal();
        String result;
        if (temp > 0) {
            result = Common.WIN;
        } else if (temp == 0) {
            result = Common.TIE;
        } else {
            result = Common.LOSE;
        }
        updateContestLotteryType0ToWin(bean.getId(), result);
        updateContestLotteryType0ToLose(bean.getId(), result);

        // update offer goal win or lose
        temp = bean.getHostGoal() + getContestOfferGoal(bean.getId()) - bean.getVisitorGoal();
        if (temp > 0) {
            result = Common.WIN;
        } else if (temp == 0) {
            result = Common.TIE;
        } else {
            result = Common.LOSE;
        }
        updateContestLotteryType1ToWin(bean.getId(), result);
        updateContestLotteryType1ToLose(bean.getId(), result);

        // update total goal win or lose
        updateContestLotteryType2ToWin(bean.getId(), bean.getHostGoal() + bean.getVisitorGoal());
        updateContestLotteryType2ToLose(bean.getId(), bean.getHostGoal() + bean.getVisitorGoal());

        // update score win or lose
        updateContestLotteryType3ToWin(bean.getId(), bean.getHostGoal(), bean.getVisitorGoal());
        updateContestLotteryType3ToLose(bean.getId(), bean.getHostGoal(), bean.getVisitorGoal());

        // Step 2: update all user lotteries which has
        // one or more lose contest lottery items in this contest
        updateUserLotteryToLose(bean.getId());

        // Step 3: Get finished and won user lottery ids
        // Get all user lottery ids which has one or more win contest lotteries in this contest
        List<Integer> mightWinUserLotteryIds = getMightWinUserLotteryIds(bean.getId());
        if (null == mightWinUserLotteryIds || mightWinUserLotteryIds.isEmpty())
            return;

        // Get all user lottery ids which has one or more unfinished contest lotteries.
        List<Integer> unfinishedUserLotteryIds = getUnfinishedUserLotteryIds();

        // Eliminate user lottery ids which has unfinished contest lotteries from might win ids.
        // And then the definitely win user lottery ids left.
        // The ids being eliminated in this step survived, they will be updated when
        // the next contest they relate is finished.
        if (null != unfinishedUserLotteryIds && !unfinishedUserLotteryIds.isEmpty()) {
            mightWinUserLotteryIds.removeAll(unfinishedUserLotteryIds);
            if (mightWinUserLotteryIds.isEmpty())
                return;
        }

        // Step 4: update all win ids to win
        updateUserLotteryToWin(mightWinUserLotteryIds);

        // Step 5: pay the bonus

    }

    @SqlUpdate("update t_contest set host_goal=:hostGoal,visitor_goal=:visitorGoal where id=:id")
    abstract void updateContestResult(@BindBean ContestBean bean);

    // clean all contest lotteries which type is 0
    @SqlUpdate("update t_contest_lottery set result=1,status=1 " +
        "where contest_id=:id and type=0 and win=:result")
    abstract void updateContestLotteryType0ToWin(@Bind("id") int contestId, @Bind("result") String result);

    @SqlUpdate("update t_contest_lottery set result=0,status=1 " +
        "where contest_id=:id and type=0 and win!=:result")
    abstract void updateContestLotteryType0ToLose(@Bind("id") int contestId, @Bind("result") String result);

    // clean all contest lotteries which type is 1
    @SqlUpdate("update t_contest_lottery set result=1,status=1 " +
        "where contest_id=:id and type=1 and offer_win=:result")
    abstract void updateContestLotteryType1ToWin(@Bind("id") int contestId, @Bind("result") String result);

    @SqlUpdate("update t_contest_lottery set result=0,status=1 " +
        "where contest_id=:id and type=1 and offer_win!=:result")
    abstract void updateContestLotteryType1ToLose(@Bind("id") int contestId, @Bind("result") String result);

    // clean all contest lotteries which type is 2
    @SqlUpdate("update t_contest_lottery set result=1,status=1 " +
        "where contest_id=:id and type=2 and total_goal=:goal")
    abstract void updateContestLotteryType2ToWin(@Bind("id") int contestId, @Bind("goal") int totalGoal);

    @SqlUpdate("update t_contest_lottery set result=0,status=1 " +
        "where contest_id=:id and type=2 and total_goal!=:goal")
    abstract void updateContestLotteryType2ToLose(@Bind("id") int contestId, @Bind("goal") int totalGoal);

    // clean all contest lotteries which type is 3
    @SqlUpdate("update t_contest_lottery set result=1,status=1 " +
        "where contest_id=:id and type=3 and host_goal=:host_goal and visitor_goal=:visitor_goal")
    abstract void updateContestLotteryType3ToWin(@Bind("id") int contestId,
                                                 @Bind("host_goal") int hostGoal,
                                                 @Bind("visitor_goal") int visitorGoal);

    @SqlUpdate("update t_contest_lottery set result=0,status=1 " +
        "where contest_id=:id and type=3 and (host_goal!=:host_goal or visitor_goal!=:visitor_goal)")
    abstract void updateContestLotteryType3ToLose(@Bind("id") int contestId,
                                                  @Bind("host_goal") int hostGoal,
                                                  @Bind("visitor_goal") int visitorGoal);

    // Update all user lottery which has
    // one or more lose contest lotteries in this contest to lose.
    @SqlUpdate("update t_user_lottery set result=0,status=1 where id in (\n" +
        "\t\tselect distinct u.id from t_user_lottery u, t_user_lottery_item ui,t_contest_lottery c\n" +
        "\t\t\twhere u.status=0 and u.id=ui.user_lottery_id and ui.contest_lottery_id=c.id and c.id in \n" +
        "\t\t\t(select id from t_contest_lottery where contest_id=:id and result=0)\n" +
        ")")
    abstract void updateUserLotteryToLose(@Bind("id") int contestId);

    /**
     * Get all uncleaned user lottery ids which has
     * one or more win contest lotteries in this contest.
     * And there is also some user lotteries whose items contain
     * one or more unfinished contests, we should eliminate them later.
     *
     * @param contestId contestId
     * @return might win user lottery ids
     */
    @SqlQuery("select distinct u.id from t_user_lottery u, t_user_lottery_item ui,t_contest_lottery c\n" +
        "\t\twhere u.status=0 and u.id=ui.user_lottery_id and ui.contest_lottery_id=c.id and c.id in \n" +
        "\t\t(select id from t_contest_lottery where contest_id=:id and result=1)")
    abstract List<Integer> getMightWinUserLotteryIds(@Bind("id") int contestId);

    /**
     * Get all user lottery ids which has
     * one or more unfinished contest lotteries.
     *
     * @return unfinished user lottery ids
     */
    @SqlQuery("select distinct u.id from t_user_lottery u, t_user_lottery_item ui, t_contest_lottery c\n" +
        "\t\twhere u.id=ui.user_lottery_id and ui.contest_lottery_id=c.id and c.status=0")
    abstract List<Integer> getUnfinishedUserLotteryIds();

    @SqlUpdate("update t_user_lottery set result=1,status=1 where id in (<ids>)")
    abstract void updateUserLotteryToWin(@BindIn("ids") Collection<Integer> winUserLotteryIds);

    @SqlQuery("select COALESCE(json_agg(t),'[]') from (\n" +
        "\tselect id,host_team,visiting_team,host_goal,visitor_goal," +
        "to_char(start_at, 'YYYY-MM-DD HH24:MI:SS') start_at\n" +
        "\tfrom t_contest\n" +
        ")t")
    public abstract String getContests();

    @SqlQuery("select offer_goal from t_contest_lottery where contest_id=:id limit 1;")
    abstract int getContestOfferGoal(@Bind("id") int contestId);
}
