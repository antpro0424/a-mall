CREATE KEYSPACE IF NOT EXISTS order_keyspace
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 1
        };
