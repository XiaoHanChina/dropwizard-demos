package com.hanxs.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanxs.api.UserLotteryBean;
import com.hanxs.db.UserLotteryDao;
import com.hanxs.utils.JsonUtils;
import org.skife.jdbi.v2.sqlobject.Transaction;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/lotteries")
@Produces(MediaType.APPLICATION_JSON)
public class UserLotteryResource {
    private UserLotteryDao dao;

    public UserLotteryResource(UserLotteryDao userLotteryDao) {
        dao = userLotteryDao;
    }

    @POST
    @Transaction
    public UserLotteryBean insert(@FormParam("userId") int userId,
                                  @FormParam("contestLotteryIds") String idsStr,
                                  @FormParam("times") int times) throws IOException {
        UserLotteryBean bean = new UserLotteryBean();
        bean.setUserId(userId);
        bean.setContestLotteryIds(new ObjectMapper().readValue(idsStr, new TypeReference<ArrayList<Integer>>() {
        }));
        bean.setTimes(times);

        int userLotteryId = dao.insertUserLottery(bean.getUserId(),
            new ObjectMapper().writeValueAsString(bean.getContestLotteryIds()),
            bean.getTimes());
        dao.buyLottery(bean.getUserId(), bean.getTimes());
        return JsonUtils.snakeJsonToJavaBean(dao.getUserLottery(userLotteryId),
            UserLotteryBean.class);
    }
//    @POST
//    @Transaction
//    public UserLotteryBean insert(UserLotteryBean bean) throws IOException {
//        int userLotteryId = dao.insertUserLottery(bean.getUserId(),
//            new ObjectMapper().writeValueAsString(bean.getContestLotteryIds()),
//            bean.getTimes());
//        dao.buyLottery(bean.getUserId(), bean.getTimes());
//        return JsonUtils.snakeJsonToJavaBean(dao.getUserLottery(userLotteryId),
//            UserLotteryBean.class);
//    }

    @GET
    public List<UserLotteryBean> list() throws IOException {
        return JsonUtils.snakeJsonToJavaBean(dao.getUserLotteries(10000000),
            new TypeReference<ArrayList<UserLotteryBean>>() {
            });
    }
}
