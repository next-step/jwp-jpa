INSERT INTO station (id, created_date, modified_date, name) VALUES (1, NOW(), NOW(), '당산역');
INSERT INTO station (id, created_date, modified_date, name) VALUES (2, NOW(), NOW(), '홍대입구역');
INSERT INTO station (id, created_date, modified_date, name) VALUES (3, NOW(), NOW(), '여의도역');

INSERT INTO line (id, created_date, modified_date, color, name) VALUES (1, NOW(), NOW(), '#009D3E', '2호선');
INSERT INTO line (id, created_date, modified_date, color, name) VALUES (2, NOW(), NOW(), '#BDB092', '9호선');

INSERT INTO line_station (line_id, station_id, previous_station_id, distance, created_date, modified_date)
VALUES (1, 1, null, 0, NOW(), NOW());
INSERT INTO line_station (line_id, station_id, previous_station_id, distance, created_date, modified_date)
VALUES (2, 1, null, 0, NOW(), NOW());
INSERT INTO line_station (line_id, station_id, previous_station_id, distance, created_date, modified_date)
VALUES (1, 2, 1, 10, NOW(), NOW());
INSERT INTO line_station (line_id, station_id, previous_station_id, distance, created_date, modified_date)
VALUES (2, 3, 1, 8, NOW(), NOW());

INSERT INTO member (id, email, password, age, created_date, modified_date) VALUES (1, 'qwer@google.com', '1234', 20, NOW(), NOW());
INSERT INTO favorite (id, member_id, departure_id, destination_id, created_date, modified_date) VALUES (1, 1, 1, 2, NOW(), NOW());