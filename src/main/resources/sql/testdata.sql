INSERT INTO client(id,name,address,address2,city_id,tel,mobile,email,btw_number)
VALUES (1,'VZW Spetserevents','Molenzijze 99',NULL,400,NULL,NULL,NULL,'BE0505.523.220');

INSERT INTO category(id,name) VALUES (1,'Consultancy');

INSERT INTO article(id, description, name, price, btw_percentage, unit, category_id)
VALUES (1, null, 'Consultancy', 450, 21, 'DAG', 1);
