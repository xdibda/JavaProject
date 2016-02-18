package othello;

import java.util.EmptyStackException;

class ComputerHasPlayed extends Exception {
    char x; int y;
    ComputerHasPlayed(int x, int y) {
        this.x = Utility.transformIntToChar(x);
        this.y = y + 1;
    }
    public String toString() {
        return Utility.PlayerType.COMP.getName() + " tahl na pole: " + x + " " + y + "\n" + Utility.getPlayerTurnString(Utility.PLAYERONE, Utility.PlayerType.HUMAN);
    }

    int getX() { return x; }
    int getY() { return y; }
}

class FieldIsEmptyException extends Exception {
    public String toString() {
        return "Prazdne pole nema kamen.";
    }
}

class NotEnoughStonesToFreezeException extends Exception {
    public String toString() {
        return "Nelze zmrazit tento pocet kamenu, protoze tento pocet kamenu neni k dispozici.";
    }
}

class NotValidCharException extends Exception {
    public String toString() {
        return "Zadany identifikator neni platny";
    }
}

class NotValidMatrixSize extends Exception {
    public String toString() {
        return "Velikost hraci desky neni povolena.\n" +
                "Byla nastavena implicitni velikost desky 8.\n" +
                "--------------------------------------------";
    }
}

class InvalidCoordsInputException extends Exception {
    @Override
    public String toString() {
        return "Tuto kombinace znaku nelze zadat.Zkuste to prosim znovu.";
    }
}

class ReadingFromConsoleFailureException extends Exception {
    @Override
    public String toString() {
        return "Doslo k chybe cteni z konzole, zkuste to prosim znovu.";
    }
}

class InvalidTokenInputException extends Exception {
    @Override
    public String toString() {
        return "Byl zadany neznamy token, zkuste to prosim znovu.";
    }
}

class BadTokenArgumentException extends Exception {
    @Override
    public String toString() {
        return "Byl zadany neznamy argument tokenu nebo pocet techto tokenu.";
    }
}

class NoMoreMovesToUndoException extends Exception {
    @Override
    public String toString() {
        return "Nejsou k dispozici zadne dalsi tahy k vraceni.";
    }
}

class GameSavingFailureException extends Exception {
    @Override
    public String toString() {
        return "Pri ukladani hry se vyskytla chyba. Hra nebyla ulozena.";
    }
}

class GameLoadingFailureException extends Exception {
    @Override
    public String toString() {
        return "Pri nahravani hry se vyskytla chyba.";
    }
}

class GameLoadingNameNotFoundException extends Exception {
    @Override
    public String toString() {
        return "Jmeno hry k nacteni nebylo nalezeno.";
    }
}

class FieldIsNotEmptyException extends Exception {
    @Override
    public String toString() {
        return "Policko je jiz obsazeno a nelze na nej umistit kamen.";
    }
}

class MoveNotAvailableException extends Exception {
    @Override
    public String toString() {
        return "Tento tah neni pristupny. Opakujte prosim tah.";
    }
}

class GameEndedException extends Exception {
    @Override
    public String toString() {
        return "Hra skoncila.";
    }
}

class NoMovesAvailableException extends Exception {
    @Override
    public String toString() {
        return "Neni k dispozici zadny tah.";
    }
}