-- create table account_token
-- (
--     account_id         uniqueidentifier                 not null
--         constraint FKj3hd5090eomc28qusumn0g6rt
--             references account,
--     registration_token varchar(225),
--     update_token_date  datetime2,
--     id                 uniqueidentifier default newid() not null
--         constraint account_token_pk
--             primary key
-- )
-- go

INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'FA375463-D4CB-441C-A946-5D523B22564B', N'The first', N'2022-06-19 10:41:29.0000000', N'975B832D-CB25-49CE-AD08-14BBB3FD2B3C');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'flUGvCSuQ3SINSZX2SAxAo:APA91bGlt9jE1w0BOI49yuiX2PvGvaPH9F4_Xlgn-HVo9gzn39SUzsXGxFZM8HdZusS7razJG6x3s1ByMuTt5RbHRjrEUUG6pwP4qQxM33igsOrrRbqTc9d3zOlZa06aNZB7F4SwPis5', N'2022-07-11 14:18:20.8770000', N'3B267615-371B-E245-A21D-42C45B5588B3');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'emVFflTiTUygk1bPd42Hvn:APA91bHTQ-8HLB_876YrUuEiQTyiRyMEgxbfqQZY8-QSLB6wMhOvI4ao3mrstfAw8qalY--e0mC6QFygYudLrwwje_G6zITMoiQnSUGnVjQuo07oBEl-nwjbET3KpZOB8pDzjxziePxm', N'2022-07-20 17:17:14.0670000', N'B27CA700-4A89-7B48-B75B-71DE370863A5');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'FA375463-D4CB-441C-A946-5D523B22564B', N'The second', N'2022-09-19 10:41:32.0000000', N'86130A7E-AA3B-4466-A27D-72FF45ACD660');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'DE361154-29B4-CD49-8DF1-A7D8CCB0778D', N'c66f1Zi9Txutrr5hN5hoUp:APA91bEsjWFsVKYfAjzWxV46uMK5xCPgrL4EZZ9QEFgkzCYBRERz0mpgu4pbc8LVs2fw2qUbHSUb4SdAbSvICtjKBZU7xp9a9KgwN-W-PhpjMZYxVzYM36yGTh_8sp720BFjROmWdErN', N'2022-07-11 13:14:47.4170000', N'B07D072B-C42B-4745-A385-879046BFA60F');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'eNRXglr1S0uhB0QwjhLei7:APA91bFafnq6xC4hjFkBhpXQENiqDryqMvXTJStmJrSnuowNmyfpbdOuHBhPNk0WVqvl8eUmeTb63YDLXw3c2QLBtCJOeyCOw2i3r03RjpiLpueKNwveYlfJiiBKHeeRH8vZJdUO6lc1', N'2022-07-20 17:34:02.3320000', N'B2192CFA-F7E3-904F-94F1-A88086252951');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'eIXnIS0HTCOAtS3vfBHVdx:APA91bFX641XKtvc2T9Tj51A1uhdI6VP44WQhXIqV4H-UHaBDJ0-QLMe7HS3opyuH7WzP2R_8od3oBx1A6QiLSvSHLvDiixLZxFksQFyg8Oj03t_EUIpKcS-f74yfm8nzPWBAx2m5re6', N'2022-07-19 17:18:18.3750000', N'7F4A6F0A-C5F5-6E4C-BBAE-B8CB682CE908');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'c8gy1yrJQYuN1EbTAj8RJZ:APA91bFDwh2XCHsOxNIuRu-JOwh5UXN5tjfDvVzksehG_X5LIYPXq73Aq2S7YY-qIZTiiORk7EdsILJcEpOwXAjlmBeY9Tm41TqMqF6Gm943hSu4RcRoSKNnZhjgp8cZo9bEafn_MZGi', N'2022-07-11 13:56:13.1330000', N'8EF89209-71CA-8C49-B067-D4AB6BFB5916');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'cP_CKGwVSfmrRxcjAmPqCw:APA91bHQ7ZBNW5DmYB6dQXjl7G4ma9dq1rFJ2MB_L70l6-iixVdHDFZfbfOzvM6j-an35p4TpL-skQx8zqewI7nvYAcJxw9WdOIYzoGXsOakFH48BTNEAEdPY1W2cRSQTJPQl18FSSKp', N'2022-07-21 13:31:17.7100000', N'F48CCAF5-C452-C547-A579-DBF0E25576B8');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'FA375463-D4CB-441C-A946-5D523B22564B', N'cHQfa68QSiW47z_F6a_pk7:APA91bEN40YRpeu6ePE2bCbjkzJhsgqcyiUjYvEm9aQU_huaGwHskIeRzSL3m5QIJcz0Dc8rYhA2DeY2yurnt4zLoZNRbCDwqSNinUOiMNL_n2TgqelAPNSdMQ06I7KUacA0eqNnxxL3', N'2024-06-19 10:41:36.0000000', N'34FFDD55-E388-4E8F-92A8-E5D46F05940F');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'DE361154-29B4-CD49-8DF1-A7D8CCB0778D', N'c8gy1yrJQYuN1EbTAj8RJZ:APA91bFDwh2XCHsOxNIuRu-JOwh5UXN5tjfDvVzksehG_X5LIYPXq73Aq2S7YY-qIZTiiORk7EdsILJcEpOwXAjlmBeY9Tm41TqMqF6Gm943hSu4RcRoSKNnZhjgp8cZo9bEafn_MZGi', N'2022-07-11 13:41:06.9330000', N'1C7B6E34-A9FB-DD4F-897F-E67FE034A5E6');
INSERT INTO [css-database].dbo.account_token (account_id, registration_token, update_token_date, id) VALUES (N'DE361154-29B4-CD49-8DF1-A7D8CCB0778D', N'dunuzsfDRlqvfuJ3DbhRI4:APA91bFcfElMsY-x62mjNmsTvuLgnbWXlgUluhhmwgSBdqK80lU3pAoDRq_f7rQs8JKEDiVU2Gw_MSTR2N6hKbfVLDNNXqYsEXlyQFGFcwoX_uQf-KMF4WN7s7tOkm8BQHhbLMKKPzJs', N'2022-07-25 13:25:16.8590000', N'4CCFB46D-362A-4542-B0AA-F429AB355664');
