set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'catalogue');

INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `menu_url`, `parent_id`) VALUES (@id,'ACTIVE','/warehouses', 49, 'Warehouses','list_warehouses', '/warehouses', @parent_id);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/addwarehouse', 50, 'Add Warehouse', 'add_warehouse', @parent_id);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/editwarehouse', 51, 'Edit Warehouse', 'edit_warehouse', @parent_id);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/deletewarehouse', 52, 'Delete Warehouse', 'delete_warehouse', @parent_id);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);
