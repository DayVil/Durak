package de.uni_hannover.hci.cardgame.gameLogic.Cards;

public class Cards
{
    private static final String[] textures =
    {
        // 00
        "/textures/cards/clubs/club_symbol.png",
        "/textures/cards/clubs/club_blank_card.png",

        // 02
        "/textures/cards/diamonds/diamond_symbol.png",
        "/textures/cards/diamonds/diamond_blank_card.png",

        // 04
        "/textures/cards/hearts/heart_symbol.png",
        "/textures/cards/hearts/heart_blank_card.png",

        // 06
        "/textures/cards/spades/spade_symbol.png",
        "/textures/cards/spades/spade_blank_card.png",

        // 08
        "/textures/cards/card_back_highsat.png",
        "/textures/cards/card_back_lowsat.png",
        "/textures/cards/card_blanc.png",

        // 11
        "/textures/cards/clubs/two_of_clubs.png",
        "/textures/cards/clubs/three_of_clubs.png",
        "/textures/cards/clubs/four_of_clubs.png",
        "/textures/cards/clubs/five_of_clubs.png",
        "/textures/cards/clubs/six_of_clubs.png",
        "/textures/cards/clubs/seven_of_clubs.png",
        "/textures/cards/clubs/eight_of_clubs.png",
        "/textures/cards/clubs/nine_of_clubs.png",
        "/textures/cards/clubs/ten_of_clubs.png",
        "/textures/cards/clubs/jack_of_clubs.png",
        "/textures/cards/clubs/queen_of_clubs.png",
        "/textures/cards/clubs/king_of_clubs.png",
        "/textures/cards/clubs/ace_of_clubs.png",

        // 24
        "/textures/cards/diamonds/two_of_diamonds.png",
        "/textures/cards/diamonds/three_of_diamonds.png",
        "/textures/cards/diamonds/four_of_diamonds.png",
        "/textures/cards/diamonds/five_of_diamonds.png",
        "/textures/cards/diamonds/six_of_diamonds.png",
        "/textures/cards/diamonds/seven_o_diamonds.png",
        "/textures/cards/diamonds/eight_of_diamonds.png",
        "/textures/cards/diamonds/nine_of_diamonds.png",
        "/textures/cards/diamonds/ten_of_diamonds.png",
        "/textures/cards/diamonds/jack_of_diamonds.png",
        "/textures/cards/diamonds/queen_of_diamonds.png",
        "/textures/cards/diamonds/king_of_diamonds.png",
        "/textures/cards/diamonds/ace_of_diamonds.png",

        // 37
        "/textures/cards/hearts/two_of_hearts.png",
        "/textures/cards/hearts/three_of_hearts.png",
        "/textures/cards/hearts/four_of_hearts.png",
        "/textures/cards/hearts/five_of_hearts.png",
        "/textures/cards/hearts/six_of_hearts.png",
        "/textures/cards/hearts/seven_of_hearts.png",
        "/textures/cards/hearts/eight_of_hearts.png",
        "/textures/cards/hearts/nine_of_hearts.png",
        "/textures/cards/hearts/ten_of_hearts.png",
        "/textures/cards/hearts/jack_of_hearts.png",
        "/textures/cards/hearts/queen_of_hearts.png",
        "/textures/cards/hearts/king_of_hearts.png",
        "/textures/cards/hearts/ace_of_hearts.png",

        // 50
        "/textures/cards/spades/two_of_spades.png",
        "/textures/cards/spades/three_of_spades.png",
        "/textures/cards/spades/four_of_spades.png",
        "/textures/cards/spades/five_of_spades.png",
        "/textures/cards/spades/six_of_spades.png",
        "/textures/cards/spades/seven_of_spades.png",
        "/textures/cards/spades/eight_of_spades.png",
        "/textures/cards/spades/nine_of_spades.png",
        "/textures/cards/spades/ten_of_spades.png",
        "/textures/cards/spades/jack_of_spades.png",
        "/textures/cards/spades/queen_of_spades.png",
        "/textures/cards/spades/king_of_spades.png",
        "/textures/cards/spades/ace_of_spades.png",
    };

    public static String getCardTexture(int Nr)
    {
        return (textures[Nr]);
    }

    public static String getSymbolTexture(CardColor color)
    {
        if(color == CardColor.Clubs) return getSpecialTexture(SpecialTexture.ClubsSymbol);
        if(color == CardColor.Diamonds) return getSpecialTexture(SpecialTexture.DiamondsSymbol);
        if(color == CardColor.Hearts) return getSpecialTexture(SpecialTexture.HeartsSymbol);
        if(color == CardColor.Spades) return getSpecialTexture(SpecialTexture.SpadesSymbol);
        return null;
    }

    public static String getSpecialTexture(SpecialTexture specialTexture)
    {
        if(specialTexture == SpecialTexture.ClubsSymbol) return "/textures/cards/clubs/club_symbol.png";
        if(specialTexture == SpecialTexture.ClubsBlankCard) return "/textures/cards/clubs/club_blank_card.png";
        if(specialTexture == SpecialTexture.DiamondsSymbol) return "/textures/cards/diamonds/diamond_symbol.png";
        if(specialTexture == SpecialTexture.DiamondsBlankCard) return "/textures/cards/diamonds/diamond_blank_card.png";
        if(specialTexture == SpecialTexture.HeartsSymbol) return "/textures/cards/hearts/heart_symbol.png";
        if(specialTexture == SpecialTexture.HeartsBlankCard) return "/textures/cards/hearts/heart_blank_card.png";
        if(specialTexture == SpecialTexture.SpadesSymbol) return "/textures/cards/spades/spade_symbol.png";
        if(specialTexture == SpecialTexture.SpadesBlankCard) return "/textures/cards/spades/spade_blank_card.png";
        if(specialTexture == SpecialTexture.BackHighsat) return "/textures/cards/card_back_highsat.png";
        if(specialTexture == SpecialTexture.BackLowsat) return "/textures/cards/card_back_lowsat.png";
        if(specialTexture == SpecialTexture.BlancCard) return "/textures/cards/card_blanc.png";
        return null;
    }


    /**
     * Used to convert the servers Color ID into a usable SpecialTexture Enum
     *
     * @param id the id of the Color used by the server to distinguish card colors
     * @return The enum corresponding to a SpecialTexture
     */
    public static CardColor intToCardColor(int id)
    {
        if(id == 0) return CardColor.Clubs;
        if(id == 1) return CardColor.Diamonds;
        if(id == 2) return CardColor.Hearts;
        if(id == 3) return CardColor.Spades;
        return null;
    }

}
