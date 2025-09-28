
UPDATE users SET modified_at = CURRENT_TIMESTAMP WHERE modified_at IS NULL;
UPDATE passports SET modified_at = CURRENT_TIMESTAMP WHERE modified_at IS NULL;


ALTER TABLE users ALTER COLUMN modified_at SET NOT NULL;
ALTER TABLE passports ALTER COLUMN modified_at SET NOT NULL;