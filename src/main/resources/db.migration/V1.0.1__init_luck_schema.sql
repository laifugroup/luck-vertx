--  USD-T红包扫雷

-- 群组设置
drop table if  exists "luck"."luck_platform";
create table if not exists "luck"."luck_platform" (
   "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
   "group_id"  BIGINT NOT NULL,
   "group_name"  varchar(128)  NULL,
   "admin_bot_user_id"  BIGINT NOT NULL,
   "status"  smallint default 1 not null,
   "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
   "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
   "deleted_at" timestamp DEFAULT NULL
);

comment on table  "luck"."luck_platform" is '扫雷群组';

comment on column "luck"."luck_platform".id is 'id';
comment on column "luck"."luck_platform".group_id is '博弈组ID[112333222]';
comment on column "luck"."luck_platform".group_name is '博弈组名称[众彩国际]';
comment on column "luck"."luck_platform".admin_bot_user_id is '管理员用户ID[334454555656]';
comment on column "luck"."luck_platform".status is '状态[1-启用中,2-未启用]';

comment on column "luck"."luck_platform".created_at is '创建时间';
comment on column "luck"."luck_platform".updated_at is '更新时间';
comment on column "luck"."luck_platform".deleted_at is '删除时间';

drop index if exists luck_platform_group_id_idx;
CREATE INDEX luck_platform_group_id_idx ON "luck"."luck_platform" (group_id);


--- 幸运用户
drop table if  exists "luck"."luck_user";

create table if not exists "luck"."luck_user" (
     "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
     "bot_user_id"  BIGINT NOT NULL,
     "group_id"  BIGINT  NULL,
     "roles" varchar(128)   null default 'user',
     "first_name" varchar(128)   null,
     "last_name"  varchar(128)   null,
     "user_name"  varchar(128)   null,
     "remark" varchar(64)  null,
     "status"  smallint default 1 not null,
     "inviter_user_id"  BIGINT  NULL,
     "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
     "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
     "deleted_at" timestamp DEFAULT NULL
);


comment on table  "luck"."luck_user" is '幸运用户';

comment on column "luck"."luck_user".id is 'id';
comment on column "luck"."luck_user".bot_user_id is 'BOT用户ID[430713401]';
comment on column "luck"."luck_user".group_id is '博弈组ID[-1001977552617]';
comment on column "luck"."luck_user".roles is '角色[admin,,user]';
comment on column "luck"."luck_user".first_name is '名字[san]';
comment on column "luck"."luck_user".last_name is '姓[zhang]';
comment on column "luck"."luck_user".user_name is '用户名[laowang]';
comment on column "luck"."luck_user".remark is '备注[封禁用户]';
comment on column "luck"."luck_user".status is '状态[1-正常,2-封禁中]';
comment on column "luck"."luck_user".inviter_user_id is '邀请人用户Id[1541693333435453411]';

comment on column "luck"."luck_user".created_at is '创建时间';
comment on column "luck"."luck_user".updated_at is '更新时间';
comment on column "luck"."luck_user".deleted_at is '删除时间';


drop index if exists luck_user_bot_user_id_idx;
CREATE INDEX luck_user_bot_user_id_idx ON "luck"."luck_user" (bot_user_id);

drop index if exists luck_user_user_name_idx;
CREATE INDEX luck_user_user_name_idx ON "luck"."luck_user" (user_name);

drop index if exists luck_user_inviter_user_id_idx;
CREATE INDEX luck_user_inviter_user_id_idx ON "luck"."luck_user" (inviter_user_id);

-- 用户钱包
drop table if  exists "luck"."luck_wallet";
create table if not exists "luck"."luck_wallet" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "group_id"  BIGINT NOT NULL,
      "credit"  numeric  not null,
      "status"  INT default 1 not null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);


comment on table  "luck"."luck_wallet" is '用户钱包';

comment on column "luck"."luck_wallet".id is 'id';
comment on column "luck"."luck_wallet".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_wallet".group_id is '群组ID[23334444]';
comment on column "luck"."luck_wallet".credit is '余额[100]';
comment on column "luck"."luck_wallet".status is '状态[1-已启用,2-封存中,2-已废弃]';
comment on column "luck"."luck_wallet".created_at is '创建时间';
comment on column "luck"."luck_wallet".updated_at is '更新时间';
comment on column "luck"."luck_wallet".deleted_at is '删除时间';


