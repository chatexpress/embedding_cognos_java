#
# Demo Embedding Cognos Java

#### Jersey, Spring Bot

## Flujo del proceso
<p align="justify">
Diagrama de contexto
</p>
<div align="center">
<img height="200" src="https://leoesleoesleo.github.io/imagenes/flujo_cognos.png" alt="Flujo Cognos">
</div>
<p align="justify">
El alcance es poder embeber reportes de IBM Cognos en una plicación externa	
</p>

- Estrucutra de las cookies
	```
	{
            'cam_passport': 'MTsxMDE6ZDg4NDAzMDgtMTU0ZS04OTBhLTM5YzgtYmU1NDMzOTE2Y2MyOjEwNjI5OTUwMjY7MDszOzA7',
            'up': 'H4sIAAAAAAAAAGWR3UoDMRBGX0Vyrbi9ULF3xVqrVK10RRBBJsl0mzbJLMlsbRHf3Yk/VRRykTlJTr5MXhW7gI8U8XKo+moQMDkDT4fn/nkGfg2WktpXc0oBWNbH9fVE6rxyUaozSi0lYBTkck3kNaQhmRVa1efU4b7SYFZNoi7aGebsKE6oaVxs5HTv9KQ6qHoy9qqq/zGKekEvYxc5y44yH3gvtMuYbiCgQN8Cu0gCedsWAMaInwUsKOAUmgKlEl6u1M473o4QuEso1jn4jCXvIFLcBup+WJvIdoYnZMAXCcZdIGsx3uolGv6lkFeqTX2/uKr93bGt26Ve9rK5GFXwcLSWoyaEKSUG/xlIu6FLYpAugP8OdB5B+9KvL6nFOXSe/z3WUGSMf7JpyFjjhnfe8oUdk3p7B9EIEt/YAQAA', 
            'usersessionid': 'AggAAAASPhdlAAAAAAoAAABNnVTuTJ5fR9rHFAAAAOu/Qt44unGzFEQ0HwNWVv+5GcE1BwAAAFNIQS0yNTYgAAAAcveQmKlNTJ4cI6Wlgv8uynilTmiBBXDt16K8BxefeCA=',
            'CRN': 'http%3A%2F%2Fdeveloper.cognos.com%2Fceba%2Fconstants%2FbiDirectionalOptionEnum%23biDirectionalFeaturesEnabled%3Dfalse%26showWelcomePage%3Dtrue%26isToolbarDocked%3Dtrue%26timeZoneID%3DAmerica%252FEl_Salvador%26showOptionSummary%3Dtrue%26showHiddenObjects%3Dfalse%26format%3DHTML%26skin%3Dcorporate%26contentLocale%3Den%26linesPerPage%3D15%26automaticPageRefresh%3D30%26backgroundSessionLogging%3D1970-01-01%2B00%253A00%253A00%26columnsPerPage%3D3%26http%3A%2F%2Fdeveloper.cognos.com%2Fceba%2Fconstants%2FsystemOptionEnum%23accessibilityFeatures%3Dfalse%26listViewSeparator%3Dnone%26productLocale%3Den%26showHints%3DshowAll%26displayMode%3Dlist%26',
            'cea-ssa': 'false', 
            'userCapabilities': 'f%3Bfdbffc6d%3Bf07c1faf%3Bff27defa%26AwcAAABTSEEtMjU2FAAAAOu%2FQt44unGzFEQ0HwNWVv%2B5GcE1Kp3zwkHAwRd2UC%2FRq%2FKEjxOutt7T%2FcEIS1LwCzl22cY%3D',
            'userCapabilitiesEx': 'ffef9fff%3Bf%3Bfdbffc6d%3Bf07c1faf%3Bff27defa%26AhQAAADrv0LeOLpxsxRENB8DVlb%2FuRnBNQcAAABTSEEtMjU2IAAAAJijCXFRp05vjWLS%2FsaiYdWu994qvWLU3ep%2FaBqXHmlI',
            'XSRF-TOKEN': 'lL_gI1Zjo3v_Ybrj84nVFtJwroXv8iHx',
            
            'abuse_interstitial': 'cd61-155-190-18-7.ngrok-free.app',
            'caf': 'CAFW00000128Q0FGQTYwMDAwMDAwNmVBaFFBQUFEcnYwTGVPTHB4c3hSRU5COERWbGItdVJuQk5RY0FBQUJUU0VFdE1qVTJJQUFBQUVYZGRFa0dnVHV4UHV1TGNIdlBUZmMzS0E1SERxMWFmQkwtay03eWhHbWQ0NzA5NDl8MTAxOjAwNTNiN2Y0LWE4OTctYjI0My0wNDhhLTJlY2JlYTg3OTVmMjowNzc4NjI3ODk2fDEwMTpkODg0MDMwOC0xNTRlLTg5MGEtMzljOC1iZTU0MzM5MTZjYzI6MTA2Mjk5NTAyNg__',
            'MRUStorage': '%7B%22xTUhJTlQ6dTpjbj1scGF0aW5v%22%3Atrue%7D',
            'CogCacheService': '20545151307800',
        }
	```


## Manual de instalación

- Clonar repositorio
	```
	git clone https://github.com/leoesleoesleo/....
	```
 
- Instalar Java (Ejemplos en Linux Ubuntu)
	```
	java -version
	```
 
  ```
	sudo apt-get install openjdk-11-jdk
	```

- Instalar Maven
	```
	sudo apt-get install maven
	```
 
- Instalar Docker (Opcional)
	```
	sudo apt-get update
	```
 
  ```
	sudo apt-get install docker.io
	```
  
  ```
	sudo systemctl start docker
	```
  
  ```
	sudo systemctl enable docker
	```

### Iniciando proyecto

- Instalar dependencias
	```
	mvn clean install
	```

- correr la app
	```
	mvn spring-boot:run
	```

 - Generar el .jar (Compilar)
	```
	mvn clean package
	```

 - Ubicar el .jar
	```
	java - jar .\target\springtest-0.0.1-SNAPSHOT.jar
	```
 
### Iniciando proyecto con Docker (Opcional)

- Crear imagen
	```
	docker build -t springtest:0.0.1 ./
	```

- Correr aplicación
	```
	docker run -d -p 8080:8080 -t springtest:0.0.1
	```

### Recuerda editar urls de los reportes de Cognos en la ruta:
	```
	src/main/java/com/example/springtest/flows/HelloWebRestService.java
	```

### Correr la aplicación
 - Ejecutar aplicación desde NGROK con el fin de generar una url con certificados válidos (https) para que las imagenes puedan cargar con los certificados de seguridad.
 - Ejemplo ruta: https://018b-181-128-238-68.ngrok-free.app/backend/api/helloworld
 - Nota: para que las imagenes carguen correctamente ademas de correr la aplicación en https, tambien será necesario hacer el logueo desde cognos.
   
