# InstaNotify

##即時通知系統##
##2015-2016##
大學畢業專題介紹影片:
https://youtu.be/wEy2dKvQvd0

    Used Techique:
        Java. Android. Google Cloud Messenging(Deprecated, Replaced by firebase)
        Microsoft SQL Server, JDBC(Tool that enable JAVA communicate with MSSQL)
               
![img](http://i.imgur.com/eAh7U77l.jpg "App Screenshot 1")

![img](http://i.imgur.com/X18MUOdl.jpg "App Screenshot 2")

![img](http://i.imgur.com/FoGJYWWl.jpg "App Screenshot 3")



    GCM Device Token + Account ID與資料庫互動規則
        1.同一支手機 不同Account, 找regis_id的ID
        如不等於目前帳號的ID, 就刪除資料表中的該筆資料
        **insert目前ID之 "Token"**
        2.不同手機 同一個Account, 找ID的regis_id
        如不等於目前手機的Token, 就刪除資料表中的該筆資料
        **insert "目前ID + Token"**
