<reference types="Cypress"/>
describe('Primer caso de prueba',function(){
    /*
    //Esto sirve para que realice algo antes de cada uno de los test
    beforeEach(()=>{        
        cy.visit('http://localhost:1367')
    })
*/  
//esto solo para que se ejecute este test y no todos
//it.only
    it('the user can login',()=>{
        //para darle clic al elemento que contenga el texto "Login"
        //cy.contains('Login').click();

        // ejemplo de recuperacion a traves de propiedades de primero recupera el componente con el 
        //nombre de la propiedad1 despues recupera todos los input con el nombre de la propiedad 2
        //asi se realiza un escalado en el filtro ademas de evitar el uso de CLASES(class="nombre de clase")
        //cy.get('[propiedad1="nombre propiedad"] input[propiedad2="nombre propiedad"]')

        /*se utiliza para hacer una peticion (es aconsejable tener una base de datos para pruebas y 
        no trabajar sobre la base de datos de produccion ademas de tener un llamado de peticion que pueda
        resetear los datos de esta base)*/
        //cy.request('POST','http:localhost:1367/resetdbtest')
        //creacion de un usuario ejemplo en el que el sevidor de peticiones es Axios
        /*const user={
            username:"yeison",
            password:"321321"
        }*/
        //cy.request('POST','http:localhost:1367/saveUser',user)

        cy.get('#idcampoEmail').type('jefaso')
        cy.get('#idcampoContrasema').type('12345')
        cy.get('#idbotonLogin').clic()
        //este sirve para hacer la validacion de que el componente contenga el texto 'Contratos'
        //adicionalemte se pueden encadenar .should().should()...
        cy.get('#idcomponente').should('contain','Contratos')


    })

    it('ingresar a la pagina de pruebas',function(){
        cy.visit('http://localhost:1367')
    })
})