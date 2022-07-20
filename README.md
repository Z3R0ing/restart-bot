# Hosts checker
Telegram bot for notification about hosts is down

Every minute checks hosts from host_list.properties. If at least one will be down - sends notification to all subscribed users. Uses H2 database on file /src/main/resources/testDB.mv.db

### Commands

`/ping HOST` - check availability host. Replace "HOST" with IP address or host name

`/status` - get report about all hosts statuses

`/notification` - for subscribe or unsubscribe to notification

### Settings

Make properties file with hosts which will check:
```properties
#src/main/resources/host_list.properties
google=74.125.131.102
yandex=yandex.ru
```

Make properties file with telegram bot credentials:
```properties
#src/main/resources/host_list.properties
bot.username=your_awesome_bot
bot.token=YOUR_TOKEN
```

### How to run

In repository root:

```shell
./gradlew bootRun
```
