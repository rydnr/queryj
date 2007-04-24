(jde-project-file-version "1.0")
(jde-set-variables
 '(jde-project-name "QueryJ")
 '(jde-import-excluded-classes (quote (("^bsh\\..*") ("^sun\\..*") ("^com\\.sun\\..*") (jde-import-current-package-p . t))))
 '(jde-global-classpath (quote ("./target/classes" "./dependencies/lib/java" "./dependencies/lib/java/build" "./dependencies/lib/java/runtime" "./dependencies/lib/java/development" "./dependencies/lib/java/unittest")))
 '(jde-javadoc-gen-window-title " -  ()")
 '(jde-compile-option-directory "./target/classes")
 '(jde-lib-directory-names (quote ("./dependencies/lib/java" "./dependencies/lib/java/build" "./dependencies/lib/java/runtime" "./dependencies/lib/java/development" "./dependencies/lib/java/unittest")))
 '(jde-sourcepath (quote ("./src/main/java")))
 '(jde-project-file-name "prj.el")
 '(jde-import-excluded-packages (quote ("target.*")))
 '(jde-docindex-project-alist '("QueryJ"))
 '(jde-db-source-directories '("./src/main/java"))
 '(jde-docindex-dest-directory "./target/docs/apidocs"))
(setq jtags-javadoc-root "./target/docs/apidocs")
(make-symbolic-link "./src/toolconf/maven/project.xml" "./project.xml" "ignore")
(require 'pom)
(custom-set-variables
 '(pom-project-file-name "./src/toolconf/maven/project.xml"))
(let ((pom (pom-read-pom)))
  (jde-project-file-version "1.0")
  (jde-set-variables
   '(jde-javadoc-gen-destination-directory "./target/docs/apidocs")
   '(jde-project-name (pom-get-project-id pom))
   '(jde-global-classpath (pom-get-classpath pom))))


