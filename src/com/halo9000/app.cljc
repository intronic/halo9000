(ns com.halo9000.app
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]))

;; Saving this file will automatically recompile and update in your browser

#?(:clj (defn client-userinfo [userinfo]
          (-> userinfo
              (select-keys [:email
                            :email_verified
                            :family_name
                            :given_name
                            :locale
                            :name
                            :phone_number
                            :preferred_username
                            :sub
                            :updated_at]))))

(e/defn LoggedOut [ctx]
  (e/client
   (binding [dom/node js/document.body]
     (dom/h1 (dom/text "Welcome to Halo9000 App"))
     (dom/p (dom/a (dom/props {:href (:launch-uri ctx)})
                   (dom/text "Login"))))))

(e/defn LoggedIn [user ctx]
  (e/client
   (binding [dom/node js/document.body]
     (dom/h1 (dom/text (str "Welcome back to Halo9000 App")))
     (dom/p (dom/text "user:" (str user)))
     (dom/p (dom/a (dom/props {:href (:logout-ring-uri ctx)}) (dom/text "Logout"))
            (dom/text " | ") (dom/a (dom/props {:href (:logout-oidc-uri ctx)}) (dom/text "End Session"))))))

(e/defn Main [ring-request ctx]
  (e/server
   (if-let [user (some-> ring-request :com.halo9000.ring-oidc-session/userinfo client-userinfo)]
     (LoggedIn. user ctx)
     (LoggedOut. ctx))))
