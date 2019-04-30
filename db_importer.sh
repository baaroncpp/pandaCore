# When you have logged in to the server:


su postgres
cd /home/peter/panda-core/

psql postgres

# ... while in the postgres DB:

# Check if developers are connected:

SELECT
    usesysid
    , usename
    , datname
FROM pg_stat_activity
WHERE usename != 'peter';

# If not:

psql -d postgres -U naphlin

ALTER DATABASE solar_db_test
RENAME TO solar_db_test_old;

DROP DATABASE IF EXISTS solar_db_test;
CREATE DATABASE solar_db_test;

GRANT ALL ON DATABASE solar_db_test TO peter;
GRANT ALL ON DATABASE solar_db_test TO testdbuser;

# Log out (\q)

# After you have logged out:

# To restore the whole DB

psql --set ON_ERROR_STOP=on solar_db_test < /home/peter/panda-core/dbexport.pgsql

# Log in as donata:

psql -d solar_db_test -U peter -W

GRANT CONNECT ON DATABASE solar_db_test TO peter;

GRANT CONNECT ON DATABASE solar_db_test TO testdbuser;


ALTER DATABASE solar_db_test
SET search_path TO
    panda_core,
    public;

GRANT USAGE ON SCHEMA panda_core TO peter;
GRANT USAGE ON SCHEMA panda_core TO testdbuser;


GRANT SELECT ON ALL SEQUENCES IN SCHEMA panda_core TO peter;
GRANT SELECT ON ALL SEQUENCES IN SCHEMA panda_core TO testdbuser;

GRANT SELECT ON ALL TABLES IN SCHEMA panda_core TO peter;
GRANT SELECT ON ALL TABLES IN SCHEMA panda_core TO testdbuser;

SELECT * FROM information_schema.role_table_grants
WHERE grantee = 'peter' AND
TABLE_SCHEMA = 'panda_core';

\c postgres

ALTER DATABASE solar_db_test
SET search_path TO, panda_core;
