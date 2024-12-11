set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', NULL, 24, 'cil-spreadsheet', 'Catalogue', 'catalogue', NULL);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);
update permissions set parent_id = @id where permission in ('list_partners', 'list_services', 'list_products', 'list_categories');

update permissions set icon = 'cil-phone' where permission in ('list_crm');

update permissions set menu_url = display_menu;
update permissions set menu_url = NULL where permission = 'profile';