--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Directeur général',2);
update jobs set name = 'Directeur général' where id = 1;

--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Directeur finance',2);
update jobs set name = 'Directeur finance' where id = 2;
update jobs set access_level_id = 2 where id = 2;

--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Directeur Administratif',2);
update jobs set name = 'Directeur Administratif' where id = 3;
update jobs set access_level_id = 2 where id = 3;

--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Assistant comptable',2);
update jobs set name = 'Assistant comptable' where id = 4;
update jobs set access_level_id = 2 where id = 4;

--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Agent Administratif',6);
update jobs set name = 'Agent Administratif' where id = 5;
update jobs set access_level_id = 6 where id = 5;

--set @id = (select max(id) + 1 from jobs);
--INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Agent commerciale',6);
update jobs set name = 'Agent commerciale' where id = 6;
update jobs set access_level_id = 6 where id = 6;

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Floor manager',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Gestionnaire de stock',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Head of logistics',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Préparateur de commande',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Réceptionniste',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Secrétaire de direction',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Controleur',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Chauffeur',6);

set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Cleaner',6);

update permissions set name = 'In transit' where permission = 'confirmed_orders';