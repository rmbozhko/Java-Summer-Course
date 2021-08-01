<#import "parts/common.ftl" as common>
<#import "parts/profile.ftl" as profile>
<@common.page title="Profile | Info">
    <@profile.user_info>
    <table>
        <thead>
        <tr>
            <th>Book data</th>
            <th>Keep until</th>
            <th>Penalty</th>
        </tr>
        </thead>
        <tbody>
            <#list loans as loan>
                <tr>
                    <td><span>${loan.book.title} | ${loan.book.author} | ${loan.book.ISBN}</span></td>
                    <td>${loan.endDate}</td>
                    <td>
                        <#if (loan.penalty > 0.0)>
                            ${loan.penalty}
                        <#else>
                            -
                        </#if>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    </@profile.user_info>
</@common.page>