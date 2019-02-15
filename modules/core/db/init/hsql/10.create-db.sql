-- begin MAPSDEMO_ORDER
create table MAPSDEMO_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    AMOUNT double precision,
    DATE_ date,
    SALESPERSON_ID varchar(36),
    LOCATION VARCHAR(100),
    --
    primary key (ID)
)^
-- end MAPSDEMO_ORDER
-- begin MAPSDEMO_TERRITORY
create table MAPSDEMO_TERRITORY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    POLYGON VARCHAR(4000),
    --
    primary key (ID)
)^
-- end MAPSDEMO_TERRITORY
-- begin MAPSDEMO_SALESPERSON
create table MAPSDEMO_SALESPERSON (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    PHONE varchar(255),
    LOCATION VARCHAR(100),
    TERRITORY_ID varchar(36),
    --
    primary key (ID)
)^
-- end MAPSDEMO_SALESPERSON