drop index if exists luck_wallet_user_id_idx;
CREATE INDEX luck_wallet_user_id_idx ON "luck"."luck_wallet" (user_id);



--- 上分/下分日志记录
drop table if  exists "luck"."luck_credit_log";
create table if not exists "luck"."luck_credit_log" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "group_id" BIGINT NOT NULL,
      "credit_before"  numeric  not null default 0,
      "credit"  numeric  not null  default 0,
      "credit_after"  numeric  not null  default 0,
      "type"  int  not null,
      "remark" varchar(64)  null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);

comment on table  "luck"."luck_credit_log" is '上下分日志记录';

comment on column "luck"."luck_credit_log".id is 'id';
comment on column "luck"."luck_credit_log".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_credit_log".group_id is '博弈组ID[-1001977552617]';

comment on column "luck"."luck_credit_log".credit_before is '之前积分[100]';
comment on column "luck"."luck_credit_log".credit is '积分[100]';
comment on column "luck"."luck_credit_log".credit_after is '之后积分[200]';

comment on column "luck"."luck_credit_log".type is '类型[1-充值上分,2-信用上分,3-抢到红包上分,4-奖励上分,5-中雷下分,6-提现下分,7-抢红包保证金下分,8-未中雷上分,9-调账]';
comment on column "luck"."luck_credit_log".remark is '备注[封禁用户]';

comment on column "luck"."luck_credit_log".created_at is '创建时间';
comment on column "luck"."luck_credit_log".updated_at is '更新时间';
comment on column "luck"."luck_credit_log".deleted_at is '删除时间';

drop index if exists luck_credit_log_user_id_idx;
CREATE INDEX luck_credit_log_user_id_idx ON "luck"."luck_credit_log" (user_id);

drop index if exists luck_credit_log_group_id_idx;
CREATE INDEX luck_credit_log_group_id_idx ON "luck"."luck_credit_log" (group_id);


-- 邀请链接
drop table if  exists "luck"."luck_invite";
create table if not exists "luck"."luck_invite" (
        "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
        "user_id"  BIGINT NOT NULL,
        "group_id"  BIGINT  NULL,
        "url" varchar(128)   null,
        "url_hash" varchar(32)   null,
        "remark" varchar(64)  null,
        "status"  smallint default 1 not null,
        "expired_at" timestamp NOT NULL,
        "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
        "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
        "deleted_at" timestamp DEFAULT NULL
);


comment on table  "luck"."luck_invite" is '邀请链接';

comment on column "luck"."luck_invite".id is 'id';
comment on column "luck"."luck_invite".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_invite".group_id is '博弈组ID[112333222]';
comment on column "luck"."luck_invite".url is '邀请链接[https://t.me/+33lIub2gs-44YTg1]';
comment on column "luck"."luck_invite".url_hash is '邀请链接Hash[adea241183d46ecfbf829ba50165129b]';
comment on column "luck"."luck_invite".remark is '备注[封禁用户]';
comment on column "luck"."luck_invite".status is '状态[1-正常,2-封禁中]';
comment on column "luck"."luck_invite".expired_at is '过期时间';
comment on column "luck"."luck_invite".created_at is '创建时间';
comment on column "luck"."luck_invite".updated_at is '更新时间';
comment on column "luck"."luck_invite".deleted_at is '删除时间';


drop index if exists luck_invite_user_id_idx;
CREATE INDEX luck_invite_user_id_idx ON "luck"."luck_invite" (user_id);

drop index if exists luck_invite_group_id_idx;
CREATE INDEX luck_invite_group_id_idx ON "luck"."luck_invite" (group_id);

drop index if exists luck_invite_url_hash_idx;
CREATE INDEX luck_invite_url_hash_idx ON "luck"."luck_invite" (url_hash);



-- 邀请记录
drop table if  exists "luck"."luck_invite_log";
create table if not exists "luck"."luck_invite_log" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "invite_url"  varchar(128) NOT NULL,
      "url_hash" varchar(32)   null,
      "group_id"  BIGINT  NULL,
      "invitee_user_id"  BIGINT NOT NULL,
      "remark" varchar(64)  null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);


comment on table  "luck"."luck_invite_log" is '邀请记录';

