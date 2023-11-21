LOAD DATA INFILE './is_open/address_information.csv'
    INTO TABLE address_information
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@campus, @building_name_address, @load_name_address)
    set
        campus = @campus,
        building_name_address = @building_name_address,
        load_name_address = @load_name_address;

LOAD DATA INFILE './is_open/map_information.csv'
    INTO TABLE map_information
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@lat, @long)
    set
        latitude = @lat,
        longitude = @long;

LOAD DATA INFILE './is_open/operating_information.csv'
    INTO TABLE operating_information
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@close, @open, @break, @note)
    set
        close_at = @close,
        open_at = @open,
        break_day = @break,
        note = @note;

insert into user(created_at, last_modified_at, contact, nickname, password, role, username)
VALUES (now(), now(), '010-0000-0000', 'testAdmin', '1234', 'ROLE_ADMIN', 'testID');


LOAD DATA INFILE './is_open/place.csv'
    INTO TABLE place
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@address_id, @map_id, @operating_id, @user_id, @contact, @created_by, @las_modified_by, @name, @place_type, @description)
    set
        created_at = now(),
        last_modified_at = now(),
        created_by = 'testAdmin',
        last_modified_by = 'testAdmin',
        user_id = @user_id,
        address_information_id = @address_id,
        map_information_id = @map_id,
        operating_information_id = @operating_id,
        contact = @contact,
        name = @name,
        place_type = @place_type,
        description = @description;


LOAD DATA INFILE './is_open/mart.csv'
    INTO TABLE mart
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@id)
    set
        place_id = @id;

LOAD DATA INFILE './is_open/pc_cafe.csv'
    INTO TABLE pc_cafe
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@id)
    set
        place_id = @id;

LOAD DATA INFILE './is_open/cafe.csv'
    INTO TABLE cafe
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@id)
    set
        place_id = @id;

LOAD DATA INFILE './is_open/restaurant.csv'
    INTO TABLE restaurant
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@id, @food, @delivery, @pickup)
    set
        place_id = @id,
        food_type = @food,
        has_delivery_service = IF(@delivery, 1, 0),
        has_pick_up_service = IF(@pickup, 1, 0);

LOAD DATA INFILE './is_open/school_place.csv'
    INTO TABLE school_place
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@id, @talk, @eat, @typeable, @consent)
    set
        place_id = @id,
        talkable = @talk,
        eatable = @eat,
        typeable = @typeable,
        has_consent = IF(@consent, TRUE, FALSE);

LOAD DATA INFILE './is_open/image.csv'
    INTO TABLE image
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\r\n'
    IGNORE 1 ROWS
    (@place_id, @origin, @saved)
    set
        place_id = @place_id,
        original_name = @origin,
        saved_name = @saved;

delete from image where image_id = 127;

LOAD DATA INFILE './is_open/medical_place.csv'
    INTO TABLE medical_place
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '$\r\n'
    IGNORE 1 ROWS
    (@place_id, @medical_type)
    set
        place_id = @place_id,
        medical_type = @medical_type;

# LOAD DATA INFILE './is_open/notice.csv'
#     INTO TABLE notice
#     FIELDS TERMINATED BY ','
#     LINES TERMINATED BY '\r\n'
#     IGNORE 1 ROWS
#     (@title, @content)
#     set
#         title = @title,
#         content = @content;

