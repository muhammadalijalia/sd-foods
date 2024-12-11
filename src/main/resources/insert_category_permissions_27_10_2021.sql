set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE','/categories', 49, 'Categories','list_categories', 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/addcategory', 50, 'Add Category', 'add_category', 49);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/editcategory', 51, 'Edit Category', 'edit_category', 49);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (@id,'ACTIVE', '/deletecategory', 52, 'Delete Category', 'delete_category', 49);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, @id);
