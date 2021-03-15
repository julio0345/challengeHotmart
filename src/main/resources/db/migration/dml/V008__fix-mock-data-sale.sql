/* Just fixing the date of sale of products that was created after the date sale */

update sale 
	set sale_date = '2020-09-01 12:00:53' 
where 
	id in (select * from ( select 
								s.id
							from 
								product p
							    inner join sale s on p.id = s.id_product
							where 
								p.creation_date > s.sale_date 
						) as X);