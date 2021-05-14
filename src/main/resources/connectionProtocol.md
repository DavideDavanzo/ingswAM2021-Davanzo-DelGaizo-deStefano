#Net Protocol
Server and clients connect with each other following TCP standard.
Every interaction/phase follows a ping check.
___
###Login
    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |<------------- login request ---------------|
        |-------------- login response ------------->|
        |                                            |
        |<------------- start request ---------------|
        |-------------- start choice --------------->|
        |                                            |
        |           (if interrupted match)           |
        |<--------------- ID request ----------------|
        |--------------- ID response --------------->|
        |   (if invalid username or password or ID)  |
        |<------------------- ko --------------------|
        |                 (if valid)                 |
        |<------------------- ok --------------------|
        |                                            |
        |               (if new match)               |
        |        (if match does not exist yet)       |
        |<----------- num players request -----------|
        |------------ num players response --------->|
        |        (if invalid number of players)      |
        |<------------------- ko --------------------|
        |                 (if valid)                 |
        |<----------- match ID assignment------------|
        |                                            |
        |          (if match exist already)          |
        |<----------- match ID assignment------------|
        |                                            |
**login request**\
{"message": "Insert username and password"}\
\
**login response**\
{"username", "password"}\
\
**start request**\
{"message": "start a new match or an interrupted one?"}\
\
**start choice**\
{"choice"}\
\
**ID request**\
{"message": "Insert interrupted match ID"\
\
**ID response**\
{"matchID"}\
\
**invalid username**\
{"message" : "invalid username, please try again"}\
\
**wrong password**\
{"message"; "wrong password,try again"}\
\
**wrong ID**\
{"message": "The ID you have inserted is wrong, please try again"}\
\
**num players request**\
{"message" : "how many players?"}\
\
**num players response**\
{"numPlayers"}\
\
**invalid number**\
{"message" : "Invalid number of players, please choose between 1 and 4"}\
\
**match ID assignment**\
{"matchID"}

###Match start Actions

###Action choice
    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |<------------------ ping -------------------|
        |------------------- pong ------------------>|
        |                                            |
        |<------------------ wake -------------------|
        |<-------- list_1 of possible action --------|
        |--------------- chosen action ------------->|
        |               (while invalid)              |
        |<------------------- ko --------------------|
        |<-------- list_1 of possible action --------|
        |                                            |
        |              (if valid action)             |
        |<-------- list_2 of possible action --------|
        |--------------- chosen action ------------->|
        |               (while invalid)              |
        |<------------------- ko --------------------|
        |<-------- list_2 of possible action --------|
        |                                            |
        |              (if valid action)             |
        |                     ...                    |
        |                                            |
        |<-------- list_1 of possible action --------|
        |--------------- chosen action ------------->|
        |               (while invalid)              |
        |<------------------- ko --------------------|
        |<-------- list_1 of possible action --------|
        |                                            |
        |              (if valid action)             |
        |<-------------- turn completed -------------|
        |                                            |
**wake**\
{"message", "your turn"}\
\
**list_1 of possible action**\
{"firstChoice": "toss first leader card (1)",\
"secondChoice": "toss second leader card (2)",\
"thirdChoice": "activate first leader card (3)",\
"fourthChoice": "activate second leader card (4)"}\
\
**action choice**\
{"choice"}\
\
**invalid choice**\
{"message": "Try again"}\
\
**list_2 of possible action**\
{"firstChoice": "get market's resources (1)",\
"secondChoice": "buy a development card (2)",\
"thirdChoice": "activate production (3)"\
\
**turn completed**\
{"message", "your turn is completed"}

###Action: buy a development card
    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |--------------- buy a card ---------------->|
        |                                            |
        |              (if valid action)             |
        |<--------------- select one ----------------|
        |<----------- list of 12 dev cards ----------|
        |---------- selected (color, level)--------->|
        |                                            |
        |                                            |
        |                                            |
        |     (if there are not enough resources)    |
        |<------------------ error ------------------|
        |<-------- list_2 of possible action --------|
        |                                            |
        |               (if there are)               |
        |<---------------- in which -----------------|
        |<----------- changed GUI object ------------|
**choice**\
{"choice": 2}\
\
**select**\
{"message": "select one of these"}\
\
**dev cards list**\
[ list of purchasable dev cards ]\
\
**selected**\
{"color", "level"}\
\
**invalid action**\
{"message": "You have not enough resources to buy this card, do you want to
              choose another action(1) or buy another card(2)?"}\
\
**changed GUI object**\
{[ ]}


###Action: activate production
    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |------------ activate production ---------->|
        |                                            |
        |              (if valid action)             |
        |                                            |
        |<--------- base production choice ----------|
        |--------- base production reply ----------->|
        |               ( if invalid )               |
        |<---------- choose another action ----------|
        |                                            |
        |              (if base production)          |
        |<------- select first input resource -------|
        |------------------- choice ---------------->|
        |<------- select second input resource ------|
        |------------------- choice ---------------->|
        |<---------- select output resource ---------|
        |------------------- choice ---------------->|
        |                                            |
        |<-------------- select one -----------------|
        |<--------- list player's dev cards ---------|
        |------------- select a, b, c... ----------->|
        |                                            |
        |        (for each extra trade, if any)      |
        |<-------- select '?' output resource -------|
        |------------------- choice ---------------->|
        |                                            |
        |            (if valid production)           |
        |<-------------------- ok -------------------|
        |<------------ changed GUI object -----------|
        |                                            |
        |     (if there are not enough resources)    |
        |<------------------ error ------------------|
        |<-------- list_2 of possible action --------|
        |                                            |
**base production**\
{"message" : "Do you want to activate the base production (1) or not (2)?"}\
\
**select action**\
{"choice"}\
\
**first choice**\
{"message" : "choose the first resource you want to transform:\
(1) Coin\
(2) Shield\
(3) Stone\
(4) Servant"}\
\
**second choice**\
{"message": "choose the second resource you want to transform:\
(1) Coin\
(2) Shield\
(3) Stone\
(4) Servant"}\
\
**output choice**\
{"message": "choose the resource you want to produce:
(1) Coin\
(2) Shield\
(3) Stone\
(4) Servant"}\
\
**changed GUI object**\
{[ ]}\
\
**error message**\
{"message": "you do not have enough resources, choose another action"}\
\
**player's dev cards**\
{player's dev card list}\
\
**select from list**\
{}\
\
**select unknown output**\
{"message": choose a type of resource you need}\

###Action: get resources from market
    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |--------- get resources from market ------->|
        |                                            |
        |               (if valid action)            |
        |<----------------- market toString() -------|
        |<------------- select row or col -----------|
        |------------------- choice ---------------->|
        |                                            |
        |             ( if invalid choice )          |
        |<-------------- select row or col-----------|
        |<--------------- select index --------------|
        |------------------- choice ---------------->|
        |                                            |
        |             (if invalid index )            |
        |<-------------- select index----------------|
        |                                            |
        |                                            |
        |            (for each white marble)         |
        |            (if white marble leader)        |
        |<------------ select active leader ---------|
        |------------------- choice ---------------->|
        |                                            |
        |             (for each resource)            |
        |<----------- select toss or stock ----------|
        |------------------ choice ----------------->|
        |                 (if stock)                 |
        |<-------------- select shelf ---------------|
        |------------------ choice ----------------->|
        |             (if valid action)              |
        |<------------------ ok ---------------------|
        |<----------- changed GUI object ------------|
        |                                            |

**move choice**\
{"choice"}\
\
**select line**\
{"message": "do you want to move a row (1) or a column (2)?"}\
\
**select index**\
{"message": "choose index"}

###Action: Switch Shelves

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |-------------- switch shelves ------------->|                                            |
        |------------------ choice ----------------->|
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<----------- move among shelves ------------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |
        
**move choice**\
{"choice"}\
\
**invalid**\
{"message" : " You cannot move those resources, you can try moving others if you want"}\
\

###Action: activate Leader Card

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |-------------- activate leader ------------>|                                            |
        |------------------ choice ----------------->|
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<---------- ok: activated leader -----------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |
        
**move choice**\
{"choice"}\
\
**invalid**\
{"message" : " You cannot activate those cards, try again "}\
\

###Action: toss Leader Card

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |---------------- toss leader -------------->|                                            |
        |------------------ choice ----------------->|
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<------------- ok: toss leader -------------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |
        
**move choice**\
{"choice"}\
\
**invalid**\
{"message" : " You cannot toss those cards, try again "}\
\