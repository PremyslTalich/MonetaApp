package cz.talich.ma.library.datanba.model

val Team.logo: String?
    get() = when (abbreviation.uppercase()) {
        "ATL" -> "https://content.sportslogos.net/logos/6/220/thumbs/22081902021.gif"
        "BOS" -> "https://content.sportslogos.net/logos/6/213/thumbs/slhg02hbef3j1ov4lsnwyol5o.gif"
        "BKN" -> "https://content.sportslogos.net/logos/6/3786/thumbs/378615012025.gif"
        "CHA" -> "https://content.sportslogos.net/logos/6/5120/thumbs/512019262015.gif"
        "CHI" -> "https://content.sportslogos.net/logos/6/221/thumbs/hj3gmh82w9hffmeh3fjm5h874.gif"
        "CLE" -> "https://content.sportslogos.net/logos/6/222/thumbs/22253692023.gif"
        "DAL" -> "https://content.sportslogos.net/logos/6/228/thumbs/22834632018.gif"
        "DEN" -> "https://content.sportslogos.net/logos/6/229/thumbs/22989262019.gif"
        "DET" -> "https://content.sportslogos.net/logos/6/223/thumbs/22321642018.gif"
        "GSW" -> "https://content.sportslogos.net/logos/6/235/thumbs/23531522020.gif"
        "HOU" -> "https://content.sportslogos.net/logos/6/230/thumbs/23068302020.gif"
        "IND" -> "https://content.sportslogos.net/logos/6/224/thumbs/22448122018.gif"
        "LAC" -> "https://content.sportslogos.net/logos/6/236/thumbs/23655422025.gif"
        "LAL" -> "https://content.sportslogos.net/logos/6/237/thumbs/23773242024.gif"
        "MEM" -> "https://content.sportslogos.net/logos/6/231/thumbs/23143732019.gif"
        "MIA" -> "https://content.sportslogos.net/logos/6/214/thumbs/burm5gh2wvjti3xhei5h16k8e.gif"
        "MIL" -> "https://content.sportslogos.net/logos/6/225/thumbs/22582752016.gif"
        "MIN" -> "https://content.sportslogos.net/logos/6/232/thumbs/23296692018.gif"
        "NOP" -> "https://content.sportslogos.net/logos/6/4962/thumbs/496292922024.gif"
        "NYK" -> "https://content.sportslogos.net/logos/6/216/thumbs/21671702024.gif"
        "OKC" -> "https://content.sportslogos.net/logos/6/2687/thumbs/khmovcnezy06c3nm05ccn0oj2.gif"
        "ORL" -> "https://content.sportslogos.net/logos/6/217/thumbs/wd9ic7qafgfb0yxs7tem7n5g4.gif"
        "PHI" -> "https://content.sportslogos.net/logos/6/218/thumbs/21870342016.gif"
        "PHX" -> "https://content.sportslogos.net/logos/6/238/thumbs/23843702014.gif"
        "POR" -> "https://content.sportslogos.net/logos/6/239/thumbs/23997252018.gif"
        "SAC" -> "https://content.sportslogos.net/logos/6/240/thumbs/24040432017.gif"
        "SAS" -> "https://content.sportslogos.net/logos/6/233/thumbs/23325472018.gif"
        "TOR" -> "https://content.sportslogos.net/logos/6/227/thumbs/22770242021.gif"
        "UTA" -> "https://content.sportslogos.net/logos/6/234/thumbs/23485132023.gif"
        "WAS" -> "https://content.sportslogos.net/logos/6/219/thumbs/21956712016.gif"
        else -> null
    }
