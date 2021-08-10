<#macro signin>
    <div class="container text-center">
        <div class="row justify-content-md-center">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default" style="margin-top:45px">
                    <div class="panel-heading">
                        <h3 class="panel-title">Log in to E-Library</h3>
                    </div>
                    <div class="panel-body">
                        <#if logout??>
                            <div class="alert alert-info" role="alert">${logout}</div>
                        <#elseif error??>
                            <div class="alert alert-danger" role="alert">${error}</div>
                        </#if>
                        <form action="/login" method="post" class="col-lg-5 offset-lg-4">
                            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                            <div class="row">
                                <div class="col">
                                    <label for="username">Username</label>
                                    <input type="text" class="form-control" id="username" placeholder="" name="username">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <label for="password">Password</label>
                                    <input type="password" class="form-control" id="password" placeholder="" name="password">
                                </div>
                            </div>
                            <br>
                            <div class="text-center">
                                <button type="submit" class="btn text-center btn-primary">Log In</button>
                                <p class="text-inverse">Haven't an account yet? <a href="/signup" data-abc="true">Sign Up</a></p>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro signup>
    <div class="container text-center">
        <div class="row justify-content-md-center">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default" style="margin-top:45px">
                    <div class="panel-heading">
                        <h3 class="panel-title">Sign Up and become a member of E-Library</h3>
                    </div>
                    <div class="panel-body">
                        <form action="/signup" method="post" class="col-lg-8 offset-lg-2">
                            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                            <div class="row">
                                <div class="col">
                                    <label for="username">Username</label>
                                    <input type="text" class="form-control" id="username" placeholder="Username" name="username" required>
                                </div>
                                <div class="col">
                                    <label for="password">Password</label>
                                    <input type="text" class="form-control" id="password" placeholder="Password" name="password" required>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <label for="firstName">First Name</label>
                                    <input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName" required>
                                </div>
                                <div class="col">
                                    <label for="lastName">Last Name</label>
                                    <input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label for="email">Email</label>
                                <input type="text" class="form-control" id="email" placeholder="Email" name="email" required>
                            </div>
                            <br>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary">Sign Up</button>
                            </div>
                        </form>
                        <p class="text-inverse text-center">Already have an account? <a href=/login" data-abc="true">Login</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>