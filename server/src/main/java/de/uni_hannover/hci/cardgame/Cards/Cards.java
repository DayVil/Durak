package de.uni_hannover.hci.cardgame.Cards;

public class Cards
{
    /**
     *
     * @param Nr The Nr (index in the textures Array) of a Card
     *
     * @return One of the four Card-Colors or Undefined if the function is not called correctly
     *
     * */
    public static CardColor getColor(int Nr)
    {
        // first real card is two_of_clubs (index 11),
        // so club is index 11 till index 23 and always gives 0
        // ((11 - 11) / 13) == 0 and ((23 - 11) / 13) == 0
        // next color starts at index 24 and gives 1 und so on
        // club == 0
        // disamonds == 1
        // hearts == 2
        // spades == 3

        int cardColor = (Nr - 11) / 13;

        if (cardColor == 0)    return CardColor.CLUB;
        if (cardColor == 1)    return CardColor.DIAMONDS;
        if (cardColor == 2)    return CardColor.HEARTS;
        if (cardColor == 3)    return CardColor.SPADES;

        return (CardColor.UNDEFINED_COLOR);
    }

    /**
     *
     * @param Nr The Nr (index in the textures Array) of a Card
     * @return The card Number in increasing order ignoring the color, so ace of clubs and ace of hearts will both return 12
     *  If the method is not called correctly (card number lower than 11 or higher than 63) -1 will be returned
     */
    public static int getCard(int Nr)
    {
        if (Nr < 11 || Nr > 63) return (-1);

        return ((Nr - 11) % 13);
    }

    /**
     * Gets the the corresponding value of the card
     * @param nr The Nr (index in the textures Array) of a Card
     * @return Corresponding value.
     */
    public static int getCardValue(int nr)
    {
        int val = getCard(nr);

        if (val <= 11)
        {
            return val + 2;
        } else
        {
            return 1;
        }
    }

    /**
     *
     * @param Trump The Trump color
     * @param Nr1   The Nr (index in the textures Array) of the first Card
     * @param Nr2   The Nr (index in the textures Array) of the second Card
     * @return  1 if Card1 will beat Nr2, -1 if Card2 will beat Card 1, 0 if both cards are equal (this should not happen)
     */
    public static int compareCards(CardColor Trump, int Nr1, int Nr2)
    {
        CardColor cardColorNr1 = getColor(Nr1);
        CardColor cardColorNr2 = getColor(Nr2);

        if (cardColorNr1 == Trump && cardColorNr2 != Trump) return (1);
        if (cardColorNr1 != Trump && cardColorNr2 == Trump) return (-1);
        if (Nr1 > Nr2) return (1);
        if (Nr1 < Nr2) return (-1);

        return (0);
    }
}
