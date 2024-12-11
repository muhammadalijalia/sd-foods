insert into
	product_stock (
	product_id,
	quantity_out, 
	quantity_in, 
	created_on, 
	updated_on , 
	created_by, 
	transaction_type, 
	prestashop_product_id,
	order_id 
)
select *  from (
	select 
		p.id , 
		pop.product_quantity, 
		null, 
		date(po.date_added) date_, 
		date(po.date_updated) date_updated, 
		'system', 
		'SOLD' transaction_type,
		pop.product_id,
		po.prestashop_order_id 
	from prestashop_orders po 
	inner join prestashop_order_products pop on po.prestashop_order_id = pop.prestashop_order_id 
	left join products p on pop.product_id = p.prestashop_product_id  where po.order_current_status in (31,32)
	union all
	select 
		op.product_id, 
		null, 
		case when received_number_of_units is null then received_quantity else received_number_of_units end,
		date(opl.created_on) date_, 
		date(opl.updated_on) date_updated,
		'system', 
		'INVENTORY' transaction_type,
		p.prestashop_product_id,
		o.id
	from order_product_locations opl
	join order_products op
	on op.id = opl.order_product_id
	join orders o
	on op.order_id = o.id
	join products p 
	on op.product_id = p.id 
	and o.flag <> 'DELETED'
	union all
	select 
		op.product_id, 
		null, 
		case 
			when opl.damaged_quantity is null then 
			coalesce(opl.damage_number_of_units, 0)  
			else coalesce(opl.damaged_quantity,0) 
		end ,
		date(opl.created_on) date_, 
		date(opl.updated_on) date_updated,
		'system', 
		'DAMAGED_INVENTORY' transaction_type,
		p.prestashop_product_id,
		o.id 
	from order_product_locations opl
	join order_products op
	on op.id = opl.order_product_id
	join orders o
	on op.order_id = o.id
	join products p 
	on op.product_id = p.id 
	and o.flag <> 'DELETED'
	and (coalesce(opl.damaged_quantity, 0) <> 0
	or coalesce(opl.damage_number_of_units,0) <> 0)
) a