# red-reader

Currently you can just open it in IntelliJ IDEA and HIT RUN


[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/go5xfN3Ldqk/0.jpg)](https://www.youtube.com/watch?v=go5xfN3Ldqk)

Requirements to authenticate:
You have to go to reddit.com , login and create a user script app.

Afterwords open https://github.com/gochev/red-reader/blob/master/src/sample/service/RedditService.java  and fill your credentials

````
 Credentials credentials = Credentials.script("username", "password",
            "18t2hp3BFVnT0Q", "-vwVXoGfNVKUD6yknrYXBlTDxO8");
 ````

TODO :
- Upgrade JRAW
- fix the JRAW to use interactive authentication and not the one above (there is a bug I wait to be fixed https://github.com/mattbdean/JRAW/issues/290)
- Implement all of the buttons :)
- Ask https://www.teamdev.com/jxbrowser for a Open-Source license
- Change JavaFX WebView to use JxBrowser
