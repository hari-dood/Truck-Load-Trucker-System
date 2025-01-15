-- V2__Create_trip_table.sql

CREATE TABLE data_backup (
    trip_sno SERIAL PRIMARY KEY, -- Auto-incremented primary key
    driver_name VARCHAR(50) NOT NULL, -- Driver's name, up to 50 characters
    month VARCHAR(20) NOT NULL, -- Month, up to 20 characters
    loading_date DATE NOT NULL, -- Loading date
    trip_start_date DATE NOT NULL, -- Trip start date
    trip_end_date DATE NOT NULL, -- Trip end date
    payment_status VARCHAR(20) NOT NULL, -- Payment status, up to 20 characters
    loading_point VARCHAR(50) NOT NULL, -- Loading point, up to 50 characters
    delivery_point VARCHAR(50) NOT NULL, -- Delivery point, up to 50 characters
    load_quantity VARCHAR(30) NOT NULL, -- Load quantity, up to 30 characters
    goods_description VARCHAR(100) NOT NULL, -- Description of goods, up to 100 characters
    load_amount INTEGER NOT NULL, -- Load amount
    load_advance INTEGER NOT NULL, -- Load advance amount
    weight_bridge INTEGER NOT NULL, -- Weight bridge amount
    broker_amount INTEGER NOT NULL, -- Broker amount
    up_amount INTEGER NOT NULL, -- Up amount
    down_amount INTEGER NOT NULL, -- Down amount
    toll_amount INTEGER NOT NULL, -- Toll amount
    diesel_expenses INTEGER NOT NULL, -- Diesel expenses
    rto_amount INTEGER NOT NULL, -- RTO amount
    pc_amount INTEGER NOT NULL, -- PC amount
    others_expenses INTEGER NOT NULL, -- Other expenses
    total_expenses INTEGER NOT NULL -- Total expenses
);
