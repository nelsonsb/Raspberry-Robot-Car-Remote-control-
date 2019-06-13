# Control Remoto para Raspberry Robot Car 

El objetivo de este proyecto es crear una manera de controlar mi carro robótico creado con una Raspberry Pi 3.

Todo comenzó cuando quise construir mi propio carro a control remoto. Como ya tenía una pequeña experiencia trabajando con arduinos y raspberrys, empecé a investigar cómo fabricarlo. 

Encontré que se podía construir utilizando una raspberry, arduino o un esp32.

En el momento solo tenía a mano una raspberry, así que procedí a iniciar el proyecto.

La construcción la empecé con las instrucciones originales de la página oficial

https://projects.raspberrypi.org/en/projects/build-a-buggy

Aunque hice algunos cambios en la estructura y decidí utilizar una pieza de madera como chasis.


![Robot Car](screenshots/20190525_150453.jpg?raw=true "Última Versión")

Al finalizar me dí cuenta que no me gustaba la manera como controlan el robot. Para ello utilizan una aplicación android llamada BlueDot y esta se conecta a un script en phyton. Esto obliga a conectarse remotamente vía VNC a la Raspberry para iniciar el programa y correrlo en la consola gráfica de phyton.

Buscando la manera de hacerlo más independiente, me puse a investigar y en lo posible desarrollar una mejor manera de suplir estas funciones.

Decidí usar Java para el script del servidor y una App sencilla en Android del lado del usuario.

La forma de conexión es muy sencilla, utilizando sockets, solo debes conectarte a una ip y un puerto, y luego enviar comandos sencillos a la raspberry.

En el repositorio existen 2 carpetas : una para la parte del servidor (Java) y otra para la app (Android)

## Primera Versión (Servidor)

En la primera versión solo tenía 2 motores para la tracción trasera del carro, pero lamentablemente hacer los giros no funcionaba. Las ruedas del carro patinaban y no giraba muy bien.

## Segunda versión (Servidor)

Posteriormente adicioné 2 motores más al carro y ya tenía tracción en las 4 ruedas. Esto facilitaba notablemente los giros.  Básicamente los cambios se hicieron en el script del lado del servidor. Solo fué adicionar 4 nuevos puntos de conexión.

## Tercera versión (Servidor)

En la tercera versión solamente incorporé un pin más para controlar una luz led que adicioné al carro.

## Primera versión (Android App)

La aplicación en Android no es estéticamente muy bonita que digamos. Solo tiene un par de cuadros de texto para digitar la IP y el puerto a donde se va a conectar y unos botones con las funciones de : avanzar, retroceder, izquierda, derecha y parar.

## Segunda versión (Android App)

La siguiente versión de la App la modifiqué para que el carro se mueva solo si se tiene presionado algún botón. En el momento en que se suelta el botón correspondiente, se envía la señal de parar.

## Tercera versión (Android App)

En la tercera versión de la App incluí un switch que me permite encender/apagar una luz que incorporé en el carro.

## Ejecución del lado del servidor (Raspberry)

Del lado del servidor solo tenemos un archivo jar, el cual se ejecuta de una manera muy simple :

java -jar RemoteControlServer2.jar

Si se desea se puede automatizar en un shell o en el /etc/rc.local para que inicie al arrancar el sistema operativo.


No llevo un control muy estricto de las versiones así que posiblemente no se encuentren todas en el repositorio. Por lo general trato de mantener solo la última versión.

Revisar el Wiki para más información

Gracias

