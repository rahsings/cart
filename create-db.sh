#!/bin/bash
set -e

# Ensure the database is created if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    SELECT 'CREATE DATABASE cart'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'cart')\gexec
EOSQL
