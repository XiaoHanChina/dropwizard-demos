insert into t_user(name,password,balance) values('hanxs','111111',10000);

insert into t_contest(host_team,visiting_team,host_goal,visitor_goal,start_at)
		values('丹麦','澳大利亚',0,0,'2018-06-21 20:00:00');
insert into t_contest_lottery(contest_id,type,win,odds)
		values(10000000,0,'胜',1.71);
insert into t_contest_lottery(contest_id,type,win,odds)
		values(10000000,0,'平',3.11);
insert into t_contest_lottery(contest_id,type,win,odds)
		values(10000000,0,'负',3.95);

insert into t_contest_lottery(contest_id,type,offer_goal,offer_win,odds)
		values(10000000,1,-1,'胜',3.35);
insert into t_contest_lottery(contest_id,type,offer_goal,offer_win,odds)
		values(10000000,1,-1,'平',3.35);
insert into t_contest_lottery(contest_id,type,offer_goal,offer_win,odds)
		values(10000000,1,-1,'负',1.78);

insert into t_contest_lottery(contest_id,type,total_goal,odds)
		values(10000000,2,0,7.50);
insert into t_contest_lottery(contest_id,type,total_goal,odds)
		values(10000000,2,1,3.6);
insert into t_contest_lottery(contest_id,type,total_goal,odds)
		values(10000000,2,2,3);
insert into t_contest_lottery(contest_id,type,total_goal,odds)
		values(10000000,2,3,3.8);
insert into t_contest_lottery(contest_id,type,total_goal,odds)
		values(10000000,2,4,6.2);

insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,1,0,5);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,2,0,6.5);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,2,1,6.8);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,0,0,7.5);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,0,1,9.5);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,1,1,6);
insert into t_contest_lottery(contest_id,type,host_goal,visitor_goal,odds)
		values(10000000,3,1,2,13);