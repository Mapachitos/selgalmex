(ns selgalmex.routes.home
  (:require
   [selgalmex.layout :as layout]
   [selgalmex.db.core :as db]
   [clojure.java.io :as io]
   [selgalmex.middleware :as middleware]
   [ring.util.response :refer [redirect]]
   [ring.util.http-response :as response]))

;HOME PAGE
"Renderiza la página de inicio.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Renderiza la página 'home.html' con los siguientes datos:
    * entregas: Lista de entregas obtenida de la base de datos.
    * cedes: Lista de cedes obtenida de la base de datos.
    * estados: Lista de estados obtenida de la base de datos.
    * municipios: Lista de municipios obtenida de la base de datos.
    * localidades: Lista de localidades obtenida de la base de datos."
(defn home-page [request]
  (layout/render request "home.html" {
    :entregas (db/get-entregas) 
    :cedes (db/get-cedes) 
    :estados (db/get-estados) 
    :municipios (db/get-municipios) 
    :localidades (db/get-localidades)
    }))

;CEDES PAGE
"Renderiza la página de cedes.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Renderiza la página 'cedes.html' con la lista de cedes obtenida de la base de datos."
(defn cedes-page [request]
  (layout/render request "cedes.html" {:cedes (db/get-cedes)}))

;ENTREGAS PAGE
"Renderiza la página de entregas.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Renderiza la página 'entregas.html' con la lista de entregas y cedes obtenidas de la base de datos."
(defn entregas-page [request]
  (layout/render request "entregas.html" {:entregas (db/get-entregas) :cedes (db/get-cedes)}))

;CREATE CEDE
"Crea un nuevo registro de CEDE en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de cedes en caso de éxito."
(defn create-cede! [request]
  (let [params (:params request)]
    (db/create-cede! params)
    (redirect "/cedes"))) 

;UPDATE CEDE
"Actualiza un registro de la tabla CEDE en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de cedes en caso de éxito.
  - Redirige a una página de error en caso de fallo o parámetros inválidos."
(defn update-cede! [request]
  (let [params (:params request)
        cede-id-str (:id params)]
    (if (and (some? cede-id-str) (not-empty cede-id-str))
      (let [cede-id (try (Integer/parseInt cede-id-str)
                          (catch NumberFormatException e nil))]
        (if (some? cede-id)
          (do
            (db/update-cede! (assoc params :id cede-id))
            (redirect "/cedes"))
          (redirect "/error")))
      (redirect "/error"))))

;DELETE CEDE
"Elimina un registro de CEDE de la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de cedes en caso de éxito."
(defn delete-cede! [request]
  (let [params (:params request)]
    (db/delete-cede! params)
    (redirect "/cedes"))) 

;CREATE ENTREGA
"Crea un nuevo registro de ENTREGA en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de entregas en caso de éxito."
(defn create-entrega! [request]
  (let [params (:params request)]
    (db/create-entrega! params)
    (redirect "/entregas"))) 

;DELETE ENTREGA
"Elimina un registro de ENTREGA de la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de cedes en caso de éxito."
(defn delete-entrega! [request]
  (let [params (:params request)]
    (db/delete-entrega! params)
    (redirect "/cedes"))) 

;No funciona bien xd - DELETE ENTREGA
"(defn delete-entrega! [request]
  (let [params (:params request)
        cedeid (get-in request [:params :cedeid])
        _ (db/delete-entrega! params)
        entregas (db/get-entregas-id {:cedeid cedeid})]
    (layout/render request 'entregas.html' {:entregas entregas})))"

;UPDATE ENTREGA
"Actualiza un registro de la tabla ENTREGA en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de entregas en caso de éxito.
  - Redirige a una página de error en caso de fallo o parámetros inválidos."
(defn update-entrega! [request]
  (let [params (:params request)
        entrega-id-str (:id params)]
    (if (and (some? entrega-id-str) (not-empty entrega-id-str))
      (let [entrega-id (try (Integer/parseInt entrega-id-str)
                          (catch NumberFormatException e nil))]
        (if (some? entrega-id)
          (do
            (db/update-entrega! (assoc params :id entrega-id))
            (redirect "/entregas"))
          (redirect "/error")))
      (redirect "/error"))))

;ENTREGAS CEDE
"Renderiza la página de entregas para un cede específico.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Renderiza la página 'entregas.html' con las entregas asociadas al cede especificado e
    informacion de las cedes."
(defn entregas-id-page [request]
  (let [cedeid (get-in request [:params :id])  ;; Recupera :id desde :params
        entregas (db/get-entregas-id {:cedeid cedeid})]
    (layout/render request "entregas.html" {:entregas entregas :cedes (db/get-cedes)})))

;UPDATE ENTREGA ESTATUS
"Actualiza el estatus de una entrega en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de entregas en caso de éxito.
  - Redirige a una página de error en caso de fallo o parámetros inválidos."
(defn update-entrega-estatus! [request]
  (let [params (:params request)
        entrega-id-str (:id params)]
    (if (and (some? entrega-id-str) (not-empty entrega-id-str))
      (let [entrega-id (try (Integer/parseInt entrega-id-str)
                          (catch NumberFormatException e nil))]
        (if (some? entrega-id)
          (do
            (db/update-entrega-estatus! (assoc params :id entrega-id))
            (redirect "/entregas"))
          (redirect "/error")))
      (redirect "/error"))))

;UPDATE ENTREGA INE
"Actualiza el archivo INE de una entrega en la base de datos.

  Parámetros:
  - request: Mapa que representa la solicitud HTTP recibida.

  Retorna:
  - Redirige a la lista de entregas en caso de éxito.
  - Redirige a una página de error en caso de fallo o parámetros inválidos."
(defn update-entrega-ine! [request]
  (let [params (:params request)
        entrega-id-str (:id params)]
    (if (and (some? entrega-id-str) (not-empty entrega-id-str))
      (let [entrega-id (try (Integer/parseInt entrega-id-str)
                          (catch NumberFormatException e nil))]
        (if (some? entrega-id)
          (do
            (db/update-entrega-ine! (assoc params :id entrega-id))
            (redirect "/entregas"))
          (redirect "/error")))
      (redirect "/error"))))

;HOME ROUTES
"Define las rutas para la aplicación.

  Retorna:
  - Lista de rutas definidas para la aplicación, junto con los controladores correspondientes."
(defn home-routes []
  [""
    {:middleware [middleware/wrap-csrf
                  middleware/wrap-formats]}
    ["/" {:get home-page}]
    ["/cedes" {:get cedes-page}]
    ["/entregas" {:get entregas-page}]
    ;CEDES
    ["/create-cede" {:post create-cede!}]
    ["/update-cede" {:post update-cede!}]
    ["/delete-cede" {:post delete-cede!}]
    ;ENTREGAS
    ["/create-entrega" {:post create-entrega!}]
    ["/delete-entrega" {:post delete-entrega!}]
    ["/update-entrega" {:post update-entrega!}]
    ;ENTREGAS-CEDE
    ["/entregas/:id" {:post entregas-id-page}]
    ["/update-entrega-estatus" {:post update-entrega-estatus!}]
    ["/update-entrega-ine" {:post update-entrega-ine!}]
   ])

