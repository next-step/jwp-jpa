INSERT INTO station (id, created_date, modified_date, name) VALUES (1, NOW(), NOW(), '당산역');
INSERT INTO station (id, created_date, modified_date, name) VALUES (2, NOW(), NOW(), '홍대입구역');
INSERT INTO station (id, created_date, modified_date, name) VALUES (3, NOW(), NOW(), '여의도역');

INSERT INTO line (id, created_date, modified_date, color, name) VALUES (1, NOW(), NOW(), '#009D3E', '2호선');
INSERT INTO line (id, created_date, modified_date, color, name) VALUES (2, NOW(), NOW(), '#BDB092', '9호선');

INSERT INTO line_stations (lines_id, stations_id) VALUES (1, 1);
INSERT INTO line_stations (lines_id, stations_id) VALUES (2, 1);
INSERT INTO line_stations (lines_id, stations_id) VALUES (1, 2);
INSERT INTO line_stations (lines_id, stations_id) VALUES (2, 3);