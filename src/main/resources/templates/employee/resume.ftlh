<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${resume.name}</h1>
        </div>
        <div class="card-body">

            <div class="col-sm-6 my-4 mb-sm-0 mx-auto">
                <table class="table text-start table-striped">
                    <tbody>
                    <tr>
                        <th scope="row">Профиль/ Email:</th>
                        <td class="pt-2"><a href="/users/profile/${resume.userEmail}"
                                            class="text-decoration-none border border-primary rounded-4 p-1">${resume.userEmail}</a>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Родительская категория:</th>
                        <td>
                            <#if resume.category??>
                                <#if resume.category.parent??> ${resume.category.parent} <#else> - </#if>
                            <#else>
                                -
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Категория:</th>
                        <td>
                            <#if resume.category??>
                                <#if resume.category.name??> ${resume.category.name} <#else> - </#if>
                            <#else>
                                -
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Заработная плата:</th>
                        <td><#if resume.salary??> ${resume.salary} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Активно:</th>
                        <td><#if resume.isActive??> ${resume.isActive?string('Да', 'Нет')} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Дата создания:</th>
                        <td>${resume.createdDate.toLocalDate()}</td>
                    </tr>

                    </tbody>
                </table>
            </div>


            <div class="row mt-3">
                <h2 class="mb-3">Опыт работы:</h2>
                <#if resume.workExperienceInfoDtos??>
                    <div class="col-sm-8 mb-3 mb-sm-0 mx-auto">
                        <table class="table">
                            <thead>
                            <tr class="table-success">
                                <th scope="col">#</th>
                                <th scope="col">Компания</th>
                                <th scope="col">Стаж(лет)</th>
                                <th scope="col">Позиция</th>
                                <th scope="col">Обязанности</th>
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
                <h2 class="mb-3">Образование:</h2>
                <#if resume.educationInfos??>
                    <div class="col-sm-8 mb-3 mb-sm-0 mx-auto">
                        <table class="table">
                            <thead>
                            <tr class="table-info">
                                <th scope="col">#</th>
                                <th scope="col">Учреждение</th>
                                <th scope="col">Программа обучения</th>
                                <th scope="col">Дата начала</th>
                                <th scope="col">Дата завершения</th>
                                <th scope="col">Степень</th>
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
                <h2 class="mt-2">Контакты:</h2>
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
        <div class="card-footer text-body-secondary">
            Дата обновления: ${resume.updateTime.toLocalDate()}
        </div>

    </div>

</@main.layout>