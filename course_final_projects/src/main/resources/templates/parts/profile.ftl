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
    <tr>
        <td>First name</td>
        <td>${user.firstName}</td>
    </tr>
    <tr>
        <td>Last name</td>
        <td>${user.lastName}</td>
    </tr>
    <tr>
        <td>E-mail</td>
        <td>${user.email}</td>
    </tr>
    </tbody>
</table>
<#nested>
</#macro>