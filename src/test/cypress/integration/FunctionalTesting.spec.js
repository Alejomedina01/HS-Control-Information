//<reference types="Cypress"/>
describe('functional testing first sprint',function(){
    it('crear cliente', () => {
        const IdClient="33333"
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="clients"]').click()
        cy.get('[test-id="addClients"]').click()
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
    it('crear contrato',()=>{
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="contracts"]').click()
        cy.get('[test-id="addContract"]').click();
        cy.get('input[id="idClient"]').type('33333')
        cy.get('input[test-id="btnsearch"]').click()
        cy.get('[test-id="confirmClient"]').click()
        cy.get('input[name="idContract"]').type('88888')
        cy.get('input[name="projectName"]').type('remodelacion restaurante')
        cy.get('input[name="city"]').type('Tunja')
        cy.get('input[name="contractDate"]').type('2022-02-10')
        cy.get('input[name="initialValue"]').type('20000000')
        cy.get('input[name="initialDateAct"]').type('2022-02-15')
        cy.get('input[name="invoicedValue"]').type('10000000')
        cy.get('input[name="warratyValue"]').type('200000')
        cy.get('select[name="warrantyState"]').select('Sin pagar')
        cy.get('select[name="contractState"]').select('En ejecucion')
        cy.get('button[test-id="saveContract"]').click()
        cy.get('input[id="myInput"]').type('88888')
        cy.get('tr[test-id=rowtable]').siblings().
        get('td[test-id="idContract"]').
        should('contain','88888')
    })  
    it('editar contrato',()=>{
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="contracts"]').click()
        cy.contains('88888').parent('tr').within(() => {
        cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.get('[test-id="editContract"]').click()
        cy.get('input[name="aditionalValue"]').clear()
        cy.get('input[name="aditionalValue"]').type(1000000)
        cy.get('button[test-id="saveChangesContract"]').click()
        cy.contains('88888').parent('tr').within(() => {
        cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.get('td[test-id="valuePending"]').should('contain','$1.000.000')
    })
    it('Consultar contrato',()=>{
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="contracts"]').click()
        cy.get('input[id="myInput"]').type('88888')
        cy.contains('88888').parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
            })
    })
    it('Consultar clientes registrados', () => {
        const IdClient="33333"
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type('33333')
        cy.get('[test-id="datasClient"]').should('contain','33333')
    });
    it.only('Consultar contrato mediante cliente', () => {
        const IdClient="33333"
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type('33333')
        cy.get('[test-id="datasClient"]').should('contain','33333')
        cy.contains('33333').parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.contains('88888').parent('tr').within(() => {
            cy.get('td').eq(3).contains('a', 'Abrir').click()
        })
        cy.get('[test-id="id-contract"]').should('contain','88888')
    })
    it('Consultar Empleado', () => {
        const IdClient="33333"
        cy.login({username:'jefaso',password:'12345'})
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type('33333')
        cy.get('[test-id="datasClient"]').should('contain','33333')
    });
})