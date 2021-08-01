<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>
<@common.page title = "Sign Up">
    <p>Sign Up</p>
    ${message}
    <@auth.signin path = "/signup" />
    <a href="/signup">Sign Up</a>
</@common.page>