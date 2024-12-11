set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, '/sales', @id, 'cilBasket', '/sales', 'Sales','sales', null);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);

set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'sales');
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, '/sales-orders', @id, NULL, '/sales-orders', 'Sales Orders', 'list_sales_orders', @parent_id);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);

set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'sales');
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, '/product-sales', @id, NULL, '/product-sales', 'Product Sales', 'list_product_sales', @parent_id);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);