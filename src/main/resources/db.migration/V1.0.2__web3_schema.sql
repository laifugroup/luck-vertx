
--  hash
drop table if  exists "luck"."hash";

create table if not exists "luck"."hash" (
       "id" BIGSERIAL  NOT NULL   PRIMARY KEY ,
       "address"  varchar(50)   not null,
       "type"  smallint   not null,
       "hash" varchar(255)   not null,
       "log_index" varchar(50)    null,
       "info" text   not null,
       "block" varchar(11)   not null,
       "status"  smallint default 1 not null,
       "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
       "updated_at" timestamp  DEFAULT CURRENT_TIMESTAMP ,
       "deleted_at" timestamp DEFAULT NULL
);

comment on table  "luck"."hash" is '哈希记录';

comment on column "luck"."hash".id is 'id';
comment on column "luck"."hash".address is '合约地址[0xddefbd8999caece153d494b17aef6a9614cb9b09]';
comment on column "luck"."hash".type is '类型[1-充值,2-提现]';
comment on column "luck"."hash".hash is '哈希[0x0d8958348687180d5c05f543d7da92503fc2b6fa54a7ccf1e1a0d306106d9fae]';
comment on column "luck"."hash".log_index is '事件日志的索引号[111111]';
comment on column "luck"."hash".info is '数据[{\"blockNumber\":\"23465089\"}]';
comment on column "luck"."hash".block is '区块[23465089]';
comment on column "luck"."hash".status is '状态[1-启用中,2-未启用]';

comment on column "luck"."hash".created_at is '创建时间';
comment on column "luck"."hash".updated_at is '更新时间';
comment on column "luck"."hash".deleted_at is '删除时间';


drop index if exists luck_hash_address_idx;
CREATE INDEX luck_hash_address_idx ON "luck"."hash" (address);