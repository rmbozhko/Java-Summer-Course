<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>
<@common.page title = "Sign Up">
    <p>Sign Up</p>
    <#if message??>
        ${message}
    </#if>
    <@auth.signin path = "/signup" />
    <a href="/signup">Sign Up</a>
</@common.page>