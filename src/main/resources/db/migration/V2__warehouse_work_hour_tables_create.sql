alter table box drop column city, drop column address;

DROP TABLE IF EXISTS working_schedule CASCADE;

DROP TABLE IF EXISTS warehouse CASCADE;

create table working_schedule(
    id UUID PRIMARY KEY,
    opening_time_seconds BIGINT,
    closing_time_seconds BIGINT,
    working_days varchar(255)
);

create table warehouse
(
    id UUID PRIMARY KEY,
    address VARCHAR(255),
    city VARCHAR(255),
    status varchar(50) check (status in ('OPEN', 'CLOSED', 'UNDER_MAINTENANCE')),
    working_schedule_id UUID,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    foreign key (working_schedule_id) references working_schedule(id)
)