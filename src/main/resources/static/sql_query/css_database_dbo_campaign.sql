-- create table campaign
-- (
--     id                         uniqueidentifier default newid() not null
--         primary key,
--     campaign_description       varchar(max),
--     campaign_short_description varchar(max),
--     campaign_status            varchar(255),
--     create_date                datetime2,
--     end_date                   datetime2,
--                         varchar(255),
--     kpi                        bigint,
--     last_modified_date         datetime2,
--     name                       varchar(255),
--     start_date                 datetime2,
--     account_id                 uniqueidentifier
--         constraint FKlqesxxnp9uqmams3lg7vvlk01
--             references account
-- )
-- go

INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'0F38008A-95F3-C946-899D-1011CE5FBB38', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:14:59.3380000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:14:59.3380000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'2BDCC557-CC0B-C442-BEA3-1BF178C34CF7', N'description', N'short-description', N'APPROVAL',
        N'2022-07-06 13:21:29.3541770', N'2023-12-31 23:59:59.0000000', 13, N'2022-07-06 13:31:21.8851933',
        N'name', N'2023-01-01 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'4BB7EDEB-7E23-494C-BE0F-3069FEBE9725', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:16:54.8390000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:16:54.8390000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'B833A782-146B-BE4B-8BD5-354DEB461E07', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:17:15.1770000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:17:15.1770000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'096285B5-10E7-0F4B-A102-3B4829962C01', N'test ne', N'test ', N'CREATED', N'2022-07-25 09:19:55.4810000',
        N'2022-08-25 00:00:00.0000000', 25, N'2022-07-25 09:19:55.4810000', N' son create campaign  test 1',
        N'2022-07-31 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'8F3AFA80-D337-B845-91D6-4D7C69610E9E', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:16:55.5940000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:16:55.5940000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'439AD0C8-B4EC-4A1D-95A3-54F82A8BD3EC', N'This is to test campaign', N'The test campaign', N'FINISHED',
        N'2022-06-30 07:34:19.0000000', N'2022-06-30 07:34:30.0000000', 10, N'2022-07-01 09:49:34.4171947',
        N'This is test campaign', N'2022-06-29 07:35:03.0000000', N'2E87378B-74B9-044E-A485-CEDFEE79A511');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'24F1E18B-65E0-7641-8E31-5BC558BE7198', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:17:16.1910000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:17:16.1910000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'BC94E59C-0409-B84D-8864-62556748C720', N'description', N'short-description', N'CREATED',
        N'2022-07-21 08:20:16.1000000', N'2023-12-31 23:59:59.0000000', 13, N'2022-07-21 08:20:16.1000000',
        N'name', N'2023-01-01 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'BEDABAA4-C3B2-5E4B-A87E-9CB940E4F6DC', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:30:55.4300000',
        N'2022-07-30 00:00:00.0000000', 22, N'2022-07-23 11:30:55.4300000', N'wewe',
        N'2022-07-29 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'9298BCB3-648E-904A-8093-A5374336C9D6', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:17:20.4150000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:17:20.4150000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'514B7347-1616-3949-9224-BBE16B960C71', N'description', N'short-description', N'DISABLED',
        N'2022-07-04 04:57:32.6520000', N'2023-12-31 23:59:59.0000000', 13, N'2022-07-06 13:45:16.1806127',
        N'name', N'2023-01-01 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
-- INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
--                                          create_date, end_date, kpi, last_modified_date, name, start_date,
--                                          account_id)
-- VALUES (N'B0C7FEC8-8832-4FD4-A177-BE927F4A412C', N'description', N'short-description', N'REJECTED',
--         N'2022-07-17 10:44:46.0000000', 13, N'2022-07-16 11:07:30.5365899', N'the second campaign',
--         N'2022-07-15 10:44:16.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'89469FF7-E96F-AD41-B135-C809CA4F787A', N'wewe', N'ewe', N'CREATED', N'2022-07-23 11:32:47.9170000',
        N'2022-07-30 00:00:00.0000000', 112, N'2022-07-23 11:32:47.9170000', N'eee',
        N'2022-07-29 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'AE7B69F9-9091-3143-893A-DCC39A11EA46', N'sffsdf', N'34errer', N'CREATED', N'2022-07-23 11:26:55.3470000',
        N'2022-08-03 00:00:00.0000000', 23, N'2022-07-23 11:26:55.3470000', N'gfgf',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'8F1BE6C6-A2F0-424C-A360-DEB6DF27FCC7', N'description', N'short-description', N'CREATED',
        N'2022-07-23 18:01:44.6620398', N'2023-12-31 00:00:00.0000000', 13, N'2022-07-23 18:01:44.6620398',
        N'name', N'2023-01-01 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'E60DEB1F-8615-4840-A2B0-E828FDFA27D6', N'description', N'short-description', N'CREATED',
        N'2022-07-23 10:43:29.5280000', N'2023-12-31 00:00:00.0000000', 13, N'2022-07-23 10:43:29.5280000',
        N'name', N'2023-01-01 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'4A642B38-495E-0848-A108-FD9ABA629650', N'description', N'short-description', N'APPROVAL',
        N'2022-06-30 19:19:04.7955061', N'2022-07-01 14:26:44.0000000', 13, N'2022-06-30 19:19:04.7950000',
        N'the second campaign', N'2022-07-15 10:44:16.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.campaign (id, campaign_description, campaign_short_description, campaign_status,
                                         create_date, end_date, kpi, last_modified_date, name, start_date,
                                         account_id)
VALUES (N'6FD09FCD-D290-C449-BE13-FE6E00D50F5D', N'wewewe', N'wewe', N'CREATED', N'2022-07-23 11:17:13.4940000',
        N'2022-07-30 00:00:00.0000000', 34, N'2022-07-23 11:17:13.4940000', N'abc',
        N'2022-07-27 00:00:00.0000000', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