comment on column "luck"."luck_invite_log".id is 'id';
comment on column "luck"."luck_invite_log".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_invite_log".invite_url is '邀请链接[https://t.me/+33lIub2gs-44YTg1]';
comment on column "luck"."luck_invite_log".url_hash is '邀请链接Hash[adea241183d46ecfbf829ba50165129b]';
comment on column "luck"."luck_invite_log".group_id is '博弈组ID[112333222]';
comment on column "luck"."luck_invite_log".invitee_user_id is '被邀请人用户Id[1541693333435453411]';
comment on column "luck"."luck_invite_log".remark is '备注[封禁用户]';

comment on column "luck"."luck_invite_log".created_at is '创建时间';
comment on column "luck"."luck_invite_log".updated_at is '更新时间';
comment on column "luck"."luck_invite_log".deleted_at is '删除时间';


drop index if exists luck_invite_log_user_id_idx;
CREATE INDEX luck_invite_log_user_id_idx ON "luck"."luck_invite_log" (user_id);

drop index if exists luck_invite_log_group_id_idx;
CREATE INDEX luck_invite_log_group_id_idx ON "luck"."luck_invite_log" (group_id);

drop index if exists luck_invite_log_url_hash_idx;
CREATE INDEX luck_invite_log_url_hash_idx ON "luck"."luck_invite_log" (url_hash);





--- 发红包
drop table if  exists "luck"."luck_send_luck";

create table if not exists "luck"."luck_send_luck" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "credit"  numeric  not null  default 0,
      "red_pack_numbers"  int  not null  default 6,
      "boom_number"  int  not null,
      "group_id"  BIGINT  NULL,
      "first_name" varchar(126)   null,
      "last_name"  varchar(126)   null,
      "user_name"  varchar(126)   null,
      "status"  smallint default 1 not null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);

comment on table  "luck"."luck_send_luck" is '发红包';

comment on column "luck"."luck_send_luck".id is 'id';
comment on column "luck"."luck_send_luck".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_send_luck".credit is '积分[100]';
comment on column "luck"."luck_send_luck".red_pack_numbers is '红包个数[6]';
comment on column "luck"."luck_send_luck".boom_number is '中雷数字[1]';
comment on column "luck"."luck_send_luck".first_name is '名字[san]';
comment on column "luck"."luck_send_luck".last_name is '姓[zhang]';
comment on column "luck"."luck_send_luck".user_name is '用户名[laowang]';
comment on column "luck"."luck_send_luck".status is '状态[1-已结算,2-未结算,3-已过期,4-已撤销]';
comment on column "luck"."luck_send_luck".group_id is '博弈组ID[112333222]';

comment on column "luck"."luck_send_luck".created_at is '创建时间';
comment on column "luck"."luck_send_luck".updated_at is '更新时间';
comment on column "luck"."luck_send_luck".deleted_at is '删除时间';

drop index if exists luck_send_luck_user_id_idx;
CREATE INDEX luck_send_luck_user_id_idx ON "luck"."luck_send_luck" (user_id);



--- 抢红包
drop table if  exists "luck"."luck_good_luck";
create table if not exists "luck"."luck_good_luck" (
     "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
     "luck_red_pack_id"  BIGINT NOT NULL,
     "send_red_pack_user_id"  BIGINT NOT NULL,
     "user_id"  BIGINT NOT NULL,
     "group_id"  BIGINT  NULL,
     "first_name" varchar(126)   null,
     "last_name"  varchar(126)   null,
     "user_name"  varchar(126)   null,
     "credit"  numeric   null  ,
     "numbers"  int  not null  default 1,
     "last_number"  int   null,
     "boom_number"  int  not null,
     "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
     "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
     "deleted_at" timestamp DEFAULT NULL
);

comment on table  "luck"."luck_good_luck" is '抢红包';

comment on column "luck"."luck_good_luck".id is 'id';
comment on column "luck"."luck_good_luck".luck_red_pack_id is '红包ID[1541693333435453411]';
comment on column "luck"."luck_good_luck".send_red_pack_user_id is '发红包人[1541693333435453411]';
comment on column "luck"."luck_good_luck".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_good_luck".first_name is '名字[san]';
comment on column "luck"."luck_good_luck".last_name is '姓[zhang]';
comment on column "luck"."luck_good_luck".user_name is '用户名[laowang]';
comment on column "luck"."luck_good_luck".credit is '抢红包金额分[100]';
comment on column "luck"."luck_good_luck".numbers is '抢红包个数[1]';
comment on column "luck"."luck_good_luck".last_number is '尾数[2]';
comment on column "luck"."luck_good_luck".boom_number is '雷数[3]';
comment on column "luck"."luck_good_luck".group_id is '博弈组ID[112333222]';

