set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where name = 'Orders');
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id, menu_url) VALUES (@id, 'ACTIVE', '/published', 3, '', 'Published', 'published_orders', @parent_id, '/published');
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

update permissions set display_order = 4 where permission = 'cancelled_orders';