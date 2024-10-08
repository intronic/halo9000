(ns com.halo9000.app
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            #?(:clj [com.halo9000.ring-oidc-session])))

;; Saving this file will automatically recompile and update in your browser

(e/defn LoggedOut [ctx]
  (e/client
  ;;  (println "> logged out: ctx=" ctx)
   (binding [dom/node js/document.body]
     (dom/h1 (dom/text "Welcome to Halo9000 App"))
     (dom/p (dom/a (dom/props {:href (:launch-uri ctx)})
                   (dom/text "Login"))))))

(e/defn LoggedIn [user ctx]
  (e/client
  ;;  (println "> logged in:" user ctx)
   (binding [dom/node js/document.body]
     (dom/h1 (dom/text (str "Welcome back to Halo9000")))
     (dom/p)
     (dom/p (dom/a (dom/props {:href (:logout-ring-uri ctx)}) (dom/text "Logout"))
            (dom/text " | ") (dom/a (dom/props {:href (:logout-oidc-uri ctx)}) (dom/text "End Session"))))))

(e/defn Main [ring-request ctx]
  (e/server
  ;;  (println "> server main user=" (:oidc/userinfo ring-request) " user:" (:com.halo9000.ring-oidc-session/userinfo ring-request) " keys=" (keys ring-request))
   (if-let [user (:com.halo9000.ring-oidc-session/userinfo ring-request)]
     (LoggedIn. user ctx)
     (LoggedOut. ctx))))
