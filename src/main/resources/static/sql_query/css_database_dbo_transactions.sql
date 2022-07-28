-- create table transactions
-- (
--     id                      uniqueidentifier default newid() not null
--         primary key,
--     last_modified_date      datetime2,
--     point                   float                            not null,
--     transaction_status      varchar(255),
--     create_transaction_date datetime2,
--     account_approver_id     uniqueidentifier
--         constraint FKc2uvg7qi4elumle5k38m1vkx8
--             references account,
--     account_creator_id      uniqueidentifier
--         constraint FKoalh1wgf7l6bi0yc4gs8u3mtr
--             references account
-- )
-- go

INSERT INTO [css-database].dbo.transactions (id, last_modified_date, point, transaction_status, create_transaction_date, account_approver_id, account_creator_id) VALUES (N'BEC5A5F2-825D-984D-801C-2625E4B53C10', N'2022-06-28 14:51:08.5949133', 1234, N'ACCEPT', N'2022-06-25 08:00:56.8172469', N'4184629A-392E-4B7E-9F16-DFFB3EA15A4B', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.transactions (id, last_modified_date, point, transaction_status, create_transaction_date, account_approver_id, account_creator_id) VALUES (N'FF6ABADC-4DDF-6848-AECB-5C364EBD987E', N'2022-06-28 14:52:10.9574634', 1234, N'REJECT', N'2022-06-27 12:53:28.6840000', null, N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.transactions (id, last_modified_date, point, transaction_status, create_transaction_date, account_approver_id, account_creator_id) VALUES (N'EE90042D-3230-1440-9318-671CA56144B4', N'2022-06-28 10:14:14.9180942', 123, N'CREATED', N'2022-06-24 07:38:06.0632075', N'4184629A-392E-4B7E-9F16-DFFB3EA15A4B', N'CDBCCE52-2393-FD48-A223-96E6C226AB02');
