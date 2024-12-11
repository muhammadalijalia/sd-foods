set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where name = 'Orders');
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/new', 3, '', 'New', 'new_orders', @parent_id, '/new');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/validated', 3, '', 'Validated', 'validated_orders', @parent_id, '/validated');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/confirmed', 3, '', 'Confirmed', 'confirmed_orders', @parent_id, '/confirmed');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/received', 3, '', 'Received', 'received_orders', @parent_id, '/received');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/stocked', 3, '', 'Stocked', 'stocked_orders', @parent_id, '/stocked');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/cancelled', 3, '', 'Cancelled', 'cancelled_orders', @parent_id, '/cancelled');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

