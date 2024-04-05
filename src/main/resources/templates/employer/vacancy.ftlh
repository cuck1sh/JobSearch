<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            ${vacancy.name}
        </div>
        <div class="card-body">

            <#if vacancy.description??>
                <h5 class="card-title">Описание: ${vacancy.description}</h5>
            </#if>

            <#if vacancy.category.parent??>
                <h5 class="card-title">Родительская категория: ${vacancy.category.parent}</h5>
            </#if>

            <h5 class="card-title">Категория: ${vacancy.category.name}</h5>

            <#if vacancy.salary??>
                <h5 class="card-title">Заработная плата: ${vacancy.salary}</h5>
            </#if>

            <#if vacancy.expFrom??>
                <h5 class="card-title">Опыт от: ${vacancy.expFrom} (лет)</h5>
            </#if>

            <#if vacancy.expTo??>
                <h5 class="card-title">Опыт до: ${vacancy.expTo} (лет)</h5>
            </#if>

            <#if vacancy.isActive??>
                <h5 class="card-title">Активно: ${vacancy.isActive?string('Да', 'Нет')}</h5>
            </#if>

            <h5 class="card-title">Автор: ${vacancy.userEmail}</h5>

            <h6 class="card-title">Дата создания ${vacancy.createdDate} </h6>


        </div>
        <div class="card-footer text-body-secondary">
            Дата обновления ${vacancy.updateTime}
        </div>

    </div>

</@main.layout>