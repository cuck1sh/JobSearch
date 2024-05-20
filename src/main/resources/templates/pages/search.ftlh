<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <form class="mt-4 w-75" id="form">
        <div class="mb-3">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <label for="text" class="form-label"><@spring.message 'search.title'/></label>
            <input type="text" id="input" name="text" class="form-control">
        </div>
    </form>

    <div class="mt-3" id="vacancies">

    </div>

    <div hidden="hidden" id="hidden-company"><@spring.message 'search.company'/></div>
    <div hidden="hidden" id="hidden-vacancy"><@spring.message 'search.vacancy'/></div>
    <div hidden="hidden" id="hidden-description"><@spring.message 'search.description'/></div>
    <div hidden="hidden" id="hidden-parentCategory"><@spring.message 'search.parent.category'/></div>
    <div hidden="hidden" id="hidden-category"><@spring.message 'search.category'/></div>
    <div hidden="hidden" id="hidden-expFrom"><@spring.message 'search.expFrom'/></div>
    <div hidden="hidden" id="hidden-extTo"><@spring.message 'search.extTo'/></div>
    <div hidden="hidden" id="hidden-salary"><@spring.message 'search.salary'/></div>
    <div hidden="hidden" id="hidden-creationDate"><@spring.message 'search.creation.date'/></div>
    <div hidden="hidden" id="hidden-link"><@spring.message 'search.link'/></div>
    <div hidden="hidden" id="hidden-linkText"><@spring.message 'search.link.text'/></div>

    <script src="/static/js/search.js">

    </script>


</@main.layout>