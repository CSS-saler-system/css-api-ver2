-- create table account_prize
-- (
--     account_id uniqueidentifier not null
--         constraint FK47v2tjhejucf47cwueiihbekg
--             references account,
--     prize_id   uniqueidentifier not null
--         constraint FKd2yg8674afnrtp8o1q96yghfb
--             references prize,
--     primary key (account_id, prize_id)
-- )
-- go

INSERT INTO [css-database].dbo.account_prize (account_id, prize_id) VALUES (N'451A745A-4C66-40B1-873B-3E3B7CC4D04F', N'14960AFC-286A-4C4D-8A91-6CFC68419685');
INSERT INTO [css-database].dbo.account_prize (account_id, prize_id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'14960AFC-286A-4C4D-8A91-6CFC68419685');
