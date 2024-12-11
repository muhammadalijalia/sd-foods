set @id = (select id from permissions where permission = 'reports');
UPDATE permissions
SET parent_id = @id
WHERE id=79;