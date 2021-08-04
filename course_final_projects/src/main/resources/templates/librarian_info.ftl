<#import "parts/common.ftl" as common>
<#import "parts/profile.ftl" as profile>
<@common.page title="Profile | Info">
    <@profile.user_info>
    <form method="post" action="/librarian/update/presence">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="radio" name="present" value="False" checked>Absent
        <input type="radio" name="present" value="True">On Duty
        <button type="submit">Update presence</button>
    </form>
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
            <th>Readers' data</th>
        </tr>
        <tr>
            <th>Reader data</th>
            <th>Subscription token</th>
        </tr>
        </thead>
        <tbody>
        <#list readers?keys as key>
            <tr>
                <td><span>${readers[key].username} | ${readers[key].password} | ${readers[key].active?then('True', 'False')}</span></td>
                <td><span>${key}</span></td>
            </tr>
        </#list>
        </tbody>
    </table>
    </@profile.user_info>
</@common.page>