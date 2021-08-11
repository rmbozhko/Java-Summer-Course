<#import "parts/common.ftl" as common>
<@common.page title = "Validation Errors">
<table class="table">
    <thead>
    <tr>
        <th scope="col">Field name</th>
        <th scope="col">Error Description</th>
    </tr>
    </thead>
    <tbody>
    <#list violations as violation>
        <tr>
            <th>${violation.fieldName}</th>
            <td>${violation.message}</td>
        </tr>
    </#list>
    </tbody>
</table>
</@common.page>

