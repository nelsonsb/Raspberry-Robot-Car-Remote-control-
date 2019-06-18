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


Revisar el [Wiki](https://github.com/nelsonsb/Raspberry-Robot-Car-Remote-control-/wiki) para más información

Gracias

