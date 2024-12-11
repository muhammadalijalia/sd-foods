DELIMITER //
	CREATE DEFINER=`root`@`localhost` TRIGGER `INVENTORY_STOCK_TOTAL_QUANTITY` AFTER INSERT ON `order_product_locations` 
		FOR EACH ROW begin
			insert into product_stock (
				product_id, 
				lot_number, 
				expiry_date, 
				quantity_in, 
				transaction_type, 
				created_on,
				prestashop_product_id
			)
		           (select 
		           		op.product_id, 
		           		opl.lot_number, 
		           		opl.expiry, 
		           		case when opl.received_quantity is null then opl.received_number_of_units else opl.received_quantity end quantity,
		           		'INVENTORY', 
		           		opl.created_on,
		           		p.prestashop_product_id
					from order_product_locations opl 
					inner join order_products op ON opl.order_product_id = op.id
					inner join products p on p.id = op.product_id 
					inner join orders o on o.id = op.order_id 
					and o.status = 'STOCKED'
					and opl.id = new.id 
					);
    end //