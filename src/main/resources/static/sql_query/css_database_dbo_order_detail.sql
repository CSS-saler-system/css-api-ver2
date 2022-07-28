-- create table order_detail
-- (
--     id                  uniqueidentifier default newid() not null
--         primary key,
--     product_name        varchar(255),
--     total_price_product float,
--     quantity            bigint,
--     order_id            uniqueidentifier
--         constraint FKrws2q0si6oyd6il8gqe2aennc
--             references orders,
--     product_point       float,
--     product_price       float,
--     total_point_product float,
--     product_id          uniqueidentifier
--         constraint FKc7q42e9tu0hslx6w4wxgomhvn
--             references products
-- )
-- go

INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'58FB0E3B-A913-476E-8D2E-06F0864EC479', N'Iphone 15', 156, 13, N'DDF06201-DC5C-6C43-B54B-579A798F2186', 12, 12, 156, N'39ED1D57-82A1-4B3B-B2AE-D379ACB81D25');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'F662860D-F2F8-134F-A816-0F544F5F33CC', N'This is Test Async var 2', 26, 2, N'F0D6C545-BB58-0D46-817F-AF09F73E73A2', 13, 13, 26, N'F53F6E84-91E7-614C-A864-144DB2DDBBF6');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'652C312A-24FF-40E8-80E2-4660CCFBB9E5', N'OdTestProduct3', 123, 2, N'13B1045D-98A3-4621-85AE-66B739BBA719', 12, 12, 12, N'420D04B5-34BA-4C5B-901E-48B1ED061A0D');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'CDBF0B91-9D5F-214D-A72E-482957F85CA2', N'Iphone 15', 32.7, 3, N'F0D6C545-BB58-0D46-817F-AF09F73E73A2', 19, 10.9, 57, N'7C97A746-E4C8-4644-9598-1EAD6265DCA8');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'E6111A65-DD10-EE46-932C-484BF7F535A5', N'Cooler', 322, 1, N'9A4BC1A7-5B9E-4B4D-8B09-3EB8A3B49D48', 333, 322, 333, N'1FFAFBB7-A32A-1E48-A484-FB996A9A482A');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'E4FFB45A-EF15-42FB-B494-4E5DE3E9F34E', N'OdTestProduct2', 111, 2, N'019187F9-84A7-40BB-824F-A44B68404C73', 12, 12, 12, N'E7862A84-89A7-419C-99A7-3C63F146C312');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'71A77FCC-F8D1-994B-BEB4-503593AD73BB', N'Cooler', 322, 1, N'5DA67305-667E-B840-886C-6F1D3E945500', 333, 322, 333, N'1FFAFBB7-A32A-1E48-A484-FB996A9A482A');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'D6A03124-A19E-974A-BCBC-6EE6C14BCED5', N'Cooler', 644, 2, N'05786427-3237-F046-9309-E8523EAC8087', 333, 322, 666, N'82DCE0CA-D036-11EC-9D64-0242AC120002');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'5447D9AE-B337-4340-918D-76F3FC1415C3', N'OdTestProduct1', 111, 2, N'019187F9-84A7-40BB-824F-A44B68404C73', 12, 12, 24, N'259AE0C3-1A52-4C12-BB2B-972826D23D8B');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'E68B48C5-0AB0-9147-82E9-7E489DAA56A0', N'Cooler', 322, 1, N'0E17C882-B649-034A-932A-885C385BC28F', 333, 322, 333, N'82DCE0CA-D036-11EC-9D64-0242AC120002');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'297771D2-D47D-A745-9405-825A69E72CB9', N'Cooler', 322, 1, N'415DBF58-93A6-5643-A0E1-8F720AC3D5B3', 333, 322, 333, N'82DCE0CA-D036-11EC-9D64-0242AC120002');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'A9F74BDD-DFDD-A044-8C6E-82DBB24A640A', N'demo product', 0, 0, N'74A58878-3139-4749-B7EC-F1E85B90F161', 13, 13, 0, N'158EEA5A-5774-9C49-A40F-700DFFF3EDF1');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'1E03BE76-554A-F441-ACA7-88EFFB7B57BE', N'Cooler', 322, 1, N'2C09B04E-05F0-9A40-BAE7-A868AF78A283', 333, 322, 333, N'82DCE0CA-D036-11EC-9D64-0242AC120002');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'37ED1948-157F-814D-AA8C-8A8D01B93517', N'This is Test Async var 2', 13, 1, N'0B4164B9-EDCD-794D-8222-2A52A85961E6', 13, 13, 13, N'F53F6E84-91E7-614C-A864-144DB2DDBBF6');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'DB682400-F6AD-0B46-A7E5-8C35D71E2B9D', N'Cooler', 322, 1, N'67A47E33-3FC3-AD46-B4F2-016F78D01C02', 333, 322, 333, N'82DCE0CA-D036-11EC-9D64-0242AC120002');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'88614F27-407F-7041-9936-8CB9B0900B27', N'LG Screen', 156, 13, N'C89C1EC5-3FD9-2B43-8AF7-250487965407', 12, 12, 156, N'1FFAFBB7-A32A-1E48-A484-FB996A9A482A');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'BDA09F4D-00F1-AD46-867C-948FEBFAF5EC', N'Samsung Furniture', 13, 1, N'9A4BC1A7-5B9E-4B4D-8B09-3EB8A3B49D48', 13, 13, 13, N'81F5398A-92F8-DD4A-882E-76531D3E142A');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'A1765694-FB36-9140-896B-A32B624B29B8', N'This is Test Async var 3', 13, 1, N'0F31E195-2537-D943-B0D9-3D560B17BD42', 13, 13, 13, N'8C886DC7-1B63-454B-BD83-00129ED125CF');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'64D36DA8-5575-4442-AAEA-A736E803BF8D', N'LG Screen', 120, 10, N'DDF06201-DC5C-6C43-B54B-579A798F2186', 12, 12, 120, N'1FFAFBB7-A32A-1E48-A484-FB996A9A482A');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'2B248FD0-5A5C-784F-B660-A7A1C732A7A3', N'Iphone 15', 10.9, 1, N'5DA67305-667E-B840-886C-6F1D3E945500', 19, 10.9, 19, N'7C97A746-E4C8-4644-9598-1EAD6265DCA8');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'CCCC87D2-7C1D-E349-8653-A8968B351A1E', N'Iphone 15', 10.9, 1, N'0B4164B9-EDCD-794D-8222-2A52A85961E6', 19, 10.9, 19, N'7C97A746-E4C8-4644-9598-1EAD6265DCA8');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'EDEFCF3F-2EA7-7845-83BA-A8B59C22344F', N'Iphone 15', 10.9, 1, N'0B4164B9-EDCD-794D-8222-2A52A85961E6', 19, 10.9, 19, N'D1D6749B-7D27-4341-8718-2BC82230FE50');
INSERT INTO [css-database].dbo.order_detail (id, product_name, total_price_product, quantity, order_id, product_point, product_price, total_point_product, product_id) VALUES (N'89F73DC2-3BB3-40A2-8CBD-C0BA4E5AA096', N'demo product', 156, 13, N'934D04FF-D1A4-47D9-8ADA-ADC393924FF3', 12, 12, 156, N'158EEA5A-5774-9C49-A40F-700DFFF3EDF1');
