CREATE TABLE entregas (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    estado VARCHAR(255),
    municipio VARCHAR(255),
    localidad VARCHAR(255),
    nombres VARCHAR(255),
    apaterno VARCHAR(255),
    amaterno VARCHAR(255),
    curp VARCHAR(255),
    ine VARCHAR(255),
    curpfile VARCHAR(255),
    inefile VARCHAR(255),
    fecha VARCHAR(255),
    estatusentrega VARCHAR(255),
    cedeid INTEGER,
    FOREIGN KEY (cedeid) REFERENCES cedes (id) ON DELETE CASCADE
);