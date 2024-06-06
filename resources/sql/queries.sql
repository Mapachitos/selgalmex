-- :name get-cede :? :1
-- :doc retrieves 1 cede
SELECT * FROM cedes
WHERE id = :id;

-- :name get-cedes :? :*
-- :doc retrieves all cedes
SELECT * FROM cedes;

-- :name create-cede! :! :n
-- :doc creates a new cede
INSERT INTO cedes
(nombre, ubicacion, responsable, selgalmexid, saderid, telefono, titulo)
VALUES (:nombre, :ubicacion, :responsable, :selgalmexid, :saderid, :telefono, :titulo);

-- :name update-cede! :! :n
-- :doc updates an existing cede record
UPDATE cedes
SET nombre = :nombre,
 ubicacion = :ubicacion,
 responsable = :responsable, 
 selgalmexID = :selgalmexid, 
 saderID = :saderid, 
 telefono = :telefono, 
 titulo = :titulo
WHERE id = :id;

-- :name delete-cede! :! :n
-- :doc deletes a cede record given the id
DELETE FROM cedes
WHERE id = :id;

------------------------------ ENTREGAS --------------------------------

-- :name get-entregas :? :*
-- :doc retrieves all entregas
SELECT * FROM entregas;

-- :name get-entregas-id :? :*
-- :doc retrieves all entregas
SELECT * FROM entregas WHERE cedeid = :cedeid;

-- :name create-entrega! :! :n
-- :doc creates a new entrega
INSERT INTO entregas
(estado, municipio, localidad, nombres, apaterno, amaterno, curp, ine, curpfile, inefile, fecha, estatusentrega, cedeid)
VALUES (:estado, :municipio, :localidad, :nombres, :apaterno, :amaterno, :curp, :ine, 'Si', 'Si', :fecha, :estatusentrega, :cedeid);

-- :name delete-entrega! :! :n
-- :doc deletes a entrega record given the id
DELETE FROM entregas
WHERE id = :id;

-- :name update-entrega! :! :n
-- :doc updates an existing entrega record
UPDATE entregas
SET estado = :estado,
    municipio = :municipio,
    localidad = :localidad, 
    nombres = :nombres,
    apaterno = :apaterno,
    amaterno = :amaterno,
    curp = :curp,
    ine = :ine,
    curpfile = 'Si',
    inefile = 'Si',
    fecha = :fecha,
    estatusentrega = :estatusentrega,
    cedeid = :cedeid
    WHERE id = :id;

-- :name update-entrega-estatus! :! :n
-- :doc updates an existing entrega record
UPDATE entregas
SET estatusentrega = :flagestatusentrega
    WHERE id = :id;

-- :name update-entrega-ine! :! :n
-- :doc updates an existing entrega record
UPDATE entregas
SET ine = :flagine
    WHERE id = :id;
