# 应用场景： #

第三方应用授权登录：在APP或者网页接入一些第三方应用时，时长会需要用户登录另一个合作平台，比如QQ，微博，微信的授权登录。

![图片](../cloud-demo-security-Oauth2/src/main/resources/static/应用场景.png)
**名词说明：**
<br/>（1）Third-party application：第三方应用程序，本文中又称"客户端"（client），比如打开知乎，使用第三方登录，选择qq登录，这时候知乎就是客户端。
<br/>（2）HTTP service：HTTP服务提供商，本文中简称"服务提供商"，即上例的qq。
<br/>（3）Resource Owner：资源所有者，本文中又称"用户"（user）,即登录用户。
<br/>（4）User Agent：用户代理，本文中就是指浏览器。
<br/>（5）Authorization server：认证服务器，即服务提供商专门用来处理认证的服务器。
<br/>（6）Resource server：资源服务器，即服务提供商存放用户生成的资源的服务器。它与认证服务器，可以是同一台服务器，也可以是不同的服务器。

**授权方式： 授权码（authorization-code）、密码式（password）、隐藏式（implicit）、客户端凭证（client credentials）**



## 授权码模式（authorization-code） ##

**授权码模式（authorization code）是功能最完整、流程最严密的授权模式。**

<br/>（1）用户访问客户端，后者将前者导向认证服务器，假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
<br/>（2）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌：GET /oauth/token?response_type=code&client_id=test&redirect_uri=重定向页面链接。请求成功返回code授权码，一般有效时间是10分钟。
<br/>（3）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。POST /oauth/token?response_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=重定向页面链接。


    +--------+                                +-------------+
    |        |--(A)- Authorization Request -> |             |
    |        |                                |   Resource  |
    |        |<-(B)- Authorization Grant   -- |    Owner    |
    |        |                                |             |
    |        |                                +-------------+
    |        |								  
    |        |                                +-------------+
    |        |--(C)- Authorization Grant   -> |             |
    | Client |                                |Authorization|
    |        |<-(D)-     Access Token      -- |   Server  	|
    |        |                                |             |
    |        |                                +-------------+
    |        |
    |        |                                +-------------+
    |        |--(E)-    Access Token       -- |             |
    |        |                                |   Resource  |
    |        |<-(F)-  Protected Resource   -- |   Server  	|
    |        |                                |             |
    +--------+                                +-------------+

步骤说明：
<br/>（A）用户打开客户端以后，客户端要求用户给予授权。
<br/>（B）用户同意给予客户端授权。
<br/>（C）客户端使用上一步获得的授权，向认证服务器申请令牌。
<br/>（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。
<br/>（E）客户端使用令牌，向资源服务器申请获取资源。
<br/>（F）资源服务器确认令牌无误，同意向客户端开放资源。

## 用户名密码模式（password） ##
<br/>用户向客户端提供自己的用户名和密码，一般不支持refresh token。

    +--------+							
    |        |
    |Resource|
    | Owner	 |
    |        |	
 	+--------+
	    ∨
	    |  	  Resource Owner
	   (A)
	    |  	(password credentials)
	    ∨							  
    +--------+     (password credentials)     +-------------+
    |        |--(B)-   Resource Owner      -> |             |
    | Client |                                |Authorization|
    |        |<-(C)-    Access Token       -- |   Server  	|
    |        |     (Option Refresh Token)     |             |
    +--------+                                +-------------+

步骤说明：
<br/>（A）用户向客户端提供用户名和密码。
<br/>（B）客户端将用户名和密码发给认证服务器，向后者请求令牌。
<br/>（C）认证服务器确认无误后，向客户端提供访问令牌。

## 隐藏模式（implicit） ##
不通过第三方应用程序的服务器，直接在浏览器中向认证服务器申请令牌，跳过了"授权码"这个步骤。所有步骤在浏览器中完成，令牌对访问者是可见的，且客户端不需要认证。<br/>

步骤说明：
<br/>（A）客户端将用户导向认证服务器。
<br/>（B）用户决定是否给于客户端授权。
<br/>（C）假设用户给予授权，认证服务器将用户导向客户端指定的"重定向URI"，并在URI的Hash部分包含了访问令牌。
<br/>（D）浏览器向资源服务器发出请求，其中不包括上一步收到的Hash值。
<br/>（E）资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌。
<br/>（F）浏览器执行上一步获得的脚本，提取出令牌。
<br/>（G）浏览器将令牌发给客户端。


## 客户端模式（client credentials） ##
用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务，其实不存在授权问题

    +--------+                                +-------------+
    |        |--(A)- Client Authorizatio   -> |             |
    | Client |                                |Authorization|
    |        |<-(B)-  Access Token        --  |   Server  	|
    |        |                                |             |
    +--------+                                +-------------+

步骤说明：
<br/>（A）客户端向认证服务器进行身份认证，并要求一个访问令牌。
<br/>（B）认证服务器确认无误后，向客户端提供访问令牌。