package com.hanxs;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class LotteryConfiguration extends Configuration {
    LotteryConfiguration() {

    }

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public class JWT {
        private long expiresIn = 0;
        private long refreshExpiresIn = 0;
        private String hmacSecret = "123456";

        @JsonProperty("expiresIn")
        public void setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            System.out.print(expiresIn);
        }

        @JsonProperty("expiresIn")
        public long getExpiresIn() {
            return expiresIn;
        }

        @JsonProperty("refreshExpiresIn")
        public void setRefreshExpiresIn(long expiresIn) {
            this.refreshExpiresIn = expiresIn;
        }

        @JsonProperty("refreshExpiresIn")
        public long getRefreshExpiresIn() {
            return refreshExpiresIn;
        }

        @JsonProperty("hmacSecret")
        public void setHmacSecret(String hmacSecret) {
            this.hmacSecret = hmacSecret;
        }

        @JsonProperty("hmacSecret")
        public String getHmacSecret() {
            return hmacSecret;
        }
    }

//    JWT jwt;
//
//    @JsonProperty("jwt")
//    public void setJwt(JWT jwt) {
//        this.jwt = jwt;
//    }
//
//    @JsonProperty("jwt")
//    public JWT getJwt() {
//        return jwt;
//    }


}
