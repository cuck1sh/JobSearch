<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            ${resume.name}
        </div>
        <div class="card-body">
            <h5 class="card-title">Имя: ${resume.userEmail}</h5>

            <#if resume.category.parent??>
                <h5 class="card-title">Родительская категория: ${resume.category.parent}</h5>
            </#if>

            <h5 class="card-title">Категория: ${resume.category.name}</h5>

            <#if resume.salary??>
                <h5 class="card-title">Заработная плата: ${resume.salary}</h5>
            </#if>

            <#if resume.isActive??>
                <h5 class="card-title">Активно: ${resume.isActive?string('Да', 'Нет')}</h5>
            </#if>

            <h6 class="card-title">Дата создания ${resume.createdDate} </h6>

            <div class="row">
                <h2 class="my-2">Образование:</h2>
                <#list resume.workExperienceInfos as exp>
                    <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Компания: ${exp.companyName}</h5>
                                <p class="card-text">Стаж (годы): ${exp.years}</p>
                                <p class="card-text">Позиция: ${exp.position}</p>
                                <p class="card-text">Обязанности: ${exp.responsibilities}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

            <div class="row">
                <h2 class="my-2">Образование:</h2>
                <#list resume.educationInfos as education>
                    <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Учреждение: ${education.institution}</h5>
                                <p class="card-text">Программа обучения: ${education.program}</p>
                                <p class="card-text">Дата начала: ${education.startDate}</p>
                                <p class="card-text">Дата завершения: ${education.endDate}</p>
                                <#if education.degree??>
                                    <p class="card-text">Степень: ${education.degree}</p>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

            <div class="row">
                <h2 class="my-2">Контакты:</h2>
                <#list resume.contacts as contact>
                    <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${contact.type.type}</h5>
                                <p class="card-text">Контакт: ${contact.info}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>


        </div>
        <div class="card-footer text-body-secondary">
            Дата обновления ${resume.updateTime}
        </div>

    </div>

</@main.layout>