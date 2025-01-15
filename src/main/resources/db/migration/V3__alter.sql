-- Create a sequence for the driver_info table if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS driver_info_id_seq START 1;

-- Alter the driver_info table to use the sequence for the id column
ALTER TABLE driver_info ALTER COLUMN id SET DEFAULT nextval('driver_info_id_seq');

-- Create a sequence for the trip_details table if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS trip_details_id_seq START 1;

-- Alter the trip_details table to use the sequence for the id column
ALTER TABLE trip_details ALTER COLUMN id SET DEFAULT nextval('trip_details_id_seq');
