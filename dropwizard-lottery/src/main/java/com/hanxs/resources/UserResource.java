package com.hanxs.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hanxs.api.UserBean;
import com.hanxs.db.UserDao;
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

@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDao dao;

    public UserResource(UserDao userDao) {
        dao = userDao;
    }

    @GET
    public List<UserBean> list() throws IOException {
        return JsonUtils.snakeJsonToJavaBean(dao.list(),
            new TypeReference<ArrayList<UserBean>>() {
            });
    }

    @GET
    @Path("/{user_id}")
    public UserBean getUser(@PathParam("user_id") int userId) throws IOException {
        return JsonUtils.snakeJsonToJavaBean(dao.getUser(userId), UserBean.class);
    }
}
