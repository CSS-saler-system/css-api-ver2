create table prize
(
    id           uniqueidentifier default newid() not null
        primary key,
    description  varchar(max),
    name         varchar(255),
    price        float,
    status_prize varchar(255),
    account_id   uniqueidentifier
        constraint FKt0xvm9lvp260xmnmps6tpv0af
            references account
)
go

INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'28041FC5-7448-4540-8123-0C04134A7B6F', N'this is test 0', N'string', 0, N'ACTIVE', N'7539A214-CEF7-7042-A0BC-CC2D43E7DADD');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'6B154C9B-E992-B24D-9F81-566A10A85970', N'this is test 0', N'vcd', null, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'CCCEE65A-21C6-C04F-A315-5DED3C7D806E', N'this is test 0', N'abc', null, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'2041346E-4D67-2247-BE8A-62414A9D76B9', N'this is test 0', N'Test Prize', 10.3, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'EF3C0E02-054B-4B3A-BD84-683C8EFEAAEB', N'this is test 1', N'test', 13, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'14960AFC-286A-4C4D-8A91-6CFC68419685', N'this is test 2', N'test', 113, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'D2ACC2E3-DDFD-4C40-B1E9-83C4057EC29A', N'this is test 0', N'sdds', null, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'B7CEDF36-A136-D949-B74F-8788288DE5BE', N'this is test 0', N'dfdf', null, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'F4299FD8-CCE2-BA41-A2DC-9A04CF170029', N'this is test 0', N'top 1', 10, N'ACTIVE', N'7539A214-CEF7-7042-A0BC-CC2D43E7DADD');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'CAD389C4-EF0E-4673-BBAC-D51BEDFF6C47', N'this is test 3', N'test', 100, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'26467163-13F2-BC41-85B7-F936464A9E58', N'this is test 0', N'Top 1', 100, N'ACTIVE', null);
INSERT INTO [css-database].dbo.prize (id, description, name, price, status_prize, account_id) VALUES (N'2DF28BA7-48C1-0642-8F4F-F94C738F5EDF', N'this is test 0', N'dsds', null, N'ACTIVE', N'8D0BB955-7769-3D42-A587-C720DC4B347D');
