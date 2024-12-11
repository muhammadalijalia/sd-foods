set @id = (select max(id) + 1 from permissions);
INSERT INTO permissions
(id, created_by, created_on, flag, updated_by, updated_on, display_menu, display_order, icon, menu_url, name, permission, parent_id)
VALUES(@id, NULL, NULL, 'ACTIVE', NULL, NULL, null, @id, 'cil-truck', null, 'Logistics', 'logistics', null);
INSERT INTO roles_permissions(role_id, permission_id) VALUES(1, @id);

set @parent_id = (select id from permissions where permission = 'sales');
update permissions 
set parent_id = @id
where parent_id = @parent_id;

update permissions
set name = 'Orders to prepare'
where name = 'Sales Orders';

update permissions 
set name = 'Orders to pick'
where name = 'Pick Order';

update permissions 
set flag = 'INACTIVE'
where permission = 'sales'