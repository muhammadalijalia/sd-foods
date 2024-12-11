update permissions set display_menu = '/stock-counts', menu_url = '/stock-counts', name = 'Stock Counts', permission = 'stock_counts'
where name = 'Adjustment' and menu_url = '/adjustment';

set @id = (select max(id) + 1 from permissions);
set @parent_id = (select id from permissions where permission = 'stock_counts');
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/addstockcounts', @id, 'Initiate Stock Counts', 'add_stock_counts', @parent_id);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);