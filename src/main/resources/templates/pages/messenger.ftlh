<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div id="responseId" hidden="hidden">${respondId}</div>

    <form id="form">
        <div class="card mb-3">
            <div class="row">
                <div class="col-md-4">

                    <div class="card border mt-5 ms-5" style="max-width: 18rem;">
                        <img src="/api/users/avatar/${messenger.employerProfile.id}" class="card-img-top h-75"
                             alt="avatar">
                        <div class="card-body">
                            <h4>${messenger.employerProfile.name}</h4>
                            <h5 class="card-title">
                                <a href="/users/profile/${messenger.employerProfile.email}"
                                   class="text-secondary">${messenger.employerProfile.email}</a>
                            </h5>
                            <p class="card-text"><@spring.message 'messenger.age'/> ${messenger.employerProfile.age}</p>
                            <p class="card-text"><@spring.message 'messenger.phone'/> ${messenger.employerProfile.phoneNumber}</p>
                        </div>
                        <div class="card-footer bg-transparent border">${messenger.employerProfile.accountType}</div>

                    </div>
                </div>


                <div class="col-md-8">
                    <div class="card-body" id="messages">
                        <h4><span class="text-danger"><@spring.message 'messenger.vacancy'/> </span>${vacancy.name}</h4>
                        <p><@spring.message 'messenger.vacancy.salary'/> ${vacancy.salary!'-'}</p>

                        <h4><span class="text-danger"><@spring.message 'messenger.resume'/> </span>${resume.name}</h4>
                        <p><@spring.message 'messenger.resume.salary'/> ${resume.salary!'-'}</p>

                        <hr>

                        <h5 class="card-title mb-4"><@spring.message 'messenger.messenger'/></h5>
                    </div>

                    <div class="card-footer bg-transparent border rounded-3">
                        <div class="row">
                            <div class="col-10">
                                <input type="text" id="messageValue" name="message" class="form-control"
                                       placeholder="<@spring.message 'messenger.placeholder'/>">
                            </div>

                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>

                            <div class="col-2">
                                <button type="submit" id="submit"
                                        class="btn bg-success p-2 text-dark bg-opacity-10 rounded-3">
                                    <@spring.message 'messenger.send'/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <script src="/static/js/messenger.js"></script>

</@main.layout>