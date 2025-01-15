-- Create DriverInfo table
CREATE TABLE driver_info (
    id INT  PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15) NOT NULL UNIQUE,
    experience VARCHAR(255) NOT NULL,
    licence_no VARCHAR(20) NOT NULL UNIQUE,
    aadhaar_card_no VARCHAR(16) NOT NULL UNIQUE,
    native_place VARCHAR(100) NOT NULL
);

-- Create TripDetails table (assuming this matches your trip data structure)
CREATE TABLE trip_details (
    id INT  PRIMARY KEY,
    month VARCHAR(20) NOT NULL,
    loading_date DATE NOT NULL,
    trip_start_date DATE NOT NULL,
    trip_end_date DATE NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    loading_point VARCHAR(50) NOT NULL,
    delivery_point VARCHAR(50) NOT NULL,
    load_quantity VARCHAR(30) NOT NULL,
    goods_description VARCHAR(100) NOT NULL,
    load_amount INT NOT NULL,
    load_advance INT NOT NULL,
    weight_bridge INT NOT NULL,
    broker_amount INT NOT NULL,
    up_amount INT NOT NULL,
    down_amount INT NOT NULL,
    toll_amount INT NOT NULL,
    diesel_expenses INT NOT NULL,
    rto_amount INT NOT NULL,
    pc_amount INT NOT NULL,
    others_expenses INT NOT NULL,
    total_expenses INT NOT NULL,
    driver_id INT NOT NULL,
    FOREIGN KEY (driver_id) REFERENCES driver_info(id)
);

-- You may need to adjust the types and lengths as needed based on your requirements and database.
