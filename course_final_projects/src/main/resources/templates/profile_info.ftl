<#import "parts/common.ftl" as common>
<@common.page title="Profile | Info">
    <table>
        <thead>
        <tr>
            <th colspan="2">User info</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Username</td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td>Password</td>
            <td>${user.password}</td>
        </tr>
        <tr>
            <td>Account is active</td>
            <td>${user.active?then('True', 'False')}</td>
        </tr>
        </tbody>
    </table>
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
    <a href="/">Back to catalogue</a>
</@common.page>