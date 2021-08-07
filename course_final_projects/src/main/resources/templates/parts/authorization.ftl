<#macro signin>
    <div class="container">
        <div class="row">
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
                        <form action="/login" method="post">
                            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                            <div class="form-row">
                                <div class="form-group col-md-6">
                                    <label for="username">Username</label>
                                    <input type="text" class="form-control" id="username" placeholder="" name="username">
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="inputPassword4">Password</label>
                                    <input type="password" class="form-control" id="inputPassword4" placeholder="" name="password">
                                </div>
                            </div>
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

<#macro signout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Sign Out</button>
    </form>
</#macro>

<#macro signup>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default" style="margin-top:45px">
                    <div class="panel-heading">
                        <h3 class="panel-title">Sign Up and become a member of E-Library</h3>
                    </div>
                    <div class="panel-body">
                        <form action="signup" method="post">
                            <form>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="username">Username</label>
                                        <input type="text" class="form-control" id="username" placeholder="Username" name="username">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="inputPassword4">Password</label>
                                        <input type="text" class="form-control" id="inputPassword4" placeholder="Password" name="password">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="firstname">First Name</label>
                                        <input type="text" class="form-control" id="firstname" placeholder="First Name" name="first_name">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="lastname">Last Name</label>
                                        <input type="text" class="form-control" id="lastname" placeholder="Last Name" name="last_name">
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label for="inputEmail4">Email</label>
                                    <input type="text" class="form-control" id="inputEmail4" placeholder="Email" name="email">
                                </div>
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary">Sign Up</button>
                                </div>
                                <p class="text-inverse text-center">Already have an account? <a href=/login" data-abc="true">Login</a></p>
                            </form>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>