# ProjectX
The goal of this project is to create a databases for a movie store using SQLite and GUI for the movie store using javaFX application.   

# Online   Movie   Store   | Database CSS   475:   | Database   Systems   Final   Project   |   Summer 2017      
#                                   University   Of   Washington 
# INTRODUCTION
This   document   provides   an   overview   of   the   Online   Movie   Store   database   developed by   the   ProjectX   team   for   the   final   project   in   Database   Systems   class   at   University   of Washington.   The   following   sections   outline   the   database   application,   lists   the entities   that   the   database   stores   and   provides   some   SQL   queries   that   were   used   to build   the   database.   The   document   concludes   with   a   reflection   of   the   project.
# DATABASE   APPLICATION
The   application   developed   is   an   Online   Movie   Store   which   stores   several   movies, that   customers   are   able   to   watch   depending   on   the   kind   of   Subscription   plan   they paid   for.   The   application   allows   Customers   to   create   a   profile   and   choose   among three   subscription   plans:   HD,   Non   HD   and   TV-Shows   that   are   varying   in   prices   and the   kind   of   content   that   is   available.   The   Customer   is   able   to   login   using   their   name and   the   their   unique   account   number   as   the   password.      The   Customer   is   able   to rent   movies   or   tv-shows   as   long   as   it   exists   in   the   database.   The   Online   Movie   Store holds   a   maximum   of   20   copies   for   the   movies   and   tv-shows.   The   administrator   has authority   to   manage   checking   in   and   checking   out   of   movies   and   tv   shows,   keep track   of   the   movie   inventory   and   track   Customers   along   with   the   type   of subscription   they   have   signed   up   for.
# USER     REQUIREMENT 
## CUSTOMER: 
* Each   customer’s   will   have   a   first   name,   middle   initial   and   last   name.
* Each   customer   has   unique   ID   number   provided   by   the   store.   The   unique   ID   is
6   digits   total.
* Each   customer’s   will   have   an   address   comprising   of   street,   city,   state   and   zip
code.
* Each   customer’s   subscription   date   will   be   stored.
* Each   customer’s   phone   and   email   address   is   stored.
* Each   customer   has   a   date   of   birth.
* Each   customer   must   have   a   credit   card   that   can   be   used   to   pay   for   the Subscription. 
## MOVIE:
* Each   movie   has   a   unique   ID
* Each   movie   has   a   title,   genre,   actors   and   director's   name,   release   date,   movie
and   a   type   (HD,   Non-HD   or   TV-Shows)
* Each   movie   has   a   copy   (1-20).   A   movie   copy   does   not   exist   if   the
corresponding   movie   does   not   exist 
## CHECKOUT :
* A   checkout   is   a   unique   transaction   occurring   any   particular   time   a   customer checks   out   a   movie.
* A   customer   makes   a   rental,   the   customer   he/she   is   at   least   one   copy,   any may   rent   up   to   5   copies.
* Each   rental   is   made   by   exactly   one   customer
* A   customer   can   renew   their   rental   and   submit   their   rental   PAYMENT:
* Each   transaction/payment   is   uniquely   identified   by   the   payment   ID
* Only   the   credit   card   type   is   stored   in   the   database  
**   Due   project   limitation
* Each   payments   credit   card   may   belong   to   exactly   one   customer
* The   payment   due   date   is   stored   in   database.   Payments   will   be   due   after   29
days   of   subscription SUBSCRIPTION :
* Each   subscription   is   uniquely   identified   by   the   subscription   ID.
* Each   subscription   offers   three   types   of   subscription   plan:   HD,   Non-HD   and
Tv-   shows.
* Each   subscription   package   has   its   own   price.

## ENTITIES
The   following   section   outlines   all   the   Entities   that   the   database   tracks   and   lists   its primary   key,   Foreign   keys   if   any,   and   all   it’s   attributes.
## CUSTOMER
* Primary   key:     Customer_ID
* Foreign   key:    Subscription_id   references   SUBSCRIPTION   record.
* Attributes :     Customer_id,   Customer_fname,   Customer_minit,
Customer_lname,   Customer_phone,   Customer_email,   Address,   City,   State, Zip,   DOB,   Subscription_date
## MOVIE
* Primary   key:     Movie_id
* Foreign   key:     Movie_type   ,   Movie_genre
* Attributes:     Movie_id,   Movie_title,   Movie_year,   Movie_genre,   Moive_director,
Movie_actor,   Movie_copy,   Movie_type SUB_TYPE
* Primary   key:     sub_ID
* Foreign   key:     None
* Attributes:     sub_id,   type,   price
## GENRE
* Primary   key:     Genre_ID
* Foreign   key:    None
* Attributes:     Genre_ID,   Genre_type
## PAYMENT
* Primary   key:    Payment_id
* Foreign   key:     Customer_id   references   CUSTOMER   record,   Subscription_id
* references   SUBSCRIPTION   record
* Attributes:    Payment_id,   Payment_method,   Payment_date,   Customer_id,
* (Customer_id),   Subscription_id SUBSCRIPTION
* Primary   key:     Subscription_id
* Foreign   key:    Customer_id
* Attributes:    Subscription_id,   Customer_id,   Sub_plan,   sub_type
## CHECKOUT
* Foreign   key:    Customer_id,   Movie_id
* Attributes:     Customer_id,   Movie_id,   renew_count,   checkout_time

