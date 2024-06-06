(ns selgalmex.routes.home
  (:require
   [selgalmex.layout :as layout]
   [selgalmex.db.core :as db]
   [clojure.java.io :as io]
   [selgalmex.middleware :as middleware]
   [ring.util.response :refer [redirect]]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html" {:cedes (db/get-cedes)}))

(defn cedes-page [request]
  (layout/render request "cedes.html" {:cedes (db/get-cedes)}))

(defn entregas-page [request]
  (layout/render request "entregas.html" {:entregas (db/get-entregas)}))

;CREATE CEDE
(defn create-cede! [request]
  (let [params (:params request)]
    (db/create-cede! params)
    (redirect "/cedes"))) 

;UPDATE CEDE
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
(defn delete-cede! [request]
  (let [params (:params request)]
    (db/delete-cede! params)
    (redirect "/cedes"))) 

;CREATE ENTREGA
(defn create-entrega! [request]
  (let [params (:params request)]
    (db/create-entrega! params)
    (redirect "/entregas"))) 

;DELETE ENTREGA
(defn delete-entrega! [request]
  (let [params (:params request)]
    (db/delete-entrega! params)
    (redirect "/cedes"))) 

;No funciona bien xd
;(defn delete-entrega! [request]
;  (let [params (:params request)
;        cedeid (get-in request [:params :cedeid])
;        _ (db/delete-entrega! params)
;        entregas (db/get-entregas-id {:cedeid cedeid})]
;    (layout/render request "entregas.html" {:entregas entregas})))

;UPDATE ENTREGA
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
(defn entregas-id-page [request]
  (let [cedeid (get-in request [:params :id])  ;; Recupera :id desde :params
        entregas (db/get-entregas-id {:cedeid cedeid})]
    (layout/render request "entregas.html" {:entregas entregas})))

;UPDATE ENTREGA ESTATUS
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

