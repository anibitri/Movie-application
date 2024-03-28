package stores;

import structures.*;

import java.security.cert.TrustAnchor;
import java.time.LocalDate;

public class CreditsMetaData {
    private CastCredit[] FilmCast;
    private CrewCredit[] FilmCrew;
    

    public CreditsMetaData(CastCredit[] cast, CrewCredit[] crew) {
        FilmCast = cast;
        FilmCrew = crew;
    }

    public CastCredit[] getCastCredit() {
        return FilmCast;
    }

    public CrewCredit[] getCrewCredit() {
        return FilmCrew;
    }

}
