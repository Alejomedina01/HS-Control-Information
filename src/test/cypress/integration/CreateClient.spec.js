//<reference types="Cypress"/>
describe('Creacion de cliente',function(){
    it('crea cliente', () => {
        const IdClient="33333"
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="clients"]').click()
        cy.get('[test-id="addClient"]').click()
        cy.get('input[name="idClient"]').type(IdClient)
        cy.get('input[name="clientName"]').type("UPTC")
        cy.get('input[name="numberPhone"]').type("74836267")
        cy.get('input[name="email"]').type("uptcOfical@uptc.edu.com")
        cy.get('button[test-id="registry"]').click()
        cy.visit('http://localhost:1367/addNewContract/')
        cy.get('input[id="idClient"]').type('33333')
        cy.get('input[test-id="btnsearch"]').click()
        cy.get('[test-id="datasClient"]').should('contain','33333')
    });
    it('crea contrato',()=>{
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="contracts"]').click()
        cy.get('[test-id="addContract"]').click();
        cy.get('input[id="idClient"]').type('33333')
        cy.get('input[test-id="btnsearch"]').click()
        cy.get('[test-id="confirmClient"]').click()
        
    })  
})