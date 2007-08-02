(jde-project-file-version "1.0")
(setq jde-current-project-basedir
   (find-project-basedir (expand-file-name ".")))
(jde-set-variables
 '(jde-project-name "QueryJ")
 '(jde-project-file-name "prj.el")
 '(jde-import-excluded-classes
   (quote
    (("^bsh\\..*")
     ("^sun\\..*")
     ("^com\\.sun\\..*")
     (jde-import-current-package-p . t))))
 '(jde-compile-option-directory
   (concat jde-current-project-basedir "target/classes"))
 '(jde-global-classpath
   (cons
     jde-compile-option-directory
     (complete-classpath
      (concat
       jde-current-project-basedir
       "dependencies/lib/java"))))
 '(jde-javadoc-gen-window-title " -  ()")
 '(jde-sourcepath
   (list
    (complete-sourcepath
     (concat jde-current-project-basedir
             "src/main/java"))))
 '(jde-import-excluded-packages '"target.*")
 '(jde-docindex-project-alist '("QueryJ"))
 '(jde-db-source-directories jde-sourcepath)
 '(jde-docindex-dest-directory
   (concat jde-current-project-basedir "target/docs/apidocs"))
 '(bsh-startup-directory jde-current-project-basedir)
 '(bsh-classpath jde-global-classpath))
(setq jtags-javadoc-root jde-docindex-dest-directory)
(setq jde-lib-directory-names
   (list
    (concat jde-current-project-basedir "dependencies/lib/java/*.jar")
    (concat jde-current-project-basedir "dependencies/lib/java/build/*.jar")
    (concat jde-current-project-basedir "dependencies/lib/java/runtime/*.jar")
    (concat jde-current-project-basedir "dependencies/lib/java/development/*.jar")
    (concat jde-current-project-basedir "dependencies/lib/java/unittest/*.jar")))

;;(make-symbolic-link "./src/toolconf/maven/project.xml" "./project.xml" "ignore")
;;(require 'pom)
;;(custom-set-variables
;; '(pom-project-file-name "./src/toolconf/maven/project.xml"))
;;(let ((pom (pom-read-pom)))
;;  (jde-project-file-version "1.0")
;;  (jde-set-variables
;;   '(jde-javadoc-gen-destination-directory "./target/docs/apidocs")
;;   '(jde-project-name (pom-get-project-id pom))
;;   '(jde-global-classpath (pom-get-classpath pom))))

