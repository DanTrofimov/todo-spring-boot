<#import "spring.ftl" as spring/>
<#import "tags/header.ftl" as header/>
<#import "tags/registration-form.ftl" as regForm/>
<#import "tags/headerImports.ftl" as imports/>
<#import "tags/footerImports.ftl" as footerImports/>
<#import "tags/weatherItem.ftl" as weatherItem>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registration</title>
    <link rel="stylesheet" href='<@spring.url "/styles/auth.css"/>' type="text/css">
    <link rel="icon" href='<@spring.url "/assets/favicon.ico"/>' type="image/x-icon" />
    <@imports.imports />
</head>
<body>
    <@header.header />
    <div class="registration-content">
        <div>
            <@regForm.registrationForm />
            <link rel="stylesheet" href="<@spring.url '/styles/form.css' />" type="text/css">

            <#if currentUser??>
                <p class="link-container">
                <#if currentUser.getRole() == "USER">
                    <a href="<@spring.url "/main"/>"><@spring.message 'sign_up_page.registration.profile'/></a>
                <#else>
                    <a href="<@spring.url "/admin"/>"><@spring.message 'sign_up_page.registration.profile'/></a>
                </#if>
                </p>
                <#else>
                <p class="link-container">
                    <a href="<@spring.url "/sign-in"/>"><@spring.message 'sign_up_page.registration.sign_in'/></a>
                </p>
            </#if>
            <#if signUpError??>
                <p class="link-container error-message">
                    ${ signUpError }
                </p>
            </#if>
        </div>
<#--        <div>-->
<#--            <img src="${ springMacroRequestContext.contextPath }/static/assets/welcome-image.png" alt="welcome-image" class="welcome-image">-->
<#--        </div>-->
        <div>
            <#if weatherData??>
                <@weatherItem.weatherItem weatherData/>
            </#if>
        </div>
    </div>
    <@footerImports.footerImports />
</body>
</html>
