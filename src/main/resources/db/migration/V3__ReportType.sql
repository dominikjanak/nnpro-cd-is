ALTER TABLE report DROP COLUMN description;

ALTER TABLE report
ADD COLUMN `type` INT NOT NULL AFTER filename;