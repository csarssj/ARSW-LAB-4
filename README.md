# ARSW-LAB-4

## Manual de usuario

Si se deseea hacer uso del programa lo primero que debe realizarse el clonar el repositorio almacenado en Github a través del siguiente comando:

```
git clone https://github.com/csarssj/ARSW-LAB-4.git

```
O si desea puede descargarlo como archivo zip y luego descomprimirlo en la carpeta que desee.

Una vez hecho alguno de los dos pasos anteriores, nos dirigimos a la ruta de instalación y por medio de la consola digitamos el siguiente comando para compilar:

```
mvnw package
```
y para ejecutar las dos partes ingresamos :
 
 * Con Maven:
 	```
	mvn clean compile
	$ mvn spring-boot:run
	```
## Part I - Before finishing class

1. Integre al proyecto base suministrado los Beans desarrollados en el Ejercicio Anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired.
	
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/1.png)
	
2. Modifique el bean de persistecia 'InMemoryCinemaPersistence' para que por defecto se inicialice con al menos otras 2 salas de cine, y al menos 2 funciones asociadas a cada una.
	
	   ```java
	   public InMemoryCinemaPersistence() {
			//funcion 1
			String functionDate = "2018-12-18 15:30";
			List<CinemaFunction> functions= new ArrayList<>();
			CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
			CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
			functions.add(funct1);
			functions.add(funct2);
			Cinema c=new Cinema("cinemaX",functions);
			cinemas.put("cinemaX", c);
			//funcion 2
			String functionDate2 = "2020-12-18";
			List<CinemaFunction> functions2= new ArrayList<>();
			CinemaFunction funct21 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate2);
			CinemaFunction funct22 = new CinemaFunction(new Movie("The Night","Horror"),functionDate2);
			functions.add(funct21);
			functions.add(funct22);
			Cinema c2=new Cinema("cineco",functions2);
			cinemas.put("cineco", c2);
			//funcion 3
			String functionDate3 = "2020-11-07";
			List<CinemaFunction> functions3= new ArrayList<>();
			CinemaFunction funct31 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate3);
			CinemaFunction funct32 = new CinemaFunction(new Movie("The Night","Horror"),functionDate3);
			functions.add(funct31);
			functions.add(funct32);
			Cinema c3=new Cinema("cinepolis",functions3);
			cinemas.put("cinepolis", c3);
		}    
	      ```

3. Configure su aplicación para que ofrezca el recurso "/cinema", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los cines. Para esto:
	1. Modifique la clase CinemaAPIController teniendo en cuenta el ejemplo de controlador REST hecho con SpringMVC/SpringBoot. (ver code 1)
	2. Haga que en esta misma clase se inyecte el bean de tipo CinemaServices (al cual, a su vez, se le inyectarán sus dependencias de persistencia y de filtrado de películas).	
	3. De ser necesario modifique el método getAllCinemas(), de manera que utilice la persistencia previamente inyectada y retorne todos los cines registrados.
	
	- Controller: 
	  
	  ```java
	  @RestController
	  @RequestMapping(value = "/cinemas")
	  public class CinemaAPIController {
		@Autowired
		CinemaServices service = null;

		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<?> manejadorGetCinemas(){
			Set<Cinema> cinemas = null;
			try {
				//obtener datos que se enviarán a través del API
				cinemas = service.getAllCinemas();
			} catch (Exception ex) {
				Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
				return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
			} 
			return new ResponseEntity<>(cinemas,HttpStatus.ACCEPTED);
		}		
	  ```
4. Verifique el funcionamiento de a aplicación lanzando la aplicación con maven (ver code 2). Y luego enviando una petición GET a:  http://localhost:8080/cinemas. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los cines suministados por defecto. 	

	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/2.png)
	
5. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}, el cual retorne usando una representación jSON todas las funciones del cine cuyo nombre sea {name}. Si no existe dicho cine, se debe responder con el código de error HTTP 404. Para esto, revise en la documentación de Spring, sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/cinemas/cinemaY , se obtenga en formato jSON el conjunto de funciones asociadas al cine 'cinemaY' (ajuste esto a los nombres de cine usados en el punto 2).
	- Controller: 
	  
	  ```java
	  @RequestMapping(method = RequestMethod.GET,path = "{name}")
	  public ResponseEntity<?> manejadorGetCinemasByName(@PathVariable String name ){
		Cinema cinema = null;
	    try {
	    	cinema= service.getCinemaByName(name);
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
	    } 
        return new ResponseEntity<>(cinema,HttpStatus.ACCEPTED);
		}		
	  ```
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/3.png)

6. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}/{date}, el cual retorne usando una representación jSON una lista de funciones asociadas al cine cuyo nombre es {name} y cuya fecha sea {date}, para mayor facilidad se seguirá manejando el formato "yyyy-MM-dd". De nuevo, si no existen dichas funciones, se debe responder con el código de error HTTP 404. 
	- Controller: 
	  
	  ```java
	  @RequestMapping(method = RequestMethod.GET,path = "{name}/{date}")
	  public ResponseEntity<?> manejadorGetCinemasByNameAndDate(@PathVariable String name,@PathVariable String date){
		List<CinemaFunction> cinema = null;
	    try {
	    	cinema= service.getFunctionsbyCinemaAndDate(name, date);
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
	    } 
        return new ResponseEntity<>(cinema,HttpStatus.ACCEPTED);
		}		
	  ```

	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/4.png)

7. Modifique el controlador para que ahora, acepte peticiones GET al recurso /cinemas/{name}/{date}/{moviename}, el cual retorne usando una representación jSON sólo UNA función, en este caso es necesario detallar además de la fecha, la hora exacta de la función de la forma "yyyy-MM-dd HH:mm". Si no existe dicha función, se debe responder con el código de error HTTP 404.
	- Controller: 
	  
	  ```java
	  @RequestMapping(method = RequestMethod.GET,path = "{name}/{date}/{movie}")
	  public ResponseEntity<?> manejadorGetCinemasByNameAndDateAndMovie(@PathVariable String name,
			@PathVariable String date,@PathVariable String movie){
		List<CinemaFunction> cinema = null;
	    try {
	    	cinema= service.getFunctionsbyCinemaAndDate(name, date);
	    	
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
	    } 
        return new ResponseEntity<>(cinema,HttpStatus.ACCEPTED);
		}		
	  ```
	
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/5.png)
	
## Part II 

1. Agregue el manejo de peticiones POST (creación de nuevas funciones), de manera que un cliente http pueda registrar una nueva función a un determinado cine haciendo una petición POST al recurso ‘/cinemas/{name}’, y enviando como contenido de la petición todo el detalle de dicho recurso a través de un documento jSON. Para esto, tenga en cuenta el siguiente ejemplo, que considera -por consistencia con el protocolo HTTP- el manejo de códigos de estados HTTP (en caso de éxito o error): (ver code 3)
	
	```java
	  @RequestMapping(method = RequestMethod.POST,path = "{name}")	
	  public ResponseEntity<?> manejadorPostAddNewFunction(@PathVariable String name, @RequestBody CinemaFunction function){
	    try {
	        service.addNewFunction(name, function);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 400",HttpStatus.NOT_FOUND);        
	    }        

	  }
	```
2. Para probar que el recurso ‘cinemas’ acepta e interpreta correctamente las peticiones POST, use el comando curl de Unix. Este comando tiene como parámetro el tipo de contenido manejado (en este caso jSON), y el ‘cuerpo del mensaje’ que irá con la petición, lo cual en este caso debe ser un documento jSON equivalente a la clase Cliente (donde en lugar de {ObjetoJSON}, se usará un objeto jSON correspondiente a una nueva función: (ver code 4) Con lo anterior, registre un nueva función (para 'diseñar' un objeto jSON, puede usar esta herramienta): Nota: puede basarse en el formato jSON mostrado en el navegador al consultar una función con el método GET.

	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/6.png)
	
3. Teniendo en cuenta el nombre del cine, la fecha y hora de la función y el nombre de la película, verifique que el mismo se pueda obtener mediante una petición GET al recurso '/cinemas/{name}/{date}/{moviename}' correspondiente.

	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/7.png)
	
4. Agregue soporte al verbo PUT para los recursos de la forma '/cinemas/{name}', de manera que sea posible actualizar una función determinada, el servidor se encarga de encontrar la función correspondiente y actualizarla o crearla.
	```java
	  @RequestMapping(method = RequestMethod.PUT,path = "{name}")	
	  public ResponseEntity<?> manejadorPostRecursoXX(@PathVariable String name, @RequestBody CinemaFunction function){
	    try {
	        service.setFunction(name, function);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 400",HttpStatus.NOT_FOUND);        
	    }       

		}
	  ```
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/img/9.png)

## Part III

El componente CinemaRESTAPI funcionará en un entorno concurrente. Es decir, atederá múltiples peticiones simultáneamente (con el stack de aplicaciones usado, dichas peticiones se atenderán por defecto a través múltiples de hilos). Dado lo anterior, debe hacer una revisión de su API (una vez funcione), e identificar:
Qué condiciones de carrera se podrían presentar?
  * Cuales son las respectivas regiones críticas?
  * Ajuste el código para suprimir las condiciones de carrera.
Tengan en cuenta que simplemente sincronizar el acceso a las operaciones de persistencia/consulta DEGRADARÁ SIGNIFICATIVAMENTE el desempeño de API, por lo cual se deben buscar estrategias alternativas.
Escriba su análisis y la solución aplicada en el archivo ANALISIS_CONCURRENCIA.txt
	
## Authors

[César González](https://github.com/csarssj) 
