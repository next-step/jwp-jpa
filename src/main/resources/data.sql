INSERT INTO line (color, name) VALUES ('green', 'two');
INSERT INTO line (color, name) VALUES ('pink', 'eight');
INSERT INTO station (name) VALUES ('교대역');
INSERT INTO station (name) VALUES ('신도림역');
INSERT INTO station_line (station_id, line_id, distance) values (1,1,0);
INSERT INTO station_line (station_id, line_id, distance) values (2,1,0);
INSERT INTO station_line (station_id, line_id, distance) values (2,2,7);
