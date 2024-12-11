set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'catalogue');
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, '/ordersToDeliver', @id, NULL, '/ordersToDeliver', 'Orders to Deliver', 'order-to-deliver', @parent_id);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);

set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'catalogue');
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, '/pickOrder', @id, NULL, '/pickOrder', 'Pick Order', 'pick_order', @parent_id);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);