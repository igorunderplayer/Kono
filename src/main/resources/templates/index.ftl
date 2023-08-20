<html>
    <head>
        <link rel="stylesheet" href="/static/index.css" />

        <style>
            main {
                min-height: 100vh;
                min-width: 100vw;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                gap: 4px;
            }
        </style>
    </head>

    <body>
        <main>
            <h1>Kono</h1>
            <img src="${bot.avatar.cdnUrl.toUrl()}" alt="Avatar do bot 'Kono'"/>
        </main>
    </body>
</html>