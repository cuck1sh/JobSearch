<#import  "layout.ftlh" as main>
<@main.layout>

    <div class="row row-cols-1 row-cols-md-3 g-4">
    <#list vacancies as vacancy>
        <div class="col">
            <div class="card h-100">
                <div class="card-body">
                    <a href="vacancies/${vacancy.id}"><h3 class="card-title">${vacancy.name}</h3></a>

                    <#if vacancy.description??>
                        <h4 class="card-text">${vacancy.description}</h4>
                    </#if>

                    <h4 class="card-title">${vacancy.userEmail}</h4>


                    <#if vacancy.salary??>
                        <h5 class="card-title">Заработная плата: ${vacancy.salary}</h5>
                    </#if>

                    <#if vacancy.expFrom??>
                        <h5 class="card-title">Опыт от: ${vacancy.expFrom} (лет)</h5>
                    </#if>

                    <#if vacancy.expTo??>
                        <h5 class="card-title">Опыт до: ${vacancy.expTo} (лет)</h5>
                    </#if>
                </div>
                <div class="card-footer">
                    <small class="text-body-secondary">Дата обновления ${vacancy.updateTime}</small>
                </div>
            </div>
        </div>
    </#list>

</@main.layout>