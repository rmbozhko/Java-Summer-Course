<#macro user_info>
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
        <td>Account is active</td>
        <td>${user.active?then('True', 'False')}</td>
    </tr>
    </tbody>
</table>
<#nested>
<a href="/">Back to catalogue</a>
</#macro>