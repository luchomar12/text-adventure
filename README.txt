//INSTRUCCIONES PARA CORRER EL JUEGO:
El método main() se encuentra en el paquete textadventure.TextAdventure.java 
Desde ahí hay que iniciar el juego, no hay que hacer nada más.


//INSTRUCCIONES PARA JUGAR
El juego es un loop de 3 cosas:
1)Muestra la habitación actual
2)Muestra el menú de opciones de acción
3)Espera a que se ingrese una de las opciones

//INGRESO POR TECLADO:

    //MENU
    - El menú tiene 4 optiones de acción y una para salir del juego.
    - Para realizar cada acción se debe ingresar el número de referencia (1 / 2 / 3 / 4)
    - Para salir se ingresa la letra 'q'

    //INTERPRETACION DEL INPUT
    El juego está programado para entender lo que se ingrese por teclado IGNORANDO si es mayúscula o minúscula.
    Por lo demás el ingreso por teclado debe ser EXACTO. 
    Por ejmplo: Si tengo que elegir un item debo ingresar el nombre del item EXACTO
        Ejemplo de item:
                -Llave de Cobre --> INGRESO: TOMA: llave de cobre / Llave de Cobre / LLAVE DE COBRE / LlaVe dE CobrE 
                                            NO TOMA: llave cobre / llave / cobre 
        Ejemplo de movimiento:
                -Para moverse ---> INGRESO: TOMA: n / s / e / o
                                            NO TOMA: norte / sur / este / oeste
                                            
    De todas maneras siempre va a haber unas instrucciones en pantalla 
    sobre cuáles son las opciones válidas a ingresar cuando no se trate de interactuar con algun objeto

    Casi siempre luego de realizar una acción va se va a detener el texto para poder leerlo.
    Para que siga el juego se debe presionar ENTER (es un in.nextLine()).

    NO se mostrará en cada habitación qué salidas hay disponibles. 
    La idea es que el jugador vaya descubriendo hacia dónde puede o no ir.
    Lo que SÍ se muestra son los objetos que puedes tomar o interactuar en la habitación.




