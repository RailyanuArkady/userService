ALTER TABLE passports
ALTER COLUMN modified_at SET NOT NULL;


ALTER TABLE users
ALTER COLUMN modified_at SET NOT NULL;

ALTER TABLE users ADD COLUMN sex_tmp varchar(12);

UPDATE users SET sex_tmp = sex;

ALTER TABLE users DROP COLUMN sex;

ALTER TABLE users ADD COLUMN sex varchar(12);

UPDATE users SET sex = sex_tmp;

ALTER TABLE users DROP COLUMN sex_tmp;
