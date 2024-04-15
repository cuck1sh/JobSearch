<#import  "layout.ftlh" as main>
<@main.layout>

    <form action="/messages/1" method="post">
        <div class="card mb-3">
            <div class="row">
                <div class="col-md-4">

                    <div class="card border mt-5 ms-5" style="max-width: 18rem;">
                        <div class="card-header bg-transparent border-success">${messenger.employerProfile.name}</div>
                        <div class="card-body">
                            <h5 class="card-title">${messenger.employerProfile.email}</h5>
                            <p class="card-text">Возраст: ${messenger.employerProfile.age}</p>
                            <p class="card-text">Номер телефона: ${messenger.employerProfile.phoneNumber}</p>
                        </div>
                        <div class="card-footer bg-transparent border">${messenger.employerProfile.accountType}</div>

                    </div>
                </div>


                <div class="col-md-8">
                    <div class="card-body">

                        <h5 class="card-title mb-4">Переписка</h5>

                        <#list messenger.messages as message>
                            <div class="border-bottom p-2">
                                <p class="card-text mb-1">${message.content}</p>
                                <p class="card-text"><small class="text-body-secondary">${message.timestamp}</small></p>
                            </div>
                        </#list>

                    </div>

                    <div class="card-footer bg-transparent border rounded-3">
                        <div class="row">
                            <div class="col-10">
                                <input type="text" name="message" class="form-control" placeholder="Сообщение">
                            </div>
                            <div class="col-2">
                                <button type="submit"
                                        class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3">Отправить
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <script>
        'use strict'
        window.addEventListener('load', () => {
            const base_url = 'http://localhost:8089/api/messages/1'; //Тестовый ввод айди хардкодом, потом исправить
            let messages = document.getElementById('messages');


            setInterval(() => {
                try {
                    makeRequest(base_url)
                        .then(data => {
                            console.log('пук кек', data);

                            let lastMessage = data[data.length - 1];
                            let lastTimestamp = new Date(lastMessage.timestamp);

                            console.log('lastTimestamp: ' + lastTimestamp)

                            data.forEach(e => {
                                console.log('здесь1');
                                console.log('e.timestamp: ' + e.timestamp);
                                if (e.timestamp > lastTimestamp) {
                                    console.log('здесь2');
                                    createMessage(e.content, e.timestamp);
                                }
                            })

                            function createMessage(content, timestamp) {
                                let borderElement = document.createElement('div');
                                borderElement.className = 'border-bottom p-2';
                                borderElement.innerHTML =
                                    '<p class="card-text mb-1">' + content + '</p>' +
                                    '<p class="card-text"><small class="text-body-secondary">' + timestamp + '</small></p>';
                                messages.appendChild(borderElement);
                            }
                        })
                        .catch(error => console.log(error));
                } catch (error) {
                    console.log(error);
                }
            }, 5000);

            async function makeRequest() {
                let response = await fetch(base_url);
                if (response.ok) {
                    return await response.json();
                } else {
                    let error = new Error(response.statusText);
                    error.response = response;
                    throw error;
                }
            }


        });
    </script>


</@main.layout>