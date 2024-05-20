'use strict'

const hiddenCompanyName = document.getElementById('hidden-company').innerText
const hiddenVacancy = document.getElementById('hidden-vacancy').innerText
const hiddenDescription = document.getElementById('hidden-description').innerText
const hiddenParentCategory = document.getElementById('hidden-parentCategory').innerText
const hiddenCategory = document.getElementById('hidden-category').innerText
const hiddenExpFrom = document.getElementById('hidden-expFrom').innerText
const hiddenExtTo = document.getElementById('hidden-extTo').innerText
const hiddenSalary = document.getElementById('hidden-salary').innerText
const hiddenCreationDate = document.getElementById('hidden-creationDate').innerText
const hiddenLink = document.getElementById('hidden-link').innerText
const hiddenLinkText = document.getElementById('hidden-linkText').innerText

window.addEventListener('load', () => {
    let form = document.getElementById('form')
    let vacancyBlock = document.getElementById('vacancies')
    let input = document.getElementById('input')

    input.oninput = submitHandler

    function submitHandler() {
        vacancyBlock.replaceChildren()
        let data = new FormData(form)
        makeRequest('/api/vacancies/search', {
            method: 'post',
            body: data
        }).then(vacancies => {
            createAllProfiles(vacancies)
        })
            .catch(e => console.log(e))
    }

    function createAllProfiles(vacancies) {
        for (let vacancy of vacancies) {
            createProfile(vacancy)
        }
    }

    function createProfile(vacancy) {
        let row = document.createElement('div')
        row.className = 'row border-bottom p-2 comment'
        vacancyBlock.appendChild(row)

        let col2 = document.createElement('div')
        col2.className = 'col-2'
        row.appendChild(col2)

        let col9 = document.createElement('div')
        col9.className = 'col-9'
        col9.innerHTML =
            '<p class="card-text mb-1 fw-semibold">' + hiddenCompanyName + ' ' + vacancy.companyName + '</p>' +
            '<p class="card-text mb-1">' + hiddenVacancy + ' ' + vacancy.name + '</p>' +
            '<p class="card-text mb-1">' + hiddenDescription + ' ' + vacancy.description + '</p>' +
            '<p class="card-text mb-1">' + hiddenParentCategory + ' ' + vacancy.category.parent + ' | ' + hiddenCategory + ' ' + vacancy.category.name + '</p>' +
            '<p class="card-text mb-1">' + hiddenExpFrom + ' ' + vacancy.exp_from + ' ' + hiddenExtTo + ' ' + vacancy.exp_to + '</p>' +
            '<p class="card-text mb-1">' + hiddenSalary + ' ' + vacancy.salary + '</p>' +
            '<p class="card-text mb-1">' + hiddenCreationDate + ' ' + vacancy.created_date + '</p>' +
            '<p class="card-text mb-1">' + hiddenLink + '  <a href="/vacancies/' + vacancy.id + '">' + hiddenLinkText + '</a></p>'
        row.appendChild(col9)
    }


    async function makeRequest(url, options) {
        let response = await fetch(url, options)

        if (response.ok) {
            return await response.json()
        } else {
            let error = new Error(response.status.toString());
            error.response = response;
            throw error;
        }
    }
})