INSERT INTO `permissions`
VALUES (1,NULL,NULL,'ACTIVE',NULL,NULL,'dashboard',1,NULL,'cil-speedometer',NULL,'Dashboard','dashboard',NULL),
(2,NULL,NULL,'ACTIVE',NULL,NULL,'regions',2,NULL,'cil-globe-alt','','Regions','list_regions',NULL),
(3,NULL,NULL,'ACTIVE',NULL,NULL,'addregion',3,NULL,NULL,NULL,'ADD REGION','add_region',2),
(4,NULL,NULL,'ACTIVE',NULL,NULL,'editregion',4,NULL,'','','EDIT','edit_region',2),
(5,NULL,NULL,'ACTIVE',NULL,NULL,'agencies',5,NULL,'',NULL,'Agencies','list_agencies',NULL),
(6,NULL,NULL,'ACTIVE',NULL,NULL,'addagency',6,NULL,'',NULL,'ADD AGENCY','add_agency',5),
(7,NULL,NULL,'ACTIVE',NULL,NULL,'editagency',7,NULL,NULL,NULL,'EDIT','edit_agency',5),
(8,NULL,NULL,'ACTIVE',NULL,NULL,'employees',8,NULL,'cil-user',NULL,'Employees','list_employees',NULL),
(9,NULL,NULL,'ACTIVE',NULL,NULL,'addemployee',9,NULL,'',NULL,'ADD EMPLOYEE','add_employee',8),
(10,NULL,NULL,'ACTIVE',NULL,NULL,'editemployee',10,NULL,NULL,NULL,'EDIT','edit_employee',8),
(11,NULL,NULL,'ACTIVE',NULL,NULL,'teams',11,NULL,'cil-people',NULL,'Teams','list_teams',NULL),
(12,NULL,NULL,'ACTIVE',NULL,NULL,'addteam',12,NULL,NULL,NULL,'ADD TEAMS','add_team',11),
(13,NULL,NULL,'ACTIVE',NULL,NULL,'editteam',13,NULL,NULL,NULL,'EDIT','edit_team',11),
(14,NULL,NULL,'ACTIVE',NULL,NULL,'partners',14,NULL,'',NULL,'Partners','list_partners',NULL),
(15,NULL,NULL,'ACTIVE',NULL,NULL,'addpartner',15,NULL,'',NULL,'ADD PARTNERS','add_partner',14),
(16,NULL,NULL,'ACTIVE',NULL,NULL,'editpartner',16,NULL,'',NULL,'EDIT','edit_partner',14),
(17,NULL,NULL,'ACTIVE',NULL,NULL,'roles',17,NULL,'cil-check',NULL,'Roles','list_roles',NULL),
(18,NULL,NULL,'ACTIVE',NULL,NULL,'addrole',18,NULL,NULL,NULL,'ADD ROLE','add_role',17),
(19,NULL,NULL,'ACTIVE',NULL,NULL,'editrole',19,NULL,NULL,NULL,'EDIT','edit_role',17),
(23,NULL,NULL,'ACTIVE',NULL,NULL,'crm',23,NULL,NULL,NULL,'CRM','list_crm',NULL),
(24,NULL,NULL,'ACTIVE',NULL,NULL,'myprofile',24,NULL,NULL,NULL,'My Profile','profile',1);

INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `icon`, `name`, `permission`, `parent_id`) VALUES ('26', 'ACTIVE', NULL, '26', 'cil-settings', 'Configuration', 'config', NULL);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `icon`, `name`, `permission`, `parent_id`) VALUES ('27', 'ACTIVE', '/changepassword', '27', '', 'Change Password', 'change_password', '26');

update permissions set parent_id = 26 where id = 17;

INSERT INTO `roles` VALUES (1,'system','2017-05-04 00:00:00','ACTIVE',NULL,'2017-09-29 20:04:08','Administrator'),
(2,'system','2017-05-04 00:00:00','ACTIVE','','2017-09-28 21:20:05','Sales Team'),
(3,'system','2017-07-26 09:54:42','ACTIVE','','2017-09-29 08:15:12','Back Office');

INSERT INTO `roles_permissions` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),
(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,23),(1,24);

INSERT INTO `access_level` VALUES (1,'admin',NULL,'ACTIVE',NULL,NULL,0,'self'),
(2,NULL,NULL,NULL,NULL,NULL,1,'all'),
(3,NULL,NULL,NULL,NULL,NULL,2,'team'),
(4,NULL,NULL,NULL,NULL,NULL,3,'partner'),
(5,NULL,NULL,NULL,NULL,NULL,4,'clan'),
(6,NULL,NULL,NULL,NULL,NULL,5,'agency');


