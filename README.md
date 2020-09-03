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
	
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/resources/1.png)
	
2. Modifique el bean de persistecia 'InMemoryCinemaPersistence' para que por defecto se inicialice con al menos otras 2 salas de cine, y al menos 2 funciones asociadas a cada una.
	
	   ```java
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
	![image](https://github.com/csarssj/ARSW-LAB-4/blob/master/resources/2.png)
## Authors

[César González](https://github.com/csarssj) 
