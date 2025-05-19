## Anforderungen auf Linux (Ubuntu und ähnliche systeme):

- Installation von GraalVM: https://www.graalvm.org/latest/getting-started/linux/
- Installation benötigter Pakete für Native Image: `sudo apt-get install build-essential zlib1g-dev`
- Installation der OpenGL-API: `sudo apt install libgl1-mesa-glx`
- Installation von GLU: `sudo apt install libglu1-mesa-dev`
- Installation von GLUT (freeglut): `sudo apt install freeglut3-dev`
- Installation von AssImp: `sudo apt install assimp-utils libassimp-dev libassimp5`
- Installation von FTGL: `sudo apt install libftgl2`

## Anforderungen auf Windows:

- Installation von GraalVM: https://www.graalvm.org/latest/getting-started/windows/
- Installation der Visual Studio Build Tools und Windows SDK (für Native Image) https://www.graalvm.org/latest/getting-started/windows/#prerequisites-for-native-image-on-windows
- Repo klonen und folgende Umgebungsvariablen setzten, damit der Compiler/Linker die benötigten Bibliotheken findet:
  - Path: `<pfad zum repo>\OpenGLPolyglot\C Libraries Windows\bin\x64`
  - INCLUDE: `<pfad zum repo>\OpenGLPolyglot\C Libraries Windows\headers`
  - LIB: `<pfad zum repo>\OpenGLPolyglot\C Libraries Windows\lib\x64`
- Auf Windows funktioniert nur der Branch `legacy-opengl-compatibility`, daher auf diesen Branch switchen

## Installation der Java-Abhängigkeiten:

- Falls Euclid3DViewAPI und vecmath noch nicht installiert sind:
  - https://github.com/orat/Euclid3DViewAPI.git klonen und in NetBeans öffnen
  - vecmath.jar installieren https://jogamp.org/deployment/java3d/1.7.1-final/
  - Euclid3DViewAPI -> Dependencies -> vecmath-1.7.1.jar rechtsklicken -> Manually install artifact -> Pfad zu vecmath.jar eingeben und installieren
  - Euclid3DViewAPI rechtsklicken -> Build
- Falls ein Build-Error kommt, dass Euclid3DViewAPI nicht gefunden wird:
  - OpenGLPolyglot -> Dependencies -> Euclid3DViewAPI rechtsklicken -> Remove dependency
  - dann: OpenGLPolyglot -> Dependencies -> Add dependency -> Open Projects -> Euclid3DViewAPI

## Kompilieren und ausführen:

- In Netbeans auf *Run Project* klicken (dabei wird aber immer neu kompiliert, auch wenn es keine Änderungen gab)
- Oder manuell:
  - In Verzeichnis navigieren: `cd OpenGLPolyglot`
  - Maven build ausführen: `mvn -Pnative package`
  - Executable ausführen: `./OpenGLPolyglot/target/OpenGLPolyglot`
- Wenn in der `pom.xml` unter `<buildArgs>` das argument `-Ob` übergeben wird (`<argument>-Ob</argument>`), wird der Build-Prozess optimiert (verschnellert), aber das erzeugte Programm ist evtl. langsamer
