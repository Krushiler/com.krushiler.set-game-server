## Base response format

+ Success

```
{
    "success": true,
    "exception": null,
    "nickname": "Andrey",
    "accessToken": "JusOh2nRK1kZpxzK"
}
```

+ Error
```
{
    "success": false,
    "exception": {
        "message": "Nickname or password is incorrect"
    }
}
```

### Registration
```/user/register```

+ Request
```
{
    "nickname":"Andrey",
    "password":"strongpassword1337"
}
```

+ Response
```
{
    "nickname": "Andrey",
    "accessToken": "P3Z7su6UI888hKOE"
}
```

### Websocket
```ws://<url>/set```

+ send token

## Game
### Create
```/set/room/create```

+ Request
```
{
    "accessToken":"eY2VZi90YhhkU1Yo"
}
```

+ Response
```
{
    "success": true,
    "exception": null,
    "gameId": 0
}
```

### List of games
```/set/room/list```

+ Request
```
{
    "accessToken":"eY2VZi90YhhkU1Yo"
}
```

+ Response
```
{
    "games": [
        {
            "id": 0
        }
    ]
}
```

### Enter the game
```/set/room/list/enter```

+ Request
```
{
    "accessToken":"eY2VZi90YhhkU1Yo",
    "gameId": 0
}
```

+ Response
```
{
    "success": true,
    "exception": null,
    "gameId": 0
}
```

## When user is in-game

### Field
```/set/field```

+ Request
```
{
    "accessToken":"eY2VZi90YhhkU1Yo"
}
```

+ Response
```
{
    "cards": [
        {
            "id": 6,
            "color": 1,
            "shape": 2,
            "fill": 3,
            "count": 1
        },
        {
            "id": 21,
            "color": 3,
            "shape": 1,
            "fill": 3,
            "count": 2
        },
        {
            "id": 3,
            "color": 1,
            "shape": 1,
            "fill": 3,
            "count": 3
        },
        {
            "id": 24,
            "color": 3,
            "shape": 2,
            "fill": 3,
            "count": 1
        },
        {
            "id": 15,
            "color": 2,
            "shape": 2,
            "fill": 3,
            "count": 2
        },
        {
            "id": 5,
            "color": 1,
            "shape": 2,
            "fill": 2,
            "count": 3
        },
        {
            "id": 25,
            "color": 3,
            "shape": 3,
            "fill": 1,
            "count": 1
        },
        {
            "id": 27,
            "color": 3,
            "shape": 3,
            "fill": 3,
            "count": 2
        },
        {
            "id": 10,
            "color": 2,
            "shape": 1,
            "fill": 1,
            "count": 3
        }
    ],
    "score": 0
}
```

### Pick

```/set/pick```

+ Request
```
{
    "accessToken":"eY2VZi90YhhkU1Yo",
    "cards":[
        11, 7, 29
    ]
}
```

+ Response
```
{
    "isSet": false,
    "score": 0
}
```