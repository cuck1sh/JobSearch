<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout>

    <h1 class="text-center text-danger mb-1">Резюме:</h1>

    <div class="dropdown d-flex justify-content-center m-3">
        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: Кол-во откликов
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=${page.getSize()}&filter=${filter}">none</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsResumeId,desc&filter=${filter}">По
                    убыванию</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsResumeId,asc&filter=${filter}">По
                    возрастанию</a></li>
        </ul>

        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: Дата создания
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=${page.getSize()}&filter=${filter}">none</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,desc&filter=${filter}">Новые</a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,asc&filter=${filter}">Старые</a>
            </li>
        </ul>

        <button class="btn border dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            Выберите категорию
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=#{page.getSize()}&sort=${sort}">none</a></li>
            <#list categories as category>
                <li><a class="dropdown-item"
                       href="?page=0&size=#{page.getSize()}&sort=${sort}&filter=${category.name}">${category.name}</a>
                </li>
            </#list>
        </ul>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
        <#list page.content as resume>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <a href="/employer/resumes/${resume.id}"><h3 class="card-title mb-4">${resume.name}</h3></a>

                        <#if resume.category??>
                            <#if resume.category.parent??>
                                <h6 class="card-title">Родительская категория: ${resume.category.parent}</h6>
                            </#if>

                            <#if resume.category.name??>
                                <h6 class="card-title">Категория: ${resume.category.name}</h6>
                            </#if>
                        </#if>

                        <h6 class="card-title">${resume.userEmail}</h6>


                        <#if resume.salary??>
                            <h4 class="card-title">Заработная плата: ${resume.salary}</h4>
                        </#if>

                    </div>
                    <div class="card-footer">
                        <small class="text-body-secondary">Дата обновления ${resume.updateTime.toLocalDate()}</small>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <@p.pager url page filter sort/>

</@main.layout>