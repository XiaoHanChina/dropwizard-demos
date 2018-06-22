CREATE TABLE "t_contest" (

"id" int4 NOT NULL ,

"host_team" varchar COLLATE "default" NOT NULL DEFAULT NULL,

"visiting_team" varchar COLLATE "default" NOT NULL DEFAULT NULL,

"host_goal" int4 DEFAULT NULL,

"visitor_goal" int4 DEFAULT NULL,

"start_at" timestamptz(6) NOT NULL DEFAULT NULL,

CONSTRAINT "t_contest_pkey" PRIMARY KEY ("id")

)

WITHOUT OIDS;



COMMENT ON COLUMN "t_contest"."host_goal" IS '主队进球数';

COMMENT ON COLUMN "t_contest"."visitor_goal" IS '客队进球数';

ALTER TABLE "t_contest" OWNER TO "msadmin";



CREATE TABLE "t_contest_lottery" (

"id" int4 NOT NULL,

"contest_id" int4 NOT NULL DEFAULT NULL,

"type" int4 NOT NULL DEFAULT NULL,

"win" varchar COLLATE "default" DEFAULT NULL,

"offer_goal" int2 DEFAULT NULL,

"offer_win" varchar COLLATE "default" DEFAULT NULL,

"total_goal" int2 DEFAULT NULL,

"host_goal" int2 DEFAULT NULL,

"visitor_goal" int2 DEFAULT NULL,

"odds" decimal DEFAULT NULL,

"result" int2 DEFAULT NULL,

"status" int2 NOT NULL DEFAULT 0,

"created_at" timestamptz(6) DEFAULT now(),

CONSTRAINT "t_contest_lottery_pkey" PRIMARY KEY ("id")

)

WITHOUT OIDS;



COMMENT ON COLUMN "t_contest_lottery"."type" IS '0胜平负，1让球胜平负，2总进球，3比分';

COMMENT ON COLUMN "t_contest_lottery"."win" IS '胜平负';

COMMENT ON COLUMN "t_contest_lottery"."offer_goal" IS '让球个数';

COMMENT ON COLUMN "t_contest_lottery"."offer_win" IS '让球胜平负';

COMMENT ON COLUMN "t_contest_lottery"."result" IS '比赛结果是否可胜，0不可胜，1可胜';

COMMENT ON COLUMN "t_contest_lottery"."status" IS '0未开奖1已开奖';

ALTER TABLE "t_contest_lottery" OWNER TO "msadmin";



CREATE TABLE "t_role" (

"id" int4 NOT NULL ,

"name" varchar(255) COLLATE "default" NOT NULL DEFAULT NULL::character varying,

"description" varchar(255) COLLATE "default" DEFAULT NULL::character varying,

CONSTRAINT "pk_role" PRIMARY KEY ("id") ,

CONSTRAINT "uk_role" UNIQUE ("name")

)

WITHOUT OIDS;



ALTER TABLE "t_role" OWNER TO "msadmin";



CREATE TABLE "t_user" (

"id" int4 NOT NULL ,

"name" varchar COLLATE "default" NOT NULL DEFAULT NULL,

"password" varchar COLLATE "default" NOT NULL DEFAULT NULL,

"balance" numeric NOT NULL DEFAULT 10000,

"status" int2 NOT NULL DEFAULT 0,

"created_at" timestamptz(6) NOT NULL DEFAULT now(),

CONSTRAINT "t_user_pkey" PRIMARY KEY ("id") ,

CONSTRAINT "uk_user_name" UNIQUE ("name")

)

WITHOUT OIDS;



COMMENT ON COLUMN "t_user"."status" IS '0启用，1禁用';

ALTER TABLE "t_user" OWNER TO "msadmin";



CREATE TABLE "t_user_lottery" (

"id" int4 NOT NULL,

"user_id" int4 NOT NULL,

"times" int2 DEFAULT NULL,

"result" int2,

"prize" decimal,

"status" int2 DEFAULT 0,

"created_at" timestamptz(6) DEFAULT now(),

CONSTRAINT "t_user_lottery_pkey" PRIMARY KEY ("id")

)

WITHOUT OIDS;



COMMENT ON TABLE "t_user_lottery" IS '记录用户的一次抽奖，如果是单关，则只有一个lottery_item，如果是多关，则有多个lottery_item';

COMMENT ON COLUMN "t_user_lottery"."times" IS '倍数';

COMMENT ON COLUMN "t_user_lottery"."result" IS '是否中奖0未中奖1中奖';

COMMENT ON COLUMN "t_user_lottery"."prize" IS '最高奖金。实际奖金为result * prize.';

COMMENT ON COLUMN "t_user_lottery"."status" IS '0未开奖 1已开奖';

ALTER TABLE "t_user_lottery" OWNER TO "msadmin";



CREATE TABLE "t_user_role" (

"user_id" int4 NOT NULL DEFAULT NULL,

"role_id" int4 NOT NULL DEFAULT NULL,

CONSTRAINT "uk_user_role" UNIQUE ("user_id", "role_id")

)

WITHOUT OIDS;



ALTER TABLE "t_user_role" OWNER TO "msadmin";



CREATE TABLE "t_user_lottery_item" (

"user_lottery_id" int4 NOT NULL,

"contest_lottery_id" int4 NOT NULL

)

WITHOUT OIDS;



COMMENT ON TABLE "t_user_lottery_item" IS '记录用户一条抽奖记录的一关。';





ALTER TABLE "t_contest_lottery" ADD CONSTRAINT "fk_contest_lottery_1" FOREIGN KEY ("contest_id") REFERENCES "t_contest" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "t_user_lottery" ADD CONSTRAINT "fk_user_lottery_1" FOREIGN KEY ("user_id") REFERENCES "t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "t_user_role" ADD CONSTRAINT "fk_user_role_1" FOREIGN KEY ("user_id") REFERENCES "t_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "t_user_role" ADD CONSTRAINT "fk_user_role_2" FOREIGN KEY ("role_id") REFERENCES "t_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "t_user_lottery_item" ADD CONSTRAINT "fk_user_lottery_item_1" FOREIGN KEY ("user_lottery_id") REFERENCES "t_user_lottery" ("id");

ALTER TABLE "t_user_lottery_item" ADD CONSTRAINT "fk_user_lottery_item_2" FOREIGN KEY ("contest_lottery_id") REFERENCES "t_contest_lottery" ("id");




create sequence s_user start 10000000 owned by t_user.id;
alter table t_user alter column id set default nextval('s_user');

create sequence s_role start 10000000 owned by t_role.id;
alter table t_role alter column id set default nextval('s_role');

create sequence s_user_lottery start 10000000 owned by t_user_lottery.id;
alter table t_user_lottery alter column id set default nextval('s_user_lottery');

create sequence s_contest_lottery start 10000000 owned by t_contest_lottery.id;
alter table t_contest_lottery alter column id set default nextval('s_contest_lottery');

create sequence s_contest start 10000000 owned by t_contest.id;
alter table t_contest alter column id set default nextval('s_contest');

