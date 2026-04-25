CREATE TABLE IF NOT EXISTS phones (
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    type              TEXT    NOT NULL,
    brand             TEXT    NOT NULL,
    model             TEXT    NOT NULL,
    price             REAL    NOT NULL,
    storage           INTEGER NOT NULL,
    operating_system  TEXT,
    satellite_network TEXT,
    is_cordless       INTEGER,
    has_flashlight    INTEGER
);
