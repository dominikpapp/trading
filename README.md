# Monster Trading Cards Game

GitHub repository: https://github.com/BIF-DUA-3-WS2020-SWE/exercise-dominikpapp

The application needs the PostgreSQL database to function properly. To start the aforementioned database, execute the
following Batch-script (PostgreSQL 13 64-bit):

```
[console]::WindowWidth=50; [console]::WindowHeight=25; [console]::BufferWidth=[console]::WindowWidth

if (!([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator"))
{ Start-Process powershell.exe "-NoProfile -ExecutionPolicy Bypass -File `"$PSCommandPath`"" -Verb RunAs; exit }

net start postgresql-x64-13
```

To stop the database service once the application has been shut down, execute the following Batch-script:

```
[console]::WindowWidth=50; [console]::WindowHeight=25; [console]::BufferWidth=[console]::WindowWidth

if (!([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) 
{ Start-Process powershell.exe "-NoProfile -ExecutionPolicy Bypass -File `"$PSCommandPath`"" -Verb RunAs; exit }

net stop postgresql-x64-13
```

The create script for the database is located in `src/main/resources/db/migration/V1_schema.sql`.

Furthermore, make sure that port 10001 is available since that is the port the application's server is running on (
default setting). If the application can not connect to the database, consider
checking `src/main/resources/db.properties` and adjust the properties to match your settings.

## Design/architecture of the application

The application uses one layer each for controllers, data access objects as well as services.
`RequestController` handles routing of requests.<br>

The package `http` contains the classes needed for URL/request parsing and creation of responses.

The package `exception` contains the application's own exceptions.

`UserManager`provides functionality to log in users and to keep track of currently logged-in users.
Furthermore, `UserUtil` provides helper methods to e.g. extract username/password from requests.<br>

To be able to easily change the application's settings, I decided to use `.properties`-files which are read
by `ApplicationPropertiesReader` and `DbPropertiesReader`. They extend `PropertiesReader`, a class which
implements `IPropertiesReader` and thereby provides the functionality to read `.properties`-files.<br>

Required database functionality is described in `GenericDAO`. `AbstractDAO` implements the basic methods as given
in `GenericDAO` while the concrete DAOs override or enhance the aforementioned methods. `ConnectionController` provides
a connection to the database.

## Failures and solutions

The first basic structure that I designed for my application turned out to be somewhat of a misconception. After some
time that I spent developing in the wrong direction, I noticed that my concept did not cover all specifications (
correctly). Because of that I needed to redesign my application from scratch, however, after this redesign I managed to
move forward quite well.

## Unit-Tests

I decided to test URL-, request- and response-processing because these things are what the application depends on. If
the correctness of these functions could not be verified, it would present a big threat to the application's security,
stability and performance.

## Time tracking

**Time spent with the development of the project: approximately 57 hours**<br/>