comment on column "luck"."luck_good_luck".created_at is '创建时间';
comment on column "luck"."luck_good_luck".updated_at is '更新时间';
comment on column "luck"."luck_good_luck".deleted_at is '删除时间';

drop index if exists luck_good_luck_user_id_idx;
CREATE INDEX luck_good_luck_user_id_idx ON "luck"."luck_good_luck" (user_id);

drop index if exists luck_good_luck_luck_red_pack_id_idx;
CREATE INDEX luck_good_luck_luck_red_pack_id_idx ON "luck"."luck_good_luck" (luck_red_pack_id);




-- 用户充值钱包
drop table if  exists "luck"."luck_user_recharge_wallet";
create table if not exists "luck"."luck_user_recharge_wallet" (
       "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
       "user_id"  BIGINT NOT NULL,
       "group_id"  BIGINT NOT NULL,
       "address"  varchar(255)   null,
       "type"  INT default 1 not null,
       "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
       "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
       "deleted_at" timestamp DEFAULT NULL
);


comment on table  "luck"."luck_user_recharge_wallet" is '用户充值钱包';

comment on column "luck"."luck_user_recharge_wallet".id is 'id';
comment on column "luck"."luck_user_recharge_wallet".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_user_recharge_wallet".group_id is '博弈组ID[112333222]';
comment on column "luck"."luck_user_recharge_wallet".address is '用户钱包地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]';
comment on column "luck"."luck_user_recharge_wallet".type is '网络类型[1-TRC20,2-ERC20]';

comment on column "luck"."luck_user_recharge_wallet".created_at is '创建时间';
comment on column "luck"."luck_user_recharge_wallet".updated_at is '更新时间';
comment on column "luck"."luck_user_recharge_wallet".deleted_at is '删除时间';


drop index if exists luck_user_recharge_wallet_user_id_idx;
CREATE INDEX luck_user_recharge_wallet_user_id_idx ON "luck"."luck_user_recharge_wallet" (user_id);


-- 上下分申请记录
drop table if  exists "luck"."luck_credit_apply";

create table if not exists "luck"."luck_credit_apply" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "group_id"  BIGINT  NULL,
      "credit"  numeric   null,
      "from_address" varchar(255)  null,
      "to_address" varchar(255)  null,
      "txn_hash" varchar(255)  null,
      "remark" varchar(64)  null,
      "type"  smallint default 1 not null,
      "status"  smallint default 1 not null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);
drop index if exists luck_credit_apply_user_id_idx;
CREATE INDEX luck_credit_apply_user_id_idx ON "luck"."luck_credit_apply" (user_id);



comment on table  "luck"."luck_credit_apply" is '上下分申请记录';

comment on column "luck"."luck_credit_apply".id is 'id';
comment on column "luck"."luck_credit_apply".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_credit_apply".group_id is '博弈组ID[-1001977552617]';
comment on column "luck"."luck_credit_apply".credit is '积分[100]';
comment on column "luck"."luck_credit_apply".from_address is '来源地址[TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG]';
comment on column "luck"."luck_credit_apply".to_address is '去处地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]';
comment on column "luck"."luck_credit_apply".txn_hash is '交易HASH[fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549]';
comment on column "luck"."luck_credit_apply".type is '类型[1-上分,2-下分]';
comment on column "luck"."luck_credit_apply".remark is '备注[驳回原因：未达到一倍流水]';
comment on column "luck"."luck_credit_apply".status is '类型[1-已申请,2-已通过,待交易,3-交易失败,4-交易成功,5-已驳回]';

comment on column "luck"."luck_credit_apply".created_at is '创建时间';
comment on column "luck"."luck_credit_apply".updated_at is '更新时间';
comment on column "luck"."luck_credit_apply".deleted_at is '删除时间';



--- 钱包充值记录 充值和提现记录-链上信息记录
drop table if  exists "luck"."luck_charge_log";

create table if not exists "luck"."luck_charge_log" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "user_id"  BIGINT NOT NULL,
      "type"  int  not null,
      "transaction_hash" varchar(64)  not null,
      "transaction_status" varchar(32)  not null,
      "transaction_block" int null,
      "transaction_timestamp" timestamp not null,
      "from_hash" varchar(40)  NOT null,
      "to_hash" varchar(40)  NOT null,
      "contract" varchar(40)  not null default '0x55d398326f99059ff775485246999027b3197955',
      "tokens"  numeric(10,2)  not null default 0,
      "value"  varchar(64)  null default 0,
      "transaction_fee" varchar(32)  null,
      "gas_price" varchar(32)  null,
      "bnb_price" varchar(32)  null,
      "remark" varchar(64)  null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
);

