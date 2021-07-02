# Net Protocol

Server and clients connect with each other following TCP standard.

### Login

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |-------------- login request -------------->|
        |                                            |
        |        (if match does not exist yet)       |
        |<----------- num players request -----------|
        |------------ num players response --------->|
        |                                            |
        |<------------- login response --------------|
        |                                            |
        |         (if match full start game)         |
        |                                            |
**login request**\
{"username"}\
\
**login response**\
{valid/not valid}\
\
**num players request**\
{"message" : "how many players?"}\
\
**num players response**\
{"numPlayers"}\
\
**invalid number**\
{"message" : "Invalid number of players, please choose between 1 and 4"}\

### Match start Actions

### Action choice

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |<------------------ wake -------------------|
        |--------------- chosen action ------------->|
        |               (while invalid)              |
        |<------------------- ko --------------------|
        |                                            |
        |              (if valid action)             |
        |                     ...                    |
        |                                            |
        |--------------- turn completed ------------>|
        |              (if action executed)          |
        |<-------------- turn completed -------------|
        |                                            |
**wake**\
{"message", "your turn"}\
\
**action choice**\
{"choice"}\
\
**turn completed**\
{"message", "your turn is completed"}

### Action: buy a development card

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |--------------- buy a card ---------------->|
        |                                            |
        |              (if valid action)             |
        |<---------------- ack ----------------------|
        |                                            |
        |     (if there are not enough resources)    |
        |<------------------ error ------------------|
        |                                            |
        |               (if there are)               |
        |<----------- changed GUI object ------------|

**buy a card**\
{"color", "level", "stack"}\
\
**invalid action**\
{"message": "You have not enough resources to buy this card"}\
\
**changed GUI object**\
{[ ]}


### Action: activate production

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |------------ activate production ---------->|
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
        |                                            |
**activate production**\
{"cards"}\
\
**changed GUI object**\
{[ ]}\
\
**error message**\
{"message": "you do not have enough resources, choose another action"}\
\
**select unknown output**\
{"message": choose a type of resource you need}\
\
**choice**\
{"resource"}\

### Action: get resources from market

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |                                            |
        |--------- get resources from market ------->|
        |                                            |
        |             (if invalid index )            |
        |<-------------- select index----------------|
        |                                            |
        |            (for each white marble)         |
        |            (if white marble leader)        |
        |<------------ select active leader ---------|
        |------------------- choice ---------------->|
        |                                            |
        |<----------- select toss or stock ----------|
        |------------------ choice ----------------->|
        |                                            |
        |             (if valid action)              |
        |<------------------ ok ---------------------|
        |<----------- changed GUI object ------------|
        |                                            |

**get market resources**\
{"line", "index"}\

### Action: Switch Shelves

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |-------------- switch shelves ------------->|  
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<----------- move among shelves ------------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |

**switch**\
{"first", "second"}\
\

### Action: activate Leader Card

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |-------------- activate leader ------------>|
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<---------- ok: activated leader -----------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |

**activate leader**\
{"card"}\
\

### Action: toss Leader Card

    +--------+                                  +--------+
    | Client |                                  | Server |
    +--------+                                  +--------+
        |---------------- toss leader -------------->|
        |                                            |
        |                 (if invalid)               |
        |<------------------ error ------------------|
        |                  (if valid)                |
        |<------------- ok: toss leader -------------|
        |                                            |
        |<----------- changed GUI object ------------|
        |                                            |

**toss leader**\
{"card"}\