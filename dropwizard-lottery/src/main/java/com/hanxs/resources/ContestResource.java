package com.hanxs.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanxs.api.ContestBean;
import com.hanxs.db.ContestDao;
import com.hanxs.utils.JsonUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/contests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContestResource {

    private ContestDao contestDao;

    public ContestResource(ContestDao dao) {
        contestDao = dao;
    }

    @PUT
    @Path("/{contest_id}")
    public void cleanContest(@PathParam("contest_id") int contestId, ContestBean bean) {
        bean.setId(contestId);
        contestDao.cleanContest(bean);
    }

    @GET
    public List<ContestBean> list() throws IOException {
        return JsonUtils.snakeJsonToJavaBean(contestDao.getContests(),
            new TypeReference<ArrayList<ContestBean>>() {
            });
    }
}