drop index if exists luck_charge_log_user_id_idx;
CREATE INDEX luck_charge_log_user_id_idx ON "luck"."luck_charge_log" (user_id);

drop index if exists luck_charge_log_withdrawal_log_id_idx;
CREATE INDEX luck_charge_log_withdrawal_log_id_idx ON "luck"."luck_charge_log" (transaction_hash);

drop index if exists luck_charge_log_from_hash_idx;
CREATE INDEX luck_charge_log_from_hash_idx ON "luck"."luck_charge_log" (from_hash);

drop index if exists luck_charge_log_to_hash_idx;
CREATE INDEX luck_charge_log_to_hash_idx ON "luck"."luck_charge_log" (to_hash);


comment on table  "luck"."luck_charge_log" is '钱包充值记录';

comment on column "luck"."luck_charge_log".id is 'id';
comment on column "luck"."luck_charge_log".user_id is '用户ID[1541693333435453411]';
comment on column "luck"."luck_charge_log".type is '类型[1-提现,2-充值]';
comment on column "luck"."luck_charge_log".transaction_hash is '交易hash[0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365]';
comment on column "luck"."luck_charge_log".transaction_status is '交易状态[success]';
comment on column "luck"."luck_charge_log".transaction_block is '交易区块高度[27504624]';
comment on column "luck"."luck_charge_log".transaction_timestamp is '交易区块高度[Apr-20-2023 05:26:43 AM +UTC]';
comment on column "luck"."luck_charge_log".from_hash is '发起者[0x6ba1df44827dd28a6f571fa72cdedb9100281a9a]';
comment on column "luck"."luck_charge_log".to_hash is '接收者[0x99174202841116fd7c5ca39c473c99fbe32f5c26]';
comment on column "luck"."luck_charge_log".contract is '合约[0x55d398326f99059ff775485246999027b3197955]';
comment on column "luck"."luck_charge_log".tokens is '交易数量[14555.88]';
comment on column "luck"."luck_charge_log".value is '原生代币数量[0]';
comment on column "luck"."luck_charge_log".transaction_fee is '交易费用[0.000153345 BNB]';
comment on column "luck"."luck_charge_log".gas_price is 'gas费用[0.000000003 BNB]';
comment on column "luck"."luck_charge_log".bnb_price is 'BNB价格[$318.04 / BNB]';

comment on column "luck"."luck_charge_log".remark is '备注[交易失败,BNB不足]';


comment on column "luck"."luck_charge_log".created_at is '创建时间';
comment on column "luck"."luck_charge_log".updated_at is '更新时间';
comment on column "luck"."luck_charge_log".deleted_at is '删除时间';


-- 活动
drop table if  exists "luck"."luck_activity";

create table if not exists "luck"."luck_activity" (
      "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
      "activity_code"  varchar(255)  NOT null,
      "credit"  numeric   null,
      "send_credit"  numeric  NULL,
      "remark" varchar(255)  null,
      "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
      "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
      "deleted_at" timestamp DEFAULT NULL
    );
drop index if exists luck_activity_code_idx;
CREATE INDEX luck_activity_code_idx ON "luck"."luck_activity" (activity_code);



comment on table  "luck"."luck_activity" is '活动';

comment on column "luck"."luck_activity".id is 'id';
comment on column "luck"."luck_activity".activity_code is '活动编码[RECHARGE]';
comment on column "luck"."luck_activity".credit is '积分[100]';
comment on column "luck"."luck_activity".send_credit is '赠送[10]';
comment on column "luck"."luck_activity".remark is '备注[充值100送10]';

comment on column "luck"."luck_activity".created_at is '创建时间';
comment on column "luck"."luck_activity".updated_at is '更新时间';
comment on column "luck"."luck_activity".deleted_at is '删除时间';

INSERT INTO "luck"."luck_activity" ("id", "activity_code", "credit", "send_credit", "remark", "created_at", "updated_at", "deleted_at") VALUES (1, 'NEW_USER', '0', '100', '新用户送100U', '2024-02-24 15:10:09.305832', '2024-02-24 15:10:09.305832', NULL);
