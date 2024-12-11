set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Back Office',2);
set @id = (select max(id) + 1 from jobs);
INSERT INTO `jobs` VALUES (@id,'admin',NULL,'ACTIVE',NULL,NULL,NULL,_binary '','Commercial Junior',1);
