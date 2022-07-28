create table customer
(
    id                 uniqueidentifier not null
        primary key,
    address            varchar(255),
    description        varchar(max),
    dob                date,
    name               varchar(255),
    phone              varchar(255),
    account_creator_id uniqueidentifier
        constraint FK1xln57kaqxcekkxhpdw9m133t
            references account,
    account_updater_id uniqueidentifier
        constraint FKnujdemx9i7trd3jtdm598xlg3
            references account
)
go

INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'7358FED7-1A5C-2143-9C82-2C530FAF760E', N'Birn Hoa City', N'This is test account customer by Ductlmse', N'2022-06-23', N'Duc', N'0987660451', N'FA375463-D4CB-441C-A946-5D523B22564B', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6');
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'9655BF97-88D9-5D4B-A96A-37E5D13FC70C', N'Viet Nam', N'ABC', N'1999-05-01', N'CDE', N'098765432', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'D66EB25B-687E-C34E-B6BA-3EBF8B081EE3', N'Viet Nam', N'ABC', N'1999-05-07', N'Tran Hoang Huy', N'0575431234', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'11D161A8-4515-7446-A719-40E7FF550081', N'Viet Nam', N'No', N'1999-05-01', N'HuyDev', N'0123456789', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'5BF53876-1D98-40BF-884B-53E8B808D762', N'DT', N'des', N'1999-05-01', N'Test2', N'0124214124', N'38D7E5B9-E683-4531-8995-79D573D185B3', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'9072C244-2AC0-524C-A67B-57142BFB1327', N'Viet Nam', N'ABCD', N'2000-10-10', N'Test2', N'0983456212', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'BCAB263B-906F-48C4-993B-A21849CDCAC9', N'DT', N'Des', N'1999-05-01', N'CusTest1', N'0124124', N'7FA9A0A9-60D9-4161-B423-523B15B84F25', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'C85486A4-B5B8-0A4D-97AF-B09C29D702FA', N'Viet Nam', N'ABCD', N'1999-06-07', N'ABCD', N'0848660451', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'CF7B420B-B233-DF44-892D-BAA9BA9041CB', N'VN', N'ABCD', N'1999-05-01', N'ABCD', N'0987660451', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'8A07FBDE-03F0-624E-B3BE-BD98DBE96B0F', N'HCMC', N'This is test customer', N'1999-06-08', N'sumo', N'0345184306', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'7FB32D3C-0E9C-D942-B477-BFDC0494A06C', N'Viet Nam', N'ABCD', N'1999-05-05', N'test duplicate 2', N'0987678651', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'7CD820B6-2315-624C-B3BB-C1225EACEDAD', N'Viet Nam', N'Viet Nam', N'1999-05-07', N'test duplicate', N'0987657651', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'9B51F324-9D40-5645-B4DF-CA3CEDA18A30', N'Viet Nam', N'ABC', N'1999-07-07', N'Tran', N'0987789098', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'328A01FD-B1BA-9849-89BA-E516A6B6FD5C', N'VN', N'ABC', N'1999-05-01', N'A', N'09876543212', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'6B0624AB-679B-2B48-8C68-F46C7469021C', N'ABC', N'ABC', N'1999-08-10', N'ABC', N'0987654321', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'4B5615BA-0989-DC48-A957-F58836E1D39C', N'string', N'This is test customer', N'2022-05-18', N'HaoCG', N'123345123', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
INSERT INTO [css-database].dbo.customer (id, address, description, dob, name, phone, account_creator_id, account_updater_id) VALUES (N'7E3C864C-90CE-0348-B752-FE310D7CA110', N'abc', N'ABC', N'1999-05-01', N'Test1', N'0702637656', N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', null);
