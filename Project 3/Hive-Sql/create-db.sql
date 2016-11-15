CREATE TABLE masterRaw (
vehicle_id int,
vehicle_x double,
vehicle_y double,
vehicle_angle double,
vehicle_type string,
vehicle_speed double,
vehicle_pos double,
vehicle_lane string,
vehicle_slope double,
person_id int,
person_x double,
person_y double,
person_angle double, 
person_speed double,
person_pos double,
person_slope double
) 
row format delimited fields terminated by '\073' stored as textfile 
tblproperties("skip.header.line.count"="1");