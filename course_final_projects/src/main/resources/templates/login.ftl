<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>
<@common.page title = "Log In">
    <p>Log In</p>
    <@auth.signin path = "/login" />
    <a href="/signup">Sign Up</a>
</@common.page>