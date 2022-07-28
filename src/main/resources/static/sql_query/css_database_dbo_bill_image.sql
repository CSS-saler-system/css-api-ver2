-- create table bill_image
-- (
--     id              bigint identity
--         primary key,
--     path            varchar(255),
--     transactions_id uniqueidentifier
--         constraint FKem1nr15jsg3jfcqicy703p3vj
--             references transactions
-- )
-- go

INSERT INTO [css-database].dbo.bill_image (path, transactions_id)
VALUES (
        N'https://csssalersystem.blob.core.windows.net/css-transaction-image/2d0490ee-3032-4014-9318-671ca56144b4/quotation-invoice-template-paper-bill-form-vector-21825447.jpg',
        N'EE90042D-3230-1440-9318-671CA56144B4');
INSERT INTO [css-database].dbo.bill_image (path, transactions_id)
VALUES (
        N'https://csssalersystem.blob.core.windows.net/css-transaction-image/f2a5c5be-5d82-4d98-801c-2625e4b53c10/images.jpg',
        N'BEC5A5F2-825D-984D-801C-2625E4B53C10');
INSERT INTO [css-database].dbo.bill_image (path, transactions_id)
VALUES (
        N'https://csssalersystem.blob.core.windows.net/css-transaction-image/dcba6aff-df4d-4868-aecb-5c364ebd987e/203-2032027_solution-solution-icon-png-free-transparent-png.png',
        N'FF6ABADC-4DDF-6848-AECB-5C364EBD987E');
INSERT INTO [css-database].dbo.bill_image (path, transactions_id)
VALUES (
        N'https://csssalersystem.blob.core.windows.net/css-transaction-image/2d0490ee-3032-4014-9318-671ca56144b4/images.jpg',
        N'EE90042D-3230-1440-9318-671CA56144B4');
