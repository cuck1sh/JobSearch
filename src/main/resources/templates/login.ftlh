<#import  "layout.ftlh" as main>
<@main.layout>

    <form action="/api/auth/login" method="post" class="mx-auto w-25 mt-5" id="form">

        <div class="form-outline mb-4">
            <input type="email" name="username" id="form2Example1" class="form-control"/>
            <label class="form-label" for="form2Example1">Email address</label>
        </div>

        <div class="form-outline mb-4">
            <input type="password" name="password" id="form2Example2" class="form-control"/>
            <label class="form-label" for="form2Example2">Password</label>
        </div>

        <div class="d-grid gap-2">
            <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3 mb-4">Логин
            </button>
        </div>

        <div class="text-center">
            <p>Нет аккаунта? <a href="/users/register">Зарегистрироваться</a></p>
        </div>

    </form>

    <script>
        'use strict'

        let form = document.getElementById('form')

        form.addEventListener('submit', onLoginHandler)


        function onLoginHandler(event) {
            // event.preventDefault()

            const url = 'http://localhost:8089/api/auth/login'

            let target = event.target
            let data = new FormData(target)
            let user = Object.fromEntries(data)

            fetchAuthorized(url, user).then(r => {
                let userJson = JSON.stringify(user)
                localStorage.setItem('user', userJson)
                window.location.href = 'http://localhost:8089/'
            })
        }


        async function fetchAuthorized(url, user) {

            let headers = new Headers()
            headers.set('Content-Type', 'application/json')
            headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))

            try {
                await makeRequest(url, updateOptions({
                    method: 'post',
                    headers: headers,
                    body: JSON.stringify(user)
                }));

            } catch (e) {
                localStorage.removeItem('user')
                window.location.href = 'http://localhost:8089/login'
                alert(e)
            }
        }

        function makeHeaders() {
            let user = restoreUser()
            let headers = new Headers()

            headers.set('Content-Type', 'application/json')
            if (user) {
                headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))
            }
            return headers;
        }

        const requestSettings = {
            method: 'get',
            headers: makeHeaders()
        }

        async function makeRequest(url, options) {
            let settings = options || requestSettings;
            let response = await fetch(url, settings)

            if (response.ok) {
                return await response.json()
            } else {
                let error = new Error(response.status.toString());
                error.response = response;
                throw error;
            }
        }

        function updateOptions(options) {
            let update = {...options}
            update.mode = 'cors'
            update.headers = makeHeaders()
            return update
        }

        function restoreUser() {
            return JSON.parse(localStorage.getItem('user'));
        }


    </script>

</@main.layout>