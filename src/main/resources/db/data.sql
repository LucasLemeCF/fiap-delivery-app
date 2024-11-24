INSERT INTO categories(idt_category, nam_category) VALUES
    (1, 'SNACK'),
    (2, 'SIDE-DISH'),
    (3, 'DRINK'),
    (4, 'DESSERT');

INSERT INTO products(des_product, flg_active, nam_product, num_price_product, idt_category) VALUES
    ('180G OF HAMBURGER SNACK WITH NOBLE MEAT', true, 'HAMBURGER SNACK', '30.00', 1),
    ('180G OF CHICKEN BURGER SNACK', true, 'CHICKEN HAMBURGER SNACK', '25.00', 1),
    ('310ML OF COCA-COLA SODA', true, 'COCA-COLA', '5.00', 3),
    ('510ML OF NATURAL WATER', true, 'WATER', '3.00', 3),
    ('180G OF FRENCH FRIES', true, 'FRENCH FRIES', '18.00', 2),
    ('180G OF ONION RINGS', true, 'ONION RINGS', '22.00', 2),
    ('100G OF PUDDING', true, 'PUDDING', '20.00', 4),
    ('100G OF ICE CREAM', true, 'ICE CREAM', '15.00', 4);