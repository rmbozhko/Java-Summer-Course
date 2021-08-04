<#macro page title>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
<#if message??>
    <h3>${message}</h3>
</#if>
<#nested>
</body>
</html>
</#macro>