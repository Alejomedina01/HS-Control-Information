//<reference types="Cypress"/>
describe('Creacion de cliente',function(){
    it('crea cliente', () => {
        cy.viewport(1280, 720)
        cy.login({username:'jefaso',password:'12345'})
        cy.visit('http://localhost:1367')
        cy.get('[test-id="clients"]').click()
        cy.get('input[name="idClient"]').type('33333')
        cy.get('input[name="clientName"]').type("UPTC")
        cy.get('input[name="numberPhone"]').type("74836267")
        cy.get('input[name="email"]').type("uptcOfical@uptc.edu.com")
        cy.get('button[test-id="registry"]').click()
        cy.visit('http://localhost:1367/addNewContract/')
        cy.get('input[id="idClient"]').type('33333')
        cy.get('input[test-id="btnsearch"]').click()
        //cy.request('DELETE','http:localhost:1367/deleteClient/33333')
    });
})