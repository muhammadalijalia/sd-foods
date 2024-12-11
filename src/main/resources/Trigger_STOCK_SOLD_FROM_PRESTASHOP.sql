DELIMITER //
CREATE DEFINER=`root`@`localhost` TRIGGER `STOCK_SOLD` AFTER UPDATE ON `prestashop_orders` FOR EACH ROW BEGIN
   IF (NEW.order_current_status = 31 OR NEW.order_current_status = 32) then
	     insert into product_stock (
		     product_id, 
		     quantity_out, 
		     transaction_type, 
		     created_on, 
		     updated_on,
		     prestashop_product_id
	     ) 
         select 
	         p.id, 
	         pop.product_quantity, 
	         ('SOLD'), 
	         po.date_added, 
	         po.date_updated,
	         pop.product_id 
         from prestashop_orders po         	
    		join prestashop_order_products pop 
    		on pop.prestashop_order_id = po.prestashop_order_id 
    		left join products p 
    		on p.prestashop_product_id = pop.product_id  
		  where po.prestashop_order_id = NEW.prestashop_order_id ;		      
      END IF;
    end //