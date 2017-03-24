CREATE DATABASE java_sample_app_db;

CREATE USER sample_app WITH password '*******';

GRANT ALL privileges ON DATABASE java_sample_app_db TO sample_app;
\connect java_sample_app_db
CREATE SEQUENCE sample_ids;

CREATE TABLE samples (id INTEGER PRIMARY KEY DEFAULT NEXTVAL('sample_ids'), word CHAR(64));
GRANT ALL ON samples to sample_app;
INSERT INTO samples (word) VALUES
	('testword1'),
	('testword2'),
	('testword3');
