/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

-- create car manufacture
insert into car_manufacturer (id, name) values(1,'Audi');
insert into car_manufacturer (id, name) values(2,'BMW');
insert into car_manufacturer (id, name) values(3,'Mercedes');
insert into car_manufacturer (id, name) values(4,'Toyota');
insert into car_manufacturer (id, name) values(5,'Nissan');
insert into car_manufacturer (id, name) values(6,'Honda');
insert into car_manufacturer (id, name) values(7,'Mazda');
insert into car_manufacturer (id, name) values(8,'Hyundai');
insert into car_manufacturer (id, name) values(9,'Renault');

-- create cars

insert into car (id, date_created, deleted, license_plate, seat_count, engine_type, manufacturer, car_status, convertible)
values (1, now(), false, 'ABC1234', 4, 'PETROL', 1, 'UNMAP', false);

insert into car (id, date_created, deleted, license_plate, seat_count, engine_type, manufacturer, car_status, convertible)
values (2, now(), false, 'XYZ1234', 4, 'ELECTRIC', 4, 'UNMAP', false);

insert into car (id, date_created, deleted, license_plate, seat_count, engine_type, manufacturer, car_status, convertible)
values (3, now(), false, 'IJK456', 2, 'ELECTRIC', 3, 'MAP', true);

insert into car (id, date_created, deleted, license_plate, seat_count, engine_type, manufacturer, car_status, convertible)
values (4, now(), false, 'PQR4567', 4, 'DIESEL', 8, 'MAP', false);


