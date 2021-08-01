<#import "parts/common.ftl" as common>
<#import "parts/profile.ftl" as profile>
<@common.page title="Profile | Info">
    <@profile.user_info>
    <table>
        <thead>
        <tr>
            <th>Loans' data</th>
        </tr>
        <tr>
            <th>Book data</th>
            <th>Penalty</th>
        </tr>
        </thead>
        <tbody>
            <#list loans as loan>
                <tr>
                    <td><span>${loan.book.title} | ${loan.book.author} | ${loan.book.ISBN}</span></td>
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
    <table>
        <thead>
        <tr>
            <th>Users' data</th>
        </tr>
        <tr>
            <th>User data</th>
            <th>Subscription token</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td><span>${user.username} | ${user.password} | ${user.active?then('True', 'False')}</span></td>
                <td><span>${user.subscription.id}</span></td>
            </tr>
        </#list>
        </tbody>
    </table>
    </@profile.user_info>
</@common.page>