DROP TABLE IF EXISTS judgements;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE judgements
(
  id             INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name           VARCHAR,
  email          VARCHAR,
  adress         VARCHAR,
  phone          VARCHAR,
  url            VARCHAR NOT NULL,
  schedule_MON_TH VARCHAR,
  schedule_FR    VARCHAR,
  schedule_BREAK VARCHAR
);
