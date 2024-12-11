set @order_id = (select id from permissions where permission = 'list_orders');
set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/validate_order', @id, '', 'Validate Order', 'validate_order', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/upload_signed_documents', @id, '', 'Upload Signed Documents', 'upload_signed_documents', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/confirm_order', @id, '', 'Confirm Order', 'confirm_order', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/receive_order', @id, '', 'Receive Order', 'receive_order', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/stock_order', @id, '', 'Stock Order', 'stock_order', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);

set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES (@id, 'ACTIVE', '/cancel_order', @id, '', 'Cancel Order', 'cancel_order', @order_id);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, @id);