INSERT INTO `jobs`
VALUES (1,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Director',2),
(2,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Team Chief',3),
(3,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Agency Director',6),
(4,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Commercial',1)

INSERT INTO `employees` (`id`, `created_by`, `created_on`, `flag`, `updated_by`, `updated_on`, `bban`, `birthday`, `birthplace`, `civility`, `email`, `emergency_email`, `familly_status`, `first_name`, `home_phone`, `mobile_phone`, `name`, `nationality`, `personcity`, `personcountry`, `latitude`, `longitude`, `personpostalcode`, `personstreet`, `personstreet2`, `other_names`, `job_id`, commission_percentage) VALUES
(1, 'system', current_timestamp(), 'ACTIVE', 'system', current_timestamp(), '000000000000000', '1984-10-27 00:00:00', NULL, 'MR', 'usman@caansoft.com', NULL, NULL, 'Javed', '00333466271', NULL, 'Usman', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, 0);


INSERT INTO `users` (`id`, `created_by`, `created_on`, `updated_by`, `updated_on`, `flag`, `password`, `employee_id`, `enabled`, `username`)
VALUES (1, 'system', current_timestamp(), 'system', current_timestamp(), 'ACTIVE', '$2a$10$2mUbl3XY3kH5z7CyMJr1Tuevb1dMXm4VF01EP4kOqNXv2CTlvot6q', 1, b'1', 'usman');

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1);


UPDATE `permissions` SET `display_menu` = '' WHERE (`id` = '26');
UPDATE `permissions` SET `display_menu` = '' WHERE (`id` = '23');
UPDATE `permissions` SET `display_menu` = '/dashboard' WHERE (`id` = '1');
UPDATE `permissions` SET `display_menu` = '/regions' WHERE (`id` = '2');
UPDATE `permissions` SET `display_menu` = '/addregion' WHERE (`id` = '3');
UPDATE `permissions` SET `display_menu` = '/editregion' WHERE (`id` = '4');
UPDATE `permissions` SET `display_menu` = '/agencies' WHERE (`id` = '5');
UPDATE `permissions` SET `display_menu` = '/addagency' WHERE (`id` = '6');
UPDATE `permissions` SET `display_menu` = '/editagency' WHERE (`id` = '7');
UPDATE `permissions` SET `display_menu` = '/employees' WHERE (`id` = '8');
UPDATE `permissions` SET `display_menu` = '/addemployee' WHERE (`id` = '9');
UPDATE `permissions` SET `display_menu` = '/editemployee' WHERE (`id` = '10');
UPDATE `permissions` SET `display_menu` = '/teams' WHERE (`id` = '11');
UPDATE `permissions` SET `display_menu` = '/addteam' WHERE (`id` = '12');
UPDATE `permissions` SET `display_menu` = '/editteam' WHERE (`id` = '13');
UPDATE `permissions` SET `display_menu` = '/partners' WHERE (`id` = '14');
UPDATE `permissions` SET `display_menu` = '/addpartner' WHERE (`id` = '15');
UPDATE `permissions` SET `display_menu` = '/editpartner' WHERE (`id` = '16');
UPDATE `permissions` SET `display_menu` = '/roles' WHERE (`id` = '17');
UPDATE `permissions` SET `display_menu` = '/addrole' WHERE (`id` = '18');
UPDATE `permissions` SET `display_menu` = '/editrole' WHERE (`id` = '19');

UPDATE `permissions` SET `display_menu` = '/myprofile' WHERE (`id` = '24');

INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES ('1', '27');

INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES ('1', '26');
update permissions set parent_id = 1 where id in (2,5,8,11, 14);

--INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, 30);

update permissions set display_order = 22, name='Calls' where permission = 'call';

update permissions set icon = 'cil-phone' where name = "CRM";

INSERT INTO permissions (id, flag, display_menu, display_order, icon, name, permission, parent_id) VALUES ('32', 'ACTIVE', '/employeesRanking', '31', 'cil-settings', 'EmployeesRanking', 'employees_ranking', 23);
INSERT INTO roles_permissions (role_id,permission_id) VALUES (1, 32);

INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES (33,'ACTIVE','/products', 33, 'Products','list_products', NULL);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('34','ACTIVE', '/addproduct', '34', 'Add Product', 'add_product', 33);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('35','ACTIVE', '/editproduct', '35', 'Edit Product', 'edit_product', 33);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('36','ACTIVE', '/deleteproduct', '36', 'Delete Product', 'delete_product', 33);

INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `icon`, `name`, `permission`, `parent_id`) VALUES (45,'ACTIVE','/orders', 45, 'cil-file', 'Orders','list_orders', NULL);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('46','ACTIVE', '/addorder', '46', 'Add Order', 'add_order', 45);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('47','ACTIVE', '/editorder', '47', 'Edit Order', 'edit_order', 45);
INSERT INTO `permissions` (`id`, `flag`, `display_menu`, `display_order`, `name`, `permission`, `parent_id`) VALUES ('48','ACTIVE', '/deleteorder', '48', 'Delete Order', 'delete_order', 45);

update permissions set display_order = 2 where name = 'Orders';
update permissions set menu_url = null where permission in ('add_order', 'edit_order', 'delete_order');

INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 33);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 34);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 35);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 36);

INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 45);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 46);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 47);
INSERT INTO `roles_permissions` (`role_id`, `permission_id`) VALUES (1, 48);