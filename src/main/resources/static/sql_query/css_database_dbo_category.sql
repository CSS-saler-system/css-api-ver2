create table category
(
    id            uniqueidentifier default newid() not null
        primary key,
    category_name varchar(255)                     not null,
    status        varchar(20)
)
go

create unique index category_category_name_uindex
    on category (category_name)
go

INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'A0917C68-1200-4DC0-8E73-17F616D4F8EC', N'Smart Phone', N'ACTIVE');
INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'4B95474A-BA99-4BDD-8C6D-78AD33ED6420', N'Transportation', N'ACTIVE');
INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'927349B7-59C2-4B56-AF43-82725A128A70', N'Other Technological', N'ACTIVE');
INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'865F8838-4499-4B45-8284-8741EA71FAFF', N'Drink', N'ACTIVE');
INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'7B488354-BDA7-4318-AAAD-8797BEEEDAAD', N'Kitchen', N'ACTIVE');
INSERT INTO [css-database].dbo.category (id, category_name, status) VALUES (N'4E42E47D-8942-416B-87DC-C5CE1C087695', N'Furniture', N'ACTIVE');
