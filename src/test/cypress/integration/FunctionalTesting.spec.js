//<reference types="Cypress"/>
describe('functional testing second sprint', function () {
    beforeEach(() => {
        cy.login({username: 'jefaso', password: '12345'})
    })
    it('crear cliente', () => {
        const IdClient = "33333"
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
        cy.get('[test-id="datasClient"]').should('contain', '33333')
    });
    it('crear contrato', () => {
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
        cy.get('tr[test-id=rowtable]').siblings().get('td[test-id="idContract"]').should('contain', '88888')
    })
    it('editar contrato', () => {
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
        cy.get('td[test-id="valuePending"]').should('contain', '$1.000.000')
    })
    it('Consultar contrato', () => {
        cy.get('[test-id="contracts"]').click()
        cy.get('input[id="myInput"]').type('88888')
        cy.contains('88888').parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
    })
    it('Registro datos de empleado', () => {
        const idEmployee = "44444"
        cy.get('[test-id="employees"]').click()
        cy.get('[test-id="addEmployee"]').click()
        cy.get('input[name="idEmployee"]').type(idEmployee)
        cy.get('input[name="name"]').type("Juan Pablo")
        cy.get('input[name="lastname"]').type("Romero Martinez")
        cy.get('input[name="nickname"]').type("pollo")
        cy.get('input[name="adress"]').type("calle 12 N° 38-26")
        cy.get('input[name="numberPhone"]').type("32412121")
        cy.get('input[name="eps"]').type("medimas")
        cy.get('input[name="afp"]').type("colpensiones")
        cy.get('input[name="arl"]').type("positiva")
        cy.get('input[name="consent"]').check()
        cy.get('button[test-id="registry"]').click()
        cy.get('input[id="myInput"]').type(idEmployee)
        cy.get('[test-id="datasEmployee"]').should('contain', idEmployee)
    });
    it('Consultar empleado especifico', () => {
        const idEmployee = '44444'
        cy.get('[test-id="employees"]').click()
        cy.get('input[id="myInput"]').type(idEmployee)
        cy.contains(idEmployee).parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.get('[test-id="idEmployee"]').should('contain',idEmployee)
    });

    it('Consultar clientes registrados', () => {
        const IdClient = "33333"
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type(IdClient)
        cy.get('[test-id="datasClient"]').should('contain', IdClient)
    });
    it('Consultar datos cliente especifico', () => {
        const idClient = "33333"
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type(idClient)
        cy.contains(idClient).parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.get('[test-id="idClient"]').should('contain', idClient)
    });
    it('Consultar contrato mediante cliente', () => {
        const IdClient = "33333"
        const IdContract = "88888"
        cy.get('[test-id="clients"]').click()
        cy.get('input[id="myInput"]').type(IdClient)
        cy.get('[test-id="datasClient"]').should('contain', IdClient)
        cy.contains('33333').parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.contains(IdContract).parent('tr').within(() => {
            cy.get('td').eq(3).contains('a', 'Abrir').click()
        })
        cy.get('[test-id="id-contract"]').should('contain', IdContract)
    })
    it.only('Agregar archivo PDF al contrato', () => {
        cy.get('[test-id="contracts"]').click()
        cy.get('input[id="myInput"]').type('88888')
        cy.contains('88888').parent('tr').within(() => {
            cy.get('td').eq(4).contains('a', 'Abrir').click()
        })
        cy.get('input[test-id="nameFileUpload"]').type("Contrato");
        cy.get('input[test-id="fileStream"]').selectFile('../../contrato.pdf')
        /* activar para subir .click() */
        cy.get('button[test-id="addFileContract"]').click()
        cy.get('tr[test-id="datas-fileContract"]').should('contain', "Contrato")
    })
})