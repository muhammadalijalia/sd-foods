DELIMITER //
    CREATE DEFINER=`root`@`localhost` TRIGGER `MAP_PRESTASHOP_PRODUCTID_TO_STOCK` AFTER UPDATE ON `products` FOR EACH ROW begin
	    IF (new.prestashop_product_id is not null) then
	    	update product_stock ps
	    	set ps.product_id  = new.id
	    	where ps.prestashop_product_id = new.prestashop_product_id;
	    	update product_stock ps
	    	set ps.prestashop_product_id = new.prestashop_product_id
	    	where ps.product_id = new.id;
	    end if;
	 end //