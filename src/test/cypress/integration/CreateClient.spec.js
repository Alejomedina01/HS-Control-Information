//<reference types="Cypress"/>
describe('Primer caso de prueba',function(){
    it('hacer login', () => {
        cy.login({username:'jefaso',password:'12345'})
        cy.visit('http://localhost:1367')
    });
})