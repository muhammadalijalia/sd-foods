update order_product_locations opl 
inner join order_products op on opl.order_product_id = op.id 
set opl.product_id = op.product_id 