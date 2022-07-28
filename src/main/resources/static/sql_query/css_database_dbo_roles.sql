-- create table roles
-- (
--     id   varchar(255) not null
--         primary key,
--     name varchar(255)
-- )
-- go

INSERT INTO [css-database].dbo.roles (id, name) VALUES (N'ROLE_1', N'Admin');
INSERT INTO [css-database].dbo.roles (id, name) VALUES (N'ROLE_2', N'Enterprise');
INSERT INTO [css-database].dbo.roles (id, name) VALUES (N'ROLE_3', N'Collaborator');
INSERT INTO [css-database].dbo.roles (id, name) VALUES (N'ROLE_4', N'Customer');
INSERT INTO [css-database].dbo.roles (id, name) VALUES (N'ROLE_5', N'Moderator');
