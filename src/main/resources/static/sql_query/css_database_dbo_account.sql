-- create table account
-- (
--     id           uniqueidentifier default newid()       not null
--         primary key,
--     address      varchar(max),
--     description  varchar(max),
--     day_of_birth date,
--     email        varchar(255)     default 'anonymous@gmail.com',
--     gender       bit,
--     name         varchar(255),
--     phone        varchar(255)     default '000 000 000' not null,
--     point        float,
--     role_id      varchar(255)
--         constraint FKgdpd8e1vs356bjg287jr27pl7
--             references roles,
--     is_active    bit,
--     password     varchar(255),
--     non_locked   bit
-- )
-- go

INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'D4204C9E-2242-8445-92A8-1A855D981693', N'I''m on Intenet', N'This is admin account', N'2022-05-21', N'la@gmail.com', 1, N'lalala', N'123553535634', 12341234, N'ROLE_1', 0, N'$2a$10$ACG9SOjTD2Rwl4DIBFTsg.jLkRTThhsfj.dSubdzlzwyfJcZYPYYG', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'451A745A-4C66-40B1-873B-3E3B7CC4D04F', N'280 Rockaway Court Orlando, FL 32806', N'Functional Programmer at @delyjp ', N'1988-07-06', N'collaborator_1@gmail.com', 1, N'Yasuhiro', N'091237595034', 13, N'Role_3', 1, N'$2a$10$62ua7UGd7Dj3fBUxiWXYFeGqWDL6SO2nz.46u3K.pyrZ0SYGp9Cp6', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'7FA9A0A9-60D9-4161-B423-523B15B84F25', N'DT', N'DT', N'1998-08-25', N'tt@gmail.com', 1, N'CollabTest1', N'0123467', 132, N'ROLE_3', 1, N'123', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'FA375463-D4CB-441C-A946-5D523B22564B', N'collaborators', N'collab', N'2022-06-09', N'collaborator_2@gmail.com', 1, N'alaba', N'1234567456', 179, N'Role_3', 1, N'$2a$10$62ua7UGd7Dj3fBUxiWXYFeGqWDL6SO2nz.46u3K.pyrZ0SYGp9Cp6', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'922DCB19-C3C8-734E-890B-624C38CBD4D8', N'address', N'description', N'2022-06-28', N'email@gmail.com', 1, N'name', N'1234567890', 0, N'ROLE_2', 1, null, 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'38D7E5B9-E683-4531-8995-79D573D185B3', N'DT', N'DT', N'1999-08-25', N'collabtest2@gmail.com', 1, N'CollabTest2', N'012346578', 1222, N'ROLE_3', 1, N'123', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'4559088F-EA0E-4790-A706-7BE9D0073B04', N'DT', N'DT', N'1999-08-25', N'nh@gmail.com', 1, N'EnterpriseTest1', N'012345676', 1234, N'ROLE_2', 1, N'123', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'9129AED9-D84D-4397-843E-94CE7FF1BD28', N'Collaborator_3', N'Functional Programmer at @delyjp ', N'2022-06-06', N'collaborator_3@gmail.com', 1, N'Iris Top', N'2453245423', 20, N'ROLE_3', 1, null, 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'CDBCCE52-2393-FD48-A223-96E6C226AB02', N'HCMC 9 dictrict', N'This is address', N'2022-06-16', N'enterprise_4@gmail.com', 1, N'enterprise_3', N'+34565456743', 200, N'ROLE_2', 1, N'$2a$10$pH1LM2aMRNh7powFtXnlXuGcuNe3PPHKhJJPUPzkjlDIRH7GHE2ge', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'6D7DFCF3-C819-EE49-A2C3-A72D4A2B01A6', N'(740) 787-1789 500 Woodside Ln Hopewell, Ohio(OH), 43746', N'This is dev on mobile', N'1988-07-06', N'Shu@gmail.com', 1, N'Shu Darkin', N'+84848620369', 333, N'ROLE_3', 1, N'$2a$10$pH1LM2aMRNh7powFtXnlXuGcuNe3PPHKhJJPUPzkjlDIRH7GHE2ge', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'DE361154-29B4-CD49-8DF1-A7D8CCB0778D', null, null, null, null, null, null, N'+84934134522', null, N'ROLE_3', 1, null, 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'8D0BB955-7769-3D42-A587-C720DC4B347D', N'(217) 873-4478 336 E Church St Lovington, Illinois(IL), 61937', N'Your business can now hire me (hoc081098) for small Android Kotlin and Flutter side projects.', N'2022-05-26', N'enterprise@gmail.com', 1, N'Duc', N'034518430634', 2966, N'ROLE_2', 1, N'$2a$10$TKYqSh4NAoUBNzQrbL8pCeco7ZwwyNEndsoYONDqtaRqLyVycis1S', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'7539A214-CEF7-7042-A0BC-CC2D43E7DADD', N'HCMC 9 dictrict', N'This is address', N'2022-06-16', N'sumo6842@gmail.com', 1, N'enterprise_3', N'+84345184306', 0, N'ROLE_2', 1, N'$2a$10$g0T4dAghdtez0ZcNkb6bw.DHWLBW7asUbhm1C/b0L0WY5wNRBkoke', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'2E87378B-74B9-044E-A485-CEDFEE79A511', N'06740 Stamm Stravenue', N'Your interests outside of work', N'2022-05-22', N'enterprise_2@gmail.com', 1, N'ben', N'012345353245', 1000, N'ROLE_2', 0, N'$2a$10$wxsgfYowimMRL1HqgcSoOe7BRNb.bughYpyl.N3M0z3Ep58OhWque', 1);
INSERT INTO [css-database].dbo.account (id, address, description, day_of_birth, email, gender, name, phone, point, role_id, is_active, password, non_locked) VALUES (N'4184629A-392E-4B7E-9F16-DFFB3EA15A4B', N'moderator', N'moderator', N'2022-05-22', N'moderator@gmail.com', 1, N'moderator', N'000 000 000', 42341, N'ROLE_5', 1, N'$2a$10$62ua7UGd7Dj3fBUxiWXYFeGqWDL6SO2nz.46u3K.pyrZ0SYGp9Cp6', 1);
