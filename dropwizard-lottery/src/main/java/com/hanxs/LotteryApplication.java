package com.hanxs;

import com.hanxs.db.ContestDao;
import com.hanxs.db.ContestLotteryDao;
import com.hanxs.db.UserDao;
import com.hanxs.db.UserLotteryDao;
import com.hanxs.resources.ContestLotteryResource;
import com.hanxs.resources.ContestResource;
import com.hanxs.resources.UserLotteryResource;
import com.hanxs.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class LotteryApplication extends Application<LotteryConfiguration> {

    public static void main(final String[] args) throws Exception {
        new LotteryApplication().run(args);
    }

    @Override
    public String getName() {
        return "Lottery";
    }

    @Override
    public void initialize(final Bootstrap<LotteryConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<LotteryConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LotteryConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final LotteryConfiguration configuration,
                    final Environment env) {
        final DBIFactory fac = new DBIFactory();
        final DBI dbi = fac.build(env, configuration.getDataSourceFactory(), "postgresql");

        env.jersey().register(new ContestResource(dbi.onDemand(ContestDao.class)));
        env.jersey().register(new ContestLotteryResource(dbi.onDemand(ContestLotteryDao.class)));
        env.jersey().register(new UserLotteryResource(dbi.onDemand(UserLotteryDao.class)));
        env.jersey().register(new UserResource(dbi.onDemand(UserDao.class)));
    }

}
