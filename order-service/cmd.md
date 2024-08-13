# Cassandra
```
cqlsh
DESCRIBE KEYSAPCES;
USE KEYSPACE;
DESCRIBE TABLES;

SELECT table_name FROM system_schema.tables WHERE keyspace_name = 'my_keyspace';
DROP TABLE keyspace_name.table_name;
```

# Docker
```
 docker volume ls
 docker volume rm -f volume_name
```