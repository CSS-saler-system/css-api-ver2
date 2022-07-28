create table feed_back
(
    id           uniqueidentifier not null
        primary key,
    create_date  datetime2,
    feed_content varchar(max),
    feed_reply   varchar(max),
    reply_date   datetime2,
    approver_id  uniqueidentifier
        constraint FKjbwewfg6jhgni421mvqwu2kel
            references account,
    campaign_id  uniqueidentifier
        constraint FKs88g6orvf58pb03ip9qy1tdbg
            references campaign,
    creator_id   uniqueidentifier
        constraint FK3w4ulw6a40bnsx7ba6vbsvh7
            references account
)
go

