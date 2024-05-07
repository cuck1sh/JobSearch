'use strict'
window.addEventListener('load', () => {
    let form = document.getElementById('form')
    let vacancyBlock = document.getElementById('vacancies')
    form.addEventListener('submit', submitHandler)

    function submitHandler(e) {
        vacancyBlock.replaceChildren()
        e.preventDefault()
        const form = e.target
        let data = new FormData(form)

        makeRequest('/api/vacancies/search', {
            method: 'post',
            body: data
        }).then(vacancies => {
            console.log(vacancies)
            document.getElementById('submit').value = ''
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
            '<p class="card-text mb-1 fw-semibold">Компания: ' + vacancy.companyName + '</p>' +
            '<p class="card-text mb-1">Вакансия: ' + vacancy.name + '</p>' +
            '<p class="card-text mb-1">Описание: ' + vacancy.description + '</p>' +
            '<p class="card-text mb-1">Родительская категория: ' + vacancy.category.parent + ' | категория: ' + vacancy.category.name + '</p>' +
            '<p class="card-text mb-1">Опыт от: ' + vacancy.exp_from + ' до: ' + vacancy.exp_to + '</p>' +
            '<p class="card-text mb-1">Зарпалта: ' + vacancy.salary + '</p>' +
            '<p class="card-text mb-1">Дата создания: ' + vacancy.created_date + '</p>' +
            '<p class="card-text mb-1">Ссылка на вакансию:  <a href="/vacancies/' + vacancy.id + '">Перейти</a></p>'
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