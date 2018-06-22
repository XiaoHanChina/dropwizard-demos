package com.hanxs.resources;


import com.fasterxml.jackson.core.type.TypeReference;
import com.hanxs.api.ContestLotteryBean;
import com.hanxs.db.ContestLotteryDao;
import com.hanxs.utils.JsonUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/contests/{contest_id}/lotteries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContestLotteryResource {
    private ContestLotteryDao dao;

    public ContestLotteryResource(ContestLotteryDao contestLotteryDao) {
        dao = contestLotteryDao;
    }

    @GET
    public List<ContestLotteryBean> list(@PathParam("contest_id") int contestId) throws IOException {
        return JsonUtils.snakeJsonToJavaBean(dao.getLotteries(contestId),
            new TypeReference<ArrayList<ContestLotteryBean>>() {
            });
    }

}
