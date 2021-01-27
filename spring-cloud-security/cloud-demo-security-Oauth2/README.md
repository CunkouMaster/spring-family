# 应用场景： #

第三方应用授权登录：在APP或者网页接入一些第三方应用时，时长会需要用户登录另一个合作平台，比如QQ，微博，微信的授权登录。

![Image text](https://github.com/CunkouMaster/spring-family/blob/master/spring-cloud-security/cloud-demo-security-Oauth2/src/main/resources/static/%E5%BA%94%E7%94%A8%E5%9C%BA%E6%99%AF.png)

<br/>（1）Third-party application：第三方应用程序，本文中又称"客户端"（client），比如打开知乎，使用第三方登录，选择qq登录，这时候知乎就是客户端。</br>
<br/>（2）HTTP service：HTTP服务提供商，本文中简称"服务提供商"，即上例的qq。</br>
<br/>（3）Resource Owner：资源所有者，本文中又称"用户"（user）,即登录用户。</br>
<br/>（4）User Agent：用户代理，本文中就是指浏览器。</br>
<br/>（5）Authorization server：认证服务器，即服务提供商专门用来处理认证的服务器。</br>
<br/>（6）Resource server：资源服务器，即服务提供商存放用户生成的资源的服务器。它与认证服务器，可以是同一台服务器，也可以是不同的服务器。</br>
