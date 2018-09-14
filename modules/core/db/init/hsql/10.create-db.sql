-- begin MAPSDEMO_SHOP
create table MAPSDEMO_SHOP (
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
    LOCATION VARCHAR(100),
    --
    primary key (ID)
)^
-- end MAPSDEMO_SHOP
