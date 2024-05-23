<#import  "../layout.ftlh" as main>
<@main.layout ; spring>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${resume.name}</h1>
        </div>
        <div class="card-body">

            <div class="col-sm-6 my-4 mb-sm-0 mx-auto">
                <table class="table text-start table-striped">
                    <tbody>
                    <tr>
                        <th scope="row"><@spring.message 'resume.profile'/></th>
                        <td class="pt-2"><a href="/users/profile/${resume.userEmail}"
                                            class="text-decoration-none border border-primary rounded-4 p-1">${resume.userEmail}</a>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'resume.parent.category'/></th>
                        <td>
                            <#if resume.category??>
                                <#if resume.category.parent??> ${resume.category.parent} <#else> - </#if>
                            <#else>
                                -
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'resume.category'/></th>
                        <td>
                            <#if resume.category??>
                                <#if resume.category.name??> ${resume.category.name} <#else> - </#if>
                            <#else>
                                -
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'resume.salary'/></th>
                        <td><#if resume.salary??> ${resume.salary} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'resume.active'/></th>
                        <td><#if resume.isActive??> ${resume.isActive?string('Да', 'Нет')} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'resume.creation.date'/></th>
                        <td>${resume.createdDate.toLocalDate()}</td>
                    </tr>

                    </tbody>
                </table>
            </div>


            <div class="row mt-3">
                <h2 class="mb-3"><@spring.message 'resume.work.experience'/></h2>
                <#if resume.workExperienceInfoDtos??>
                    <div class="col-sm-8 mb-3 mb-sm-0 mx-auto">
                        <table class="table">
                            <thead>
                            <tr class="table-success">
                                <th scope="col">#</th>
                                <th scope="col"><@spring.message 'resume.company'/></th>
                                <th scope="col"><@spring.message 'resume.years'/></th>
                                <th scope="col"><@spring.message 'resume.position'/></th>
                                <th scope="col"><@spring.message 'resume.responsibilities'/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list resume.workExperienceInfoDtos as exp>
                            <tbody>
                            <tr>
                                <th scope="row">${exp?counter}</th>
                                <td>${exp.companyName}</td>
                                <td>${exp.years}</td>
                                <td>${exp.position}</td>
                                <td>${exp.responsibilities}</td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </#if>
            </div>


            <div class="row mt-3">
                <h2 class="mb-3"><@spring.message 'resume.education'/></h2>
                <#if resume.educationInfos??>
                    <div class="col-sm-8 mb-3 mb-sm-0 mx-auto">
                        <table class="table">
                            <thead>
                            <tr class="table-info">
                                <th scope="col">#</th>
                                <th scope="col"><@spring.message 'resume.institution'/></th>
                                <th scope="col"><@spring.message 'resume.program'/></th>
                                <th scope="col"><@spring.message 'resume.start.date'/></th>
                                <th scope="col"><@spring.message 'resume.end.date'/></th>
                                <th scope="col"><@spring.message 'resume.end.degree'/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list resume.educationInfos as education>
                                <tr>
                                    <th scope="row">${education?counter}</th>
                                    <td>${education.institution}</td>
                                    <td>${education.program}</td>
                                    <td>${education.startDate}</td>
                                    <td>${education.endDate}</td>
                                    <td><#if education.degree??>${education.degree}<#else>-</#if></td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </#if>
            </div>


            <div class="row d-flex justify-content-center my-3">
                <h2 class="mt-2"><@spring.message 'resume.contacts'/></h2>
                <div class="row w-75 my-3 row-cols-1 row-cols-lg-2">
                    <div class="col">

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.phoneNumber??>
                                        ${resume.contacts.phoneNumber}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.email??>
                                        ${resume.contacts.email}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-envelope-at align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.telegram??>
                                        ${resume.contacts.telegram}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-telegram align-content-center fs-2"></i>
                        </div>


                    </div>

                    <div class="col">
                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.facebook??>
                                        ${resume.contacts.facebook}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-facebook align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.linkedIn??>
                                        ${resume.contacts.linkedIn}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-linkedin align-content-center fs-2"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <#if vacancies??>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-success mx-auto mb-4" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">
                <@spring.message 'vacancy.response'/>
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5"
                                id="exampleModalLabel"><@spring.message 'resume.responses.title'/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <#if vacancies?size gt 0>
                                <ol class="list-group list-group-numbered">
                                    <#list vacancies as vacancy>
                                        <li class="list-group-item d-flex justify-content-between align-items-start">
                                            <div class="ms-2 me-auto text-start">
                                                <div class="fw-bold text-start mb-2">${vacancy.name}</div>
                                                <#if vacancy.category??>
                                                    <#if vacancy.category.name??>
                                                        <p class="mb-1">
                                                            <@spring.message 'vacancy.responses.category'/> ${resume.category.name}
                                                        </p>
                                                    </#if>
                                                </#if>
                                                <#if vacancy.salary??>
                                                    <@spring.message 'vacancy.responses.salary'/> ${resume.salary}
                                                <#else>
                                                    <@spring.message 'vacancy.responses.salary'/> -
                                                </#if>

                                            </div>
                                            <button type="button"
                                                    class="btn btn-success my-auto"
                                                    onclick="responseResume(${vacancy.id}, ${resume.id})">
                                                <@spring.message 'vacancy.responses.pin'/>
                                            </button>
                                        </li>
                                    </#list>
                                </ol>
                            <#else>
                                <@spring.message 'resume.no.vacancies'/>
                            </#if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <@spring.message 'vacancy.close.button'/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </#if>


        <div class="card-footer text-body-secondary">
            <@spring.message 'resume.update.time'/> ${resume.updateTime.toLocalDate()}
        </div>

    </div>

    <script>
        function responseResume(vacancyId, resumeId) {
            const url = '/api/responses/vacancy/' + vacancyId + '/resume/' + resumeId
            fetch(url, {method: 'get'})
                .then(resp => {
                    return resp.json();
                })
                .then(data => {
                    window.location.href = '/messages/' + data
                })
                .catch(e => console.log(e))
        }
    </script>

</@main.layout>