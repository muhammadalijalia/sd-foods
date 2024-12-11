DELIMITER //
CREATE DEFINER=`root`@`localhost` TRIGGER `INVENTORY_STOCK_DAMAGED_QUANTITY` AFTER INSERT ON `order_product_locations` 
	FOR EACH ROW BEGIN
	    if (new.damaged_quantity is not null or new.damage_number_of_units is not null) then  
		    insert into product_stock (
			    product_id, 
			    lot_number, 
			    expiry_date , 
			    quantity_in, 
			    transaction_type, 
			    created_on,
			    prestashop_product_id
		    )
		           (
		           select 
			           op.product_id, 
			           opl.lot_number, 
			           opl.expiry , 
			           case 
				           when opl.damage_number_of_units is null then opl.damaged_quantity 
				           	else opl.damage_number_of_units end, 
			           'DAMAGED INVENTORY', opl.created_on,
			           p.prestashop_product_id 
					from order_product_locations opl 
					inner join order_products op ON opl.order_product_id = op.id
					inner join products p on op.product_id = p.id
					inner join orders o on o.id = op.order_id 
					and o.status = 'STOCKED'
					and opl.id = new.id 
					);
			end if;
      end //