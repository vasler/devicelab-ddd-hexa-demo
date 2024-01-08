CREATE TABLE phone_type (
    phone_type_id varchar(255) PRIMARY KEY,
    name varchar(255) NOT NULL,
    version integer
);

CREATE TABLE tester (
    tester_id varchar(255) PRIMARY KEY,
    version integer
);

CREATE TABLE phone (
    phone_id uuid PRIMARY KEY,
    phone_type_id varchar(255) NOT NULL,
    available boolean NOT NULL,
    reserved_on timestamp,
    reserved_by varchar(255),
    version integer,

    CONSTRAINT fk_phone_type FOREIGN KEY(phone_type_id) REFERENCES phone_type(phone_type_id),
    CONSTRAINT fk_tester FOREIGN KEY(reserved_by) REFERENCES tester(tester_id)
);
CREATE INDEX phone__available__idx ON phone(available);
CREATE INDEX phone__phone_type_id__idx ON phone(phone_type_id);